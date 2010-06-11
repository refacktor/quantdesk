package com.winance.ui.compare;
import java.net.*;
import java.io.*;


public class YFinanceWeb
{
  public static final String URL = "http://finance.yahoo.com/q/ks?s=";

  private String key;
  private String htmlSource;
  
  public YFinanceWeb(String key) {
    this.key = key;
    loadPage();
  }

  public void loadPage() {
    try {
      URL url = new URL(makeURL());
      URLConnection connection = url.openConnection();
      BufferedReader in = new BufferedReader(
        new InputStreamReader(connection.getInputStream())
      );
      htmlSource = "";
      String htmlLine;
      while((htmlLine = in.readLine()) != null)
        htmlSource += htmlLine;
      in.close();
    } catch(Exception e) {
      // @error
    }
  }

  public String getSource() {
    return htmlSource;
  }
  
  public String makeURL() {
    return URL + key;
  }
};

