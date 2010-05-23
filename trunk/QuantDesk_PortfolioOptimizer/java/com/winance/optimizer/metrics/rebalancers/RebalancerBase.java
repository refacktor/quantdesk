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


package com.winance.optimizer.metrics.rebalancers;

import java.util.ArrayList;


public abstract class RebalancerBase implements IPortfolioRebalancer
{
    /*
     * weight(symbol) = %(symbol) / 100.
     * R - full portfolio ratio
     * we should keep full portfolio ratio the same before and after rebalancing,
     * so next equation should be true:
     * rate(symbol1) * weight(symbol1) * rk(symbol1) = R / 100 * %(symbol1) =
     * R / 100 * weight(symbol1) * 100
     * ...
     * rk(symbol1) = R / rate(symbol1)
     */
    public void rebalance(final ArrayList<Double> rebalancingKoeffs,
        int[] percents, final ArrayList<Double> currentRatios)
    {
        double portfolioRatio = 0;

        //  calculating portfolio ratio before rebalancing
        for(int i=0;i<currentRatios.size();i++)
        {
            portfolioRatio += currentRatios.get(i) * (percents[i] / 100.) * rebalancingKoeffs.get(i);
        }

        //  calculating new rebalancing koefficients
        for(int i=0;i<currentRatios.size();i++)
        {
            double newRatio = 0;

            if(0 != percents[i])
            {
                newRatio = portfolioRatio / currentRatios.get(i);
            }

            rebalancingKoeffs.set(i, newRatio);
        }
    }
}
