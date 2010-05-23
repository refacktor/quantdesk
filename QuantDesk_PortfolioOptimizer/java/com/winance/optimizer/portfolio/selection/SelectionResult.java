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

import com.winance.optimizer.portfolio.Portfolio;
import java.util.Arrays;


public class SelectionResult
{
        public SelectionResult(double ratio, int[] weights, Portfolio p)
        {
            this.ratio = ratio;
            if(null!=weights)
                this.weights = Arrays.copyOf(weights, weights.length);
            if(null!=p)
            {
                this.symbols = new String[p.size()];
                for(int i=0;i<p.size();i++)
                    symbols[i] = p.getPosition(i).getName();

            }
        }

        public void setData(SelectionResult other)
        {
            this.ratio = other.ratio;
            this.weights = Arrays.copyOf(other.weights, other.weights.length);
            this.symbols = Arrays.copyOf(other.symbols, other.symbols.length);
        }

        public double getRatio()
        {
            return ratio;
        }

        public int[] getWeights()
        {
            return Arrays.copyOf(weights, weights.length);
        }

        public String[] getSymbolNames()
        {
            return Arrays.copyOf(symbols, symbols.length);
        }

        public void print()
        {
            String answer = "ratio = " + this.getRatio() + ", allocation: ";

            for(int i=0;i<getSymbolNames().length;i++)
            {
                answer +="'" + this.getSymbolNames()[i] + "' " + this.getWeights()[i] + "% ";
            }

            System.out.println(answer);
        }

        private double ratio;
        private int[] weights;
        private String[] symbols;
}
