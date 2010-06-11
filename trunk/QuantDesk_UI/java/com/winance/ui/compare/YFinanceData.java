package com.winance.ui.compare;
import java.util.*;
import java.util.regex.*;


public class YFinanceData
{
  private String name;
  private String html;
  private Hashtable hash;
  private int size;
  private int datapos;

  public YFinanceData(String name, String html, Hashtable hash, int size, int datapos) {
    this.name = name;
    this.html = html;
    this.hash = hash;
    this.size = size;
    this.datapos = datapos;
  }

  public void parse() {
    // Head: <td colspan="2"><b>Profitability </b></td>

    Pattern p = Pattern.compile(
      "\\<td\\s+class\\=\\\"yfnc_tablehead1\\\"\\s+width\\=\\\"75\\%\\\"\\>" +
        "(.+?)" +
      "\\<\\/td\\>" +
      "\\<td\\s+class\\=\\\"yfnc_tabledata1\\\"\\>" +
        "(.+?)" +
      "\\<\\/td\\>",
      Pattern.CASE_INSENSITIVE |
      Pattern.DOTALL |
      Pattern.MULTILINE
    );
    Matcher m = p.matcher(html);
    while(m.find())
      addToHash(m.group(1), m.group(2), m.start(1));

    p = Pattern.compile(
      "\\<td\\s+colspan\\=\\\"2\\\"\\>(.+?)\\<\\/td\\>",
      Pattern.CASE_INSENSITIVE |
      Pattern.DOTALL |
      Pattern.MULTILINE
    );
    m = p.matcher(html);
    while(m.find())
      addToHash(m.group(1), null, m.start(1));
  }

  public void addToHash(String name, String value, int pos) {
    YFinanceDataLine line = (YFinanceDataLine)hash.get(name);
    if(line == null)
      line = new YFinanceDataLine(pos, name, size);
    line.setData(datapos, value);
    hash.put(name, line);
  }
};

