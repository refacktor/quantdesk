package com.zigabyte.stock.correlation;

public class DoubleDataPoint
{
	// id of record - used during searching first and last date
	private int theId;
	// date on which these trades were made
	private String theDate;
	// change in percent from previous day
	private Double theChangeFromLastClose1;
	private Double theChangeFromLastClose2;
	
	// difference between last change and mean
	private double difference1;
	private double difference2;
	
	
	public DoubleDataPoint( String aDate )
	{
		theId		= 0;
		theDate = aDate;
		theChangeFromLastClose1 = null;
		theChangeFromLastClose2 = null;
		
		difference1	= 0;
		difference2 = 0;
	}

	/**
	 * @return Returns true if both changeFromLastClose1 and changeFromLastClose2 are non-null
	 */
	public boolean isFull()
	{
		return theChangeFromLastClose1 != null && theChangeFromLastClose2 != null;
	}
	
	/**
	 * @return Returns the changeFromLastClose1.
	 */
	public double getChangeFromLastClose1() {
		return theChangeFromLastClose1.doubleValue();
	}
	/**
	 * @param aChangeFromLastClose1 The changeFromLastClose1 to set.
	 */
	public void setChangeFromLastClose1(double aChangeFromLastClose1) {
		this.theChangeFromLastClose1 = new Double( aChangeFromLastClose1 );
	}
	/**
	 * @return Returns the changeFromLastClose2.
	 */
	public double getChangeFromLastClose2() {
		return theChangeFromLastClose2.doubleValue();
	}
	/**
	 * @param aChangeFromLastClose2 The changeFromLastClose2 to set.
	 */
	public void setChangeFromLastClose2(double aChangeFromLastClose2) {
		this.theChangeFromLastClose2 = new Double( aChangeFromLastClose2 );
	}
	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return theDate;
	}
	/**
	 * @param aDate The date to set.
	 */
	public void setDate(String aDate) {
		this.theDate = aDate;
	}
	
	public void setDifference1( double average )
	{
		difference1 = theChangeFromLastClose1.doubleValue() - average;
	}
	
	public void setDifference2( double average )
	{
		difference2 = theChangeFromLastClose2.doubleValue() - average;
	}
	
	public double getSquaredDifference1()
	{
		return difference1*difference1;
	}

	public double getSquaredDifference2()
	{
		return difference2*difference2;
	}
	
	public double getDifferenceRatio()
	{
		return difference1 * difference2;
	}
	
	public int getId()
	{
		return theId;
	}
	
	public void setId( int anId )
	{
		theId = anId;
	}

}
