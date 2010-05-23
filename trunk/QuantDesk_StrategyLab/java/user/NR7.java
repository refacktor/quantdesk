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

// NR7 found profitable in 1999-2001 but looks funny in early2004.
// +++++ arghh... may have been code issue fixed later.

import com.zigabyte.metastock.data.*;
import com.zigabyte.stock.trade.*;
import com.zigabyte.stock.indicator.*;
import com.zigabyte.stock.strategy.*;

import java.util.*;

public class NR7 implements TradingStrategy {

  private static final Indicator BASE = new MovingMinimum(50);
  private Indicator FASTLINE;
    //private static final Indicator SLOWLINE = new MovingAverage(21);
    //private static final Indicator MAXWEEK = new MovingMaximum(7,true);
  private static final Indicator DOLLARVOLUMEWEEK = new MovingDollarVolume(5);

  private final Map<String,Double> profitWaterMark 
      = new HashMap<String,Double>();

  private final Map<String,Double> previousMaxPriceCache =
    new HashMap<String,Double>();
  private Date lastDateCached = null;
  private final double minCashMaxBuyFraction;

  public NR7(double sizingFactor, double fastLength) {
    this.minCashMaxBuyFraction = 1/sizingFactor;
    this.FASTLINE = new MovingAverage((int)fastLength);
  }

  /** Calls {@link #placeSellOrders placeSellOrders} then
      {@link #placeBuyOrders placeBuyOrders} **/
  public void placeTradeOrders(final StockMarketHistory histories,
			       final TradingAccount account,
			       final Date date, int daysUntilMarketOpen) {
      // avoid re-entering Saturday's orders on Sunday
      if(!account.hasPendingOrders()) {
          placeBuyOrders(histories, account, date);
          placeSellOrders(histories, account, date);
      }
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
      StockDataPoint sdp = history.get(item);
      boolean isShort = position.getShares() < 0;

      // take profits when the moving average catches up.
      double exit1 = FASTLINE.compute(history, item);

      // cut losses at %
      double exit2 = position.getCostBasis() * (isShort? 1.03 : 1/1.03); 

      // profit-taking trailing stop.
      double exit3 = 0;
      Double wm = profitWaterMark.get(symbol);
      if(isShort) {
          if(sdp != null && wm != null && sdp.getAdjustedLow() < wm) {
              wm = new Double(sdp.getAdjustedLow());
              profitWaterMark.put(symbol, wm);
          }
          if(wm != null) {
              exit3 = (position.getCostBasis() + wm) / 2;
          }
          if(exit2 < exit1) exit1 = exit2;
          if(exit3 != 0 && exit3 < exit1) exit1 = exit3;
      }
      else {
          if(sdp != null && wm != null && sdp.getAdjustedHigh() > wm) {
              wm = new Double(sdp.getAdjustedHigh());
              profitWaterMark.put(symbol, wm);
          }
          if(wm != null) {
              exit3 = (position.getCostBasis() + wm) / 2;
          }
          if(exit2 > exit1) exit1 = exit2;
          if(exit3 > exit1) exit1 = exit3;
      }

      if(isShort) 
          account.buyStock(symbol, (-1)*position.getShares(),
                            OrderTiming.NEXT_DAY_STOP,
                            exit1);
      else
          account.sellStock(symbol, position.getShares(),
                            OrderTiming.NEXT_DAY_STOP,
                            exit1);
    }
  }

  protected void placeBuyOrders(final StockMarketHistory histories,
				final TradingAccount account,
				final Date date) { 

    // must have at least minCashFraction * accountValue in cash
    if (account.getProjectedCashBalance() <
	this.minCashMaxBuyFraction * 
	(account.getProjectedCashBalance() + account.getProjectedStockValue()))
      return;

    updatePreviousMaxPriceCache(histories, date);

    List<StockHistory> candidates = new ArrayList<StockHistory>();
    NEXT_CANDIDATE: for (StockHistory history : histories) {
      int item = history.getIndexAtOrBefore(date);
      if (item >= 0) { 
        if (history.get(item).getAdjustedLow() < 10)
            continue;
        // is already held?
        if (account.getStockPosition(history.getSymbol()) != null)
            continue;
        // is today narrowest of seven days?
        double range = history.get(item).getAdjustedHigh() -
            history.get(item).getAdjustedLow();
        for(int i=1; i < 7; ++i) {
            if(item - i < 0) continue NEXT_CANDIDATE;
            StockDataPoint dp = history.get(item-i);
            if(dp == null) continue NEXT_CANDIDATE;
            double r2 = dp.getAdjustedHigh() - dp.getAdjustedLow();
            if (r2 < range) continue NEXT_CANDIDATE;
        }

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
        return DOLLARVOLUMEWEEK.compute(history,
                                        history.getIndexAtOrBefore(date));
      }
    });

      // value before new purchases
    double projectedAccountValue = 
	account.getProjectedCashBalance() +
	account.getProjectedStockValue();

    // use only available BP for purchases, do not rely on sales proceeds.
    double cashRemaining = account.getMarginBuyingPower();
    double marketExposure = account. getCurrentStockValue();

    System.out.println(date.toString() + " BP=" + cashRemaining 
                       + " MX=" + marketExposure);
    
    // how much money to put into each stock purchase
    double positionSize = this.minCashMaxBuyFraction * projectedAccountValue;

    // 5. buy as many stocks as possible
    for (StockHistory history : candidates) {
      double sma = FASTLINE.compute(history, date);
      StockDataPoint dp = history.getAtOrBefore(date);
      boolean longTrade = dp.getAdjustedClose() > sma;

      // balance out longs and shorts over time.
      if((longTrade && marketExposure > 0) ||
         (!longTrade && marketExposure < 0)) {
          System.out.println(date.toString() +
                             " Reject: " + history.getSymbol() +
                             (longTrade ? " long" : " short")
                             + " MX=" + marketExposure);
          continue;
      }

      double projectedPrice =  // per share
          longTrade ? dp.getAdjustedHigh()+.01 : dp.getAdjustedLow()-.01;

      int nShares = (int) (positionSize / projectedPrice);
      double projectedCost = // total
              nShares * projectedPrice + account.getTradeFees(nShares);

      // must have at least minCashFraction * accountValue in cash
      cashRemaining -= projectedCost;
      if (cashRemaining <= 0)
	break;
      System.out.println("exposure=" + 
                         marketExposure + ", tradeLong=" + longTrade);
      marketExposure += projectedCost * (longTrade ? 1 : -1);

      if (nShares > 0) {
	// ok to go into a little debt due to price increase or trade fees.
          if(longTrade) {
              account.buyStock(history.getSymbol(), nShares,
                               OrderTiming.NEXT_DAY_STOP, 
                               projectedPrice);
              System.out.println("BUY " + history.getSymbol() + " @ " +
                                 projectedPrice);
          }
          else {
              account.sellStock(history.getSymbol(), nShares,
                                OrderTiming.NEXT_DAY_STOP,
                                projectedPrice);
              System.out.println("SHORT " + history.getSymbol() + " @ " +
                                 projectedPrice);
          }
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
