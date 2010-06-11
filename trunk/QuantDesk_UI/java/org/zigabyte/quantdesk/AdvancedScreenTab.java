package org.zigabyte.quantdesk;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.*;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.yccheok.jstock.engine.Country;
import org.yccheok.jstock.engine.Duration;
import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.StockHistoryNotFoundException;

public class AdvancedScreenTab extends Composite {
	
	private FormLayout layout;
	private Context cx;
	private AnalyzerUI mainUI;
	private boolean shouldStop = false;
	
	public AdvancedScreenTab(Composite parent, AnalyzerUI ui) {
		super(parent, SWT.NONE);
		layout = new FormLayout();
		
		this.mainUI = ui;
		Button addFolder = new Button(this, SWT.PUSH);
		addFolder.setText("Add Folder");
		FormData addFolderData = new FormData();
		addFolderData.left = new FormAttachment(0);
		addFolderData.top = new FormAttachment(0);
		addFolder.setLayoutData(addFolderData);
		addFolder.pack();
		
		Button createScreen = new Button(this, SWT.PUSH);
		createScreen.setText("Create Screen");
		FormData createScreenData = new FormData();
		createScreenData.left = new FormAttachment(addFolder, 5, SWT.RIGHT);
		createScreenData.top = new FormAttachment(0);
		createScreen.setLayoutData(createScreenData);
		createScreen.pack();
		
		Tree screenTree = new Tree(this, SWT.BORDER);
		TreeItem root = new TreeItem(screenTree, SWT.NONE);
		root.setText("Screening");
		TreeItem folder = new TreeItem(root, SWT.NONE);
		folder.setText("Folder 1");
		TreeItem item = new TreeItem(folder, SWT.NONE);
		item.setText("Item 1");
		FormData treeData = new FormData();
		treeData.top = new FormAttachment(addFolder, 5, SWT.BOTTOM);
		treeData.left = new FormAttachment(0);
		treeData.right = new FormAttachment(100);
		screenTree.setLayoutData(treeData);
		screenTree.pack();
		
		cx = Context.enter();
		final Text jsArea = new Text(this, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		Button execute = new Button(this, SWT.PUSH);
		execute.setText("Execute");
		FormData jsData = new FormData();
		FormData executeData = new FormData();
		jsData.top = new FormAttachment(screenTree, 5, SWT.BOTTOM);
		executeData.top = new FormAttachment(jsArea, 5, SWT.BOTTOM);
		jsData.bottom = new FormAttachment(90);
		jsData.left = new FormAttachment(0);
		jsData.right = new FormAttachment(100);
		jsArea.setLayoutData(jsData);
		execute.setLayoutData(executeData);
		jsArea.pack();
		execute.pack();
		
		execute.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shouldStop = false;
				System.out.println("Widget " + e.widget + " selected.  Text is: " + jsArea.getText());
				new ScanningThread(jsArea.getText()).start();
			}
		});
		
		Button stop = new Button(this, SWT.PUSH);
		stop.setText("Stop");
		FormData stopData = new FormData();
		stopData.top = new FormAttachment(jsArea, 5, SWT.BOTTOM);
		stopData.left = new FormAttachment(execute, 5, SWT.RIGHT);
		stop.setLayoutData(stopData);
		stop.pack();
		
		stop.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shouldStop = true;
			}
		});
		
		this.setLayout(layout);
		this.pack();
	}
	
	private class ScanningThread extends Thread {
		private String script;
		
		public ScanningThread(String script) {
			this.script = script;
		}
		
		public void run() {
			if(mainUI.display.isDisposed() || shouldStop) {
				return;
			}
			mainUI.display.asyncExec(new Runnable() {
				public void run() {
					mainUI.scanTable.clearAll();
					mainUI.scanTable.removeAll();
				}
			});
			Context context = Context.enter();
			String func = "";
			func += "var s;\n";
			func += "function callback(stock) {\n";
			//func += "var s = new Packages.org.zigabyte.quantdesk.MyYahooStockHistoryServer(Packages.org.yccheok.jstock.engine.Country.UnitedState, stock.code);\n";
			func += "return " + script;
			func += "}";
			Scriptable scope = context.initStandardObjects();
			System.out.println("Calling code:\n" + func);
			Object result = context.evaluateString(scope, func, "<cmd>", 1, null);
			System.out.println("Result 1: " + result);
			Object o = scope.get("callback", scope);
			if(o instanceof Function) {
				for(Stock s : mainUI.stocks) {
					if(mainUI.display.isDisposed() || shouldStop) {
						return;
					}
					try {
						MyYahooStockHistoryServer server = new MyYahooStockHistoryServer(Country.UnitedState, s.getCode(), Duration.getTodayDurationByYears(1));
						scope.put("s", scope, server);
						Function f = (Function)o;
						Object r2 = f.call(context, scope, scope, new Stock[] { s });
						System.out.println("Result 2: " + r2 + " stock: " + s.getCode());
						if(r2 instanceof Boolean && (Boolean)r2) {
							String[] rowData = Utils.getRowString(s);
							mainUI.display.asyncExec(new DataUpdater(rowData, mainUI));
						}
					}
					catch(StockHistoryNotFoundException ex) {
						continue;
					}
				}
			}
		}
	}
}