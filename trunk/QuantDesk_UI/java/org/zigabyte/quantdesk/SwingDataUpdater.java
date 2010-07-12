package org.zigabyte.quantdesk;

import javax.swing.table.DefaultTableModel;

import org.yccheok.jstock.engine.Country;
import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.StockNotFoundException;

public class SwingDataUpdater extends Thread {
	private String[] data;
	private AnalyzerUI mainUI;
	
	public SwingDataUpdater(String[] data, AnalyzerUI mainUI) {
		this.data = data;
		this.mainUI = mainUI;
	}
	
	public SwingDataUpdater(Stock stock, AnalyzerUI mainUI) {
		MyYahooStockServer server = new MyYahooStockServer(Country.UnitedState);
		try {
			this.data = Utils.getRowString(server.getStock(stock.getCode()));
		} catch (StockNotFoundException e) {
			e.printStackTrace();
		}
		this.mainUI = mainUI;
	}
	
	public void run() {
		DefaultTableModel m = (DefaultTableModel) mainUI.getScreeningTable().getModel();
		m.addRow(data);
	}
}