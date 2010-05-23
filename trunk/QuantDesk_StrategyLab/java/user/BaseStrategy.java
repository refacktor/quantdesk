package user;

import java.util.ArrayList;
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

public abstract class BaseStrategy implements TradingStrategy {

	protected final double sizingFactor;
	
	// Minimum number of days of history before a stock can
	// be considered as a candidate for purchase.
	protected final int minHistory;

	private final int maxBuysPerDay;

	/***************************************************************************
	 * Calls {@link #placeSellOrders placeSellOrders} then
	 * {@link #placeBuyOrders placeBuyOrders}
	 **************************************************************************/
	public final void placeTradeOrders(final StockMarketHistory histories, final TradingAccount account, final Date date, int daysUntilMarketOpen) {
		placeBuyOrders(histories, account, date);
		placeSellOrders(histories, account, date);
	}
	
	protected BaseStrategy(int nStocks, int minHistory) {
		this.sizingFactor = 1.0/nStocks;
		this.minHistory = minHistory;
		this.maxBuysPerDay = nStocks/3 + 1;
	}

	protected void placeSellOrders(final StockMarketHistory histories, final TradingAccount account, final Date date) {
		if (!histories.hasTradingData(date)
				|| account.getStockPositionCount() == 0)
			return; // not a trading day or no positions

		for (StockPosition position : account) {
			String symbol = position.getSymbol();
			StockDataPoint stockDataPoint = histories.get(symbol).get(date);
			
			if(stockDataPoint != null) {
				position.updateWaterLines(stockDataPoint);
			}
			
			placeSellOrder(histories, account, position, date);
		}
	}

	protected void placeSellOrder(StockMarketHistory histories, final TradingAccount account, StockPosition position, Date date) {
		account.sellStock(position.getSymbol(), position.getShares(),
				OrderTiming.NEXT_DAY_STOP,
				position.getHighWaterLine() / 1.05);
	}

	private void sellLowMomentum(final StockMarketHistory histories, final TradingAccount account, final Date date, List<StockPosition> positions) {
		Collections.sort(positions, new Comparator<StockPosition>() {
			public int compare(StockPosition p1, StockPosition p2) {
				double mo1 = momentum(histories, date, p1.getSymbol(), 7);
				double mo2 = momentum(histories, date, p2.getSymbol(), 7);
				return lowestFirst(mo1, mo2);
			}
	
			private double momentum(final StockMarketHistory histories, final Date date, String symbol, int n) {
				StockHistory h = histories.get(symbol);
				Date daysBefore = daysBefore(date, n);
				StockDataPoint sdp = h.getAtOrBefore(daysBefore);
				if(sdp == null) {
					return 0;
				}
				return h.getAtOrBefore(date).getAdjustedClose() 
					 - sdp.getAdjustedClose();
			}
		});
		StockPosition loser = positions.remove(0);
		account.sellStock(loser.getSymbol(), loser.getShares(),
				OrderTiming.NEXT_DAY_OPEN, Double.NaN);
	}

	public static final Date daysBefore(Date date, int n) {
		return new Date(date.getTime() - n * 86400000L);
	}

	public static final int lowestFirst(double a, double b) {
		return Double.compare(a,b);
	}

	public static final int highestFirst(double a, double b) {
		return -1 * lowestFirst(a, b);
	}

	private void buyCandidates(final TradingAccount account, final Date date, List<StockHistory> candidates) {
		// value before new purchases
		double projectedAccountValue = account.getProjectedCashBalance()
				+ account.getProjectedStockValue();
		// use only available cash for purchases, do not rely on sales proceeds.
		double cashRemaining = account.getCurrentCashBalance();
		// how much money to put into each stock purchase
		double positionSize = this.sizingFactor
				* projectedAccountValue;
		for (StockHistory history : candidates) {
			double projectedPrice = (history.get(date)
					.getAdjustedHigh() + history.get(date)
					.getAdjustedLow()) / 2;
			int nShares = (int) (positionSize / projectedPrice);
			double projectedCost = nShares * projectedPrice
					+ account.getTradeFees(nShares);
	
			// must have at least minCashFraction * accountValue in cash
			cashRemaining -= positionSize;
			if (cashRemaining <= 0)
				break;
			
			if (nShares > 0) {
				placeBuyOrder(account, date, history, nShares);
			}
		}
	}

	protected void placeBuyOrder(final TradingAccount account, final Date date, StockHistory history, int nShares) {
		account.buyStock(history.getSymbol(), nShares,
				OrderTiming.NEXT_DAY_STOP, history.get(date).getAdjustedHigh() + .01);
	}

	/***************************************************************************
	 * Return the day of the week as an int. To test if it is Sunday, use
	 * 
	 * <pre>
	 * Calendar.SUNDAY == dayOfWeek(date)
	 * </pre>
	 **************************************************************************/
	public final static int dayOfWeek(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	protected void placeBuyOrders(final StockMarketHistory histories,
			final TradingAccount account, final Date date) {
		
		if (!histories.hasTradingData(date))
			return;	// trades are placed at the end of trading each day.
		
		// must have at least minCashFraction * accountValue in cash
		if (account.getProjectedCashBalance() < this.sizingFactor
				* (account.getProjectedCashBalance() + account
						.getProjectedStockValue()))
			return;
	
		List<StockHistory> candidates = new ArrayList<StockHistory>();
		for (StockHistory history : histories) {
			if (history.getIndexAtOrBefore(daysBefore(date,minHistory)) < 0)
				continue;
			int index = history.binarySearch(date);
			if (index >= 0 && isBuyCandidate(history, index) && 
				account.getStockPosition(history.getSymbol()) == null) {
				// keep candidate if traded today & not already held
				candidates.add(history);
			}
		}
	
		candidates = rankByLiquidity(date, candidates);
		rankCandidates(date, candidates);		

		if(candidates.size() > maxBuysPerDay) {
			candidates = candidates.subList(0,maxBuysPerDay);
		}
		
		buyCandidates(account, date, candidates);
	}

	protected abstract boolean isBuyCandidate(StockHistory hist, int i);

	abstract protected void rankCandidates(final Date date, List<StockHistory> candidates);

	protected List<StockHistory> rankByLiquidity(final Date date, List<StockHistory> candidates) {
		return this.rankByLiquidity(date, candidates, candidates.size() / 2);
	}
		
	final public static List<StockHistory> rankByLiquidity(final Date date, List<StockHistory> candidates, int n) {
		Collections.sort(candidates, new Comparator<StockHistory>() {
			public int compare(StockHistory h1, StockHistory h2) {
				return highestFirst(liquidity(date, h1), liquidity(date, h2));
			}
			private double liquidity(final Date date, StockHistory h) {
				if(h.get(date) == null) {
					return 0;
				}
				double total = 0.0;
				int i = h.binarySearch(date);
				for(int j = 0; j < 20 && i-j >= 0; ++j) {
					StockDataPoint p = h.get(i-j);
					if(p != null) {
						total += p.getAdjustedClose() * p.getVolumeLots();
					}
				}
				return total;
			}
		});
		return candidates.subList(0, n);
	}

	public final static double momentum(Date date, StockHistory history, int n) {
		Date base = daysBefore(date, n);
		if (history.getIndexAtOrBefore(base) < 0)
			return 0;
		return history.getAtOrBefore(date).getAdjustedClose() / 
		       history.getAtOrBefore(base).getAdjustedClose();
	}

	public static final boolean isLowVolumePullback(StockHistory hist, int i) {
		if (i <= 0)
			return false;

		int n = 0;
		
		if (hist.get(i).getAdjustedLow() <= hist.get(i - 1).getAdjustedLow())
			++n;
		
		if (hist.get(i).getAdjustedHigh() <= hist.get(i - 1).getAdjustedHigh())
			++n;
		
		if (hist.get(i).getAdjustedClose() <= hist.get(i - 1).getAdjustedClose())
			++n;

		return n >= 2
				&& hist.get(i).getVolumeLots() <= hist.get(i - 1).getVolumeLots();
	}

	public String toString() {
		String className = this.getClass().getName();
		String shortName = className.substring(className.lastIndexOf('.') + 1);
		return shortName + "(nStocks=" + 1/this.sizingFactor + ")";
	}

}
