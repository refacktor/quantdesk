package com.zigabyte.stock.correlation;

import java.util.HashMap;
import java.util.Iterator;

import com.strikeiron.www.HistoricalStockQuotesLocator;
import com.strikeiron.www.HistoricalStockQuotesSoap;
import com.strikeiron.www.SIDate;
import com.strikeiron.www.SIHistoricalQuotes;


public class Invoke
{
	SIHistoricalQuotes quotes1;
	SIHistoricalQuotes quotes2;
	HashMap changes;
	
	public Invoke()
	{
		changes	= new HashMap();
	}

	public int getData( String stock1, String stock2, SIDate dateFrom, SIDate dateTo )
	{
		try
		{
			// obtaining stub in order to invoke webservice method
			HistoricalStockQuotesLocator loc	= new HistoricalStockQuotesLocator();
			HistoricalStockQuotesSoap soap	= loc.getHistoricalStockQuotesSoap();
			
			// invoking method for the first stock with given date range
			quotes1 = soap.getQuotesForOneStockOverDateRange( stock1, dateFrom, dateTo );
			// invoking method for the second stock with the same date range
			quotes2 = soap.getQuotesForOneStockOverDateRange( stock2, dateFrom, dateTo );
		}
		catch( Exception e )
		{
			System.err.println( e.getClass() + ": " + e.getMessage() );
			return -1;
		}
		
		
		// creating collection with percent changes of selected stocks
		
		int length1 = quotes1.getQuotes().length;
		int id = 0;
		
		for( int i=0; i<length1; i++ )
		{
			DoubleDataPoint ddp	= new DoubleDataPoint( quotes1.getQuotes()[i].getDate() );
			ddp.setId( ++id );
			ddp.setChangeFromLastClose1( quotes1.getQuotes()[i].getChangeFromLastCloseAdjusted() );
			changes.put( ddp.getDate(), ddp );
		}
		int length2 = quotes2.getQuotes().length;
		for( int i=0; i<length1; i++ )
		{
			DoubleDataPoint ddp	= (DoubleDataPoint)changes.get( quotes1.getQuotes()[i].getDate() );
			if( ddp != null )
			{
				ddp.setChangeFromLastClose2( quotes2.getQuotes()[i].getChangeFromLastCloseAdjusted() );
			}
		}
		return 1;
	}
	
	
	public void count()
	{
		try
		{
			if( changes.size() == 0 )
			{
				System.err.println( "There are no correct dataPoints" );
				return;
			}
			
			double mean1		= 0;
			double mean2		= 0;
			int observations	= 0;
			DoubleDataPoint ddp	= null;
			double squareSum1	= 0;
			double squareSum2	= 0;
			double variance1	= 0;
			double variance2	= 0;
			double stdDeviation1= 0;
			double stdDeviation2= 0;
			double covariance	= 0;
			double correlation	= 0;
			int maxId			= 0;
			int minId			= Integer.MAX_VALUE;
			String startDate	= "";
			String endDate		= "";
			
			// counting observations
			// counting means
			// setting first and last date
			Iterator iter	= changes.values().iterator();
			while( iter.hasNext() )
			{
				ddp	= (DoubleDataPoint)iter.next();
				if( ddp.isFull() )
				{
					observations++;
					mean1 += ddp.getChangeFromLastClose1();
					mean2 += ddp.getChangeFromLastClose2();
					if( ddp.getId() < minId )
					{
						endDate		= ddp.getDate();
						minId		= ddp.getId();
					}
					if( ddp.getId() > maxId )
					{
						startDate	= ddp.getDate();
						maxId		= ddp.getId();
					}
				}
			}

			// counting correlation
			if( observations > 0 )
			{
				mean1	= mean1 / observations;	
				mean2	= mean2 / observations;
				
				// counting difference between changes and means
				iter	= changes.values().iterator();
				while( iter.hasNext() )
				{
					ddp	= (DoubleDataPoint)iter.next();
					if( ddp.isFull() )
					{
						ddp.setDifference1( mean1 );
						ddp.setDifference2( mean2 );
					}
				}
				
				// counting sum of squares and covariance
				iter	= changes.values().iterator();
				while( iter.hasNext() )
				{
					ddp	= (DoubleDataPoint)iter.next();
					if( ddp.isFull() )
					{
						squareSum1 += ddp.getSquaredDifference1();
						squareSum2 += ddp.getSquaredDifference2();
						covariance += ddp.getDifferenceRatio();
					}
				}

				// counting variances
				variance1	= squareSum1 / observations;
				variance2	= squareSum2 / observations;

				// counting standard deviations
				stdDeviation1	= Math.sqrt( variance1 );
				stdDeviation2	= Math.sqrt( variance2 );
				
				// counting covariance and correlation
				covariance		= covariance / observations;
				correlation		= covariance / ( stdDeviation1 * stdDeviation2 );
				
				System.out.println( "Date range: " + startDate + " - " + endDate );
				System.out.println( "correlation: " + correlation );
			}
		}
		catch( Exception e )
		{
			System.err.println( e.getClass() + ": " + e.getMessage() );
		}
	}
	
	public static void main( String args[] )
	{
		if( args.length != 4 )
		{
			System.err.println( "You should set 4 input parameters: stock1 stock2 data_from date_to" );
			return;
		}
		String tokens[] = args[2].split( "-" );
		if( tokens.length != 3 )
		{
			System.err.println( "Bad format date (dateFrom). Correct is: MM-DD-YYYY" );
			return;
		}
		SIDate dateFrom	= new SIDate( tokens[0], tokens[1], tokens[2] );
		tokens = args[3].split( "-" );
		if( tokens.length != 3 )
		{
			System.err.println( "Bad format date (dateTo). Correct is: MM-DD-YYYY" );
			return;
		}
		SIDate dateTo	= new SIDate( tokens[0], tokens[1], tokens[2] );
		
        Invoke invoke = new Invoke();
        
        // reading historic data from webservice
        int ret	= invoke.getData( args[0], args[1], dateFrom, dateTo );
        if( ret == -1 )
        {
        	return;
        }
        // counting correlation
        invoke.count();
	}

}
