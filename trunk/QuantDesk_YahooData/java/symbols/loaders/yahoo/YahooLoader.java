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
  
  
 package com.winance.optimizer.symbols.loaders.yahoo; 
  
 import com.winance.optimizer.symbols.Symbol; 
 import com.winance.optimizer.symbols.loaders.ISymbolsLoader; 
 import java.io.File; 
  
  
 public class YahooLoader 
         implements ISymbolsLoader 
 { 
  
     public Symbol loadData(String symbolName, int year, String cacheFolder) 
     { 
         YahooNetworkLoader net = new YahooNetworkLoader(); 
         System.out.print( 
                 String.format("Loading data for symbol %1$s for year %2$d using '%3$s' folder for cache.. ", 
                 symbolName, year, cacheFolder)); 
         File symbolFile = net.getSymbolFilePath(symbolName, year, cacheFolder); 
         if(!symbolFile.exists()) 
         { 
             System.out.println("not found in cache."); 
             System.out.print("Loading from net.. "); 
             net.downloadSymbolData(symbolName, year, cacheFolder); 
             System.out.println("done."); 
         } 
         else 
         { 
             System.out.println("found in cache."); 
         } 
  
         System.out.print("Loading from file.. "); 
         Symbol ret = YahooCsvLoader.loadFromFile(symbolFile.getAbsolutePath(), symbolName); 
         System.out.println("done."); 
  
         return ret; 
     } 
 } 