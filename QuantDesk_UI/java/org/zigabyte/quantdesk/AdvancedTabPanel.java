package org.zigabyte.quantdesk;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.yccheok.jstock.engine.Stock;

public class AdvancedTabPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnAddFilter = null;
	private JButton btnCreateScreen = null;
	private JTree advancedScreensTree = null;
	private JButton btnExecute = null;
	private JButton btnModify = null;
	private JButton btnExport = null;
	private JTextArea txtJavaScript = new JTextArea("history.correlation('DELL') > 0.8\n&&\nstock.dividendYield > 1.0");
	private JButton btnStart = null;
	private JButton btnStop = null;
	private JScrollPane paneJavaScript = null;
	private boolean shouldStop = false;
	private AnalyzerUI parent;

	public AdvancedTabPanel(AnalyzerUI parent) {
		this.parent = parent;
		initialize();
	}

	private void initialize() {
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.gridy = 3;
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
		setLayout(new GridBagLayout());
		add(getAddFilterButton(), gridBagConstraints);
		add(getCreateScreenButton(), gridBagConstraints1);
		add(getAdvancedScreensTree(), gridBagConstraints2);
		add(getExecuteButton(), gridBagConstraints3);
		add(getModifyButton(), gridBagConstraints4);
		add(getExportButton(), gridBagConstraints5);
		add(getJavascriptScrollPane(), gridBagConstraints6);
		add(getStartButton(), gridBagConstraints7);
		add(getStopButton(), gridBagConstraints11);
		add(new JLabel("JavaScript: "), gridBagConstraints12);
	}

	/**
	 * This method initializes btnAddFilter
	 */
	private JButton getAddFilterButton() {
		if (btnAddFilter == null) {
			btnAddFilter = new JButton("Add Filter");
		}
		return btnAddFilter;
	}

	/**
	 * This method initializes btnCreateScreen
	 */
	private JButton getCreateScreenButton() {
		if (btnCreateScreen == null) {
			btnCreateScreen = new JButton("Create Screen");
		}
		return btnCreateScreen;
	}

	/**
	 * This method initializes advancedScreensTree	
	 */
	private JTree getAdvancedScreensTree() {
		if (advancedScreensTree == null) {
			advancedScreensTree = new JTree();
		}
		return advancedScreensTree;
	}

	/**
	 * This method initializes btnExecute
	 */
	private JButton getExecuteButton() {
		if (btnExecute == null) {
			btnExecute = new JButton("Execute");
		}
		return btnExecute;
	}

	/**
	 * This method initializes btnModify
	 */
	private JButton getModifyButton() {
		if (btnModify == null) {
			btnModify = new JButton("Modify");
		}
		return btnModify;
	}

	/**
	 * This method initializes btnExport
	 */
	private JButton getExportButton() {
		if (btnExport == null) {
			btnExport = new JButton("Export Results");
		}
		return btnExport;
	}
	
	private JScrollPane getJavascriptScrollPane() {
		if(paneJavaScript == null) {
			paneJavaScript = new JScrollPane(txtJavaScript);
			paneJavaScript.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			paneJavaScript.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
		return paneJavaScript;
	}

	/**
	 * This method initializes btnStart
	 */
	private JButton getStartButton() {
		if (btnStart == null) {
			btnStart = new JButton("Start");
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new ScanningThread(txtJavaScript.getText()).start();
				}
			});
		}
		return btnStart;
	}
	
	private class ScanningThread extends Thread {
		private String script;
		
		public ScanningThread(String script) {
			this.script = script;
		}
		
		public void run() {
			shouldStop = false;
			DefaultTableModel m = (DefaultTableModel)parent.getScreeningTable().getModel();
			int numRows = m.getRowCount();
			for(int i = 0; i < numRows; i++) {
				m.removeRow(0); // TODO: this is cleaning the table, should be put into a separate method
			}
			Context context = Context.enter();
			String func = "function callback(stock, history) {\n  return " + script + "\n}";
			Scriptable scope = context.initStandardObjects();
			context.evaluateString(scope, func, "<cmd>", 1, null);
			Object o = scope.get("callback", scope);
			if(!(o instanceof Function)) return;

			for(Stock s : parent.getDataModel().getAllStocks()) {
				if(shouldStop) return;
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
			parent.setStatusBar("Finished scanning stocks with JavaScript");

		}
	}

	/**
	 * This method initializes stopButton	
	 */
	private JButton getStopButton() {
		if (btnStop == null) {
			btnStop = new JButton("Stop");
			btnStop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					shouldStop = true;
				}
			});
		}
		return btnStop;
	}
}
