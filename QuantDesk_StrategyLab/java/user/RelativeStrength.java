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

public class RelativeStrength extends BaseStrategy {

	private static final int RS_DAYS = 91;
	
	public RelativeStrength(int nStocks) {
		super(nStocks, RS_DAYS);
	}

	protected void rankCandidates(final Date date, List<StockHistory> candidates) {
		
		Collections.sort(candidates, new Comparator<StockHistory>() {
			public int compare(StockHistory history1, StockHistory history2) {
				double mo1 = momentum(date, history1, RS_DAYS);
				double mo2 = momentum(date, history2, RS_DAYS);
				return highestFirst(mo1, mo2);
			}
		});
	}

	protected boolean isBuyCandidate(StockHistory hist, int index) {
		return true;
	}
}
