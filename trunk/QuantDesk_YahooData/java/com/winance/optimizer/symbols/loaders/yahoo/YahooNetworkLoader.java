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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;


public class YahooNetworkLoader
{
    //  http://ichart.finance.yahoo.com/table.csv?s=EEM&a=00&b=1&c=2009&d=11&e=31&f=2009&g=d&ignore=.csv

    private static final String URL_TEMPLATE = "http://ichart.finance.yahoo.com/table.csv?s=%1$s&a=00&b=1&c=%2$d&d=11&e=31&f=%2$d&g=d&ignore=.csv";
    private static final String SITE_NAME = "YAHOO";

    public File getSymbolFilePath(String symbolName, int year, String cachePath)
    {
        File cacheFolder = new File(cachePath);
        String symbolFileName = String.format("%1$s_%2$d_%3$s.csv", symbolName, year, SITE_NAME);
        File symbolFile = new File(cacheFolder, symbolFileName);

        File ret = null;
        try
        {
            ret = symbolFile.getCanonicalFile();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        return ret;
    }

    public void downloadSymbolData(String symbolName, int year, String cachePath)
    {
        String url = String.format(URL_TEMPLATE, symbolName, year);
        URL downloadURL = null;
        try
        {
            downloadURL = new URL(url);
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
            return;
        }

        InputStream is = null;
        BufferedReader br = null;
        BufferedWriter bw = null;

        File cacheFolder = new File(cachePath);
        cacheFolder.mkdirs();
        
        File outputSymbolFile = getSymbolFilePath(symbolName,year,cachePath);
        
        try
        {
            is = downloadURL.openStream();
            br = new BufferedReader(new InputStreamReader(is));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputSymbolFile)));

            String s;
            while( (s = br.readLine() ) != null)
            {
                bw.write(s);
                bw.write(String.format("%n"));
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return;
        }
        finally
        {
            try
            {
                is.close();
                bw.close();
            }
            catch(IOException e)
            {
            }
        }

    }
}
