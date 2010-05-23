package user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zigabyte.metastock.data.StockHistory;
import com.zigabyte.metastock.data.StockMarketHistory;
import com.zigabyte.metastock.data.StockDataPoint.FieldID;
import com.zigabyte.stock.indicator.Indicator;
import com.zigabyte.stock.indicator.MovingAverage;

public class BaseHedged {

	protected static final Indicator IAverageVolume = new MovingAverage(30, true, FieldID.VOLUME);
	protected static final Indicator IMovingAverage = new MovingAverage(20);

	protected List<StockHistory> getLiquidStocks(final StockMarketHistory histories, final Date date, int n) {
		List<StockHistory> liquidList = new ArrayList<StockHistory>();
		for (StockHistory h : histories) {
			liquidList.add(h);
		}
		liquidList = BaseStrategy.rankByLiquidity(BaseStrategy.daysBefore(date,1), 
				liquidList, liquidList.size() / n);
		//liquidList = liquidList.subList(liquidList.size()/2, liquidList.size()); // mid-tier
		return liquidList;
	}

}
