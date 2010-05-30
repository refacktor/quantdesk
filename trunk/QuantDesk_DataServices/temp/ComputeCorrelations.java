package com.zigabyte.stock.correlation;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

public class ComputeCorrelations
{
	Connection con;
	Statement stmt;
	Properties props;

	public ComputeCorrelations()
	{
		con		= null;
		stmt	= null;
		props	= new Properties();
		try
		{
			props.load( new FileInputStream( "config.properties" ) );
		}
		catch( Exception e )
		{
			System.out.println( "Import: " + e.getClass() + " - " + e.getMessage() );
		}
	}
	
	private void connectDB()
	{
		try
		{
			Class.forName( "com.mysql.jdbc.Driver" );
			String host		= props.getProperty( "db_host" );
			String login	= props.getProperty( "db_login" );
			String password	= props.getProperty( "db_password" );
			String name		= props.getProperty( "db_name" );
			con = DriverManager.getConnection("jdbc:mysql://"+host+"/"+name+"?user="+login+"&password="+password);
			stmt	= con.createStatement();
		}
		catch( Exception e )
		{
			System.err.println( "connectDB: " + e.getClass() + " - " + e.getMessage() );
		}
	}
	
	private void disconnectDB()
	{
		try
		{
			if( con != null && !con.isClosed() )
			{
				stmt.close();
				con.close();
			}
		}
		catch( Exception e )
		{
			System.err.println( "disconnectDB: " + e.getClass() + " - " + e.getMessage() );
		}
	}

	public static void main( String args[] )
	{
		ComputeCorrelations cc	= new ComputeCorrelations();
		cc.compute();	
	}
	
	public void compute()
	{
		int min_stock	= 0;
		int max_stock	= 0;

		connectDB();

		try
		{
			deleteCorrelations();
			min_stock	= getMinStock();
			max_stock	= getMaxStock();
			for( int i=min_stock; i<max_stock; i++ )
			{
				computeCorrelation( i );
			}
		}
		catch( Exception e )
		{
			System.err.println("compute: " + e.getClass() + " - " + e.getMessage() );
			e.printStackTrace();
		}
		
		disconnectDB();
	}	
	
	private int getMinStock()
	{
		ResultSet rs;
		int min_stock	= 0;
		try
		{
			rs	= stmt.executeQuery( "SELECT MIN(id) as min_id FROM stocks" );
			if( rs.next() )
			{
				min_stock	= rs.getInt( "min_id" );
			}
			rs.close();
		}
		catch( Exception e )
		{
			System.err.println( "getMinStock: "+ e.getClass() + " - " + e.getMessage() );
		}
		return min_stock;
	}
	
	private int getMaxStock()
	{
		ResultSet rs;
		int max_stock	= 0;
		try
		{
			rs	= stmt.executeQuery( "SELECT MAX(id) as max_id FROM stocks" );
			if( rs.next() )
			{
				max_stock	= rs.getInt( "max_id" );
			}
			rs.close();
		}
		catch( Exception e )
		{
			System.err.println( "getMaxStock: "+ e.getClass() + " - " + e.getMessage() );
		}
		return max_stock;
	}


	private int computeCorrelation( int stock1 )
	{
		ResultSet rs; 
		DoubleDataPoint ddp;
		Vector<DoubleDataPoint> quotes		= null;
		HashMap<String,Vector<DoubleDataPoint>> super_quotes	= new HashMap<String,Vector<DoubleDataPoint>>();
		double correlation;
		int id	= 0;
		int currentId	= 0;
		String idStr	= "";
		StringBuffer query	= new StringBuffer( "INSERT INTO correlations (id_stock_1,id_stock_2,correlation) VALUES" );
		try
		{
			rs	= stmt.executeQuery( "SELECT sq2.id_stock as id2, sq1.last_change as last1, sq2.last_change as last2, q.date as date " +
					"FROM stock_quotations sq1, stock_quotations sq2, quotations q " +
					"WHERE sq1.id_stock="+stock1+" AND sq1.id_stock<sq2.id_stock AND " +
					"q.id=sq1.id_quotation AND q.id=sq2.id_quotation ORDER BY sq2.id_stock" );
			while( rs.next() )
			{
				currentId	= rs.getInt( "id2" );
				if( id != currentId )
				{
					if( id > 0 )
					{
						super_quotes.put( id+"", quotes );
					}
					id	= currentId;
					quotes	= new Vector<DoubleDataPoint>();
				}
				ddp	= new DoubleDataPoint( rs.getString( "date" ) );
				ddp.setChangeFromLastClose1( rs.getDouble( "last1" ) );
				ddp.setChangeFromLastClose2( rs.getDouble( "last2" ) );
				quotes.add( ddp );
			}
			rs.close();
			if( id != 0 )
			{
				super_quotes.put( id+"", quotes );	
			}
			Iterator iter	= super_quotes.keySet().iterator();
			while( iter.hasNext() )
			{
				idStr	= (String)iter.next();
				quotes	= (Vector<DoubleDataPoint>)super_quotes.get( idStr );
				correlation	= count( quotes );				
				query.append( "("+stock1+","+idStr+","+correlation+")," );
			}
			if( id != 0 )
			{
				stmt.executeUpdate( query.toString().substring( 0, query.length() - 1 ) );	
			}

			return super_quotes.size();
		}
		catch( Exception e )
		{
			System.err.println( "computeCorrelation: "+ e.getClass() + " - " + e.getMessage() );
		}
		return 0;
	}
	
	public double count( Vector changes )
	{
		try
		{
			if( changes.size() == 0 )
			{
				System.err.println( "There are no correct dataPoints" );
				return -2;
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
			
			// counting observations
			// counting means
			// setting first and last date
			Iterator iter	= changes.iterator();
			while( iter.hasNext() )
			{
				ddp	= (DoubleDataPoint)iter.next();
				if( ddp.isFull() )
				{
					observations++;
					mean1 += ddp.getChangeFromLastClose1();
					mean2 += ddp.getChangeFromLastClose2();
				}
			}

			// counting correlation
			if( observations > 0 )
			{
				mean1	= mean1 / observations;	
				mean2	= mean2 / observations;
				
				// counting difference between changes and means
				iter	= changes.iterator();
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
				iter	= changes.iterator();
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
				
				if( squareSum1 == 0 || squareSum2 == 0 )
				{
					return -3;
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

				return correlation;
				//System.out.println( "Date range: " + startDate + " - " + endDate );
				//System.out.println( "correlation: " + correlation );
			}
		}
		catch( Exception e )
		{
			System.err.println( e.getClass() + ": " + e.getMessage() );
		}
		return -3;
	}
	
	private void deleteCorrelations()
	{
		try
		{
			stmt.executeUpdate( "DELETE FROM correlations" );
		}
		catch( Exception e )
		{
			System.err.println( "deleteCorrelations: " + e.getClass() + ": " + e.getMessage() );
		}
	}
}
