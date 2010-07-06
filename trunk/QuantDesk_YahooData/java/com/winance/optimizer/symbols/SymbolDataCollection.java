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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;


public class SymbolDataCollection
{
    public class SymbolDataItem implements Comparable<SymbolDataItem>
    {
        public SymbolDataItem(Calendar date, double open, double high, double low, double close, double volume)
        {
            this.date = date;
            this.open = open;
            this.high = high;
            this.low = low;
            this.close = close;
            this.volume = volume;
        }
        public Calendar date;
        public double open;
        public double high;
        public double low;
        public double close;
        public double volume;

        public int compareTo(SymbolDataItem o)
        {
            long val = this.date.getTimeInMillis() - o.date.getTimeInMillis();
            if(val<0)
                return -1;
            if(val>0)
                return 1;
            return 0;
        }
    }

    public SymbolDataCollection()
    {
        data = new TreeMap<Long, SymbolDataItem>();
        yearlyUnadjustedRatios = new HashMap<Integer, ArrayList<SymbolRatioItem>>();
        yearlyAdjustedRatios = new HashMap<Integer, ArrayList<SymbolRatioItem>>();
    }

    public void addDataItem(Date date, double open, double high, double low, double close, double volume)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        SymbolDataItem item = new SymbolDataItem(c, open, high, low, close, volume);
        data.put(c.getTimeInMillis(), item);
    }

    public ArrayList<SymbolDataItem> getFromInterval(Calendar startDate, Calendar endDate)
    {
        ArrayList<SymbolDataItem> ret = new ArrayList<SymbolDataItem>();
        ret.addAll(data.subMap(startDate.getTimeInMillis(), true, endDate.getTimeInMillis(), false).values());

        return ret;
    }

    public ArrayList<SymbolRatioItem> getUnadjustedRatiosForYear(int year)
    {
        ArrayList<SymbolRatioItem> ret = new ArrayList<SymbolRatioItem>();
        if(yearlyUnadjustedRatios.containsKey(year))
        {
            Collections.copy(ret, yearlyUnadjustedRatios.get(year));
            return ret;
        }

        ret = calculateUnadjustedRatiosForYear(year);
        ArrayList<SymbolRatioItem> cacheItem = new ArrayList<SymbolRatioItem>();
        cacheItem.addAll(ret);
        yearlyUnadjustedRatios.put(year, cacheItem);

        return ret;
    }

    public ArrayList<SymbolRatioItem> getAdjustedRatiosForYear(int year)
    {
        ArrayList<SymbolRatioItem> ret = new ArrayList<SymbolRatioItem>();
        if(yearlyAdjustedRatios.containsKey(year))
        {
            ret.addAll(yearlyAdjustedRatios.get(year));
            return ret;
        }

        ret = calculateAdjustedRatiosForYear(year);
        ArrayList<SymbolRatioItem> cacheItem = new ArrayList<SymbolRatioItem>();
        cacheItem.addAll(ret);
        yearlyAdjustedRatios.put(year, cacheItem);

        return ret;

    }


    public int size()
    {
        return data.size();
    }

    private ArrayList<SymbolRatioItem> calculateUnadjustedRatiosForYear(int year)
    {
        ArrayList<SymbolRatioItem> ret = new ArrayList<SymbolRatioItem>();
        
        Calendar startDate = Calendar.getInstance();
        startDate.clear();
        startDate.set(year, 0, 1);

        Calendar endDate = Calendar.getInstance();
        endDate.clear();
        endDate.set(year+1, 0, 1);

        ArrayList<SymbolDataItem> prices = getFromInterval(startDate, endDate);

        for(int i=1;i<prices.size();i++)
        {
            SymbolDataCollection.SymbolDataItem currentItem = prices.get(i);
            SymbolDataCollection.SymbolDataItem previousItem = prices.get(i-1);

            double ratio = currentItem.open / previousItem.open;

            ret.add(new SymbolRatioItem(currentItem.date, ratio));
        }

        return ret;
    }

    private ArrayList<SymbolRatioItem> calculateAdjustedRatiosForYear(int year)
    {
        ArrayList<SymbolRatioItem> ret = new ArrayList<SymbolRatioItem>();

        Calendar startDate = Calendar.getInstance();
        startDate.clear();
        startDate.set(year, 0, 1);

        Calendar endDate = Calendar.getInstance();
        endDate.clear();
        endDate.set(year+1, 0, 1);

        ArrayList<SymbolDataItem> prices = getFromInterval(startDate, endDate);

        long datesDifference = 0;
        double daysDifference =0;
        double returnRatio = 0;
        for(int i=1;i<prices.size();i++)
        {
            SymbolDataCollection.SymbolDataItem currentItem = prices.get(i);
            SymbolDataCollection.SymbolDataItem previousItem = prices.get(i-1);

            datesDifference = currentItem.date.getTimeInMillis() - previousItem.date.getTimeInMillis();

            daysDifference = datesDifference / 1000. / 60. / 60. / 24.;

            returnRatio = currentItem.open / previousItem.open;

            returnRatio = Math.pow(returnRatio, 1. / daysDifference);

            ret.add(new SymbolRatioItem(currentItem.date, returnRatio));
        }

        return ret;
    }

    private TreeMap<Long, SymbolDataItem> data;
    private HashMap<Integer, ArrayList<SymbolRatioItem>> yearlyUnadjustedRatios;
    private HashMap<Integer, ArrayList<SymbolRatioItem>> yearlyAdjustedRatios;
}
