package org.zigabyte.quantdesk;

import org.yccheok.jstock.engine.Stock;

public class Utils {
	private Utils() {
	}
	
	public static String[] getRowString(Stock stock) {
		return new String[] {
				"",
				"",
				stock.getCode().toString(),
				stock.getIndustry().toString(),
				String.valueOf(stock.getLastPrice()),
				String.valueOf(stock.getVolume()),
				String.valueOf(stock.getChangePrice()),
				String.valueOf(stock.getChangePricePercentage()),
				String.valueOf(stock.getPrevPrice())
			};
	}
}
