package org.zigabyte.quantdesk;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.yccheok.jstock.engine.Stock;
import javax.swing.JScrollBar;
import javax.swing.JLabel;

public class AdvancedTabPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton addFilterButton = null;
	private JButton createScreenButton = null;
	private JTree advancedScreensTree = null;
	private JButton executeButton = null;
	private JButton modifyButton = null;
	private JButton exportButton = null;
	private JTextArea javascriptTextArea = null;
	private JButton startButton = null;
	private JButton stopButton = null;
	private JScrollPane javascriptScrollPane = null;
	private boolean shouldStop = false;
	private AnalyzerUI parent;
	private JLabel javascriptLabel = null;
	public AdvancedTabPanel(AnalyzerUI parent) {
		super();
		this.parent = parent;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
        gridBagConstraints12.gridx = 0;
        gridBagConstraints12.gridy = 3;
        javascriptLabel = new JLabel();
        javascriptLabel.setText("JavaScript:");
        GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
        gridBagConstraints11.gridx = 1;
        gridBagConstraints11.anchor = GridBagConstraints.WEST;
        gridBagConstraints11.gridy = 5;
        GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
        gridBagConstraints7.gridx = 0;
        gridBagConstraints7.anchor = GridBagConstraints.WEST;
        gridBagConstraints7.gridy = 5;
        GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
        gridBagConstraints6.fill = GridBagConstraints.BOTH;
        gridBagConstraints6.gridy = 4;
        gridBagConstraints6.weightx = 1.0;
        gridBagConstraints6.weighty = 3.0;
        gridBagConstraints6.gridwidth = 3;
        gridBagConstraints6.insets = new Insets(5, 0, 5, 0);
        gridBagConstraints6.gridx = 0;
        GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
        gridBagConstraints5.gridx = 2;
        gridBagConstraints5.gridy = 2;
        GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
        gridBagConstraints4.gridx = 1;
        gridBagConstraints4.gridy = 2;
        GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.gridx = 0;
        gridBagConstraints3.gridy = 2;
        GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.fill = GridBagConstraints.BOTH;
        gridBagConstraints2.gridy = 1;
        gridBagConstraints2.weightx = 1.0;
        gridBagConstraints2.weighty = 1.0;
        gridBagConstraints2.gridwidth = 4;
        gridBagConstraints2.gridx = 0;
        GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.gridy = 0;
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.setLayout(new GridBagLayout());
        this.add(getAddFilterButton(), gridBagConstraints);
        this.add(getCreateScreenButton(), gridBagConstraints1);
        this.add(getAdvancedScreensTree(), gridBagConstraints2);
        this.add(getExecuteButton(), gridBagConstraints3);
        this.add(getModifyButton(), gridBagConstraints4);
        this.add(getExportButton(), gridBagConstraints5);
        this.add(getJavascriptScrollPane(), gridBagConstraints6);
        this.add(getStartButton(), gridBagConstraints7);
        this.add(getStopButton(), gridBagConstraints11);
        this.add(javascriptLabel, gridBagConstraints12);
			
	}

	/**
	 * This method initializes addFilterButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddFilterButton() {
		if (addFilterButton == null) {
			addFilterButton = new JButton();
			addFilterButton.setText("Add Filter");
		}
		return addFilterButton;
	}

	/**
	 * This method initializes createScreenButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCreateScreenButton() {
		if (createScreenButton == null) {
			createScreenButton = new JButton();
			createScreenButton.setText("Create Screen");
		}
		return createScreenButton;
	}

	/**
	 * This method initializes advancedScreensTree	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private JTree getAdvancedScreensTree() {
		if (advancedScreensTree == null) {
			advancedScreensTree = new JTree();
		}
		return advancedScreensTree;
	}

	/**
	 * This method initializes executeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getExecuteButton() {
		if (executeButton == null) {
			executeButton = new JButton();
			executeButton.setText("Execute");
		}
		return executeButton;
	}

	/**
	 * This method initializes modifyButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getModifyButton() {
		if (modifyButton == null) {
			modifyButton = new JButton();
			modifyButton.setText("Modify");
		}
		return modifyButton;
	}

	/**
	 * This method initializes exportButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getExportButton() {
		if (exportButton == null) {
			exportButton = new JButton();
			exportButton.setText("Export Results");
		}
		return exportButton;
	}

	/**
	 * This method initializes javascriptTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJavascriptTextArea() {
		if (javascriptTextArea == null) {
			javascriptTextArea = new JTextArea();
		}
		return javascriptTextArea;
	}
	
	private JScrollPane getJavascriptScrollPane() {
		if(javascriptScrollPane == null) {
			javascriptScrollPane = new JScrollPane(getJavascriptTextArea());
		}
		return javascriptScrollPane;
	}

	/**
	 * This method initializes startButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getStartButton() {
		if (startButton == null) {
			startButton = new JButton();
			startButton.setText("Start");
			startButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					shouldStop = false;
					new ScanningThread(javascriptTextArea.getText()).start();
				}
			});
		}
		return startButton;
	}
	
	private class ScanningThread extends Thread {
		private String script;
		
		public ScanningThread(String script) {
			this.script = script;
		}
		
		public void run() {
			if(shouldStop) {
				return;
			}
			DefaultTableModel m = (DefaultTableModel)parent.getScreeningTable().getModel();
			int numRows = m.getRowCount();
			for(int i = 0; i < numRows; i++) {
				m.removeRow(0);
			}
			Context context = Context.enter();
			String func = "";
			func += "function callback(stock, history) {\n";
			func += "return " + script;
			func += "\n}";
			Scriptable scope = context.initStandardObjects();
			context.evaluateString(scope, func, "<cmd>", 1, null);
			Object o = scope.get("callback", scope);
			if(o instanceof Function) {
				for(Stock s : parent.getDataModel().getAllStocks()) { 
					if(shouldStop) {
						return;
					}
					parent.setStatusBar("Scanning stock " + s.getCode().toString());
					s = parent.getDataModel().getStock(s.getCode().toString());
					MyYahooStockHistoryServer server = (MyYahooStockHistoryServer)parent.getDataModel().getStockData(s);
					Function f = (Function)o;
					if(server != null) {
						Object r2 = f.call(context, scope, scope, new Object[] { s, server });
						if(r2 instanceof Boolean && (Boolean)r2) {
							if(shouldStop) {
								return;
							}
							new SwingDataUpdater(s, parent).start();
						}
					}
				}
				parent.setStatusBar("Finished scanning stocks with Javascript");
			}
		}
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
			stopButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					shouldStop = true;
				}
			});
		}
		return stopButton;
	}
}
