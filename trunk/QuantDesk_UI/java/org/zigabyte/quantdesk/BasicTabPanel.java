package org.zigabyte.quantdesk;

import javax.swing.*;
import java.awt.*;

public class BasicTabPanel extends JPanel {
	private JLabel lblCategories = new JLabel("Categories:");
	private JLabel lblEasyScreens = new JLabel("Easy Screens");
	private JLabel lblPriceBetween = new JLabel("Price is between:");
	private JLabel lblAnd = new JLabel("and");
	private JLabel lblVolume = new JLabel("Volume is greater than:");

	private JComboBox categoriesComboBox = null;
	private JList easyScreensList = null;
	private JTextArea txtInfo = new JTextArea();
	private JTextField txtStartPrice = new JTextField();
	private JTextField txtEndPrice = new JTextField();
	private JTextField txtVolume = new JTextField();

	private JButton btnExecute = null;
	private JButton btnDefaults = null;

	public BasicTabPanel() {
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 3;
		gridBagConstraints12.insets = new Insets(2, 20, 0, 0);
		gridBagConstraints12.gridy = 7;
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 3;
		gridBagConstraints11.insets = new Insets(0, 20, 0, 0);
		gridBagConstraints11.gridy = 6;
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.fill = GridBagConstraints.BOTH;
		gridBagConstraints10.gridy = 8;
		gridBagConstraints10.weightx = 1.0;
		gridBagConstraints10.gridwidth = 2;
		gridBagConstraints10.gridx = 0;
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.gridx = 0;
		gridBagConstraints9.gridwidth = 3;
		gridBagConstraints9.anchor = GridBagConstraints.WEST;
		gridBagConstraints9.insets = new Insets(0, 0, 0, 5);
		gridBagConstraints9.gridy = 7;
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 1;
		gridBagConstraints8.insets = new Insets(0, 0, 0, 5);
		gridBagConstraints8.gridy = 6;
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.fill = GridBagConstraints.BOTH;
		gridBagConstraints7.gridy = 6;
		gridBagConstraints7.weightx = 3.0;
		gridBagConstraints7.insets = new Insets(0, 0, 0, 5);
		gridBagConstraints7.gridx = 2;
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.fill = GridBagConstraints.BOTH;
		gridBagConstraints6.gridy = 6;
		gridBagConstraints6.weightx = 1.0;
		gridBagConstraints6.ipadx = 0;
		gridBagConstraints6.insets = new Insets(0, 0, 0, 5);
		gridBagConstraints6.anchor = GridBagConstraints.EAST;
		gridBagConstraints6.gridx = 0;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.gridwidth = 4;
		gridBagConstraints4.anchor = GridBagConstraints.WEST;
		gridBagConstraints4.gridy = 5;
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.fill = GridBagConstraints.BOTH;
		gridBagConstraints5.gridy = 4;
		gridBagConstraints5.weightx = 1.0;
		gridBagConstraints5.weighty = 1.0;
		gridBagConstraints5.gridwidth = 4;
		gridBagConstraints5.insets = new Insets(5, 5, 0, 5);
		gridBagConstraints5.gridx = 0;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.BOTH;
		gridBagConstraints3.gridy = 2;
		gridBagConstraints3.weightx = 1.0;
		gridBagConstraints3.weighty = 1.0;
		gridBagConstraints3.gridwidth = 4;
		gridBagConstraints3.insets = new Insets(0, 5, 0, 5);
		gridBagConstraints3.ipadx = 8;
		gridBagConstraints3.gridx = 0;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridwidth = 4;
		gridBagConstraints2.anchor = GridBagConstraints.WEST;
		gridBagConstraints2.gridy = 1;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridx = 2;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridwidth = 2;
		gridBagConstraints1.insets = new Insets(5, 3, 5, 5);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(8, 5, 8, 2);
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridx = 0;

		setLayout(new GridBagLayout());
		setSize(new Dimension(210, 153));
		add(lblCategories, gridBagConstraints);
		add(getCategoriesComboBox(), gridBagConstraints1);
		add(lblEasyScreens, gridBagConstraints2);
		add(getEasyScreensList(), gridBagConstraints3);
		add(txtInfo, gridBagConstraints5);
		add(lblPriceBetween, gridBagConstraints4);
		add(txtStartPrice, gridBagConstraints6);
		add(txtEndPrice, gridBagConstraints7);
		add(lblAnd, gridBagConstraints8);
		add(lblVolume, gridBagConstraints9);
		add(txtVolume, gridBagConstraints10);
		add(getExecuteButton(), gridBagConstraints11);
		add(getDefaultsButton(), gridBagConstraints12);
	}

	/**
	 * This method initializes categoriesComboBox		
	 */
	private JComboBox getCategoriesComboBox() {
		if (categoriesComboBox == null) {
			categoriesComboBox = new JComboBox();
		}
		return categoriesComboBox;
	}

	/**
	 * This method initializes easyScreensList	
	 */
	private JList getEasyScreensList() {
		if (easyScreensList == null) {
			easyScreensList = new JList();
		}
		return easyScreensList;
	}

	/**
	 * This method initializes executeButton	
	 */
	private JButton getExecuteButton() {
		if (btnExecute == null) {
			btnExecute = new JButton("Execute");
		}
		return btnExecute;
	}

	/**
	 * This method initializes defaultsButton	
	 */
	private JButton getDefaultsButton() {
		if (btnDefaults == null) {
			btnDefaults = new JButton("Defaults");
		}
		return btnDefaults;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
