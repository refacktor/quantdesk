package jmirror;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DB {
    
    private Connection dBConn = null;

    public DB(String properties) throws ClassNotFoundException, FileNotFoundException,
            IOException, SQLException {
        // read application params from properties.txt
        Properties myProperties = new Properties();
        FileInputStream fis = new FileInputStream(properties);
        myProperties.load(fis);
        fis.close();
        String jdbcDriver = myProperties.getProperty("JDBC_DRIVER");
        String jdbcURL = myProperties.getProperty("JDBC_URL");
        String jdbcUser = myProperties.getProperty("JDBC_USER");
        String jdbcPwd = myProperties.getProperty("JDBC_PASSWORD");
        Class.forName(jdbcDriver);
        System.out.println("DB URL: " + jdbcURL);
        dBConn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPwd);
    }

    // check the time of the index file
    public boolean indexFileChanged(String path, String name, Date ftpDate) throws
            SQLException {
        boolean changed = true;
        // connect to the DB
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT FTPDate FROM edgar_index " +
                     "WHERE Path = '" + path + "' AND Name = '" + name + "'";
        stmt = dBConn.createStatement();
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            Timestamp tsFromDB = rs.getTimestamp("FTPDate");
            if (tsFromDB != null) {
                long timeFromDB = tsFromDB.getTime();
                long timeFromFTP = ftpDate.getTime();
                if (timeFromDB == timeFromFTP)
                    changed = false;
            }
        }

        return changed;
    }

    // check existence of the index file
    public boolean indexFileFound(String path, String name) throws SQLException {
        boolean found = false;

        // connect to the DB
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT Id FROM edgar_index " +
                     "WHERE Path = '" + path + "' AND Name = '" + name + "'";
        stmt = dBConn.createStatement();
        rs = stmt.executeQuery(sql);
        if (rs.next())
            found = true;

        return found;
    }

    // add record about index file
    public void addIndexFile(String path, String name) throws SQLException {

        // connect to the DB
        PreparedStatement psb = null;

        String insSQL = "INSERT INTO edgar_index (Path, Name) " +
                        " VALUES (?, ?)";
        psb = dBConn.prepareStatement(insSQL);
        psb.setString(1, path);
        psb.setString(2, name);
        psb.execute();

    }

    // add record about index file
    public int getIndexFileId(String path, String name) throws SQLException {

        // connect to the DB
        Statement stmt = null;
        ResultSet rs = null;
        int id = 0;

        String sql = "SELECT Id FROM edgar_index " +
                     "WHERE Path = '" + path + "' AND Name = '" + name + "'";
        stmt = dBConn.createStatement();
        rs = stmt.executeQuery(sql);
        if (rs.next())
            id = rs.getInt("Id");

        return id;
    }

    // update FTP time of index file
    public void updateIndexFile(String path, String name, Date dateFiled,
                                Date ftpDate) throws SQLException {

        // connect to the DB
        PreparedStatement psb = null;

        String insSQL = "UPDATE edgar_index " +
                        " SET DateFiled = ?, FTPDate = ?, DownloadedDate = ? " +
                        " WHERE Path = ? AND Name = ?";
        psb = dBConn.prepareStatement(insSQL);
        psb.setTimestamp(1, new Timestamp(dateFiled.getTime()));
        psb.setTimestamp(2, new Timestamp(ftpDate.getTime()));
        psb.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        psb.setString(4, path);
        psb.setString(5, name);
        psb.execute();

    }

    // update FTP time and other info of data file
    public void updateDataFile(DataFile df) throws SQLException {
    	
        // connect to the DB
        PreparedStatement psb = null;

        String insSQL = "UPDATE edgar_data " +
                        " SET FTPDate = ?, DownloadedDate = ?, Symbol = ?, FileType = ?, FileSize = ? " +
                        " WHERE FileName = ?";
        psb = dBConn.prepareStatement(insSQL);
        psb.setTimestamp(1, new Timestamp(df.ftpDate.getTime()));
        psb.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        psb.setString(3, df.symbol);
        psb.setString(4, df.type);
        psb.setInt(5, df.fileLen);        
        psb.setString(6, df.fullFileName);

        psb.execute();

        System.out.println(df.fullFileName + "\t symbol:" + df.symbol);

    }

    public void runQuery(String query) throws SQLException {

        // connect to the DB
        PreparedStatement psb = null;

        psb = dBConn.prepareStatement(query);
        psb.execute();

        // closing the JDBC resources
        try {
            psb.close();
        } catch (Exception ex1) {}
    }

    // get the list of not downloaded text files
    public Vector getTextFiles(String fieldsFilter, int minYear, int maxYear) throws SQLException {
        // generate query
        String[] fields = fieldsFilter.split(",");
        String query = "SELECT DISTINCT FileName FROM edgar_data,symbols WHERE FormType IN (";
        for (int i = 0; i < fields.length; i++) {
            query += "'" + fields[i].trim() + "', ";
        }
        query = query.substring(0, query.length() - 2);
        query += ") AND (DateFiled BETWEEN CAST('" + minYear + "-01-01' AS DATETIME) " +
                "AND CAST('" + (maxYear + 1) + "-01-01' AS DATETIME)) " +
                "AND DownloadedDate IS NULL AND edgar_data.cik = symbols.cik";

        Vector files = new Vector();

        // connect to the DB
        Statement stmt = null;
        ResultSet rs = null;

        String sql = query;
        stmt = dBConn.createStatement();
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String st = rs.getString(1);
            files.add(st);
        }

        return files;
    }

    // Close DB connection
    public void close() {
        try {
            dBConn.close();
        } catch (Exception ex1) {}
    }

}
