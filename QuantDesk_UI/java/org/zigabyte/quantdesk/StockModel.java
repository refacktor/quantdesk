package org.zigabyte.quantdesk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.yccheok.jstock.engine.Code;
import org.yccheok.jstock.engine.Country;
import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.StockHistoryNotFoundException;
import org.yccheok.jstock.engine.StockHistoryServer;
import org.yccheok.jstock.engine.StockNotFoundException;
import org.yccheok.jstock.engine.Symbol;
import org.yccheok.jstock.engine.Stock.Board;
import org.yccheok.jstock.engine.Stock.Industry;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class StockModel extends DataModel {

	private Map<String, Stock> stocksMap = null;
	private List<Stock> stocks = null;
	private String stockFileLoc = "data" + File.separator + "stocks.csv";
	private Country country = Country.UnitedState;
	
	public StockModel() {
	}
	
	@Override
	public List<Stock> getAllStocks() {
		// TODO Auto-generated method stub
		if(stocks == null) {
			stocks = new ArrayList<Stock>();
			stocksMap = new HashMap<String, Stock>();
			File dataFile = new File(stockFileLoc);
			if(dataFile.exists()) {
				try {
					System.out.println("Reading stocks from datafile");
					FileReader fileReader = new FileReader(dataFile);
					CSVReader reader = new CSVReader(fileReader);
					String[] data = null;
					try {
						data = reader.readNext();
					}
					catch(IOException ioe) {
						Log.error(null, ioe);
						return null;
					}
					do {
						try {
							Calendar c = Calendar.getInstance();
							try {
								c.setTime(DateFormat.getInstance().parse(data[26]));
							}
							catch(ParseException p) {
								Log.error(null, p);
							}
							Stock s = new Stock(
									Code.newInstance(data[0]),
									Symbol.newInstance(data[1]),
									data[2],
									data[3].equals("Pink Sheet") ? Board.PinkSheet : Board.valueOf(data[3]),
									Industry.valueOf(data[4]),
									Double.parseDouble(data[5]),
									Double.parseDouble(data[6]),
									Double.parseDouble(data[7]),
									Double.parseDouble(data[8]),
									Double.parseDouble(data[9]),
									Long.parseLong(data[10]),
									Double.parseDouble(data[11]),
									Double.parseDouble(data[12]),
									Integer.parseInt(data[13]),
									Double.parseDouble(data[14]),
									Integer.parseInt(data[15]),
									Double.parseDouble(data[16]),
									Integer.parseInt(data[17]),
									Double.parseDouble(data[18]),
									Integer.parseInt(data[19]),
									Double.parseDouble(data[20]),
									Integer.parseInt(data[21]),
									Double.parseDouble(data[22]),
									Integer.parseInt(data[23]),
									Double.parseDouble(data[24]),
									Integer.parseInt(data[25]),
									c
							);
							stocks.add(s);
							stocksMap.put(s.getCode().toString(), s);
							data = reader.readNext();
						}
						catch(IOException ioe) {
							Log.error(null, ioe);
						}
					} while (data != null);
				}
				catch(FileNotFoundException fnfe) {
					Log.error(null, fnfe);
				}
			}
			else {
				MyYahooStockServer m = new MyYahooStockServer(country);
				try {
					List<Stock> temp = m.getAllStocks();
					for(Stock s : temp) {
						stocks.add(s);
						stocksMap.put(s.getCode().toString(), s);
					}
					writeStockData();
				}
				catch(StockNotFoundException snfe) {
				}
			}
		}
		return stocks;
	}

	@Override
	public Stock getStock(String code) {
		// TODO Auto-generated method stub
		MyYahooStockServer server = new MyYahooStockServer(Country.UnitedState);
		Stock s;
		try {
			s = server.getStock(Code.newInstance(code));
		} catch (StockNotFoundException e) {
			return null;
		}
		stocksMap.put(code, s);
		return s;
	}

	@Override
	public StockHistoryServer getStockData(Stock s) {
		// TODO Auto-generated method stub
		try {
			MyYahooStockHistoryServer server = new MyYahooStockHistoryServer(country, s.getCode());
			return server;
		} catch (StockHistoryNotFoundException snfe) {
			Log.error(null, snfe);
			return null;
		}
	}

	@Override
	public Map<String, Stock> getStockMap() {
		if(stocksMap == null) {
			getAllStocks();
		}
		return stocksMap;
	}
	
	private void writeStockData() {
		System.out.println("Writing stock data to file.");
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(stockFileLoc));
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		}
		CSVWriter csv = new CSVWriter(writer);
		for(Stock s : stocks) {
			String[] line = new String[] {
				s.getCode().toString(),
				s.getSymbol().toString(),
				s.getName(),
				s.getBoard().toString(),
				s.getIndustry().toString(),
				String.valueOf(s.getPrevPrice()),
				String.valueOf(s.getOpenPrice()),
				String.valueOf(s.getLastPrice()),
				String.valueOf(s.getHighPrice()),
				String.valueOf(s.getLowPrice()),
				String.valueOf(s.getVolume()),
				String.valueOf(s.getChangePrice()),
				String.valueOf(s.getChangePricePercentage()),
				String.valueOf(s.getLastVolume()),
				String.valueOf(s.getBuyPrice()),
				String.valueOf(s.getBuyQuantity()),
				String.valueOf(s.getSellPrice()),
				String.valueOf(s.getSellQuantity()),
				String.valueOf(s.getSecondBuyPrice()),
				String.valueOf(s.getSecondBuyQuantity()),
				String.valueOf(s.getSecondSellPrice()),
				String.valueOf(s.getSecondSellQuantity()),
				String.valueOf(s.getThirdBuyPrice()),
				String.valueOf(s.getThirdBuyQuantity()),
				String.valueOf(s.getThirdSellPrice()),
				String.valueOf(s.getThirdSellQuantity()),
				s.getCalendar().getTime().toString()
			};
			csv.writeNext(line);
		}
		System.out.println("All stock data written.");
		try {
			csv.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

}
