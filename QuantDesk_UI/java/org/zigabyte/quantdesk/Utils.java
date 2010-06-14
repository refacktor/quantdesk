package org.zigabyte.quantdesk;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.yccheok.jstock.engine.Code;
import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.Symbol;
import org.yccheok.jstock.engine.YahooStockFormat;
import org.yccheok.jstock.engine.Stock.Board;
import org.yccheok.jstock.engine.Stock.Industry;

public class Utils {
	private Utils() {
	}
	
	public static String[] getRowString(Stock stock) {
		return new String[] {
				"",
				"",
				stock.getCode().toString(),
				stock.getIndustry().toString(),
				String.valueOf(stock.getLastPrice()),
				String.valueOf(stock.getVolume()),
				String.valueOf(stock.getChangePrice()),
				String.valueOf(stock.getChangePricePercentage()),
				String.valueOf(stock.getPrevPrice()),
				String.valueOf(stock.getSecondBuyPrice()),
				String.valueOf(stock.getSecondSellPrice())
			};
	}
	
	// Update on 19 March 2009 : We cannot assume certain parameters will always
    // be float. They may become integer too. For example, in the case of Korea
    // Stock Market, Previous Close is in integer. We shall apply string quote
    // protection method too on them.
    //
    // Here are the index since 19 March 2009 :
    // (0) Symbol
    // (1) Name
    // (2) Stock Exchange
    // (3) Previous Close
    // (4) Open
    // (5) Last Trade
    // (6) Day's high
    // (7) Day's low
    // (8) Volume
    // (9) Change
    // (10) Change Percent
    // (11) Last Trade Size
    // (12) Bid
    // (13) Bid Size
    // (14) Ask
    // (15) Ask Size
    // (16) Last Trade Date
    // (17) Last Trade Time.
	// (18) Dividend/Share
	// (19) Dividend Yield
    //
    // s = Symbol
    // n = Name
    // x = Stock Exchange
    // o = Open             <-- Although we will keep this value in our stock data structure, we will not show
    //                          it to clients. As some stock servers unable to retrieve open price.
    // p = Previous Close
    // l1 = Last Trade (Price Only)
    // h = Day's high
    // g = Day's low
    // v = Volume           <-- We need to take special care on this, it may give us 1,234. This will
    //                          make us difficult to parse csv file. The only workaround is to make integer
    //                          in between two string literal (which will always contains "). By using regular
    //                          expression, we will manually remove the comma.
    // c1 = Change
    // p2 = Change Percent
    // k3 = Last Trade Size <-- We need to take special care on this, it may give us 1,234...
    // b = Bid
    // b6 = Bid Size        <-- We need to take special care on this, it may give us 1,234...
    // a = Ask
    // a5 = Ask Size        <-- We need to take special care on this, it may give us 1,234...
    // d1 = Last Trade Date
    // t1 = Last Trade Time
    //
    // c6k2c1p2c -> Change (Real-time), Change Percent (Real-time), Change, Change in Percent, Change & Percent Change
    // "+1400.00","N/A - +4.31%",+1400.00,"+4.31%","+1400.00 - +4.31%"
    //
    // "MAERSKB.CO","AP MOELLER-MAERS-","Copenhagen",32500.00,33700.00,34200.00,33400.00,660,"+1200.00","N/A - +3.69%",33,33500.00,54,33700.00,96,"11/10/2008","10:53am"    
    public static List<Stock> parse(String source) {
        List<Stock> stocks = new ArrayList<Stock>();
        String[] strings = source.split("\r\n|\n|\r");
        for(String line : strings) {
        	String symbol = line.substring(0, line.indexOf(','));
        	
        	line = line.substring(line.indexOf(','));
        	String[] data = line.split(symbol);
        	line = line.replaceAll("\"", "");
        	line = line.replaceAll(",", "");
        	for(int i = 0; i < data.length; i++) {
        		data[i] = data[i].replaceAll("\"", "");
        		data[i] = data[i].replaceAll(",", "");
        		data[i] = data[i].replaceAll("%", "");
        	}
        	
        	String name = data[0];
        	String exchange = data[1];
        	Board board;
        	try {
                board = Stock.Board.valueOf(exchange);
            }
            catch (java.lang.IllegalArgumentException exp) {
                board = Stock.Board.Unknown;
            }
        	double prevPrice = 0.0;
        	try { prevPrice = Double.valueOf(data[2]); } catch(NumberFormatException nfe) {}
        	
        	double openPrice = 0.0;
        	try { openPrice = Double.valueOf(data[3]); } catch(NumberFormatException nfe) {}
        	
        	double lastPrice = 0.0;
        	try { lastPrice = Double.valueOf(data[4]); } catch(NumberFormatException nfe) {}
        	
        	double highPrice = 0.0;
        	try { highPrice = Double.valueOf(data[5]); } catch(NumberFormatException nfe) {}
        	
        	double lowPrice = 0.0;
        	try { lowPrice = Double.valueOf(data[6]); } catch(NumberFormatException nfe) {}
        	
        	long volume = 0;
        	try { volume = Long.valueOf(data[7]); } catch(NumberFormatException nfe) {}
        	if(volume == 0) {
        		continue;
        	}
        	
        	double changePrice = 0.0;
        	try { changePrice = Double.valueOf(data[8]); } catch(NumberFormatException nfe) {}
        	
        	double changePricePercentage = 0.0;
        	try { changePricePercentage = Double.valueOf(data[9]); } catch(NumberFormatException nfe) {}
        	
        	int lastVolume = 0;
        	try { lastVolume = Integer.valueOf(data[10]); } catch(NumberFormatException nfe) {}
        	
        	double buyPrice = 0.0;
        	try { buyPrice = Double.valueOf(data[11]); } catch(NumberFormatException nfe) {}
        	
        	int buyQuantity = 0;
        	try { buyQuantity = Integer.valueOf(data[12]); } catch(NumberFormatException nfe) {}
        	
        	double sellPrice = 0.0;
        	try { sellPrice = Double.valueOf(data[13]); } catch(NumberFormatException nfe) {}
        	
        	int sellQuantity = 0;
        	try { sellQuantity = Integer.valueOf(data[14]); } catch(NumberFormatException nfe) {}
        	
        	double secondBuyPrice = 0.0;
        	try { secondBuyPrice = Double.valueOf(data[17]); } catch(NumberFormatException nfe) {}
        	
        	int secondBuyQuantity = 0;
        	
        	double secondSellPrice = 0.0;
        	try { secondSellPrice = Double.valueOf(data[18]); } catch(NumberFormatException nfe) {}
        	
        	int secondSellQuantity = 0;
        	double thirdBuyPrice = 0.0;
            int thirdBuyQuantity = 0;
            double thirdSellPrice = 0.0;
            int thirdSellQuantity = 0;
            java.util.Calendar calendar = Calendar.getInstance();
            
            SimpleDateFormat format = (SimpleDateFormat)DateFormat.getInstance();
            format.applyPattern("MM/dd/yyyy hh:mmaa");
            try {
				calendar.setTime(format.parse(data[15] + " " + data[16]));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			symbol = symbol.replaceAll("\"", "");
			symbol = symbol.replaceAll(",", "");
			Stock s = new Stock(
					Code.newInstance(symbol),
					Symbol.newInstance(name),
					name,
					board,
					Industry.Unknown,
					prevPrice,
					openPrice,
					lastPrice,
					highPrice,
					lowPrice,
					volume,
					changePrice,
					changePricePercentage,
					lastVolume,
					buyPrice,
					buyQuantity,
					sellPrice,
					sellQuantity,
					secondBuyPrice,
					secondBuyQuantity,
					secondSellPrice,
					secondSellQuantity,
					thirdBuyPrice,
					thirdBuyQuantity,
					thirdSellPrice,
					thirdSellQuantity,
					calendar
			);
			stocks.add(s);
        }
        return stocks;
    }

}
