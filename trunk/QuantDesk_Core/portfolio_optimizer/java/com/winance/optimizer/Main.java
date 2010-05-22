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

import com.winance.optimizer.metrics.IMetricCalculator;
import com.winance.optimizer.metrics.SharpeCalculator;
import com.winance.optimizer.metrics.SortinoCalculator;
import com.winance.optimizer.metrics.rebalancers.MonthlyRebalancer;
import com.winance.optimizer.portfolio.Portfolio;
import com.winance.optimizer.portfolio.selection.BasicPortfolioSelector;
import com.winance.optimizer.portfolio.selection.ExchaustiveWeightsSelector;
import com.winance.optimizer.portfolio.selection.IPortfolioSelector;
import com.winance.optimizer.portfolio.selection.ISymbolsSelector;
import com.winance.optimizer.portfolio.selection.IWeightsSelector;
import com.winance.optimizer.portfolio.selection.IterativeSymbolsSelector;
import com.winance.optimizer.symbols.Symbol;
import com.winance.optimizer.symbols.loaders.ISymbolsLoader;
import com.winance.optimizer.symbols.loaders.yahoo.YahooLoader;
import java.util.ArrayList;
import java.util.Calendar;


public class Main
{

    private static void printUsage()
    {
        String usage = String.format(
                "Usage: <ratio> <year> <symbol names>%n"+
                "<ratio> can be 'sharpe' or 'sortino'%n"+
                "<year> - number for year to use historical data from%n"+
                "<symbol names> - names of symbols, separated by space%n");

        System.out.println(usage);
    }

    private static IMetricCalculator getRatioFromArgs(String[] args)
    {
        IMetricCalculator ret = null;

        String ratio = args[0];
        ratio = ratio.trim();
        if(0 == ratio.compareToIgnoreCase("sharpe"))
            ret = new SharpeCalculator();
        else
        if(0 == ratio.compareToIgnoreCase("sortino"))
            ret = new SortinoCalculator();
        else
            System.out.println("Error: unknown ratio given.");
        
        return ret;
    }

    private static Integer getYearFromAgrs(String[] args)
    {
        Integer ret = null;

        String yearStr = args[1];

        try
        {
            ret = Integer.parseInt(yearStr);
        }
        catch(NumberFormatException e)
        {
            System.out.println("Error: non-integer year given.");
        }
        finally
        {
            return ret;
        }
    }

    private static ArrayList<Symbol> loadSymbols(String[] args, int year, String cacheFolder)
    {
        ArrayList<Symbol> ret = new ArrayList<Symbol>();
        ISymbolsLoader loader = new YahooLoader();

        for(int i=2;i<args.length;i++)
        {
            Symbol s = loader.loadData(args[i], year, cacheFolder);
            ret.add(s);
        }

        return ret;
    }

    private static IPortfolioSelector createSelector(String[] args)
    {
        IPortfolioSelector portfolioSelector = null;

        IMetricCalculator metricCalculator = getRatioFromArgs(args);
        if(null==metricCalculator)
            return portfolioSelector;

        //  basic selection algorithm root
        portfolioSelector = new BasicPortfolioSelector();
            //  part of algorithm responsible for best symbols conbination selection
            ISymbolsSelector symbolsSelector = new IterativeSymbolsSelector();
                //  calculator of metric used to determine portfolio value
                    //  rebalancer for metric calculator
                    //  implemented IntervalRebalancer and MonthlyRebalancer
                    metricCalculator.setRebalancer(new MonthlyRebalancer());
                symbolsSelector.setMetricCalculator(metricCalculator);
            portfolioSelector.setSymbolsSelector(symbolsSelector);

            //  part of algorithm responsible for best weights selection for given symbols combination
            IWeightsSelector weightsSelector = new ExchaustiveWeightsSelector();
            portfolioSelector.setWeightsSelector(weightsSelector);

       return portfolioSelector;
    }
    /**
     * @param args the command line arguments
     */

    private static final String CACHE_FOLDER = "./symbols cache";
    public static void main(String[] args)
    {
        if(args.length < 3)
        {
            printUsage();
            return;
        }

        Integer year = getYearFromAgrs(args);
        if(null==year)
            return;

        IPortfolioSelector portfolioSelector = createSelector(args);
        if(null==portfolioSelector)
            return;
        
        ArrayList<Symbol> p = loadSymbols(args, year, CACHE_FOLDER);

        //  EEM GDX GLD IWM JNK SPY TIP TLT VNQ XLE

        portfolioSelector.select(p, year);
    }

    public static void testSymbols(IMetricCalculator calculator, ArrayList<Symbol> availableSymbols)
    {
        int year = 2009;

        for(int i=0;i<availableSymbols.size();i++)
        {
            Portfolio p = new Portfolio();
            p.addPosition(availableSymbols.get(i));

            int[] weights = {100};

            String summary = "";

            summary += "'" + availableSymbols.get(i).getName() + "':";
            summary += " " + calculator.calculate(p, year, weights);

            System.out.println(summary);
        }
    }
}
