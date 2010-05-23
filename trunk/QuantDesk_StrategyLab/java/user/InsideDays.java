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
import java.util.List;

import com.zigabyte.metastock.data.StockHistory;

public class InsideDays extends BaseStrategy {

	public InsideDays(int nStocks) {
		super(nStocks, 90);
	}

	protected void rankCandidates(final Date date, List<StockHistory> candidates) {
		
		Collections.sort(candidates, new Comparator<StockHistory>() {
			public int compare(StockHistory h1, StockHistory h2) {
				return highestFirst(countInsideDays(h1,date), countInsideDays(h2,date));
			}
			public int countInsideDays(StockHistory h, Date d) {
				int i = h.getIndexAtOrBefore(d);
				int j = i;
				do {
					if(h.get(j-1).getAdjustedLow() >= h.get(j).getAdjustedLow() ||
					   h.get(j-1).getAdjustedHigh() <= h.get(j).getAdjustedHigh()) {
						return i-j;
					}
				} while(j-- >= 0);
				return -1;
			}
		});
	}

	protected boolean isBuyCandidate(StockHistory hist, int date) {
		return true;
	}
}
