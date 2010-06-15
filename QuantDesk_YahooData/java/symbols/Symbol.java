/* 
  *  Copyright (C) 2010 Zigabyte Corporation. All rights reserved. 
  * 
  *  This file is part of QuantDesk - http://code.google.com/p/quantdesk/ 
  * 
  *  QuantDesk is free software: you can redistribute it and/or modify 
  *  it under the terms of the GNU General Public License as published by 
  *  the Free Software Foundation, either version 3 of the License, or 
  *  (at your option) any later version. 
  * 
  *  QuantDesk is distributed in the hope that it will be useful, 
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of 
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
  *  GNU General Public License for more details. 
  * 
  *  You should have received a copy of the GNU General Public License 
  *  along with QuantDesk.  If not, see <http://www.gnu.org/licenses/>. 
  */ 
  
 package com.winance.optimizer.symbols; 
  
 import java.util.ArrayList; 
 import java.util.Calendar; 
 import java.util.Date; 
  
  
 public class Symbol 
 { 
  
     public Symbol(String name) 
     { 
         data = new SymbolDataCollection(); 
         this.name = name; 
     } 
      
     public void addDataItem(Date date, double open, double high, double low, double close, double volume) 
     { 
         data.addDataItem(date, open, high, low, close, volume); 
     } 
  
     public ArrayList<Double> getRawValues(Calendar startDate, Calendar endDate) 
     { 
         ArrayList<Double> ret = new ArrayList<Double>(); 
         ArrayList<SymbolDataCollection.SymbolDataItem> items = data.getFromInterval(startDate, endDate); 
  
         for(int i=0;i<items.size();i++) 
         { 
             ret.add(items.get(i).open); 
         } 
  
         return ret; 
     } 
  
     public ArrayList<SymbolRatioItem> getReturnRatios(int year) 
     { 
         return data.getAdjustedRatiosForYear(year); 
     } 
  
     /** 
      * @return the name 
      */ 
     public String getName() 
     { 
         return name; 
     } 
  
     public int getDataItemsCount() 
     { 
         return data.size(); 
     } 
  
     private String name; 
     private SymbolDataCollection data; 
 } 
 