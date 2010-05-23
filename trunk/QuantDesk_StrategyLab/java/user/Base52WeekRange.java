package user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zigabyte.metastock.data.StockHistory;
import com.zigabyte.metastock.data.StockMarketHistory;
import com.zigabyte.metastock.data.StockDataPoint.FieldID;
import com.zigabyte.stock.indicator.Indicator;
import com.zigabyte.stock.indicator.MovingAverage;
import com.zigabyte.stock.indicator.MovingMaximum;

public class Base52WeekRange {

	private static final Indicator ISupportLevel = new MovingMinimum(20, false, FieldID.LOW);
	static final Indicator I52WeekHigh = new MovingMaximum(365, true, FieldID.HIGH);
	protected static final Indicator I1WeekHigh = new MovingMaximum(7, true);
	protected static final Indicator I1WeekLow = new MovingMaximum(7, true);
	protected static final Indicator IMovingAverage = new MovingAverage(20);
	protected static final Indicator IAverageVolume = new MovingAverage(30, true, FieldID.VOLUME);

	protected List<StockHistory> getNewHighs(final Date date, List<StockHistory> liquidList) {
		List<StockHistory> candidates = new ArrayList<StockHistory>();
		for (StockHistory history : liquidList) {
			int index = history.binarySearch(date);
			if (index >= 0) { // traded today?
				
				double yday52wkHigh = I52WeekHigh.compute(history, index-1);
	
				// close higher than yesterday's 52-week high?
				if(history.get(index).getAdjustedClose() > yday52wkHigh) {		
					candidates.add(history);
				}
			}
		}
		return candidates;
	}

	protected List<StockHistory> getLiquidStocks(final StockMarketHistory histories, final Date date) {
		List<StockHistory> liquidList = new ArrayList<StockHistory>();
		for (StockHistory h : histories) {
			liquidList.add(h);
		}
		liquidList = BaseStrategy.rankByLiquidity(BaseStrategy.daysBefore(date,1), 
				liquidList, liquidList.size() / 3);
		//liquidList = liquidList.subList(liquidList.size()/2, liquidList.size()); // mid-tier
		return liquidList;
	}

}
