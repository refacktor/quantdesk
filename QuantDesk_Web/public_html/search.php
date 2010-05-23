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
 * COPYRIGHT (C)2010 ZIGABYTE CORPORATION. ALL RIGHTS RESERVED.
 */
Require_Once('lib/scylla.php');

/**
 * ShowIndex
 *
 * Parse the index page with some extra features
 */
Function ShowSearch()
{
	Global $_CONFIG;
	/**
	 * Check if a letter is selected
	 */
	If (IsSet($_GET['letter']) && Preg_Match('/^[A-Z]$/', $_GET['letter']))
	{
		$ActiveLetter = $_GET['letter'];
	}
	Else
	{
		$ActiveLetter = 'A';
	}

	$Searching = True;

	$Query = New Query();
	$Query->SQL = "SELECT count(*) as n FROM symbols where symbolcode = :symbol";
	$Query->Bind(Q_STRING, 'symbol', $_GET['search']);
	$Query->Execute();
	$Results = $Query->FetchObjectList();
	if($Results[0]['n'] == 1) {
		header("Location: /stock/0/" . $_GET['search']);
	}

    $Query = New Query();
	$Query->SQL = 'SELECT
                            `symbols`.`symbolcode`,
                            `symbols`.`sourceid`,
                            `symbols`.`name`,
                            `stocks`.`date`,
                            `stocks`.`open`,
                            `stocks`.`high`,
                            `stocks`.`low`,
                            `stocks`.`close`,
                            `stocks`.`volume`,
                            `stocks`.`changepoints`,
                            `stocks`.`changeperc`,
                            `sources`.`name` AS `sourcename`
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
                            `sources`.`sourceid` = `symbols`.`sourceid`
                       WHERE
                            `stocks`.`high` <> 0
                       AND
                            (`symbols`.`symbolcode` LIKE :search
                            OR
                            `symbols`.`name` LIKE :search )
                       GROUP BY
                            `symbols`.`symbolcode`
                       ORDER BY
                            `symbols`.`symbolcode` ASC';

	$Query->Bind(Q_STRING, 'search', '%' . $_GET['search'] . '%');

	$Query->Execute();
	$Results = $Query->FetchObjectList();
	$StockList = Array();

	$Count = $Query->NumRows();

	ForEach ($Results As $Result)
	{
		$Result['symbolcodeencoded'] = URLEncode($Result['symbolcode']);
		$Result['volume'] = Number_Format($Result['volume'], 0, ',', '.');
		$StockList[] = $Result;
	}

	$Letters = Array('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

	/**
	 * Create a new template
	 */
	Require(SCYLLA_ROOT . 'templates/search.php');
}
?>
