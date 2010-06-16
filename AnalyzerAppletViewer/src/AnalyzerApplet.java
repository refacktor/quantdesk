
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;



public class AnalyzerApplet extends JApplet {
	private static final long serialVersionUID = 7365446548166114222L;
	private static final String[] navigationButtons = new String[] {"Update", "Basic", "Advanced", "Watch Lists", "Charting", "Portfolio", "Research", "Trading", "Indicators", "Quotes"};
	private static final String[] toolButtons = new String[] {"Strength Meter", "Bull:Bear Report", "Market Barometer", "Strategy Guide", "Trader's Dictionary", "Preferences"};
	private static final String[] researchButtons = new String[] {"Back", "Forward", "Stop", "Refresh"};
	
	public void init(){
		int width=0,height=0;
	    try {
	        width = Integer.parseInt(this.getParameter("w", "800"));
	      }
	      catch(Exception e) {
	        e.printStackTrace();
	      }
	      try {
	        height = Integer.parseInt(this.getParameter("h", "600"));
	      }
	      catch(Exception e) {
	        e.printStackTrace();
	      }

		setSize(width, height);
		setJMenuBar(getMainMenuBar());
		setContentPane(getJContentPane());
		showStatus("Analyzer UI");
	}

	public void start(){
	}
	
	/**Get a parameter value*/
	public String getParameter(String key, String def) {
    	return (getParameter(key) != null ? getParameter(key) : def);
	}

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
//			screenTabs.addTab("Basic", null, getBasicPanel(), null);
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
			basicPanel = new JPanel();
			basicPanel.setLayout(new GridBagLayout());
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
			advancedPanel = new JPanel();
			advancedPanel.setLayout(new GridBagLayout());
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
			chartPanel = new JPanel();
			chartPanel.setLayout(new GridBagLayout());
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
	private JTable getScreeningTable() {
		if (screeningTable == null) {
			screeningTable = new JTable();
		}
		return screeningTable;
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
			quotesTable = new JTable();
		}
		return quotesTable;
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getToolBarPanel(), BorderLayout.NORTH);
			jContentPane.add(getMainSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}


}
