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

import java.util.Calendar;



//  rebalances portfolio each month
public class MonthlyRebalancer 
        extends RebalancerBase
        implements IPortfolioRebalancer
{

    public MonthlyRebalancer()
    {
        lastRebalanceDate = Calendar.getInstance();
        lastRebalanceDate.clear();
    }

    public void initRebalancer(Calendar date)
    {
        lastRebalanceDate = date;
    }

    public boolean shouldRebalance(Calendar date)
    {
        boolean ret = false;
        if(lastRebalanceDate.get(Calendar.YEAR) != date.get(Calendar.YEAR))
            ret = true;
        if(lastRebalanceDate.get(Calendar.MONTH) != date.get(Calendar.MONTH))
            ret = true;

        if(true==ret)
            lastRebalanceDate = date;
        
        return ret;
    }

    private Calendar lastRebalanceDate;
}
