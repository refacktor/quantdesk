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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class YahooCsvLoader
{
    public static Symbol loadFromFile(String fileName, String symbolName)
    {
        Symbol ret = null;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            
            ret = new Symbol(symbolName);
            String line;

            //  skipping headers
            line = br.readLine();

            while((line = br.readLine()) != null)
            {
                parseAddLine(ret, line);
            }

            System.out.println("loaded " + ret.getDataItemsCount() + " lines for symbol '" + symbolName + "'");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("file '" + fileName + "' not found for symbol '" + symbolName + "'");
        }
        catch(IOException e)
        {
        }
        
        return ret;
    }

    private static void parseAddLine(Symbol symbol, String line)
    {
        String parts[] = line.split(",");
        //  invalid line format
        /*
        if(6 != parts.length)
            return;
         * */

        try
        {
            SimpleDateFormat df = new SimpleDateFormat();
            //df.applyPattern("yyyyMMdd");
            df.applyPattern("yyyy-MM-dd");
            Date d = df.parse(parts[0]);

            //System.out.println(d);

            double open = Double.parseDouble(parts[1]);
            double high = Double.parseDouble(parts[2]);
            double low = Double.parseDouble(parts[3]);
            double close = Double.parseDouble(parts[4]);
            double volume = Double.parseDouble(parts[5]);

            symbol.addDataItem(d, open, high, low, close, volume);
        }
        catch(ParseException e)
        {
        }
    }
}
