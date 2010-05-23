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

import com.winance.optimizer.symbols.Symbol;
import java.util.ArrayList;
import java.util.Calendar;


public class BasicPortfolioSelector
        implements IPortfolioSelector
{
    public BasicPortfolioSelector()
    {
        this.symbolsSelector = null;
        this.weightsSelector = null;
    }
    
    public void setSymbolsSelector(ISymbolsSelector symbolsSelector)
    {
        this.symbolsSelector = symbolsSelector;
    }

    public void setWeightsSelector(IWeightsSelector weightsSelector)
    {
        this.weightsSelector = weightsSelector;
    }

    public void select(ArrayList<Symbol> availableSymbols,
            int year)
    {
        symbolsSelector.findBestSymbolsCombination(availableSymbols,
                year,
                weightsSelector);
        //  print results
    }

    private ISymbolsSelector symbolsSelector;
    private IWeightsSelector weightsSelector;
}
