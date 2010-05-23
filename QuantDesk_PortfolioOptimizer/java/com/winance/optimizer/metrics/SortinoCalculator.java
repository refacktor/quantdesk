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


package com.winance.optimizer.metrics;

import com.winance.optimizer.Calculations;
import com.winance.optimizer.portfolio.Portfolio;
import com.winance.optimizer.symbols.SymbolRatioItem;
import java.util.ArrayList;


public class SortinoCalculator
        extends MetricCalculatorBase
        implements IMetricCalculator
{

    public String getName()
    {
        return "Sortino ratio";
    }

    public double calculate(final Portfolio p, int year, int[] percents)
    {
                //  initialize rebalancing koefficients array
        ArrayList<Double> rebalancingKoeffs = new ArrayList<Double>(p.size());
        for (int i = 0; i < p.size(); i++)
        {
            rebalancingKoeffs.add(1.);
        }

        //  getting return ratios from all symbols
        ArrayList<ArrayList<SymbolRatioItem>> returnRatios = new ArrayList<ArrayList<SymbolRatioItem>>(p.size());
        for (int i = 0; i < p.size(); i++)
        {
            ArrayList<SymbolRatioItem> pos = p.getPosition(i).getReturnRatios(year);
            //ArrayList<Double> pos = positions.get(i).getRawValues(startDate, endDate);
            returnRatios.add(pos);
        }

        //  combining ratios according given percents

        rebalancer.initRebalancer(returnRatios.get(0).get(0).date);

        ArrayList<Double> combinedRatios = new ArrayList<Double>(returnRatios.get(0).size());
        
        //  outer loop - iterating all days
        for (int i = 0; i < returnRatios.get(0).size(); i++)
        {
            if(rebalancer.shouldRebalance(returnRatios.get(0).get(i).date))
            {
                //  create slice of current ratios
                ArrayList<Double> currentRatios = new ArrayList<Double>(returnRatios.size());
                for (int k = 0; k < returnRatios.size(); k++)
                {
                    currentRatios.add(returnRatios.get(k).get(i).ratio);
                }

                rebalancer.rebalance(rebalancingKoeffs, percents, currentRatios);
            }

            double combinedRatio = 0;
            //  inner loop - iterating all symbols for i-th day
            for (int j = 0; j < returnRatios.size(); j++)
            {
                combinedRatio +=
                        returnRatios.get(j).get(i).ratio
                        * (percents[j] / 100.0)
                        * rebalancingKoeffs.get(j);
            }

            combinedRatios.add(combinedRatio);
        }

        //  calculating natural logarithms from combined ratios
        ArrayList<Double> combinedRatiosLn = new ArrayList<Double>(combinedRatios.size());
        for (int i = 0; i < combinedRatios.size(); i++)
        {
            combinedRatiosLn.add(Math.log(combinedRatios.get(i)));
        }

        double combinedDailyRatiosLnAverage = Calculations.simpleAverage(combinedRatiosLn);
        double combinedDailyCAGR = 0;

        //  calculating downside deviation
        double dailyRiskFreeRate = Math.pow(RISK_FREE_RATE, 1. / TRADING_DAYS_IN_YEAR);
        ArrayList<Double> ddRatiosSquared = new ArrayList<Double>(combinedRatios.size());
        for(int i=0;i<combinedRatios.size();i++)
        {
            double val = combinedRatios.get(i) - dailyRiskFreeRate;
            ddRatiosSquared.add(Math.pow(Math.min(val, 0), 2.));
        }

        //  constant annual groth rate for whole portfolio
        combinedDailyCAGR = Math.exp(combinedDailyRatiosLnAverage);
        //  geometric standard deviation for whole portfolio
        double combinedDailyDownsideDeviation =
                Math.sqrt(Calculations.simpleAverage(ddRatiosSquared));

        double combinedAnnualizedCAGR = Math.pow(combinedDailyCAGR, TRADING_DAYS_IN_YEAR);
        double combinedAnnualizedDownsideDeviation =
                combinedDailyDownsideDeviation * SQRT_TRADING_DAYS_IN_YEAR;

        //  sharpe ratio for shole portfolio
        double sortinoRet = 0;
        sortinoRet = (combinedAnnualizedCAGR - RISK_FREE_RATE) /
                combinedAnnualizedDownsideDeviation;

        //System.out.print("(CAGR = " + combinedAnnualizedCAGR + " GSD = " + combinedAnnualizedDownsideDeviation + ")");

        return sortinoRet;
    }

}
