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

package com.winance.optimizer;

import java.util.ArrayList;


public class Calculations
{
    public static double stdev(final ArrayList<Double> values)
    {
        double ret = 0;

        if(0==values.size() || 1 == values.size())
            return 0;

        //  calculating simple average for values
        double simpleAverage = 0;

        simpleAverage = simpleAverage(values);

        //  calculating sum of squared differences
        double sum = 0;
        double difference = 0;
        for(int i=0;i<values.size();i++)
        {
            difference = values.get(i) - simpleAverage;
            sum += difference * difference;
        }

        ret = sum / (values.size() - 1);
        ret = Math.sqrt(ret);

        return ret;
    }

    public static double simpleAverage(final ArrayList<Double> values, int count)
    {
        double ret = 0;

        for (int i = 0; i < values.size(); i++)
        {
            ret += values.get(i);
        }
        ret /= count;

        return ret;
    }

    public static double simpleAverage(final ArrayList<Double> values)
    {
        return simpleAverage(values, values.size());
    }
}
