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

import com.zigabyte.metastock.data.*;
import com.zigabyte.stock.trade.*;
import com.zigabyte.stock.indicator.*;
import com.zigabyte.stock.strategy.*;

import java.util.*;

public class WeeklyPeaks4 implements TradingStrategy {
	
//	private WeakHashMap<StockPosition> profitTarget = new WeakHashMap<StockPosition>(); 
	
	private static final Indicator ISupportLevel = new MovingMinimum(20, false, StockDataPoint.FieldID.LOW);
	private static final Indicator I52WeekHigh = new MovingMaximum(365, true);
	private static final Indicator I1WeekHigh = new MovingMaximum(7, true);
	private static final Indicator I1WeekLow = new MovingMaximum(7, true);
	private static final Indicator IMovingAverage = new MovingAverage(20);

	final int nStocks = 10;

	public WeeklyPeaks4() {
	}

	public void placeTradeOrders(final StockMarketHistory histories,
			final TradingAccount account, final Date date,
			int daysUntilMarketOpen) {

		if (dayOfWeek(date) != Calendar.TUESDAY)
			return;

		double buyingPower = doSells(histories, account, date);

		if (buyingPower < account.getProjectedAccountValue() / nStocks)
			return;

		doBuys(histories, account, date, buyingPower);
	}

	private void doBuys(final StockMarketHistory histories, final TradingAccount account, final Date date, double buyingPower) {
		List<StockHistory> liquidList = new ArrayList<StockHistory>();
		for (StockHistory h : histories) {
			liquidList.add(h);
		}
		liquidList = BaseStrategy.rankByLiquidity(date, liquidList, 
				liquidList.size() / 2);
		List<StockHistory> candidates = new ArrayList<StockHistory>();
		for (StockHistory history : liquidList) {
			if (history.binarySearch(date) >= 0) { // traded today?
				double liquidity = history.get(date).getAdjustedClose() * 
				   history.get(date).getVolumeLots();
				
				double prev52wkHigh = I52WeekHigh.compute(history, BaseStrategy.daysBefore(date,7));
				double prevWeekHigh = I1WeekHigh.compute(history, date);

				if(prevWeekHigh > prev52wkHigh) {		
					candidates.add(history);
				}
			}
		}
		// eliminate super-low-volatility issues (M&A's, some ETFs, etc).
		Collections.sort(candidates, new Comparator<StockHistory>() {
			public int compare(StockHistory h1, StockHistory h2) {
				double v1 = I1WeekHigh.compute(h1,date) / I1WeekLow.compute(h1,date);
				double v2 = I1WeekHigh.compute(h2,date) / I1WeekLow.compute(h2,date);
				return BaseStrategy.highestFirst(v1, v2);
			}
		});
		candidates = candidates.subList(0, candidates.size()/2);
		// now pick stocks with the best consolidation patterns (quietest month)
		Collections.sort(candidates, new Comparator<StockHistory>() {
			public int compare(StockHistory h1, StockHistory h2) {
				double v1 = I1WeekHigh.compute(h1,date) / IMovingAverage.compute(h1,date);
				double v2 = I1WeekHigh.compute(h2,date) / IMovingAverage.compute(h2,date);
				return BaseStrategy.highestFirst(v1, v2);
			}
		});
		// value before new purchases
		double projectedAccountValue = account.getProjectedAccountValue();
		// how much money to put into each stock purchase
		double positionSize = projectedAccountValue / this.nStocks;
		while(buyingPower >= positionSize && candidates.size() > 0) {
			StockHistory candidate = candidates.remove(0);
			if(account.getStockPosition(candidate.getSymbol()) == null) {
				double projectedPrice = candidate.get(date).getAdjustedHigh();
				int nShares = (int) (positionSize / projectedPrice);

				if (nShares > 0) {
					account.buyStock(candidate.getSymbol(), nShares,
							OrderTiming.NEXT_DAY_LIMIT, projectedPrice);
				}
				buyingPower -= projectedPrice * nShares;
			}
		}
	}

	private double doSells(final StockMarketHistory histories, final TradingAccount account, final Date date) {
		double buyingPower = account.getCurrentCashBalance();
		for (StockPosition position : account) {
			String symbol = position.getSymbol();
			StockHistory history = histories.get(symbol);
			StockDataPoint sdp = history.getAtOrBefore(date);
			if(sdp.getAdjustedClose() <	
				IMovingAverage.compute(history,BaseStrategy.daysBefore(date,1))) {
				// stop-loss sales
				account.sellStock(symbol, position.getShares(),
						OrderTiming.NEXT_DAY_OPEN, Double.NaN);				
				buyingPower += sdp.getAdjustedLow() * position.getShares(); 
			}
			else {
				// profit-taking sale: a week's range in a day, sell half
				double range = I1WeekHigh.compute(history,date) -
							   I1WeekLow.compute(history,date);
				account.sellStock(symbol, position.getShares(),
						OrderTiming.NEXT_DAY_LIMIT, sdp.getAdjustedHigh());
			}
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
