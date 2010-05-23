<?/*
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
 */?>
 <?php
  $_CONFIG['SiteTitle'] =  "$Stock[name] ($Stock[symbolcode])";
  require('header.php');?>
    <div class="Bar">
        <div class="Name"><?php echo $Stock['name'];?> (<?php echo $Stock['symbolcode'];?>)</div>
        <div class="DifferenceBar <?php if ($Stock['changepoints'] > 0) echo 'Up'; else echo 'Down';?>"><?php echo $Stock['changepoints'];?> (<?php echo $Stock['changeperc'];?>%)</div>
        <div class="StockBar"><?php echo $Stock['close'];?></div>
        <div class="Date"><?php echo $Stock['date'];?>:</div>
    </div>
    <div class="ContentWrapper">
        <div class="Content">
            <div class="Row">
                <span class="Col1">Last Trade:</span>
                <span class="Col2"><?php echo $Stock['close'];?></span>
            </div>
            <div class="Row">
                <span class="Col1">Trade Time:</span>
                <span class="Col2"><?php echo $Stock['date'];?></span>
            </div>
            <div class="Row">
                <span class="Col1">Change:</span>
                <span class="Col2"><?php echo $Stock['changepoints'];?> (<?php echo $Stock['changeperc'];?>%)</span>
            </div>
        </div>
        <div class="Content">
            <div class="Row">
                <span class="Col1">Prev Close:</span>
                <span class="Col2"><?php echo $Stock['lastclose'];?></span>
            </div>
            <div class="Row">
                <span class="Col1">Open:</span>
                <span class="Col2"><?php echo $Stock['open'];?></span>
            </div>
            <div class="Row">
                <span class="Col1">Day's range:</span>
                <span class="Col2"><?php echo $Stock['low'];?> - <?php echo $Stock['high'];?></span>
            </div>
            <div class="Row">
                <span class="Col1">Volume:</span>
                <span class="Col2"><?php echo $Stock['volume'];?></span>
            </div>
        </div>

        <br />
        <div class="RowLong">
            <h3>Top 10 Most Correlated</h3>
            <br />
            <table class="Top10">
                <?php foreach($Top10 as $Item){?>
                <tr>
                    <td><a href="stock/<?php echo $Item['sourceid'];?>/<?php echo $Item['symbolcode'];?>/"><?php echo $Item['name'];?></a></td>
                    <td><?php echo $Item['symbolcode'];?></td>
                    <td><?php echo $Item['correlation'];?></td>
                </tr>
                <?php };?>
            </table>
        </div>

        <br />
        <div class="RowLong">
            <h3>Top 10 Least Correlated</h3>
            <br />
            <table class="Top10">
                <?php foreach($Bottom10 as $Item){?>
                <tr>
                    <td><a href="stock/<?php echo $Item['sourceid'];?>/<?php echo $Item['symbolcode'];?>/"><?php echo $Item['name'];?></a></td>
                    <td><?php echo $Item['symbolcode'];?></td>
                    <td><?php echo $Item['correlation'];?></td>
                </tr>
                <?php };?>
            </table>
        </div>

    </div>
    <img src="http://chart.eoddata.com?e=<?php echo $Stock['sourcename'];?>&s=<?php echo $Stock['symbolcode'];?>&w=500&h=300" width="500" height="300" border="0">
<?php require('footer.php');?>