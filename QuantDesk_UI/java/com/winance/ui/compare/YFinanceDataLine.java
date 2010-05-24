package com.winance.ui.compare;



public class YFinanceDataLine
{
  private int position;
  private String name;
  private String data[];
  private int size;

  public YFinanceDataLine(int position, String name, int size) {
    this.position = position;
    this.name = name;
    this.size = size;
    data = new String[size];
  }

  public void setData(int pos, String s) {
    data[pos] = s;
  }
  
  public String getData(int pos) {
    return data[pos];
  }

  public String getName() {
    return name;
  }

  public int getSize() {
    return size;
  }

  public int getPosition() {
    return position;
  }
};

