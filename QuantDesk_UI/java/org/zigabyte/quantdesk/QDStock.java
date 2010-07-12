package org.zigabyte.quantdesk;

import org.yccheok.jstock.engine.Code;
import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.Symbol;

public class QDStock extends Stock {
	private double dividendShare;
	private double dividendYield;
	public QDStock(
	        Code code,
	        Symbol symbol,
	        String name,
	        Board board,
	        Industry industry,
	        double prevPrice,
	        double openPrice,
	        double lastPrice,
	        double highPrice,
	        double lowPrice,
	        // TODO: CRITICAL LONG BUG REVISED NEEDED.
	        long volume,
	        double changePrice,
	        double changePricePercentage,
	        int lastVolume,
	        double buyPrice,
	        int buyQuantity,
	        double sellPrice,
	        int sellQuantity,
	        double secondBuyPrice,
	        int secondBuyQuantity,
	        double secondSellPrice,
	        int secondSellQuantity,
	        double thirdBuyPrice,
	        int thirdBuyQuantity,
	        double thirdSellPrice,
	        int thirdSellQuantity,
	        java.util.Calendar calendar
	                ) 
	    {
			super(code, symbol, name, board, industry, prevPrice, openPrice, lastPrice, highPrice, lowPrice, volume, changePrice, changePricePercentage,
					lastVolume, buyPrice, buyQuantity, sellPrice, sellQuantity, secondBuyPrice, secondBuyQuantity, secondSellPrice, secondSellQuantity,
					thirdBuyPrice, thirdBuyQuantity, thirdSellPrice, thirdSellQuantity, calendar);
			this.dividendShare = secondBuyPrice;
			this.dividendYield = secondSellPrice;
	    }
	
		public double getDividendPerShare() {
			return dividendShare;
		}
		
		public double getDividendYield() {
			return dividendYield;
		}
}
