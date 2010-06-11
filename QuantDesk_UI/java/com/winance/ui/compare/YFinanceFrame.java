package com.winance.ui.compare;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class YFinanceFrame
  extends JFrame
{
  private YFinance yFinance;
  private JTextField symbols;
  private JPanel mainPanel;
  private JScrollPane center;
  private JTextField statusBar;

  public YFinanceFrame(YFinance yFinance) {
    super("Yahoo! Finance");
    
    this.yFinance = yFinance;

    initialize();
  }

  private void initialize() {
    JButton button;

    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    setContentPane(mainPanel);

    JPanel header = new JPanel();
    header.setLayout(new FlowLayout());
    header.add(new JLabel("Comma separated stock symbols"));
    header.add(symbols = new JTextField(20));
    header.add(button = new JButton("Load"));
    button.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          loadStocks();
        }
      }
    );
    mainPanel.add(header, BorderLayout.NORTH);

    center = new JScrollPane();
    mainPanel.add(center, BorderLayout.CENTER);

    statusBar = new JTextField(20);
    statusBar.setEditable(false);
    mainPanel.add(statusBar, BorderLayout.SOUTH);

    pack();

    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void setValues(Vector v, String[] keys) {
    center.removeAll();
    JTable table = new JTable(new YFinanceTableModel(v, keys));
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

//    JButton scroll = new JButton("scroll");
//    JScrollPane scroll = new JScrollPane(new JList());
//    JScrollPane scroll = new JScrollPane(table);
//    scroll.setPreferredSize(new Dimension(800, 600));
    mainPanel.remove(center);
    center = new JScrollPane(table);
    mainPanel.add(center, BorderLayout.CENTER);

    mainPanel.validate();
  }

  public void loadStocks() {
    String[] keys = symbols.getText().split("\\s*\\,\\s*");
    yFinance.setStocks(keys);
    new Thread(yFinance).start();
  }

  public void setStatus(String status) {
    statusBar.setText(status);
  }
};

