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


public class PermutationGenerator
{
    public static boolean nextPermutation(int[] p)
    {
        int a = p.length - 2;
        while (a >= 0 && p[a] >= p[a + 1])
        {
            a--;
        }
        if (a == -1)
        {
            return false;
        }
        int b = p.length - 1;
        while (p[b] <= p[a])
        {
            b--;
        }
        int t = p[a];
        p[a] = p[b];
        p[b] = t;
        for (int i = a + 1, j = p.length - 1; i < j; i++, j--)
        {
            t = p[i];
            p[i] = p[j];
            p[j] = t;
        }
        return true;
    }
}
