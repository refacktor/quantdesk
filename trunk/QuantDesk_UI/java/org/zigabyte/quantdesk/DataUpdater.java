package org.zigabyte.quantdesk;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;
import org.yccheok.jstock.engine.Country;
import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.StockNotFoundException;

public class DataUpdater extends Thread {
	private String[] data;
	private AnalyzerUI mainUI;
	
	public DataUpdater(String[] data, AnalyzerUI mainUI) {
		this.data = data;
		this.mainUI = mainUI;
	}
	
	public DataUpdater(Stock stock, AnalyzerUI mainUI) {
		MyYahooStockServer server = new MyYahooStockServer(Country.UnitedState);
		try {
			this.data = Utils.getRowString(server.getStock(stock.getCode()));
		} catch (StockNotFoundException e) {
			e.printStackTrace();
		}
		this.mainUI = mainUI;
	}
	
	public void run() {
		TableItem item = new TableItem(mainUI.scanTable, SWT.NONE);
		item.setText(data);
	}
}