package org.zigabyte.quantdesk;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class AdvancedScreenTab extends Composite {
	
	private FormLayout layout;
	
	public AdvancedScreenTab(Composite parent) {
		super(parent, SWT.NONE);
		layout = new FormLayout();
		
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
		
		this.setLayout(layout);
		this.pack();
	}
}