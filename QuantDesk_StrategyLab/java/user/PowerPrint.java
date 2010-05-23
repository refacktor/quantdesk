/*
 *  javastock - Java MetaStock parser and Stock Portfolio Simulator
 *  Copyright (C) 2005 Zigabyte Corporation. ALL RIGHTS RESERVED.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package user;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.zigabyte.metastock.data.StockDataPoint;
import com.zigabyte.metastock.data.StockHistory;
import com.zigabyte.metastock.data.StockMarketHistory;
import com.zigabyte.stock.strategy.TradingStrategy;
import com.zigabyte.stock.trade.OrderTiming;
import com.zigabyte.stock.trade.StockPosition;
import com.zigabyte.stock.trade.TradingAccount;

public class PowerPrint 
extends BaseHedged
implements TradingStrategy {
	
	final int nStocks;
	final int nBuys;

	public PowerPrint(int nStocks, int nBuys) {
		this.nStocks = nStocks;
		this.nBuys = nBuys;
	}

	public void placeTradeOrders(final StockMarketHistory histories,
			final TradingAccount account, final Date date,
			int daysUntilMarketOpen) {
		
		if(!histories.hasTradingData(date))
			return;

		double buyingPower = doSells(histories, account, date);

		if (buyingPower < account.getProjectedAccountValue() / nStocks)
			return;

		doBuys(histories, account, date, buyingPower);
	}

	private void doBuys(final StockMarketHistory histories, final TradingAccount account, final Date date, double buyingPower) {
		List<StockHistory> candidates = getLiquidStocks(histories, date, 2);

		Collections.sort(candidates, new Comparator<StockHistory>() {
			public int compare(StockHistory h1, StockHistory h2) {
				double v1 = relativeVolume(date, h1);
				double v2 = relativeVolume(date, h2);
				return BaseStrategy.highestFirst(v1, v2);
			}

			private double relativeVolume(final Date date, StockHistory h) {
				StockDataPoint stockDataPoint = h.get(date);
				if (stockDataPoint == null) {
					return 0;
				}
				return stockDataPoint.getVolumeLots() / IAverageVolume.compute(h,date);
			}
		});		

		candidates = candidates.subList(0, nBuys);
		
		// value before new purchases
		double projectedAccountValue = account.getProjectedAccountValue();
		// how much money to put into each stock purchase
		double positionSize = projectedAccountValue / this.nStocks;
		while(buyingPower >= positionSize && candidates.size() > 0) {
			StockHistory candidate = candidates.remove(0);
			if(account.getStockPosition(candidate.getSymbol()) == null) {
				int index = candidate.binarySearch(date);
				StockDataPoint p = candidate.get(index);
				double lo = p.getAdjustedLow();
				double hi = p.getAdjustedHigh();

				if (positionSize / p.getAdjustedClose() > 1 && index > 0) {
					
					if(p.getAdjustedClose() > candidate.get(index-1)
							.getAdjustedClose()) {
						account.sellStock(candidate.getSymbol(),
								(int) (positionSize / lo),
								OrderTiming.NEXT_DAY_STOP, lo);
					} 
					else {
						account.buyStock(candidate.getSymbol(),
								(int) (positionSize / hi),
								OrderTiming.NEXT_DAY_STOP, hi);
					}
					buyingPower -= lo * ((int) (positionSize / lo));
				}
			}
		}
	}

	
	private double doSells(final StockMarketHistory histories, final TradingAccount account, final Date date) {
		double buyingPower = account.getMarginBuyingPower() / 2;
		for (StockPosition position : account) {
			String symbol = position.getSymbol();
			StockHistory history = histories.get(symbol);
			StockDataPoint sdp = history.getAtOrBefore(date);
			double ma = IMovingAverage.compute(history,BaseStrategy.daysBefore(date,1));
			if(position.getShares() < 0) { // short exits
				if(sdp.getAdjustedClose() >	ma) {
					// short ma-exit
					account.buyStock(symbol, -position.getShares(),
							OrderTiming.NEXT_DAY_OPEN, Double.NaN);				
					buyingPower += sdp.getAdjustedLow() * -position.getShares();
				}
				else {
					account.buyStock(symbol, -position.getShares(),
							OrderTiming.NEXT_DAY_STOP, sdp.getAdjustedHigh());
				}
			}
			else if(position.getShares() > 0) { // long exits
				if( sdp.getAdjustedClose() < ma) {
					// long ma-exit
					account.sellStock(symbol, position.getShares(),
									 OrderTiming.NEXT_DAY_OPEN, Double.NaN);				
					buyingPower += sdp.getAdjustedLow() * position.getShares(); 
				}
			}
			
//			else {
//				// profit-taking sale: a week's range in a day, sell half
//				double range = I1WeekHigh.compute(history,date) -
//							   I1WeekLow.compute(history,date);
//				account.sellStock(symbol, position.getShares(),
//						OrderTiming.NEXT_DAY_LIMIT, sdp.getAdjustedHigh());
//			}
		}
		return buyingPower;
	}

	/** Return the day of the week as an int.
	 To test if it is Sunday, use
	 <pre>
	 Calendar.SUNDAY == dayOfWeek(date)
	 </pre> **/
	protected int dayOfWeek(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/** returns shortClassName+"("+minCashMaxBuyFraction+")" **/
	public String toString() {
		String className = this.getClass().getName();
		String shortName = className.substring(className.lastIndexOf('.') + 1);
		return shortName + "(n=" + this.nStocks + ")";
	}
}
