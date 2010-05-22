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


package com.winance.optimizer.portfolio.selection;

import com.winance.optimizer.PartitionGenerator;
import com.winance.optimizer.PermutationGenerator;
import com.winance.optimizer.metrics.IMetricCalculator;
import com.winance.optimizer.portfolio.Portfolio;
import java.util.Arrays;
import java.util.Calendar;


public class ExchaustiveWeightsSelector 
        implements IWeightsSelector
{
    public SelectionResult getWeightsForMetric(Portfolio p, 
            IMetricCalculator calculator,
            int year)
    {
        SelectionResult best = new SelectionResult(Double.NEGATIVE_INFINITY, null, null);
        SelectionResult bestWithNonZeroWeights = new SelectionResult(Double.NEGATIVE_INFINITY, null, null);

        int sum = 100;

        long combinations = 0;

        Calendar c1 = Calendar.getInstance();

        for(int parts = 1; parts <= p.size(); parts++)
        {
            PartitionGenerator pg = new PartitionGenerator(sum, parts);

            do
            {
                int[] weights = new int[p.size()];
                int i=0;
                int[] partition = pg.data();
                for(i=0;i<parts;i++)
                    weights[i] = partition[i];
                for(;i<p.size();i++)
                    weights[i] = 0;
                Arrays.sort(weights);
                
                do
                {
                    SelectionResult item2 = calculateMetric(calculator, p, year, weights);
                    if(item2.getRatio() > best.getRatio())
                        best.setData(item2);
                    //  checking if all weights are non-zero
                    boolean nonZeroWeights = true;
                    for(i=0;i<weights.length;i++)
                    {
                        if(0==weights[i])
                        {
                            nonZeroWeights = false;
                            break;
                        }
                    }

                    //  saving separate best rezult for non-zero weights allocation
                    if(nonZeroWeights)
                    {
                        if(item2.getRatio() > bestWithNonZeroWeights.getRatio())
                            bestWithNonZeroWeights.setData(item2);
                    }

                    //printSimple(item2);

                    combinations ++;

                    if(0==combinations % 10000)
                        System.out.println("checked "+combinations + " combinations");
                }
                while(PermutationGenerator.nextPermutation(weights));
            }
            while(pg.next());
        }

        Calendar c2 = Calendar.getInstance();

        long timeMs = c2.getTimeInMillis() - c1.getTimeInMillis();

        printLocalResults(p, best, bestWithNonZeroWeights, combinations, timeMs);

        return best;
    }

    private SelectionResult calculateMetric(IMetricCalculator calculator,
            Portfolio p,
            int year,
            int[] weights)
    {
        double ratio = calculator.calculate(p, year, weights);
        SelectionResult ret = new SelectionResult(ratio, weights, p);
        return ret;
    }

    private void printLocalResults(Portfolio p,
        SelectionResult bestItem, SelectionResult bestWithNonZeroWeights,
        long combinations, long timeMs)
    {

        System.out.print("Best ");
        bestItem.print();

        System.out.print("Best with all non-zero weights ");
        bestWithNonZeroWeights.print();
        /*
        String answer = "";

        answer += "Best ratio = " + bestItem.getRatio() + ", allocation: ";

        for(int i=0;i<p.size();i++)
        {
            answer +="'" + p.getPosition(i).symbolName() + "' " + bestItem.getWeights()[i] + "% ";
        }

        System.out.println(answer);
           */
        String stats = "";

        stats += "checked "+combinations+" combinations ";
        stats += "in " + (timeMs / 1000.) + " seconds, ";
        stats += "speed = " + (combinations / (timeMs / 1000.)) + " combinations / second.";

        System.out.println(stats);
    }
}
