package jmirror;

import java.util.Date;
import java.util.regex.Pattern;

public class DataFile {

	static final Pattern symbolPattern = 
		Pattern.compile("symbol[^A-Z]*([A-Z]{1,5})([^A-Z])");

	String ftpPath;
	String fileName;
	String fullFileName;
	Date ftpDate;
	String symbol;
	int fileLen;
	String type;
	
}
