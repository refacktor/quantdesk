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

public class TwoWeekMomentum implements TradingStrategy {

  private static final Indicator FastLine = new MovingAverage(3);
  private static final Indicator SlowLine = new MovingAverage(20);
  private static final Indicator MAXWEEK = new MovingMaximum(7,true);
  private static final Indicator DOLLARVOLUME = new MovingDollarVolume(20);
  private final Map<String,Double> previousMaxPriceCache =
    new HashMap<String,Double>();
  private Date lastDateCached = null;
  private final double minCashMaxBuyFraction;

  public TwoWeekMomentum(double sizingFactor) {
    this.minCashMaxBuyFraction = 1/sizingFactor;
  }

  /** Calls {@link #placeSellOrders placeSellOrders} then
      {@link #placeBuyOrders placeBuyOrders} **/
  public void placeTradeOrders(final StockMarketHistory histories,
			       final TradingAccount account,
			       final Date date, int daysUntilMarketOpen) {
    placeBuyOrders(histories, account, date);
    placeSellOrders(histories, account, date);
  }

  /** Every trading day, look at the stocks that are in the account (if any),
      and sell next day at the Open if the stock's last Closing price
      13-day moving average is below the 50-day moving average. **/
  protected void placeSellOrders(final StockMarketHistory histories,
				 final TradingAccount account,
				 final Date date) {
    if (!histories.hasTradingData(date))
      return; // not a trading day
      
    for (StockPosition position : account) {
      String symbol = position.getSymbol();
      StockHistory history = histories.get(symbol);
      int item = history.getIndexAtOrBefore(date);

      // sell stock positions where 13-day avg < 50-day avg
      if (FastLine.compute(history, item) < SlowLine.compute(history, item)) {
	account.sellStock(symbol, position.getShares(),
			  OrderTiming.NEXT_DAY_OPEN, Double.NaN);
      }
      else if (previousMaxPriceCache != null 
	       && previousMaxPriceCache.get(symbol) != null
	       && history.get(item) != null &&
	       history.get(item).getAdjustedClose() < 
	       (previousMaxPriceCache.get(symbol) + 
		position.getCostBasis())/2) {
	  // take profits
	account.sellStock(symbol, position.getShares(),
			  OrderTiming.NEXT_DAY_STOP,
			  history.get(item).getAdjustedLow() - .01);
      }
      else {
	  // place stop-loss order
	  account.sellStock(symbol, position.getShares(),
			    OrderTiming.NEXT_DAY_STOP, 
			    position.getCostBasis() / 1.08);
      }
    }
  }

  private double momentum(Date date, StockHistory history, int n) {
	  Date base = new Date(date.getTime()- n * 86400000);
          if(history.getIndexAtOrBefore(base) < 0) return 0;
          return history.getAtOrBefore(date).getAdjustedClose()
               / history.getAtOrBefore(base).getAdjustedClose();
  } 

    private int lowestFirst(double a, double b) {
        return a > b ? -1 : (a < b ? 1 : 0);
    }

    private int highestFirst(double a, double b) {
        return -1 * lowestFirst(a,b);
    }

  protected void placeBuyOrders(final StockMarketHistory histories,
				final TradingAccount account,
				final Date date) { 
    if (dayOfWeek(date) != Calendar.SUNDAY)
      return;
    // must have at least minCashFraction * accountValue in cash
    if (account.getProjectedCashBalance() <
	this.minCashMaxBuyFraction * 
	(account.getProjectedCashBalance() + account.getProjectedStockValue()))
      return;

    updatePreviousMaxPriceCache(histories, date);

    List<StockHistory> candidates = new ArrayList<StockHistory>();
    for (StockHistory history : histories) {
      int item = history.getIndexAtOrBefore(date);
      if (item >= 0) { 

	// 3. eliminate stocks where 13-day avg <= 50-day avg
	if (FastLine.compute(history, item) <= SlowLine.compute(history, item))
	  continue;
	// keep candidate
	candidates.add(history);
      }
    }
    // 4. sort candidates by dollar volume for the past week
    Collections.sort(candidates, new Comparator<StockHistory>() {
      public int compare(StockHistory history1, StockHistory history2) {
	  double dollarVolume1 = getDollarVolumeWeek(history1);
	  double dollarVolume2 = getDollarVolumeWeek(history2);
	  return (dollarVolume1 < dollarVolume2? 1 :
	         dollarVolume1 > dollarVolume2? -1 : 0);
      }
      private double getDollarVolumeWeek(StockHistory history) {
        return DOLLARVOLUME.compute(history,
                                        history.getIndexAtOrBefore(date));
      }
    });

    // keep only top percentile
    candidates = candidates.subList(0, candidates.size()/2);

    // sort by long momentum
    Collections.sort(candidates, new Comparator<StockHistory>() {
      public int compare(StockHistory history1, StockHistory history2) {
          double mo1 = momentum(date, history1, 20);
          double mo2 = momentum(date, history2, 20);
	  return highestFirst(mo1, mo2);
      }
    });

    // keep only top percentile
    candidates = candidates.subList(0, candidates.size()/3);

    // sort by middle momentum
    Collections.sort(candidates, new Comparator<StockHistory>() {
      public int compare(StockHistory history1, StockHistory history2) {
          double mo1 = momentum(date, history1, 10);
          double mo2 = momentum(date, history2, 10);
          return highestFirst(mo1, mo2);
      }
    });

    candidates = candidates.subList(0, candidates.size()/3);

    // sort by volatillity (reverse)
    Collections.sort(candidates, new Comparator<StockHistory>() {
      public int compare(StockHistory history1, StockHistory history2) {
          double mo1 = momentum(date, history1, 3);
          double mo2 = momentum(date, history2, 3);
          return lowestFirst(Math.abs(mo1), Math.abs(mo2));
      }
    });
      // value before new purchases
    double projectedAccountValue = 
	account.getProjectedCashBalance() +
	account.getProjectedStockValue();

    // use only available cash for purchases, do not rely on sales proceeds.
    double cashRemaining = account.getCurrentCashBalance(); 
    
    // how much money to put into each stock purchase
    double positionSize = this.minCashMaxBuyFraction * projectedAccountValue;

    // 5. buy as many stocks as possible
    for (StockHistory history : candidates) {
      double projectedPrice = 
	  (history.getAtOrBefore(date).getAdjustedHigh() +
	   history.getAtOrBefore(date).getAdjustedLow()) / 2;
      int nShares = (int) (positionSize / projectedPrice);
      double projectedCost =
	nShares * projectedPrice + account.getTradeFees(nShares);

      // must have at least minCashFraction * accountValue in cash
      cashRemaining -= positionSize;
      if (cashRemaining <= 0)
	break;

      if (nShares > 0) {
	// ok to go into a little debt due to price increase or trade fees.
	account.buyStock(history.getSymbol(), nShares,
			 OrderTiming.NEXT_DAY_LIMIT, 
			 projectedPrice);

	//System.err.println("Buy " + history.getSymbol() + " - $" + 
	//            cashRemaining + " left.");
      } 
    }
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

  /** 
   **/
  private void updatePreviousMaxPriceCache(StockMarketHistory histories,
					   Date untilDate) {
    // set until date to week ago
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(untilDate);
    calendar.add(Calendar.DATE, -7);
    untilDate = calendar.getTime();

    if (this.lastDateCached == null ||
	this.lastDateCached.getTime() > untilDate.getTime()) {
      // restarting, so reset cache
      this.previousMaxPriceCache.clear();
      this.lastDateCached = null;
    }
    for (StockHistory history : histories) {
      int startIndex = (this.lastDateCached == null? 0 :
			history.getIndexAtOrAfter(this.lastDateCached));
      int endIndex = history.getIndexAtOrBefore(untilDate);
      if (startIndex >= 0 && endIndex >= 0) { 
	String symbol = history.getSymbol();
	Double cachedMaxPrice = previousMaxPriceCache.get(symbol);
	double maxPrice = (cachedMaxPrice == null? 0.0 : cachedMaxPrice);

	for (int i = startIndex; i < endIndex; i++)
	  maxPrice = Math.max(maxPrice, history.get(i).getAdjustedHigh());

	previousMaxPriceCache.put(symbol, maxPrice);
      } 
    }
    this.lastDateCached = untilDate;
  }
				   
  /** returns shortClassName+"("+minCashMaxBuyFraction+")" **/
  public String toString() {
    String className = this.getClass().getName();
    String shortName = className.substring(className.lastIndexOf('.')+1);
    return shortName+"("+this.minCashMaxBuyFraction+")";
  }
}
