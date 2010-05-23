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
 <?php require('header.php');?>
    <div class="Bar"></div>
    <ul id="Pagination">
        <?php foreach($Letters as $Letter){
            echo '<a href="' . $Letter . '/"><li';
            if ($ActiveLetter == $Letter)
                echo ' class="Active"';
            echo'>' . $Letter . '</li></a>';
        };?>
    </ul>
    <div class="SearchResults">
    <?php if ($Searching == True)
    {
        if ($Count == 0)
            echo 'There are no results for this search term.';
        elseif ($Count == 1)
            echo 'There is 1 result for this search term.';
        else
            echo 'There are ' . $Count . ' results for this search term.';
    }?>
    </div>
    <br />
    <?php if ($Count > 0){?>
    <table id="SymbolTable">
        <thead>
            <tr>
                <td>Code</td>
                <td>Name</td>
                <td>Index</td>
                <td>High</td>
                <td>Low</td>
                <td>Close</td>
                <td>Volume</td>
                <td>Change</td>
            </tr>
        </thead>
        <tbody>
        <?php $i =0 ;
        foreach($StockList as $Stock){
            if ($i++ % 2 == 0)
                $RowColor = 'R1';
            Else
                $RowColor = 'R2';
            If ($Stock['changepoints'] < 0)
                $NegColor = 'Red';
            Else
                $NegColor = 'Green';
            echo'<tr onclick="document.location = \''. SCYLLA_URL . 'stock/'.$Stock['sourceid'].'/'.$Stock['symbolcodeencoded'].'/\';">
                <td class="Col1 '.$RowColor.'">'.$Stock['symbolcode'].'</td>
                <td class="Col2 '.$RowColor.'">'.$Stock['name'].'</td>
                <td class="Col3 '.$RowColor.'">'.$Stock['sourcename'].'</td>
                <td class="Col4 '.$RowColor.'">'.$Stock['high'].'</td>
                <td class="Col5 '.$RowColor.'">'.$Stock['low'].'</td>
                <td class="Col6 '.$RowColor.'">'.$Stock['close'].'</td>
                <td class="Col7 '.$RowColor.'">'.$Stock['volume'].'</td>
                <td class="Col8 '.$RowColor.' '.$NegColor.'">'.$Stock['changepoints'].' ('.$Stock['changeperc'].'%)</td>
            </tr>';

        }?>
        </tbody>
    </table>
    <?php };?>
<?php require('footer.php');?>
