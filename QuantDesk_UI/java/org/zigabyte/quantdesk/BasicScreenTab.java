package org.zigabyte.quantdesk;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.yccheok.jstock.engine.Country;
import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.Symbol;

public class BasicScreenTab extends Composite {
	
	private FormLayout layout;
	private AnalyzerUI mainUI;
	private final Map<String, Stock> found = new HashMap<String, Stock>();
	private final EasyScreens screens = new EasyScreens();
	
	public BasicScreenTab(Composite parent, AnalyzerUI main) {
		super(parent, SWT.NONE);
		this.mainUI = main;
		layout = new FormLayout();
		Label catLabel = new Label(this, SWT.HORIZONTAL);
		catLabel.setText("Categories:");
		catLabel.pack();
		FormData catLabelData = new FormData();
		catLabelData.left = new FormAttachment(0);
		catLabel.setLayoutData(catLabelData);
		
		Combo catCombo = new Combo(this, SWT.READ_ONLY);
		String[] categories = new String[] {
			"Up Trending Stocks",
			"Weak Stocks",
			"Winners/Losers",
			"Overvalued",
			"Undervalued",
			"Trending Indicators",
			"Short Term",
			"Grab Bag",
			"IPOs for the Rest of Us",
			"Industries",
			"Potential Bearish Reversals Shorts",
			"Potential Bearish Reversals Long",
			"Bearish Candlestick Reversals"
		};
		catCombo.setItems(categories);
		catCombo.pack();
		FormData catComboData = new FormData();
		catComboData.left = new FormAttachment(catLabel, 5, SWT.RIGHT);
		catLabelData.top = new FormAttachment(catCombo, 0, SWT.CENTER);
		catCombo.setLayoutData(catComboData);
		
		Label easyLabel = new Label(this, SWT.HORIZONTAL);
		easyLabel.setText("Easy Screens");
		easyLabel.pack();
		FormData easyLabelData = new FormData();
		easyLabelData.top = new FormAttachment(catLabel, 10, SWT.BOTTOM);
		easyLabelData.left = new FormAttachment(0);
		easyLabel.setLayoutData(easyLabelData);
		
		
		List easyList = new List(this, SWT.SINGLE | SWT.BORDER);
		/*String[] easyScreens = new String[] {
			"3 Higher Days @ 52 Week High",
			"Breakout Bull",
			"Bullish Breakout on Big Volume",
			"Bull Bull Bull",
			"High Earnings Growth Over 12 Months",
			"High Relative Strength (RSI)",
			"IBD New Highs with Strong Earnings Growth",
			"New Highs on Volume Growth",
			"Price Volume Explosions",
			"Range Riders",
			"Steady Climbers (High ADX & High RSI)",
			"Strong Last 30 Days",
			"Strong Last 5 Days",
			"Up Trending Stocks Under $10"
		};*/
		//easyList.setItems(easyScreens);
		easyList.setItems(screens.getScreeningNames());
		easyList.pack();
		easyList.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				String selection = ((List)evt.widget).getSelection()[0];
				ScanningThread t = new ScanningThread(selection);
				t.start();
			}
		});
		FormData easyListData = new FormData();
		easyListData.top = new FormAttachment(easyLabel, 5, SWT.BOTTOM);
		easyListData.left = new FormAttachment(0);
		easyListData.right = new FormAttachment(100);
		easyList.setLayoutData(easyListData);
		
		Text infoText = new Text(this, SWT.V_SCROLL | SWT.BORDER);
		FormData infoTextData = new FormData();
		infoTextData.top = new FormAttachment(easyList, 5, SWT.MULTI | SWT.BOTTOM);
		infoTextData.left = new FormAttachment(0);
		infoTextData.right = new FormAttachment(100);
		infoText.pack();
		infoText.setLayoutData(infoTextData);
		
		Label betweenPrice = new Label(this, SWT.HORIZONTAL);
		betweenPrice.setText("Price is between");
		FormData priceLabelData = new FormData();
		priceLabelData.top = new FormAttachment(infoText, 5, SWT.BOTTOM);
		priceLabelData.left = new FormAttachment(0);
		betweenPrice.pack();
		betweenPrice.setLayoutData(priceLabelData);
		
		Text fromPrice = new Text(this, SWT.SINGLE | SWT.BORDER);
		FormData fromPriceData = new FormData();
		fromPriceData.left = new FormAttachment(0);
		fromPriceData.top = new FormAttachment(betweenPrice, 5, SWT.BOTTOM);
		fromPrice.pack();
		fromPrice.setLayoutData(fromPriceData);
		
		Label andLabel = new Label(this, SWT.HORIZONTAL);
		andLabel.setText("and");
		FormData andLabelData = new FormData();
		andLabelData.left = new FormAttachment(fromPrice, 5, SWT.RIGHT);
		andLabelData.top = new FormAttachment(fromPrice, 0, SWT.CENTER);
		andLabel.pack();
		andLabel.setLayoutData(andLabelData);
		
		Text toPrice = new Text(this, SWT.SINGLE | SWT.BORDER);
		FormData toPriceData = new FormData();
		toPriceData.left = new FormAttachment(andLabel, 5, SWT.RIGHT);
		toPriceData.top = new FormAttachment(andLabel, 0, SWT.CENTER);
		toPrice.pack();
		toPrice.setLayoutData(toPriceData);
		
		Label volumeLabel = new Label(this, SWT.HORIZONTAL);
		volumeLabel.setText("Volume is greater than");
		FormData volumeLabelData = new FormData();
		volumeLabelData.top = new FormAttachment(fromPrice, 5, SWT.BOTTOM);
		volumeLabelData.left = new FormAttachment(0);
		volumeLabel.pack();
		volumeLabel.setLayoutData(volumeLabelData);
		
		Text volumeFilter = new Text(this, SWT.SINGLE | SWT.BORDER);
		FormData volumeFilterData = new FormData();
		volumeFilterData.top = new FormAttachment(volumeLabel, 5, SWT.BOTTOM);
		volumeFilterData.left = new FormAttachment(0);
		volumeFilter.pack();
		volumeFilter.setLayoutData(volumeFilterData);
		
		Button executeButton = new Button(this, SWT.PUSH);
		executeButton.setText("Execute");
		FormData executeButtonData = new FormData();
		executeButtonData.top = new FormAttachment(betweenPrice, 10, SWT.CENTER);
		executeButtonData.right = new FormAttachment(100);
		executeButton.pack();
		executeButton.setLayoutData(executeButtonData);
		
		Button defaultsButton = new Button(this, SWT.PUSH);
		defaultsButton.setText("Defaults");
		FormData defaultsButtonData = new FormData();
		defaultsButtonData.top = new FormAttachment(executeButton, 0, SWT.BOTTOM);
		defaultsButtonData.right = new FormAttachment(100);
		defaultsButton.pack();
		defaultsButton.setLayoutData(defaultsButtonData);
		
		Button editButton = new Button(this, SWT.PUSH);
		editButton.setText("Edit");
		FormData editButtonData = new FormData();
		editButtonData.top = new FormAttachment(defaultsButton, 0, SWT.BOTTOM);
		editButtonData.right = new FormAttachment(100);
		editButton.pack();
		editButton.setLayoutData(editButtonData);
		
		executeButtonData.left = new FormAttachment(defaultsButton, 0, SWT.LEFT);
		editButtonData.left = new FormAttachment(defaultsButton, 0, SWT.LEFT);
		
		this.setLayout(layout);
		this.pack();
	}
	
	private class ScanningThread extends Thread {
		
		private String name;
		
		public ScanningThread(String name) {
			this.name = name;
		}
		
		@Override
		public void run() {
			System.out.println("Number of stocks: " + mainUI.stocks.size());
			found.clear();
			mainUI.display.asyncExec(new Runnable() {
				public void run() {
					mainUI.scanTable.clearAll();
				}
			});
			int i = 0;
			for(i = 0; i < mainUI.stocks.size(); i++) {
				try {
					if(mainUI.display.isDisposed()) {
						return;
					}
					Stock stock = mainUI.stocks.get(i);
					System.out.println("Scanning stock " + stock.getCode() + " " + i + " " + mainUI.stocks.size());
					if(screens.stockMatches(name, new MyYahooStockHistoryServer(Country.UnitedState, stock.getCode()))) {
						System.out.println("Stock " + stock.getCode().toString() + " matches");
						MyYahooStockServer summary = new MyYahooStockServer(Country.UnitedState);
						stock = summary.getStock(stock.getCode());
						String[] rowData = new String[] {
							"",
							"",
							stock.getCode().toString(),
							stock.getIndustry().toString(),
							String.valueOf(stock.getLastPrice()),
							String.valueOf(stock.getVolume()),
							String.valueOf(stock.getChangePrice()),
							String.valueOf(stock.getChangePricePercentage()),
							String.valueOf(stock.getPrevPrice())
						};
						found.put(stock.getCode().toString(), stock);
						mainUI.display.asyncExec(new DataUpdater(rowData));					}
				}
				catch (Exception e) {
				}
			}
		}
	}
	
	private class DataUpdater extends Thread {
		String[] data;
		public DataUpdater(String[] data) {
			this.data = data;
		}
		
		public void run() {
			TableItem item = new TableItem(mainUI.scanTable, SWT.NONE);
			item.setText(data);
			item.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event evt) {
					TableItem i = (TableItem)evt.widget;
					mainUI.plotData(found.get(i.getText(2)));
				}
			});
		}
	}
}