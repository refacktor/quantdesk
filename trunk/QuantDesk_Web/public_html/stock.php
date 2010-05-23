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
/**
* Include the mainframe
*/
Require_Once('lib/scylla.php');

/**
* ShowStock
*
* Parse the index page with some extra features
*/
Function ShowStock()
{
    Global $_NAV, $_CONFIG;

# $_NAV['id'];

    //echo("<pre>" . var_export($_SERVER) . "</pre>");

    $parms = explode('/', $_SERVER['REQUEST_URI']);
    $SymbolCode = $parms[3];
    $SourceID = $parms[2];


    /**
    * Request the data
    */
    $Query = New Query();
    $Query->SQL = 'SELECT
                        `symbols`.`symbolcode`,
                        `symbols`.`sourceid`,
                        `symbols`.`name`,
                        `stocks`.`symbol`,
                        `stocks`.`date`,
                        `stocks`.`open`,
                        `stocks`.`high`,
                        `stocks`.`low`,
                        `stocks`.`close`,
                        `stocks`.`volume`,
                        `stocks`.`changepoints`,
                        `stocks`.`changeperc`,
                        `sources`.`name` AS `sourcename`,
                        (SELECT
                            `history`.`close`
                         FROM
                            `history`
                         WHERE
                            `history`.`symbol` = :symbolcode
                         AND
                            `history`.`date` < `stocks`.`date`
                         ORDER BY
                            `history`.`date` DESC
                         LIMIT
                            0, 1
                         ) AS `lastclose`
                   FROM
                        `symbols`
                   LEFT JOIN
                        `stocks`
                   ON
                        `stocks`.`symbol` = `symbols`.`symbolcode`
                   AND
                        `stocks`.`sourceid` = `symbols`.`sourceid`
                   LEFT JOIN
                        `sources`
                   ON
                        `sources`.`sourceid` = `stocks`.`sourceid`
                   WHERE
                        `symbols`.`symbolcode` = :symbolcode
                   ORDER BY
                        `stocks`.`date` DESC
                   LIMIT 0, 1';
    $Query->Bind(Q_STRING, 'symbolcode', $SymbolCode);
    $Query->Execute();

    /**
    * Check if the symbol exists
    */
    If ($Query->NumRows() == 0)
    {
        Die('This symbol was not found!');
    }

    /**
    * Fetch the result
    */
    $Stock = $Query->FetchObject();
    $Stock['volume'] = Number_Format($Stock['volume'], 0, ',', '.');
    $Stock['date'] = StrFTime2('%e %h', StrToTime($Stock['date']));

    /**
    * Get top 10 correlations
    */
    $Query->SQL = 'SELECT
                        `symbols`.`symbolcode`,
                        `symbols`.`sourceid`,
                        `symbols`.`name`,
                        `correlations`.`correlation`
                   FROM
                        `correlations`
                   LEFT JOIN
                        `symbols`
                   ON
                        `symbols`.`symbolcode` = `correlations`.`secondsymbol`
                   WHERE
                        `correlations`.`firstsymbol` = :firstsymbol
                   ORDER BY
                        `correlations`.`correlation` DESC
                   LIMIT 0, 10';
    $Query->Bind(Q_STRING, 'firstsymbol', $Stock['symbol']);
    $Query->Bind(Q_INTEGER, 'firstsource', $Stock['sourceid']);
    $Query->Execute();
    $Top10 = $Query->FetchObjectList();

    $Query->SQL = 'SELECT
                        `symbols`.`symbolcode`,
                        `symbols`.`sourceid`,
                        `symbols`.`name`,
                        `correlations`.`correlation`
                   FROM
                        `correlations`
                   LEFT JOIN
                        `symbols`
                   ON
                        `symbols`.`symbolcode` = `correlations`.`secondsymbol`
                   WHERE
                        `correlations`.`firstsymbol` = :firstsymbol
                     AND `symbols`.`name` NOT REGEXP "(ISHARES|BEAR|PROSHARES|RYDEX|DIREXION|POWERSHARES)"
                   ORDER BY
                        `correlations`.`correlation` ASC
                   LIMIT 0, 10';
    $Query->Bind(Q_STRING, 'firstsymbol', $Stock['symbol']);
    $Query->Bind(Q_INTEGER, 'firstsource', $Stock['sourceid']);
    $Query->Execute();
    $Bottom10 = $Query->FetchObjectList();

    Include(SCYLLA_ROOT . 'templates/stock.php');
}
?>
