package org.zigabyte.quantdesk;
import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.StockHistoryServer;


public class ComparisonIndicator implements Indicator {
	private ComparisonType type;
	private NumericIndicator val1;
	private NumericIndicator val2;
	
	public ComparisonIndicator(ComparisonType type, NumericIndicator val1, NumericIndicator val2) {
		this.type = type;
		this.val1 = val1;
		this.val2 = val2;
	}
	
	public boolean getResult(StockHistoryServer s) {
		boolean result = false;
		double d1 = val1.getResult(s);
		double d2 = val2.getResult(s);
		switch(type) {
			case GREATER:
				result = d1 > d2;
				break;
			case LESSER:
				result = d1 < d2;
				break;
			case EQUAL:
				result = d1 == d2;
				break;
		}
		return result;
	}
}
