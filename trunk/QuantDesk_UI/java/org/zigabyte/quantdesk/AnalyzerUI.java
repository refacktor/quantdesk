package org.zigabyte.quantdesk;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.yccheok.jstock.engine.Country;
import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.StockHistoryNotFoundException;
import org.yccheok.jstock.engine.StockNotFoundException;
import org.yccheok.jstock.engine.Symbol;

public class AnalyzerUI implements UI{
	private static final String[] navigationButtons = new String[] {"Update", "Basic", "Advanced", "Watch Lists", "Charting", "Portfolio", "Research", "Trading", "Indicators", "Quotes"};
	private static final String[] toolButtons = new String[] {"Strength Meter", "Bull:Bear Report", "Market Barometer", "Strategy Guide", "Trader's Dictionary", "Preferences"};
	private static final String[] researchButtons = new String[] {"Back", "Forward", "Stop", "Refresh"};
	
	private DataModel model;
	private Table quotesTable;
	private Plot plot;
	private ValueAxis timeAxis;
	private NumberAxis priceAxis;
	private NumberAxis volumeAxis;
	private XYDataset priceDataset;
	private XYDataset volumeDataset;
	private final Shell shell;
	private JFreeChart chart;
	private ChartComposite chartComposite;
	public TimeSeries priceSeries;
	public TimeSeries volumeSeries;
	public Table scanTable;
	public List<Stock> stocks;
	public Map<String, Stock> stockMap;
	public final Display display = new Display();
	
	public AnalyzerUI(DataModel model) {
		this.model = model;
		model.addObserver(this);
		shell = new Shell(display);
		shell.open();
		
		setupComponents();
		setupListeners();
		
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
		model.stop();
		System.exit(0);
	}
	
	// Note: the tool bar items aren't set here. Check createToolBar
	private void setupComponents() {
		shell.setText("Analyzer UI");
		FormLayout layout = new FormLayout();
		shell.setLayout(layout);
		
		FormData toolBarData = new FormData();
		toolBarData.left = new FormAttachment(0);
		toolBarData.right = new FormAttachment(100);
		toolBarData.top = new FormAttachment(0);
		
		FormData mainPaneData = new FormData();
		mainPaneData.left = new FormAttachment(0);
		mainPaneData.right = new FormAttachment(100);
		
		Menu bar = createMenu(shell);
		shell.setMenuBar(bar);
		CoolBar cb = new CoolBar(shell, SWT.BORDER);
		createToolBar(navigationButtons, cb);
		createToolBar(toolButtons, cb);
		createToolBar(researchButtons, cb);
		createTickerToolBar(cb);
		cb.setLayoutData(toolBarData);
		cb.pack();
		
		SashForm pane = new SashForm(shell, SWT.VERTICAL);
		mainPaneData.top = new FormAttachment(cb, 5, SWT.BOTTOM);
		mainPaneData.bottom = new FormAttachment(100);
		pane.setLayout(new FillLayout());
		pane.setLayoutData(mainPaneData);
		
		SashForm screeningPane = new SashForm(pane, SWT.HORIZONTAL | SWT.BORDER);
		TabFolder leftPaneTabs = new TabFolder(screeningPane, SWT.BOTTOM);
		/*TabItem basicTab = new TabItem(leftPaneTabs, SWT.NONE);
		basicTab.setText("Basic");
		basicTab.setControl(new BasicScreenTab(leftPaneTabs, this));*/
		
		TabItem advancedTab = new TabItem(leftPaneTabs, SWT.NONE);
		advancedTab.setText("Advanced");
		advancedTab.setControl(new AdvancedScreenTab(leftPaneTabs, this));
		
		TabItem watchListTab = new TabItem(leftPaneTabs, SWT.NONE);
		watchListTab.setText("Watch Lists");
		watchListTab.setControl(new Composite(leftPaneTabs, SWT.NONE));
		
		TabFolder graphFolder = new TabFolder(screeningPane, SWT.BOTTOM);
		TabItem graphItem = new TabItem(graphFolder, SWT.NONE);
		graphItem.setText("Charting");
		
		priceSeries = new TimeSeries("Price");
		priceDataset = new TimeSeriesCollection(priceSeries);
		volumeSeries = new TimeSeries("Volume");
		volumeDataset = new TimeSeriesCollection(volumeSeries);
		timeAxis = new DateAxis("Time");
		priceAxis = new NumberAxis("Price");
		priceAxis.setAutoRangeIncludesZero(false);
		volumeAxis = new NumberAxis("Volume");
		volumeAxis.setUpperMargin(1.0);
		XYPlot pricePlot = new XYPlot(priceDataset, timeAxis, priceAxis, new StandardXYItemRenderer());
		plot = new CombinedDomainXYPlot(timeAxis);

		pricePlot.setDataset(1, volumeDataset);
		pricePlot.setRangeAxis(1, volumeAxis);
		pricePlot.setRenderer(1, new XYBarRenderer());
		pricePlot.mapDatasetToRangeAxis(1, 1);
		
		((CombinedDomainXYPlot)plot).add(pricePlot);
		chart = new JFreeChart("", plot);
		chartComposite = new ChartComposite(graphFolder, SWT.NONE, chart);
		graphItem.setControl(chartComposite);
		
		screeningPane.setWeights(new int[] {20, 80});
		screeningPane.setLayout(new FillLayout());
		screeningPane.pack();
		
		TabFolder infoFolder = new TabFolder(pane, SWT.BOTTOM);
		TabItem indicatorTab = new TabItem(infoFolder, SWT.NONE);
		indicatorTab.setText("Indicators");
		String[] headers = new String[] {"Strength Meter", "Bull:Bear", "Symbol", "Industry", "Close", "Volume", "Price Chg", "% Price Chg", "Prev", "Dividend/Share", "Dividend Yield"};
		
		scanTable = new Table(infoFolder, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		scanTable.setLinesVisible(true);
		scanTable.setHeaderVisible(true);
		for(String header : headers) {
			TableColumn column = new TableColumn(scanTable, SWT.NONE);
			column.setText(header);
			column.pack();
		}
		
		scanTable.pack();
		indicatorTab.setControl(scanTable);
		
		TabItem quotesTab = new TabItem(infoFolder, SWT.NONE);
		quotesTab.setText("Quotes");
		
		quotesTable = new Table(infoFolder, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		quotesTable.setLinesVisible(true);
		quotesTable.setHeaderVisible(true);
		for(String header : headers) {
			TableColumn column = new TableColumn(quotesTable, SWT.NONE);
			column.setText(header);
			column.pack();
		}
		quotesTable.pack();
		
		new StockThread().start();
		
		quotesTab.setControl(quotesTable);
		
		pane.setWeights(new int[] {80, 20});
		
		pane.pack();
		shell.pack();
	}
	
	private void setupListeners() {
		scanTable.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event evt) {
				TableItem item = scanTable.getItem(new Point(evt.x, evt.y));
				plotData(stockMap.get(item.getText(2)));
			}
		});
		
		quotesTable.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event evt) {
				TableItem item = quotesTable.getItem(new Point(evt.x, evt.y));
				plotData(stockMap.get(item.getText(2)));
			}
		});
		
		shell.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event e) {
				shell.layout();
			}
		});
	}
	
	public void plotData(Stock s) {
		//display.asyncExec(new PlotThread(s));
		new PlotThread(s).start();
	}

	private Menu createMenu(Shell shell) {
		Menu bar = new Menu(shell, SWT.BAR);
		MenuItem file = new MenuItem(bar, SWT.CASCADE);
		file.setText("&File");
		
		MenuItem edit = new MenuItem(bar, SWT.CASCADE);
		edit.setText("&Edit");
		
		MenuItem view = new MenuItem(bar, SWT.CASCADE);
		view.setText("&View");
		
		MenuItem portfolio = new MenuItem(bar, SWT.CASCADE);
		portfolio.setText("&Portfolio");
		
		MenuItem quotes = new MenuItem(bar, SWT.CASCADE);
		quotes.setText("Q&uotes");
		
		MenuItem screening = new MenuItem(bar, SWT.CASCADE);
		screening.setText("S&creening");
		
		MenuItem research = new MenuItem(bar, SWT.CASCADE);
		research.setText("&Research");
		
		MenuItem favorites = new MenuItem(bar, SWT.CASCADE);
		favorites.setText("F&avorites");
		
		MenuItem tools = new MenuItem(bar, SWT.CASCADE);
		tools.setText("&Tools");
		
		MenuItem help = new MenuItem(bar, SWT.CASCADE);
		help.setText("&Help");
		return bar;
	}
	
	private CoolItem createToolBar(String[] names, CoolBar parent) {
		ToolBar tb = new ToolBar(parent, SWT.FLAT);
		for(String name : names) {
			ToolItem ti = new ToolItem(tb, SWT.PUSH);
			ti.setText(name);
		}
		tb.pack();
		Point size = tb.getSize();
		CoolItem ci = new CoolItem(parent, SWT.NONE);
		ci.setControl(tb);
		Point preferred = ci.computeSize(size.x, size.y);
		ci.setPreferredSize(preferred);
		return ci;
	}
	
	private CoolItem createTickerToolBar(CoolBar parent) {
		ToolBar tb = new ToolBar(parent, SWT.FLAT);
		ToolItem go = new ToolItem(tb, SWT.PUSH);
		go.setText("Go");
		Combo combo = new Combo(tb, SWT.DROP_DOWN);
		// find saved symbols
		combo.pack();
		combo.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e) {
				// Update charts
				System.out.println("Combo box: " + e.widget);
				String symbolText = ((Combo)e.widget).getText();
				boolean found = false;
				for(String item : ((Combo)e.widget).getItems()) {
					if(item.equalsIgnoreCase(symbolText)) {
						found = true;
						break;
					}
				}
				if(!found) {
					((Combo)e.widget).add(((Combo)e.widget).getText());
				}
				if(symbolText.length() > 0) {
					// Get stock info
					try {
						MyYahooStockServer summary = new MyYahooStockServer(Country.UnitedState);
						Stock s = summary.getStock(Symbol.newInstance(symbolText));
						plotData(s);
						setTableRow(s);
						updateStocks(s);
						System.out.println(s);
					}
					catch(StockNotFoundException exception) {
					}
				}
			}
		});
		ToolItem sep = new ToolItem(tb, SWT.SEPARATOR);
		sep.setWidth(combo.getSize().x);
		sep.setControl(combo);
		ToolItem quotes = new ToolItem(tb, SWT.PUSH);
		quotes.setText("Quotes");
		ToolItem update = new ToolItem(tb, SWT.PUSH);
		update.setText("Update");
		tb.pack();
		Point size = tb.getSize();
		CoolItem ci = new CoolItem(parent, SWT.NONE);
		ci.setControl(tb);
		Point preferred = ci.computeSize(size.x, size.y);
		ci.setPreferredSize(preferred);
		return ci;
	}
	
	private void setTableRow(Stock s) {
		//"Strength Meter", "Bull:Bear", "Symbol", "Industry", "Close", "Volume", "Price Chg", "% Price Chg", "Prev"
		String[] data = Utils.getRowString(s);
		
		for(TableItem item : quotesTable.getItems()) {
			if(item.getText(2).equals(s.getCode().toString())) {
				item.setText(data);
				return;
			}
		}
		TableItem row = new TableItem(quotesTable, SWT.NONE);
		row.setText(data);
	}
	
	public synchronized void updateStocks(Stock s) {
		stockMap.put(s.getCode().toString(), s);
		for(TableItem item : scanTable.getItems()) {
			if(item.getText(2).equals(s.getCode().toString())) {
				String[] data = Utils.getRowString(s);
				item.setText(data);
			}
		}
		for(TableItem item : quotesTable.getItems()) {
			if(item.getText(2).equals(s.getCode().toString())) {
				setTableRow(s);
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		StockModel model = new StockModel();
		new AnalyzerUI(model);
	}
	
	private class StockThread extends Thread {
		
		public void run() {
			try {
				System.out.println("Starting to scan for stocks");
				stocks = model.getAllStocks();
				stockMap = model.getStockMap();
				System.out.println("Finished scanning for stocks");
			}
			catch(Exception e) {
			}
		}
	}
	
	private class PlotThread extends Thread {
		
		private Stock s;
		
		public PlotThread(Stock s) {
			this.s = s;
		}
		
		public void run() {
			try {
				MyYahooStockHistoryServer server = new MyYahooStockHistoryServer(Country.UnitedState, s.getCode());
				display.asyncExec(new PlotDataUpdater(server));
			}
			catch(StockHistoryNotFoundException ex2) {
			}
		}
		
		private class PlotDataUpdater extends Thread {
			MyYahooStockHistoryServer server;
			public PlotDataUpdater(MyYahooStockHistoryServer server) {
				this.server = server;
			}
			
			public void run() {
				int numCalendar = server.getNumOfCalendar();
				chart.setTitle(s.getSymbol().toString());
				TimeSeries tempPrice = new TimeSeries("Price");
				TimeSeries tempVolume = new TimeSeries("Volume");
				priceSeries.clear();
				volumeSeries.clear();
				for(int i = 0; i < numCalendar; i++) {
					Calendar date = server.getCalendar(i);
					Stock data = server.getStock(date);
					Day day = new Day(date.getTime());
					double price = data.getLastPrice();
					long volume = data.getVolume();
					tempPrice.add(day, price);
					tempVolume.add(day, volume);
				}
				priceSeries.addAndOrUpdate(tempPrice);
				volumeSeries.addAndOrUpdate(tempVolume);
			}
		}
	}

	@Override
	public void update(Observable obs, Object arg) {
		// TODO Auto-generated method stub
		
	}
}