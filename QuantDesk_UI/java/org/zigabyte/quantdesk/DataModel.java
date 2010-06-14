package org.zigabyte.quantdesk;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.StockHistoryServer;

public abstract class DataModel extends Observable {
	public abstract List<Stock> getAllStocks();
	public abstract Map<String, Stock> getStockMap();
	public abstract Stock getStock(String code);
	public abstract StockHistoryServer getStockData(Stock s);
	public abstract void stop();
}
