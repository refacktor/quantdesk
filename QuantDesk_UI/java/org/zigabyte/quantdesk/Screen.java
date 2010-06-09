package org.zigabyte.quantdesk;

import java.util.ArrayList;
import java.util.List;

import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.StockHistoryServer;


public class Screen {
	public String name;
	public Indicator indicator;

	public Screen(String name) {
		this.name = name;
	}
	
	public void setIndicator(Indicator i) {
		indicator = i;
	}
	
	public boolean indicatorMatches(StockHistoryServer s) {
		return indicator instanceof LogicalIndicator ? ((LogicalIndicator)indicator).getResult(s) : ((ComparisonIndicator)indicator).getResult(s);
	}
	
	public String getName() {
		return name;
	}
}