package org.zigabyte.quantdesk;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.StockHistoryServer;

public class EasyScreens {
	private Map<String, Screen> screens = new HashMap<String, Screen>();
	
	public EasyScreens() {
		if(screens.size() == 0) {
			createDefaultScreens();
		}
	}
	
	private void createDefaultScreens() {
		Screen s1 = new Screen("3 Higher Days @ 52 Week High");
		NumericIndicator ind1 = new NumericIndicator(Indicator.NumericType.MAX, 365);
		NumericIndicator ind10 = new NumericIndicator(Indicator.NumericType.MAX, 3);
		NumericIndicator ind2 = new NumericIndicator(Indicator.NumericType.MIN, 5);
		NumericIndicator ind3 = new NumericIndicator(Indicator.NumericType.MIN, 4);
		NumericIndicator ind4 = new NumericIndicator(Indicator.NumericType.MIN, 3);
		ComparisonIndicator ind5 = new ComparisonIndicator(Indicator.ComparisonType.EQUAL, ind1, ind10);
		ComparisonIndicator ind6 = new ComparisonIndicator(Indicator.ComparisonType.GREATER, ind3, ind2);
		ComparisonIndicator ind7 = new ComparisonIndicator(Indicator.ComparisonType.GREATER, ind4, ind3);
		LogicalIndicator ind8 = new LogicalIndicator(Indicator.LogicalType.AND, ind5, ind6);
		LogicalIndicator ind9 = new LogicalIndicator(Indicator.LogicalType.AND, ind8, ind7);
		s1.setIndicator(ind9);
		screens.put(s1.name, s1);
		
		Screen s2 = new Screen("Latest is Max over 1 year");
		NumericIndicator ind11 = new NumericIndicator(Indicator.NumericType.MAX, 365);
		NumericIndicator ind12 = new NumericIndicator(Indicator.NumericType.MAX, 10);
		s2.setIndicator(new ComparisonIndicator(Indicator.ComparisonType.EQUAL, ind11, ind12));
		screens.put(s2.name, s2);
	}
	
	public boolean stockMatches(String name, StockHistoryServer s) {
		return screens.get(name).indicatorMatches(s);
	}
	
	public String[] getScreeningNames() {
		String[] names = new String[screens.size()];
		for(int i = 0; i < screens.size(); i++) {
			names[i] = screens.get(screens.keySet().toArray()[i]).getName();
		}
		return names;
	}
}


