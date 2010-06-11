package org.zigabyte.quantdesk;

import java.util.List;

import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.StockHistoryServer;

public interface DataModel {
	public List<Stock> getAllStocks();
	public Stock getStock(Stock s);
	public StockHistoryServer getStockData(Stock s);
}
