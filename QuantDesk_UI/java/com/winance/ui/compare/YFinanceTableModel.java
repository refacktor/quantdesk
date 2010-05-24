package com.winance.ui.compare;


/*
      TableModel dataModel = new AbstractTableModel() {
          public int getColumnCount() { return 10; }
          public int getRowCount() { return 10;}
          public Object getValueAt(int row, int col) { return new Integer(row*col); }
      };
      JTable table = new JTable(dataModel);
      JScrollPane scrollpane = new JScrollPane(table);
*/

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;


public class YFinanceTableModel
  extends AbstractTableModel
{
  private Vector values;
  private String[] keys;

  public YFinanceTableModel(Vector values, String[] keys) {
    this.values = values;
    this.keys = keys;
  }

  public int getColumnCount() {
    return ((YFinanceDataLine)values.get(0)).getSize() + 1;
  }

  public int getRowCount() {
    return values.size();
  }

  public Object getValueAt(int row, int col) {
    YFinanceDataLine line = (YFinanceDataLine)values.get(row);

    if(col == 0)
      return "<html>" + line.getName() + "</html>";

    if(line.getData(col-1) == null)
      return "";
    return "<html>" + new String(line.getData(col-1)) + "</html>";
  }

  public String getColumnName(int col) {
    if(col == 0)
      return "";
    return keys[col-1];
  }
};

