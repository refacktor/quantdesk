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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import com.zigabyte.metastock.data.StockDataPoint;
import com.zigabyte.metastock.data.StockHistory;
import com.zigabyte.metastock.data.StockMarketHistory;
import com.zigabyte.stock.strategy.TradingStrategy;
import com.zigabyte.stock.trade.OrderTiming;
import com.zigabyte.stock.trade.StockPosition;
import com.zigabyte.stock.trade.TradingAccount;

public class LiquidScalp implements TradingStrategy {

	int nStocks;
	
	public LiquidScalp(int nStocks) {
		this.nStocks = nStocks;
	}

	public void placeTradeOrders(StockMarketHistory histories, TradingAccount account, Date date, int daysUntilMarketOpen) {
		
		if(!histories.hasTradingData(date))
			return;
		
		int nHoldings = account.getStockPositionCount();
		int nToBuy = nStocks - nHoldings;
		
		List<StockHistory> candidates = new ArrayList<StockHistory>();
		for (StockHistory history : histories) {
			int index = history.binarySearch(date);
			if (index >= 0 && account.getStockPosition(history.getSymbol()) == null) {
				candidates.add(history);
			}
		}
		if(candidates.size() < nToBuy) {
			// rare cases where some data seeps into non-trading days
			return;
		}
		candidates = BaseStrategy.rankByLiquidity(date, candidates, nToBuy);
		
		// place buy orders
		for(StockHistory history : candidates) {
			StockDataPoint sdp = history.get(date);			
			double price = (sdp.getAdjustedHigh() * 2 + sdp.getAdjustedLow()) / 3;
			double nShares = (account.getCurrentAccountValue()/nStocks) / price;
			
			account.buyStock(history.getSymbol(),
					(int)nShares,
					OrderTiming.NEXT_DAY_LIMIT,
					price);			
		}
		
		// place sell orders
		for(StockPosition position : account) {
			StockDataPoint sdp = histories.get(position.getSymbol()).getAtOrBefore(date);
			account.sellStock(position.getSymbol(), 
					position.getShares(),
					OrderTiming.NEXT_DAY_LIMIT, 
					(sdp.getAdjustedHigh() + 2 * sdp.getAdjustedLow()) / 3);
		}		
	}
}
