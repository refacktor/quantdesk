package jmirror;

import java.io.FileNotFoundException;
import org.apache.commons.net.ftp.FTP;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import java.io.File;
import java.util.Vector;
import org.apache.commons.net.ftp.FTPFile;

import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipFile;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import org.apache.commons.net.ftp.FTPClient;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.util.Date;
import java.io.BufferedReader;
import java.util.Locale;
import java.text.DateFormat;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;
import java.util.zip.ZipEntry;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.OutputStream;

public class MirrorThread extends Thread {

    private String SERVER;
    private String USERNAME;
    private String PASSWORD;
    private String INDEX_FOLDER;
    private String DATA_FOLDER;
    private String INDEX_DESTINATION_FOLDER;
    private String DATA_DESTINATION_FOLDER;
    private String INDEX_FILE_ZIPPED;
    private String INDEX_FILE;
    private String FORM_TYPE;
    private int MAX_ROWS_PER_QUERY = 1000;
    private int MIN_YEAR = 1993;
    private int MAX_YEAR = 2027;
    private long TIMEOUT = 600000;

    private long lastOperationTime;
    private boolean threadFinished = false;
    private boolean finishedSuccessfully = false;
    public FTPClient ftp;
    public DB db;

    public long getLastOperationTime() {
        return lastOperationTime;
    }

    public long getTimeout() {
        return TIMEOUT;
    }

    public MirrorThread() throws UnknownHostException {
        super();
        lastOperationTime = System.currentTimeMillis();
		propertiesPath = "edgar-"
		            	+ InetAddress.getLocalHost().getHostName().toLowerCase() + ".properties";
    }

    public void run() {
        try {

            readApplicationProperties();

            // Connect and logon to FTP Server
            connectAndLogin();

            // List the years
            lastOperationTime = System.currentTimeMillis();

            ftp.changeWorkingDirectory(INDEX_FOLDER);

            for (int year = MIN_YEAR; year < MAX_YEAR + 1; year++) {
                String yearFolder = year + "";
                lastOperationTime = System.currentTimeMillis();
                if (ftp.changeWorkingDirectory(yearFolder)) {
                    lastOperationTime = System.currentTimeMillis();

                    // List the quarters in the year directory
                    for (int quarter = 1; quarter <= 4; quarter++) {
                        String quarterFolder = "QTR" + quarter;
                        lastOperationTime = System.currentTimeMillis();
                        if (ftp.changeWorkingDirectory(quarterFolder)) {
                            // List zip files in the quarter directory
                            lastOperationTime = System.currentTimeMillis();
                            FTPFile[] files = ftp.listFiles(INDEX_FILE_ZIPPED);
                            for (int i = 0; i < files.length; i++) {
                                Date ftpDate = files[i].getTimestamp().getTime();
                                String ftpPath = INDEX_FOLDER + "/" +
                                                 yearFolder +
                                                 "/" + quarterFolder;
                                String localPath = INDEX_DESTINATION_FOLDER +
                                        File.separator + yearFolder +
                                        File.separator + quarterFolder;

                                // If index file was not found in the DB
                                if (!db.indexFileFound(ftpPath,
                                        files[i].getName())) {

                                    // then copy index file
                                    copyIndexFile(yearFolder, quarterFolder,
                                                  files[i].getName(), ftpDate);

                                    // and add record to the DB
                                    db.addIndexFile(ftpPath, files[i].getName());

                                    // add records to edgar_data table
                                    Date dateFiled = parseIndexFile(localPath,
                                            ftpPath,
                                            files[i].getName(), db);

                                    // and update time in the DB
                                    db.updateIndexFile(ftpPath,
                                            files[i].getName(),
                                            dateFiled,
                                            ftpDate);

                                } else {

                                    // if time of the index file was changed
                                    if (db.indexFileChanged(ftpPath,
                                            files[i].getName(), ftpDate)) {

                                        // then copy index file
                                        copyIndexFile(yearFolder, quarterFolder,
                                                files[i].getName(), ftpDate);

                                        // add records to edgar_data table
                                        Date dateFiled = parseIndexFile(
                                                localPath,
                                                ftpPath,
                                                files[i].getName(), db);

                                        // and update time in the DB
                                        db.updateIndexFile(ftpPath,
                                                files[i].getName(), dateFiled,
                                                ftpDate);

                                    } else
                                        System.out.println(ftpPath + "/" +
                                                files[i].getName() +
                                                "\t not changed");
                                }
                            }
                            try {
                                ftp.changeToParentDirectory();
                                // the index file parsing process may be too long,
                                // therefore reconnect
                            } catch (SocketTimeoutException e) {
                                connectAndLogin();
                                ftp.changeWorkingDirectory(INDEX_FOLDER + "/" +
                                        yearFolder);
                            } catch (SocketException se) {
                                connectAndLogin();
                                ftp.changeWorkingDirectory(INDEX_FOLDER + "/" +
                                        yearFolder);
                            } catch (FTPConnectionClosedException fcce) {
                                connectAndLogin();
                                ftp.changeWorkingDirectory(INDEX_FOLDER + "/" +
                                        yearFolder);
                            }
                        }
                    }
                    ftp.changeToParentDirectory();
                }
            }

            // analize the fields filter and download txt files
            downloadTexts(db);
            finishedSuccessfully = true;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // Logout from the FTP Server and disconnect, close DB connection
            exit();
        }
        threadFinished = true;

    }

    public boolean threadFinished() {
        return threadFinished;
    }

    public boolean finishedSuccessfully() {
        return finishedSuccessfully;
    }

    // Logout from the FTP Server and disconnect, close DB connection
    public void exit() {

        // Logout from the FTP Server and disconnect
        try {
            ftp.logout();
        } catch (Exception ex) {}
        try {
            ftp.disconnect();
        } catch (Exception ex) {}

        // Close DB connection
        try {
            db.close();
        } catch (Exception ex) {}

    }

    // Connect and logon to FTP Server
    private void connectAndLogin() throws SocketException, IOException,
            ClassNotFoundException, SQLException, FileNotFoundException {
        lastOperationTime = System.currentTimeMillis();
        exit();

        ftp = new FTPClient();
        ftp.connect(SERVER);
        ftp.login(USERNAME, PASSWORD);
        ftp.enterLocalPassiveMode();
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        System.out.println("Connected to " + SERVER + ".");

        // create object for DB access
        db = new DB(propertiesPath);
    }

    // check folder or create new folder
    private void checkDir(String folder) {
        File dir = new File(folder);
        if (!dir.exists()) {
            String[] folders;
            try {
                folders = folder.split(File.separator);
            } catch (PatternSyntaxException pse) {
                folders = folder.split(File.separator + File.separator);
            }
            String parentFolders = "";
            for (int i = 0; i < folders.length; i++) {
                parentFolders += folders[i] + File.separator;
                File parentDir = new File(parentFolders);
                if (!parentDir.exists()) {
                    parentDir.mkdir();
                }
            }
        }
    }

    // copy index file
    private void copyIndexFile(String yearFolder, String quarterFolder,
                               String fileName, Date fileDate) throws
            FileNotFoundException, IOException {
        lastOperationTime = System.currentTimeMillis();

        String localPath = INDEX_DESTINATION_FOLDER +
                           File.separator + yearFolder +
                           File.separator + quarterFolder +
                           File.separator;

        File file = new File(localPath + fileName);

        // check local folder or create new folder
        checkDir(localPath);

        FileOutputStream fos = new FileOutputStream(
                file);
        boolean result = ftp.retrieveFile(fileName, fos);
        if (result) {
            file.setLastModified(fileDate.getTime());
            String ftpPath = INDEX_FOLDER + "/" +
                             yearFolder + "/" +
                             quarterFolder;
            System.out.println(ftpPath + "/" + fileName + "\t copied");
        }
        else
            System.out.println(fileName + "\t not copied");
        fos.close();
    }

    private Date parseIndexFile(String localPath, String ftpPath,
                                String fileName, DB db) throws IOException,
            SQLException {
        Date date = new Date();
        //long time = System.currentTimeMillis();

        // open zip file
        ZipFile zip = new ZipFile(localPath + File.separator + fileName);
        ZipEntry entry = zip.getEntry(INDEX_FILE);
        if (entry != null) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    zip.getInputStream(entry)));

            // generate query
            String startOfQuery = "INSERT IGNORE INTO edgar_data (IndexFileId, CompanyName, FormType, CIK, DateFiled, FileName) VALUES ";
            StringBuffer query = new StringBuffer(startOfQuery);
            boolean started = false;
            String st;
            int indexFileId = db.getIndexFileId(ftpPath, fileName);
            int queryRow = 0;
            while ((st = in.readLine()) != null) {
                if (started) {
                    String[] stArray = st.split("\\|");
                    if (stArray[0].equals(""))
                        stArray[0] = "0";
                    stArray[1] = stArray[1].replaceAll("\\\\", "\\\\\\\\");
                    stArray[1] = stArray[1].replaceAll("'", "\\\\'");
                    if (stArray[1].length() > 60)
                        stArray[1] = stArray[1].substring(0, 60);
                    String q = "(" + indexFileId + ", '" +
                               stArray[1] + "', '" + // Company Name
                               stArray[2] + "', " + // Form Type
                               stArray[0] + ", '" + // CIK
                               stArray[3] + "', '" + // Date Filed
                               stArray[4] + "'), "; // File Name

                    query.append(q);
                    queryRow++;

                    // if query is too long then run query and start generate new query
                    if (queryRow == MAX_ROWS_PER_QUERY) {
                        query = query.delete(query.length() - 2, query.length());
                        lastOperationTime = System.currentTimeMillis();
                        db.runQuery(new String(query));
                        query = new StringBuffer(startOfQuery);
                        queryRow = 0;
                    }
                } else {
                    if (st.indexOf("------") == 0)
                        started = true;
                    String lblIndexFiled = "Last Data Received:";
                    if (st.indexOf(lblIndexFiled) == 0) {
                        st = st.substring(lblIndexFiled.length()).trim();
                        try {
                            Locale locale = Locale.ENGLISH;
                            DateFormat formatter = new SimpleDateFormat(
                                    "MMM dd, yyyy", locale);
                            date = (Date) formatter.parse(st);
                        } catch (ParseException e) {}
                    }
                }
            }
            String queryString = new String(query);
            if (!queryString.equals(startOfQuery)) {
                queryString = queryString.substring(0, queryString.length() - 2);
                lastOperationTime = System.currentTimeMillis();
                db.runQuery(queryString);
            }
        }
        //System.out.println(System.currentTimeMillis() - time);
        return date;
    }

    // analize forms filter and download txt files
    private void downloadTexts(DB db) throws IOException, SQLException,
            ClassNotFoundException, SocketException {

        // get the list of appropriate text files
        lastOperationTime = System.currentTimeMillis();

        Vector fileNames = db.getTextFiles(FORM_TYPE, MIN_YEAR, MAX_YEAR);
        
        System.out.println("Remaining to Download: " + fileNames.size());

        try {
            lastOperationTime = System.currentTimeMillis();
            ftp.changeWorkingDirectory("/");
            // query may be long, therefore reconnect
        } catch (SocketTimeoutException e) {
            connectAndLogin();
            ftp.changeWorkingDirectory("/");
        } catch (SocketException se) {
            connectAndLogin();
            ftp.changeWorkingDirectory("/");
        } catch (FTPConnectionClosedException fcce) {
            connectAndLogin();
            ftp.changeWorkingDirectory("/");
        }

        Iterator it = fileNames.iterator();
        while (it.hasNext()) {
        	DataFile df = new DataFile();
        	
            df.fullFileName = (String) it.next();
            df.ftpPath = df.fullFileName.substring(DATA_FOLDER.length() + 1,
                    df.fullFileName.lastIndexOf("/"));
            df.fileName = df.fullFileName.substring(df.fullFileName.lastIndexOf(
                    "/") + 1, df.fullFileName.length());

            FTPFile[] ftpFiles = ftp.listFiles(df.fullFileName);
            if(ftpFiles.length == 1) {
            	df.ftpDate = ftpFiles[0].getTimestamp().getTime();
            	// copy data file
            	copyDataFile(df);

            	// update time & other info in the DB
            	db.updateDataFile(df);
            }
            else {
            	System.out.println(df.fullFileName + "\t not found (" + df.fileName + " in " + df.ftpPath);
            }
        }
    }
    
    static final NumberFormat nf = new DecimalFormat("#,###");
	private String propertiesPath;

    // copy data file
    private void copyDataFile(DataFile df) throws
            FileNotFoundException, IOException {
    	
        lastOperationTime = System.currentTimeMillis();

        String localPath = DATA_DESTINATION_FOLDER + File.separator + df.ftpPath +
                           File.separator;

        // check local folder or create new folder
        checkDir(localPath);

        File gzFile = new File(localPath + df.fileName + ".gz");
        
        InputStream stO =
        	new BufferedInputStream(
        			ftp.retrieveFileStream(df.fullFileName),
        			ftp.getBufferSize());

    	OutputStream stD = new GZIPOutputStream(new FileOutputStream(gzFile), 16 * 1024);

        org.apache.commons.net.io.Util.copyStream(
        		stO,
        		stD,
        		ftp.getBufferSize(),
        		org.apache.commons.net.io.CopyStreamEvent.UNKNOWN_STREAM_SIZE,
        		new org.apache.commons.net.io.CopyStreamAdapter() {
        			public void bytesTransferred(long totalBytesTransferred,
        					int bytesTransferred,
        					long streamSize) {
        				lastOperationTime = System.currentTimeMillis();
        			}
        		});
        stO.close();
        stD.close();
        ftp.completePendingCommand();

    	gzFile.setLastModified(df.ftpDate.getTime());
    	
    	// re-reading the compressed file is CPU-intensive but saves memory
    	InputStream gzIn = new GZIPInputStream(new FileInputStream(gzFile));
    	BufferedReader r = new BufferedReader(new InputStreamReader(gzIn));
    	String s;
    	int len=0;
    	
    	while((s=r.readLine()) != null) {
    		
    		len += s.length()+1;
    		
    		if(df.symbol == null) {
            	Matcher re = DataFile.symbolPattern.matcher(s);            	
    			if(re.find()) {
    				df.symbol = re.group(1);
    			}
        	}

        	if(df.type == null && s.indexOf("<HTML>") >= 0 || s.indexOf("<html>") >= 0) {
        		df.type = "H";
        	}
        	else {
        		df.type = "T";
        	}
    		
    	}
    	r.close();
    	df.fileLen = len;
    	
    }

    // read application params from properties.txt
    private void readApplicationProperties() {
        Properties myProperties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(propertiesPath);
            myProperties.load(fis);
            fis.close();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
        SERVER = myProperties.getProperty("SERVER");
        USERNAME = myProperties.getProperty("USERNAME");
        PASSWORD = myProperties.getProperty("PASSWORD");
        INDEX_FOLDER = myProperties.getProperty("INDEX_FOLDER");
        DATA_FOLDER = myProperties.getProperty("DATA_FOLDER");
        INDEX_DESTINATION_FOLDER = myProperties.getProperty(
                "INDEX_DESTINATION_FOLDER");
        DATA_DESTINATION_FOLDER = myProperties.getProperty(
                "DATA_DESTINATION_FOLDER");
        INDEX_FILE_ZIPPED = myProperties.getProperty("INDEX_FILE_ZIPPED");
        INDEX_FILE = myProperties.getProperty("INDEX_FILE");
        FORM_TYPE = myProperties.getProperty("FORM_TYPE");
        MAX_ROWS_PER_QUERY = Integer.parseInt(myProperties.getProperty(
                "MAX_ROWS_PER_QUERY"));
        TIMEOUT = Integer.parseInt(myProperties.getProperty("TIMEOUT")) * 1000;
        MIN_YEAR = Integer.parseInt(myProperties.getProperty("MIN_YEAR"));
        MAX_YEAR = Integer.parseInt(myProperties.getProperty("MAX_YEAR"));
    }

}
