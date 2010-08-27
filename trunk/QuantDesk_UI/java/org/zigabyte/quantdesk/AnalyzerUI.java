package org.zigabyte.quantdesk;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
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
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class AnalyzerUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar mainMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu editMenu = null;
	private JMenu viewMenu = null;
	private JMenu portfolioMenu = null;
	private JMenu quotesMenu = null;
	private JMenu screeningMenu = null;
	private JMenu researchMenu = null;
	private JMenu favoritesMenu = null;
	private JMenu toolsMenu = null;
	private JMenu helpMenu = null;
	private JPanel toolBarPanel = null;
	private JToolBar navigationToolBar = null;
	private JButton updateButton = null;
	private JButton basicButton = null;
	private JButton advancedButton = null;
	private JButton watchListButton = null;
	private JButton chartingButton = null;
	private JButton portfolioButton = null;
	private JButton researchButton = null;
	private JButton tradingButton = null;
	private JButton indicatorsButton = null;
	private JButton quotesButton = null;
	private JToolBar toolToolBar = null;
	private JButton strengthButton = null;
	private JButton bullBearButton = null;
	private JButton marketBarometerButton = null;
	private JButton strategyButton = null;
	private JButton traderButton = null;
	private JButton preferencesButton = null;
	private JToolBar researchToolBar = null;
	private JButton backButton = null;
	private JButton forwardButton = null;
	private JButton stopButton = null;
	private JButton refreshButton = null;
	private JSplitPane mainSplitPane = null;
	private JSplitPane graphSplitPane = null;
	private JTabbedPane screenTabs = null;
	private JPanel basicPanel = null;
	private JPanel advancedPanel = null;
	private JPanel watchListsPanel = null;
	private JPanel chartPanel = null;
	private JTabbedPane tableTabbedPane = null;
	private JScrollPane screeningScrollPane = null;
	private JTable screeningTable = null;
	private JScrollPane quotesScrollPane = null;
	private JTable quotesTable = null;
	private DataModel dataModel = null;
	private List<Stock> stocks = null;
	private Map<String, Stock> stockMap = null;
	private TimeSeries priceSeries;
	private TimeSeries volumeSeries;  //  @jve:decl-index=0:
	private JLabel statusBarLabel = null;  //  @jve:decl-index=0:visual-constraint="1033,658"
	private JComboBox stockComboBox = null;
	private TimeSeriesCollection priceDataset;
	private TimeSeriesCollection volumeDataset;
	/**
	 * This method initializes mainMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMainMenuBar() {
		if (mainMenuBar == null) {
			mainMenuBar = new JMenuBar();
			mainMenuBar.add(getFileMenu());
			mainMenuBar.add(getEditMenu());
			mainMenuBar.add(getViewMenu());
			mainMenuBar.add(getPortfolioMenu());
			mainMenuBar.add(getQuotesMenu());
			mainMenuBar.add(getScreeningMenu());
			mainMenuBar.add(getResearchMenu());
			mainMenuBar.add(getFavoritesMenu());
			mainMenuBar.add(getToolsMenu());
			mainMenuBar.add(getHelpMenu());
		}
		return mainMenuBar;
	}

	/**
	 * This method initializes fileMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
		}
		return fileMenu;
	}

	/**
	 * This method initializes editMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu();
			editMenu.setText("Edit");
		}
		return editMenu;
	}

	/**
	 * This method initializes viewMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getViewMenu() {
		if (viewMenu == null) {
			viewMenu = new JMenu();
			viewMenu.setText("View");
		}
		return viewMenu;
	}

	/**
	 * This method initializes portfolioMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getPortfolioMenu() {
		if (portfolioMenu == null) {
			portfolioMenu = new JMenu();
			portfolioMenu.setText("Portfolio");
		}
		return portfolioMenu;
	}

	/**
	 * This method initializes quotesMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getQuotesMenu() {
		if (quotesMenu == null) {
			quotesMenu = new JMenu();
			quotesMenu.setText("Quotes");
		}
		return quotesMenu;
	}

	/**
	 * This method initializes screeningMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getScreeningMenu() {
		if (screeningMenu == null) {
			screeningMenu = new JMenu();
			screeningMenu.setText("Screening");
		}
		return screeningMenu;
	}

	/**
	 * This method initializes researchMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getResearchMenu() {
		if (researchMenu == null) {
			researchMenu = new JMenu();
			researchMenu.setText("Research");
		}
		return researchMenu;
	}

	/**
	 * This method initializes favoritesMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFavoritesMenu() {
		if (favoritesMenu == null) {
			favoritesMenu = new JMenu();
			favoritesMenu.setText("Favorites");
		}
		return favoritesMenu;
	}

	/**
	 * This method initializes toolsMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getToolsMenu() {
		if (toolsMenu == null) {
			toolsMenu = new JMenu();
			toolsMenu.setText("Tools");
		}
		return toolsMenu;
	}

	/**
	 * This method initializes helpMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
		}
		return helpMenu;
	}

	/**
	 * This method initializes toolBarPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
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

	/**
	 * This method initializes navigationToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getNavigationToolBar() {
		if (navigationToolBar == null) {
			navigationToolBar = new JToolBar();
			navigationToolBar.add(getUpdateButton());
			navigationToolBar.add(getBasicButton());
			navigationToolBar.add(getAdvancedButton());
			navigationToolBar.add(getWatchListButton());
			navigationToolBar.add(getChartingButton());
			navigationToolBar.add(getPortfolioButton());
			navigationToolBar.add(getResearchButton());
			navigationToolBar.add(getTradingButton());
			navigationToolBar.add(getIndicatorsButton());
			navigationToolBar.add(getQuotesButton());
		}
		return navigationToolBar;
	}

	/**
	 * This method initializes updateButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getUpdateButton() {
		if (updateButton == null) {
			updateButton = new JButton();
			updateButton.setText("Update");
		}
		return updateButton;
	}

	/**
	 * This method initializes basicButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBasicButton() {
		if (basicButton == null) {
			basicButton = new JButton();
			basicButton.setText("Basic");
		}
		return basicButton;
	}

	/**
	 * This method initializes advancedButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAdvancedButton() {
		if (advancedButton == null) {
			advancedButton = new JButton();
			advancedButton.setText("Advanced");
		}
		return advancedButton;
	}

	/**
	 * This method initializes watchListButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getWatchListButton() {
		if (watchListButton == null) {
			watchListButton = new JButton();
			watchListButton.setText("Watch Lists");
		}
		return watchListButton;
	}

	/**
	 * This method initializes chartingButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getChartingButton() {
		if (chartingButton == null) {
			chartingButton = new JButton();
			chartingButton.setText("Charting");
		}
		return chartingButton;
	}

	/**
	 * This method initializes portfolioButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPortfolioButton() {
		if (portfolioButton == null) {
			portfolioButton = new JButton();
			portfolioButton.setText("Portfolio");
		}
		return portfolioButton;
	}

	/**
	 * This method initializes researchButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getResearchButton() {
		if (researchButton == null) {
			researchButton = new JButton();
			researchButton.setText("Research");
		}
		return researchButton;
	}

	/**
	 * This method initializes tradingButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getTradingButton() {
		if (tradingButton == null) {
			tradingButton = new JButton();
			tradingButton.setText("Trading");
		}
		return tradingButton;
	}

	/**
	 * This method initializes indicatorsButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIndicatorsButton() {
		if (indicatorsButton == null) {
			indicatorsButton = new JButton();
			indicatorsButton.setText("Indicators");
		}
		return indicatorsButton;
	}

	/**
	 * This method initializes quotesButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getQuotesButton() {
		if (quotesButton == null) {
			quotesButton = new JButton();
			quotesButton.setText("Quotes");
		}
		return quotesButton;
	}

	/**
	 * This method initializes toolToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getToolToolBar() {
		if (toolToolBar == null) {
			toolToolBar = new JToolBar();
			toolToolBar.add(getStrengthButton());
			toolToolBar.add(getBullBearButton());
			toolToolBar.add(getMarketBarometerButton());
			toolToolBar.add(getStrategyButton());
			toolToolBar.add(getTraderButton());
			toolToolBar.add(getPreferencesButton());
			toolToolBar.add(getStockComboBox());
		}
		return toolToolBar;
	}

	/**
	 * This method initializes strengthButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getStrengthButton() {
		if (strengthButton == null) {
			strengthButton = new JButton();
			strengthButton.setText("Strength Meter");
		}
		return strengthButton;
	}

	/**
	 * This method initializes bullBearButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBullBearButton() {
		if (bullBearButton == null) {
			bullBearButton = new JButton();
			bullBearButton.setText("Bull:Bear Report");
		}
		return bullBearButton;
	}

	/**
	 * This method initializes marketBarometerButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMarketBarometerButton() {
		if (marketBarometerButton == null) {
			marketBarometerButton = new JButton();
			marketBarometerButton.setText("Market Barometer");
		}
		return marketBarometerButton;
	}

	/**
	 * This method initializes strategyButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getStrategyButton() {
		if (strategyButton == null) {
			strategyButton = new JButton();
			strategyButton.setText("Strategy Guide");
		}
		return strategyButton;
	}

	/**
	 * This method initializes traderButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getTraderButton() {
		if (traderButton == null) {
			traderButton = new JButton();
			traderButton.setText("Trader's Dictionary");
		}
		return traderButton;
	}

	/**
	 * This method initializes preferencesButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPreferencesButton() {
		if (preferencesButton == null) {
			preferencesButton = new JButton();
			preferencesButton.setText("Preferences");
		}
		return preferencesButton;
	}

	/**
	 * This method initializes researchToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getResearchToolBar() {
		if (researchToolBar == null) {
			researchToolBar = new JToolBar();
			researchToolBar.add(getBackButton());
			researchToolBar.add(getForwardButton());
			researchToolBar.add(getStopButton());
			researchToolBar.add(getRefreshButton());
		}
		return researchToolBar;
	}

	/**
	 * This method initializes backButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBackButton() {
		if (backButton == null) {
			backButton = new JButton();
			backButton.setText("Back");
		}
		return backButton;
	}

	/**
	 * This method initializes forwardButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getForwardButton() {
		if (forwardButton == null) {
			forwardButton = new JButton();
			forwardButton.setText("Forward");
		}
		return forwardButton;
	}

	/**
	 * This method initializes stopButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getStopButton() {
		if (stopButton == null) {
			stopButton = new JButton();
			stopButton.setText("Stop");
		}
		return stopButton;
	}

	/**
	 * This method initializes refreshButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRefreshButton() {
		if (refreshButton == null) {
			refreshButton = new JButton();
			refreshButton.setText("Refresh");
		}
		return refreshButton;
	}

	/**
	 * This method initializes mainSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getMainSplitPane() {
		if (mainSplitPane == null) {
			mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			mainSplitPane.setDividerLocation(300);
			mainSplitPane.setLeftComponent(getGraphSplitPane());
			mainSplitPane.setRightComponent(getTableTabbedPane());
		}
		return mainSplitPane;
	}

	/**
	 * This method initializes graphSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getGraphSplitPane() {
		if (graphSplitPane == null) {
			graphSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			graphSplitPane.setLeftComponent(getScreenTabs());
			graphSplitPane.setRightComponent(getChartPanel());
		}
		return graphSplitPane;
	}

	/**
	 * This method initializes screenTabs	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getScreenTabs() {
		if (screenTabs == null) {
			screenTabs = new JTabbedPane(JTabbedPane.BOTTOM);
			screenTabs.addTab("Basic", null, getBasicPanel(), null);
			screenTabs.addTab("Advanced", null, getAdvancedPanel(), null);
			screenTabs.addTab("Watch Lists", null, getWatchListsPanel(), null);
		}
		return screenTabs;
	}

	/**
	 * This method initializes basicPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getBasicPanel() {
		if (basicPanel == null) {
			basicPanel = new BasicTabPanel();
			basicPanel.setName("Basic");
		}
		return basicPanel;
	}

	/**
	 * This method initializes advancedPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getAdvancedPanel() {
		if (advancedPanel == null) {
			advancedPanel = new AdvancedTabPanel(this);
			advancedPanel.setName("Advanced");
		}
		return advancedPanel;
	}

	/**
	 * This method initializes watchListsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getWatchListsPanel() {
		if (watchListsPanel == null) {
			watchListsPanel = new JPanel();
			watchListsPanel.setLayout(new GridBagLayout());
		}
		return watchListsPanel;
	}

	/**
	 * This method initializes chartPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
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

	/**
	 * This method initializes tableTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getTableTabbedPane() {
		if (tableTabbedPane == null) {
			tableTabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
			tableTabbedPane.addTab("Screening", null, getScreeningScrollPane(), null);
			tableTabbedPane.addTab("Quotes", null, getQuotesScrollPane(), null);
		}
		return tableTabbedPane;
	}

	/**
	 * This method initializes screeningScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScreeningScrollPane() {
		if (screeningScrollPane == null) {
			screeningScrollPane = new JScrollPane();
			screeningScrollPane.setViewportView(getScreeningTable());
		}
		return screeningScrollPane;
	}

	/**
	 * This method initializes screeningTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
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

	/**
	 * This method initializes quotesScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getQuotesScrollPane() {
		if (quotesScrollPane == null) {
			quotesScrollPane = new JScrollPane();
			quotesScrollPane.setViewportView(getQuotesTable());
		}
		return quotesScrollPane;
	}

	/**
	 * This method initializes quotesTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
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
	
	public DataModel getDataModel() {
		return dataModel;
	}

	/**
	 * This method initializes statusBarLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getStatusBarLabel() {
		if (statusBarLabel == null) {
			statusBarLabel = new JLabel();
			statusBarLabel.setText("JLabel");
		}
		return statusBarLabel;
	}

	/**
	 * This method initializes stockComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AnalyzerUI thisClass = new AnalyzerUI(new StockModel());
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public AnalyzerUI(DataModel m) {
		super();
		initialize();
		this.dataModel = m;
		updateStocksData();
		this.stocks = this.dataModel.getAllStocks();
		this.stockMap = this.dataModel.getStockMap();
	}
	
	private void updateStocksData() {
		setStatusBar("Updating stock data");
		this.stocks = this.dataModel.getAllStocks();
		this.stockMap = this.dataModel.getStockMap();
		setStatusBar("Finished updating stock data");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
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
		this.setTitle("Analyzer UI");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getToolBarPanel(), BorderLayout.NORTH);
			jContentPane.add(getMainSplitPane(), BorderLayout.CENTER);
			jContentPane.add(getStatusBarLabel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	
	public synchronized void setStatusBar(String text) {
		getStatusBarLabel().setText(text);
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
