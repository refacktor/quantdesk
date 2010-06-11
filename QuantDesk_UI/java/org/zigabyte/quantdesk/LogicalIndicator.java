package org.zigabyte.quantdesk;
import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.StockHistoryServer;


public class LogicalIndicator implements Indicator {
	private LogicalType type;
	private Indicator val1;
	private Indicator val2;
	
	public LogicalIndicator(LogicalType type, Indicator val1, Indicator val2) {
		this.type = type;
		this.val1 = val1;
		this.val2 = val2;
		
	}
	
	public boolean getResult(StockHistoryServer s) {
		boolean b1 = false;
		boolean b2 = false;
		if(val1 instanceof LogicalIndicator) {
			b1 = ((LogicalIndicator)val1).getResult(s);
		}
		else {
			b1 = ((ComparisonIndicator)val1).getResult(s);
		}
		
		if(val2 instanceof LogicalIndicator) {
			b2 = ((LogicalIndicator)val2).getResult(s);
		}
		else {
			b2 = ((ComparisonIndicator)val2).getResult(s);
		}
		switch(type) {
			case AND:
				return b1 && b2;
			case OR:
				return b1 || b2;
			default:
				return false;
		}
	}
}
