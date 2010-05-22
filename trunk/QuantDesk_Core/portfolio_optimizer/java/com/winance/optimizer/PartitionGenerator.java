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


public class PartitionGenerator
{
    public PartitionGenerator(int sum, int parts)
    {
        this.n_ = sum;
        this.m_ = parts;
        x_ = new int[m_+1];
        s_ = new int[m_+1];
        init();
    }

    private void init()
    {
        x_[0] = 0;
        for (int k=1; k<m_; ++k)
            x_[k] = 1;
        x_[m_] = n_ - m_ + 1;
        int s = 0;
        for (int k=0; k<=m_; ++k)
        {
            s+=x_[k];
            s_[k]=s;
        }
    }

    public int[] data()
    {
        int[] ret = new int[x_.length-1];
        for(int i=1;i<x_.length;i++)
            ret[i-1] = x_[i];

        return ret;
    }
    public boolean next()
    {
        int u = x_[m_];  // last element
        int k = m_;
        while ( 0 != --k )
        {
            if ( x_[k]+2<=u )
                break;
        }

        if ( k==0 )
            return false;

        int f = x_[k] + 1;
        int s = s_[k-1];
        while ( k < m_ )
        {
            x_[k] = f;
            s += f;
            s_[k] = s;
            ++k;
        }
        x_[m_] = n_ - s_[m_-1];
        // s_[m_] = n_;  // unchanged

        return true;
    }


    private int[] x_;  // partition: x[1]+x[2]+...+x[m] = n
    private int[] s_;  // aux: cumulative sums of x[]  (s[0]=0)
    private int n_;   // integer partitions of n  (must have n>0)
    private int m_;   // ... into m parts  (must have 0<m<=n)

}
