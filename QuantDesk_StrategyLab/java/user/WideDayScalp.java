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

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zigabyte.metastock.data.StockDataPoint;
import com.zigabyte.metastock.data.StockHistory;
import com.zigabyte.metastock.data.StockMarketHistory;
import com.zigabyte.stock.trade.OrderTiming;
import com.zigabyte.stock.trade.StockPosition;
import com.zigabyte.stock.trade.TradingAccount;

public class WideDayScalp extends BaseStrategy {

	public WideDayScalp(int nStocks) {
		super(nStocks, 90);
	}

	protected void rankCandidates(final Date date, List<StockHistory> candidates) {
		
		Collections.sort(candidates, new Comparator<StockHistory>() {
			public int compare(StockHistory h1, StockHistory h2) {
				return highestFirst(countNarrowDays(h1,date), countNarrowDays(h2,date));
			}
			public int countNarrowDays(StockHistory h, Date d) {
				int count = 0;
				for(int i = h.getIndexAtOrBefore(d); i > 0; --i) {
					if(range(h,i) <= range(h,i-1)) {
						return count;
					}
					++count;
				}
				return -1;
			}
			public double range(StockHistory h, int i) {
				return h.get(i).getAdjustedHigh() - h.get(i).getAdjustedLow();
			}
		});
	}

	protected boolean isBuyCandidate(StockHistory hist, int date) {
		return true;
	}

	@Override
	protected void placeBuyOrder(TradingAccount account, Date date, StockHistory history, int nShares) {
		StockDataPoint sdp = history.get(date);
		account.buyStock(history.getSymbol(), nShares,
				OrderTiming.NEXT_DAY_LIMIT,
				sdp.getAdjustedLow());
	}

	@Override
	protected void placeSellOrder(StockMarketHistory histories, TradingAccount account, StockPosition position, Date date) {
		StockDataPoint sdp = histories.get(position.getSymbol()).getAtOrBefore(date);
		account.sellStock(position.getSymbol(), 
						  position.getShares(),
						  OrderTiming.NEXT_DAY_LIMIT, 
						  sdp.getAdjustedHigh());
	}
}
