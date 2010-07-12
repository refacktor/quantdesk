package org.zigabyte.quantdesk;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

public class BasicTabPanel extends JPanel {
	private JLabel categoriesLabel = null;
	private JComboBox categoriesComboBox = null;
	private JLabel easyScreensLabel = null;
	private JList easyScreensList = null;
	private JTextArea infoTextArea = null;
	private JLabel priceBetweenLabel = null;
	private JTextField startPriceTextBox = null;
	private JTextField endPriceTextBox = null;
	private JLabel andLabel = null;
	private JLabel volumeLabel = null;
	private JTextField volumeTextBox = null;
	private JButton executeButton = null;
	private JButton defaultsButton = null;
	public BasicTabPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
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
        volumeLabel = new JLabel();
        volumeLabel.setText("Volume is greater than:");
        GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
        gridBagConstraints8.gridx = 1;
        gridBagConstraints8.insets = new Insets(0, 0, 0, 5);
        gridBagConstraints8.gridy = 6;
        andLabel = new JLabel();
        andLabel.setText("and");
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
        priceBetweenLabel = new JLabel();
        priceBetweenLabel.setText("Price is between:");
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
        easyScreensLabel = new JLabel();
        easyScreensLabel.setText("Easy Screens");
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
        this.setLayout(new GridBagLayout());
        this.setSize(new Dimension(210, 153));
        categoriesLabel = new JLabel();
        categoriesLabel.setText("Categories:");
        this.add(categoriesLabel, gridBagConstraints);
        this.add(getCategoriesComboBox(), gridBagConstraints1);
        this.add(easyScreensLabel, gridBagConstraints2);
        this.add(getEasyScreensList(), gridBagConstraints3);
        this.add(getInfoTextArea(), gridBagConstraints5);
        this.add(priceBetweenLabel, gridBagConstraints4);
        this.add(getStartPriceTextBox(), gridBagConstraints6);
        this.add(getEndPriceTextBox(), gridBagConstraints7);
        this.add(andLabel, gridBagConstraints8);
        this.add(volumeLabel, gridBagConstraints9);
        this.add(getVolumeTextBox(), gridBagConstraints10);
        this.add(getExecuteButton(), gridBagConstraints11);
        this.add(getDefaultsButton(), gridBagConstraints12);
			
	}

	/**
	 * This method initializes categoriesComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCategoriesComboBox() {
		if (categoriesComboBox == null) {
			categoriesComboBox = new JComboBox();
		}
		return categoriesComboBox;
	}

	/**
	 * This method initializes easyScreensList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getEasyScreensList() {
		if (easyScreensList == null) {
			easyScreensList = new JList();
		}
		return easyScreensList;
	}

	/**
	 * This method initializes infoTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getInfoTextArea() {
		if (infoTextArea == null) {
			infoTextArea = new JTextArea();
		}
		return infoTextArea;
	}

	/**
	 * This method initializes startPriceTextBox	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getStartPriceTextBox() {
		if (startPriceTextBox == null) {
			startPriceTextBox = new JTextField();
		}
		return startPriceTextBox;
	}

	/**
	 * This method initializes endPriceTextBox	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getEndPriceTextBox() {
		if (endPriceTextBox == null) {
			endPriceTextBox = new JTextField();
		}
		return endPriceTextBox;
	}

	/**
	 * This method initializes volumeTextBox	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getVolumeTextBox() {
		if (volumeTextBox == null) {
			volumeTextBox = new JTextField();
		}
		return volumeTextBox;
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
	 * This method initializes defaultsButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getDefaultsButton() {
		if (defaultsButton == null) {
			defaultsButton = new JButton();
			defaultsButton.setText("Defaults");
		}
		return defaultsButton;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
