package com.winance.ui.compare;
import java.util.*;


public class YFinance
  implements Comparator, Runnable
{
  private String[] keys;
  private YFinanceData[] data;
  private YFinanceFrame frame;
  private Hashtable hash;

  public void initialize() {
    frame = new YFinanceFrame(this);
    frame.setVisible(true);
  }

  public void setStocks(String[] keys) {
    this.keys = keys;
  }

  public void run() {
    data = new YFinanceData[keys.length];
    hash = new Hashtable();

    for(int i=0; i<keys.length; i++) {
      frame.setStatus("Loading " + keys[i] + "...");
      YFinanceWeb page = new YFinanceWeb(keys[i]);
      data[i] = new YFinanceData(keys[i], page.getSource(), hash, keys.length, i);
      data[i].parse();
    }

    Vector v = new Vector(hash.values());
    Collections.sort(v, this);
    frame.setValues(v, keys);

    frame.setStatus("Done");

//    YFinanceFrame frame = new YFinanceFrame(this);
//    frame.setVisible(true);

/*
    int i;
    
    for(i=0; i<keys.length; i++) {
      YFinanceWeb page = new YFinanceWeb(keys[i]);
      data[i] = new YFinanceData(keys[i], page.getSource(), hash, keys.length, i);
      data[i].parse();
    }

    Vector v = new Vector(hash.values());
    Collections.sort(v, this);

    Iterator it = v.iterator();
    while(it.hasNext()) {
//      YFinanceDataLine element = (YFinanceDataLine)it.next();
//      System.out.println(element.getName());
    }
*/
  }

  public static void main(String[] args) {
    YFinance yFinance = new YFinance();
    yFinance.initialize();
  }

  public int compare(Object o1, Object o2) {
    YFinanceDataLine line1 = (YFinanceDataLine)o1;
    YFinanceDataLine line2 = (YFinanceDataLine)o2;
    if(line1.getPosition() < line2.getPosition()) return -1;
    if(line1.getPosition() > line2.getPosition()) return 1;
    return 0;
  }
};

