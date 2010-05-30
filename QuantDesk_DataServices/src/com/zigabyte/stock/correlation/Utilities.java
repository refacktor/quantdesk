package com.zigabyte.stock.correlation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utilities
{
	static final public DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd"); 

	static public String getDateString( Date date )
	{
		Calendar cal	= Calendar.getInstance();
		cal.setTime( date );
		int day	= cal.get( Calendar.DAY_OF_MONTH );
		int month = cal.get( Calendar.MONTH ) + 1;
		int year = cal.get( Calendar.YEAR );
		
		return year + "-" + month + "-" + day;
	}
	
	static public Date getDate( String dateStr )
	{
		Date ret	 = null;
		try
		{
			ret	= DATE_FORMAT.parse( dateStr );
		}
		catch( Exception e )
		{
			System.err.println( "getDate: " + e.getClass() + " - " + e.getMessage() );
			return null;
		}
		return ret;
	}
	

}
