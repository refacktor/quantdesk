package org.zigabyte.quantdesk;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import org.yccheok.jstock.engine.Stock;

public class AnalyzerUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar mainMenuBar = null;
	private JMenu fileMenu = new JMenu("File");
	private JMenu editMenu = new JMenu("Edit");
	private JMenu viewMenu = new JMenu("View");
	private JMenu portfolioMenu = new JMenu("Portfolio");
	private JMenu quotesMenu = new JMenu("Quotes");
	private JMenu screeningMenu = new JMenu("Screening");
	private JMenu researchMenu = new JMenu("Research");
	private JMenu favoritesMenu = new JMenu("Favorites");
	private JMenu toolsMenu = new JMenu("Tools");
	private JMenu helpMenu = new JMenu("Help");
	private JPanel toolBarPanel = null;

	private JToolBar navigationToolBar = null;
	private JButton btnUpdate = new JButton("Update");
	private JButton btnBasic = new JButton("Basic");
	private JButton btnAdvanced = new JButton("Advanced");
	private JButton btnWatchLists = new JButton("Watch Lists");
	private JButton btnCharting = new JButton("Charting");
	private JButton btnPortfolio = new JButton("Portfolio");
	private JButton btnResearch = new JButton("Research");
	private JButton btnTrading = new JButton("Trading");
	private JButton btnIndicators = new JButton("Indicators");
	private JButton btnQuotes = new JButton("Quotes");

	private JToolBar toolToolBar = null;
	private JButton btnStrengthMeter = new JButton("Strength Meter");
	private JButton btnBullBearReport = new JButton("Bull:Bear Report");
	private JButton btnMarketBarometer = new JButton("Market Barometer");
	private JButton btnStrategyGuide = new JButton("Strategy Guide");
	private JButton btnTradersDictionary = new JButton("Trader's Dictionary");
	private JButton btnPreferences = new JButton("Preferences");

	private JToolBar researchToolBar = null;
	private JButton btnBack = new JButton("Back");
	private JButton btnForward = new JButton("Forward");
	private JButton btnStop = new JButton("Stop");
	private JButton btnRefresh = new JButton("Refresh");

	private JSplitPane mainSplitPane = null;
	private JSplitPane graphSplitPane = null;
	private JTabbedPane screenTabs = null;
	private JPanel basicPanel = null;
	private JPanel advancedPanel = null;
	private JPanel watchListsPanel = null;
	private JPanel chartPanel = null;
        private JPanel bottomPanel = new JPanel(new BorderLayout());
        private JProgressBar progressBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
	private JTabbedPane tableTabbedPane = null;
	private JScrollPane screeningScrollPane = null;
	private JTable screeningTable = null;
	private JScrollPane quotesScrollPane = null;
	private JTable quotesTable = null;
	private StockModel dataModel;
	private java.util.List<Stock> stocks = null;
	private Map<String, Stock> stockMap = null;
	private TimeSeries priceSeries;
	private TimeSeries volumeSeries;  //  @jve:decl-index=0:
	private JLabel statusBarLabel = null;  //  @jve:decl-index=0:visual-constraint="1033,658"
	private JComboBox stockComboBox = null;
	private TimeSeriesCollection priceDataset;
	private TimeSeriesCollection volumeDataset;

	abstract class AnalyzerUiAction extends AbstractAction {
		protected AnalyzerUI ui;
		public AnalyzerUiAction(AnalyzerUI ui, String text) {
			super(text);
			this.ui = ui;
		}
	}

	private Action actionExit = new ActionExit(this);
	class ActionExit extends AbstractAction {
		public ActionExit(AnalyzerUI ui) {
			super("Exit");
		        putValue(SHORT_DESCRIPTION, "Exits the application");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK)); 
		}
		public void actionPerformed(ActionEvent e) {
			System.out.println("Action:Exit");
			System.exit(0);
		}
	};

	private Action actionAbout = new ActionAbout(this);
	class ActionAbout extends AnalyzerUiAction {
		public ActionAbout(AnalyzerUI ui) {
			super(ui, "About");
		}
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(ui, "QuantDesk Integrated Desktop v0.1", "About", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private JMenuBar getMainMenuBar() {
		if (mainMenuBar == null) {
			mainMenuBar = new JMenuBar();

			mainMenuBar.add(fileMenu);
			fileMenu.add(new JMenuItem(actionExit));

			mainMenuBar.add(editMenu);
			
			mainMenuBar.add(viewMenu);
			viewMenu.add(SwingUtil.getLafMenu(this));
			
			mainMenuBar.add(portfolioMenu);
			mainMenuBar.add(quotesMenu);
			mainMenuBar.add(screeningMenu);
			mainMenuBar.add(researchMenu);
			mainMenuBar.add(favoritesMenu);
			mainMenuBar.add(toolsMenu);
			
			mainMenuBar.add(helpMenu);
			helpMenu.add(new JMenuItem(actionAbout));
		}
		return mainMenuBar;
	}

	private JPanel getToolBarPanel() {
		if (toolBarPanel == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(3);
			toolBarPanel = new JPanel();
			toolBarPanel.setLayout(gridLayout);
			toolBarPanel.add(getNavigationToolBar(), null);
			toolBarPanel.add(getToolToolBar(), null);
			toolBarPanel.add(getResearchToolBar(), null);
		}
		return toolBarPanel;
	}

	private JToolBar getNavigationToolBar() {
		if (navigationToolBar == null) {
			navigationToolBar = new JToolBar();
			navigationToolBar.add(btnUpdate);
			navigationToolBar.add(btnBasic);
			navigationToolBar.add(btnAdvanced);
			navigationToolBar.add(btnWatchLists);
			navigationToolBar.add(btnCharting);
			navigationToolBar.add(btnPortfolio);
			navigationToolBar.add(btnResearch);
			navigationToolBar.add(btnTrading);
			navigationToolBar.add(btnIndicators);
			navigationToolBar.add(btnQuotes);
		}
		return navigationToolBar;
	}

	private JToolBar getToolToolBar() {
		if (toolToolBar == null) {
			toolToolBar = new JToolBar();
			toolToolBar.add(btnStrengthMeter);
			toolToolBar.add(btnBullBearReport);
			toolToolBar.add(btnMarketBarometer);
			toolToolBar.add(btnStrategyGuide);
			toolToolBar.add(btnTradersDictionary);
			toolToolBar.add(btnPreferences);
			toolToolBar.add(getStockComboBox());
		}
		return toolToolBar;
	}

	private JToolBar getResearchToolBar() {
		if (researchToolBar == null) {
			researchToolBar = new JToolBar();
			researchToolBar.add(btnBack);
			researchToolBar.add(btnForward);
			researchToolBar.add(btnStop);
			researchToolBar.add(btnRefresh);
		}
		return researchToolBar;
	}

	private JSplitPane getMainSplitPane() {
		if (mainSplitPane == null) {
			mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			mainSplitPane.setDividerLocation(300);
			mainSplitPane.setLeftComponent(getGraphSplitPane());
			mainSplitPane.setRightComponent(getTableTabbedPane());
		}
		return mainSplitPane;
	}

	private JSplitPane getGraphSplitPane() {
		if (graphSplitPane == null) {
			graphSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			graphSplitPane.setLeftComponent(getScreenTabs());
			graphSplitPane.setRightComponent(getChartPanel());
		}
		return graphSplitPane;
	}

	private JTabbedPane getScreenTabs() {
		if (screenTabs == null) {
			screenTabs = new JTabbedPane(JTabbedPane.BOTTOM);
			screenTabs.addTab("Basic", null, getBasicPanel(), null);
			screenTabs.addTab("Advanced", null, getAdvancedPanel(), null);
			screenTabs.addTab("Watch Lists", null, getWatchListsPanel(), null);
		}
		return screenTabs;
	}

	private JPanel getBasicPanel() {
		if (basicPanel == null) {
			basicPanel = new BasicTabPanel();
			basicPanel.setName("Basic");
		}
		return basicPanel;
	}

	private JPanel getAdvancedPanel() {
		if (advancedPanel == null) {
			advancedPanel = new AdvancedTabPanel(this);
			advancedPanel.setName("Advanced");
		}
		return advancedPanel;
	}

	private JPanel getWatchListsPanel() {
		if (watchListsPanel == null) {
			watchListsPanel = new JPanel();
			watchListsPanel.setLayout(new GridBagLayout());
		}
		return watchListsPanel;
	}

	private JPanel getChartPanel() {
		if (chartPanel == null) {
			priceSeries = new TimeSeries("Price");
			priceDataset = new TimeSeriesCollection(priceSeries);
			volumeSeries = new TimeSeries("Volume");
			volumeDataset = new TimeSeriesCollection(volumeSeries);
			DateAxis timeAxis = new DateAxis("Time");
			NumberAxis priceAxis = new NumberAxis("Price");
			priceAxis.setAutoRangeIncludesZero(false);
			NumberAxis volumeAxis = new NumberAxis("Volume");
			volumeAxis.setUpperMargin(1.0);
			XYPlot pricePlot = new XYPlot(priceDataset, timeAxis, priceAxis, new StandardXYItemRenderer());
			Plot plot = new CombinedDomainXYPlot(timeAxis);

			pricePlot.setDataset(1, volumeDataset);
			pricePlot.setRangeAxis(1, volumeAxis);
			pricePlot.setRenderer(1, new XYBarRenderer());
			pricePlot.mapDatasetToRangeAxis(1, 1);
			
			((CombinedDomainXYPlot)plot).add(pricePlot);
			JFreeChart chart = new JFreeChart("", plot);
			chartPanel = new ChartPanel(chart);
		}
		return chartPanel;
	}

	private JTabbedPane getTableTabbedPane() {
		if (tableTabbedPane == null) {
			tableTabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
			tableTabbedPane.addTab("Screening", null, getScreeningScrollPane(), null);
			tableTabbedPane.addTab("Quotes", null, getQuotesScrollPane(), null);
		}
		return tableTabbedPane;
	}

	private JScrollPane getScreeningScrollPane() {
		if (screeningScrollPane == null) {
			screeningScrollPane = new JScrollPane();
			screeningScrollPane.setViewportView(getScreeningTable());
		}
		return screeningScrollPane;
	}

	public JTable getScreeningTable() {
		if (screeningTable == null) {
			screeningTable = new JTable(createTableModel());
			((DefaultTableModel)screeningTable.getModel()).setColumnIdentifiers(getTableHeaders());
			screeningTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int row = screeningTable.rowAtPoint(e.getPoint());
					String data = (String)((DefaultTableModel)screeningTable.getModel()).getValueAt(row, 2);
					new PlotUpdater(stockMap.get(data)).start();
				}
			});
		}
		return screeningTable;
	}

	private TableModel createTableModel() {
		return new DefaultTableModel() {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		}; 
	}

	private Object[] getTableHeaders() {
		//"Strength Meter", "Bull:Bear", "Symbol", "Industry", "Close", "Volume", "Price Chg", "% Price Chg", "Prev"
		return new String[] {
				"Strength Meter",
				"Bull:Bear",
				"Symbol",
				"Industry",
				"Close",
				"Volume",
				"Price Chg",
				"% Price Chg",
				"Prev",
				"Dividend/Share",
				"Dividend Yield"
		};
		
	}

	private JScrollPane getQuotesScrollPane() {
		if (quotesScrollPane == null) {
			quotesScrollPane = new JScrollPane();
			quotesScrollPane.setViewportView(getQuotesTable());
		}
		return quotesScrollPane;
	}

	private JTable getQuotesTable() {
		if (quotesTable == null) {
			quotesTable = new JTable(createTableModel());
			((DefaultTableModel)quotesTable.getModel()).setColumnIdentifiers(getTableHeaders());
			quotesTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int row = quotesTable.rowAtPoint(e.getPoint());
					String data = (String)((DefaultTableModel)quotesTable.getModel()).getValueAt(row, 2);
					new PlotUpdater(stockMap.get(data)).start();
				}
			});
		}
		return quotesTable;
	}
	
	public StockModel getDataModel() {
		return dataModel;
	}

	private JLabel getStatusBarLabel() {
		if (statusBarLabel == null) {
			statusBarLabel = new JLabel("Ready");
		}
		return statusBarLabel;
	}

	private JComboBox getStockComboBox() {
		if (stockComboBox == null) {
			stockComboBox = new JComboBox();
			stockComboBox.setEditable(true);
			stockComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String symbol = (String)stockComboBox.getSelectedItem();
					DefaultTableModel quoteModel = (DefaultTableModel)quotesTable.getModel();
					if(stockMap.containsKey(symbol)) {
						new PlotUpdater(stockMap.get(symbol)).start();
						int found = -1;
						for(int i = 0; i < quotesTable.getRowCount(); i++) {
							String name = (String)quotesTable.getModel().getValueAt(i, 2);
							if(name.equalsIgnoreCase(symbol)) {
								found = i;
								break;
							}
						}
						if(found == -1) {
							quoteModel.addRow(Utils.getRowString(dataModel.getStock(symbol)));
						}
						else {
							quoteModel.removeRow(found);
							quoteModel.addRow(Utils.getRowString(dataModel.getStock(symbol)));
						}
					}
					else {
						setStatusBar("Symbol " + symbol + " not found.");
					}
				}
			});
		}
		return stockComboBox;
	}

	public static void main(String[] args) {
		AnalyzerUI ui = new AnalyzerUI();
		ui.setVisible(true);
		ui.updateStocksData();
	}

	/**
	 * This is the default constructor
	 */
	public AnalyzerUI() {
		dataModel = new StockModel(this);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.setSize(1057, 633);
		this.setJMenuBar(getMainMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("QuantDesk Integrated Desktop");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void updateStocksData() {
		this.stocks = dataModel.getAllStocks();
		this.stockMap = dataModel.getStockMap();
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel(new BorderLayout());
			jContentPane.add(getToolBarPanel(), BorderLayout.NORTH);
			jContentPane.add(getMainSplitPane(), BorderLayout.CENTER);
			jContentPane.add(bottomPanel, BorderLayout.SOUTH);
                        bottomPanel.add(getStatusBarLabel(), BorderLayout.NORTH);
                        bottomPanel.add(progressBar, BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	
	public synchronized void setStatusBar(String text) {
		getStatusBarLabel().setText(text); System.out.println("STATUS: " + text);
	}

	public synchronized void setProgressBarLimits(int min, int max) {
		// Prevent min>max situation by caller's mistake:
		progressBar.setMinimum(Math.min(min, max));
		progressBar.setMaximum(Math.max(min, max));
	}
	
	public synchronized void setProgressBarValue(int n) {
		int max = progressBar.getMaximum();
		int min = progressBar.getMinimum();
		if (n > max) n = max;
		if (n < min) n = min;
		progressBar.setValue(n);
        }

	public synchronized void setStatusInfo(int min, int value, int max, String text) {
		setProgressBarLimits(min, max);
		setProgressBarValue(value);
		setStatusBar(text);
		this.repaint();
	}

	private class PlotUpdater extends Thread {
		private Stock stock;
		public PlotUpdater(Stock stock) {
			this.stock = stock;
		}
		
		public void run() {
			setStatusBar("Plotting data for stock: " + stock.getCode());
			priceSeries.clear();
			volumeSeries.clear();
			MyYahooStockHistoryServer s = (MyYahooStockHistoryServer)dataModel.getStockData(stock);
			TimeSeries tempPrice = new TimeSeries("Price");
			TimeSeries tempVolume = new TimeSeries("Volume");
			int numEntries = s.getNumOfCalendar();
			for(int i = 0; i < numEntries; i++) {
				Calendar c = s.getCalendar(i);
				Stock item = s.getStock(c);
				Day d = new Day(c.getTime());
				double price = item.getLastPrice();
				long volume = item.getVolume();
				tempPrice.addOrUpdate(d, price);
				tempVolume.addOrUpdate(d, volume);
			}
			priceDataset.addSeries(tempPrice);
			volumeDataset.addSeries(tempVolume);
			try {
				priceDataset.removeSeries(0);
				volumeDataset.removeSeries(0);
			}
			catch(IndexOutOfBoundsException iobe) {
			}
			setStatusBar("Finished Plotting data");
		}
	}

}  //  @jve:decl-index=0:visual-constraint="12,13"
