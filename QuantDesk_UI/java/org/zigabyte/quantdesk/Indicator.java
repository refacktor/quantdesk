package org.zigabyte.quantdesk;


public interface Indicator {
	public enum NumericType {
		MAX,
		MIN,
		MACD,
		MA
	}
	
	public enum ComparisonType {
		GREATER,
		LESSER,
		EQUAL
	}
	
	public enum LogicalType {
		AND,
		OR
	}
	
}
