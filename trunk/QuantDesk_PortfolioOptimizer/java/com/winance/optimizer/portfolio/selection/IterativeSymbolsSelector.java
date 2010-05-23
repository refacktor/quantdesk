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

import com.winance.optimizer.metrics.IMetricCalculator;
import com.winance.optimizer.portfolio.Portfolio;
import com.winance.optimizer.symbols.Symbol;
import java.util.ArrayList;
import java.util.Calendar;


public class IterativeSymbolsSelector
        implements ISymbolsSelector
{
    public IterativeSymbolsSelector()
    {
        this.calculator = null;
    }

    public void setMetricCalculator(IMetricCalculator calculator)
    {
        this.calculator = calculator;
    }

    public SelectionResult findBestSymbolsCombination(ArrayList<Symbol> availableSymbols,
            int year,
            IWeightsSelector weightsSelector)
    {
        Portfolio p = new Portfolio();

        SelectionResult bestResult = new SelectionResult(Double.NEGATIVE_INFINITY, null, null);

        Calendar c1 = Calendar.getInstance();

        while(0!=availableSymbols.size())
        {
            Symbol pos = availableSymbols.get(0);
            availableSymbols.remove(pos);
            p.addPosition(pos);
            System.out.println("adding '" + pos.getName() + "' into portfolio");
            SelectionResult result = weightsSelector.getWeightsForMetric
                    (
                        p, calculator,
                        year
                     );
            //  if ratio from new  result is better than current best
            if(result.getRatio() >= bestResult.getRatio())
            {
                //  save new best ratio
                bestResult.setData(result);

                ArrayList<Symbol> positionsToRemove = new ArrayList<Symbol>();
                //  removing all symbols with 0 weights:
                //  collecting all such symbols
                for(int i=0;i<bestResult.getWeights().length;i++)
                {
                    if(0==bestResult.getWeights()[i])
                        positionsToRemove.add(p.getPosition(i));
                }
                //  removing them all
                for(int i=0;i<positionsToRemove.size();i++)
                {
                    System.out.println("removing '" + positionsToRemove.get(i).getName() + "' from portfolio");
                    p.removePosition(positionsToRemove.get(i));
                }
            }
            else
            {
                System.out.println("removing '" + pos.getName() + "' from portfolio");
                p.removePosition(pos);
            }
        }

        Calendar c2 = Calendar.getInstance();
        long time = c2.getTimeInMillis() - c1.getTimeInMillis();

        printPortfolioResults(p, bestResult, time);

        return bestResult;
    }

    private void printPortfolioResults(Portfolio p, SelectionResult item, long timeMs)
    {
        String summary = "";

        summary += "Best portfolio (sharpe = " + item.getRatio() +"): ";
        for(int i=0;i<item.getWeights().length;i++)
        {
            if(0!=item.getWeights()[i])
            {
                summary += "'" + item.getSymbolNames()[i] + "' " + item.getWeights()[i] + "% ";
            }
        }

        System.out.println("========== Results ==========");
        System.out.println(summary);
        System.out.println("calculated in " + (timeMs / 1000.) + " seconds.");
    }

    private IMetricCalculator calculator;
}
