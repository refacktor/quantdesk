package org.zigabyte.quantdesk;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;

public class DataUpdater extends Thread {
	private String[] data;
	private AnalyzerUI mainUI;
	
	public DataUpdater(String[] data, AnalyzerUI mainUI) {
		this.data = data;
		this.mainUI = mainUI;
	}
	
	public void run() {
		TableItem item = new TableItem(mainUI.scanTable, SWT.NONE);
		item.setText(data);
		/*item.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				TableItem i = (TableItem)evt.widget;
				mainUI.plotData(mainUI.stockMap.get(i.getText(2)));
			}
		});*/
	}
}