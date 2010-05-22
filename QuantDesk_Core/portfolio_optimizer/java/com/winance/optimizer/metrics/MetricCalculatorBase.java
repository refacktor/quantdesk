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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.winance.optimizer.metrics;

import com.winance.optimizer.metrics.rebalancers.IPortfolioRebalancer;


public abstract class MetricCalculatorBase 
        implements IMetricCalculator
{
    protected static final double RISK_FREE_RATE = 1.05;
    protected static final int TRADING_DAYS_IN_YEAR = 251;
    protected static final double SQRT_TRADING_DAYS_IN_YEAR = Math.sqrt(TRADING_DAYS_IN_YEAR);
    public MetricCalculatorBase()
    {
        rebalancer = null;
    }

    public void setRebalancer(IPortfolioRebalancer rebalancer)
    {
        this.rebalancer = rebalancer;
    }

    protected IPortfolioRebalancer rebalancer;
}
