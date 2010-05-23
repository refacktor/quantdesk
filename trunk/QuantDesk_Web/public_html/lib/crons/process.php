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
Set_Time_Limit(0);
/**
* Keep this so that the IP will be faked for the framework
*/
$_SERVER['REMOTE_ADDR'] = '0.0.0.0';

/**
* Include the mainframe
*/
If (!IsSet($_ENV['STOCK']))
{
    Echo "Please define the 'STOCK' environment variable in linux!\r\n\r\nYou can do this by executing the following command:\r\nexport STOCK=/path/to/your/website/\r\n\r\nDon't forget the trailing slash!";
    $_ENV['STOCK'] = '../../';
}

Require_Once($_ENV['STOCK'] . 'lib/scylla.php');

/**
* The function is called ShowProcess, but it doesnt actually show anything, but it is needed to let the framework execute this function
*/
Function ShowProcess()
{
    Global $_CONFIG;
    $initMicroTime = microtime_float();

    $Query = New Query();

    Echo 'Beginning with cron...' . "\r\n";
    /**
    * Download NYSE, NASDAQ and AMEX:
    *
    * Format of the files in CSV (from NASDAQ):
    * AACC,04-Dec-2009,5.88,6.04,5.82,6.03,44300
    * AACC = stock code
    * 04-Dec-2009 = date
    * 5.88 = Open
    * 6.04 = High
    * 5.82 = Low
    * 6.03 = Close
    * 44300 = Volume
    */

    /**
    * Get all sources
    */
    $Query->SQL = 'SELECT
                        `sources`.`name`,
                        `sources`.`sourceid`
                   FROM
                        `sources`';
    $Query->Execute();

    $Sources = $Query->FetchObjectList();

    /**
    * If the date is saturday or sunday, nevermind! Exit the cron
    */
    //echo Date('N', StrToTime('Yesterday'));
    If (Date('N', StrToTime('Today')) > 5)
    {
        Echo 'Today is saturday or sunday, so skip!' . "\r\n";
        Exit;
    }

    $Date = Date('Ymd', StrToTime('Yesterday'));
    //$Date = Date('Ymd', StrToTime('last friday'));
    Echo 'Today is ' . $Date . "\r\n";

    Echo 'Connecting to ftp...' . "\r\n";
    $FTPConnection = FTP_Connect('ftp.eoddata.com');

    If (!$FTPConnection)
    {
        FTP_Close($FTPConnection);
        Die('Cannot connect to FTP!');
    }

    Echo 'Connection OK, logging in...' . "\r\n";
    $FTPLogin = FTP_Login($FTPConnection, 'atramos', 'One72f61tO5q');

    If (!$FTPLogin)
    {
        FTP_Close($FTPConnection);
        Die('Cannot connect to FTP!');
    }

    Echo 'Server contacted, downloading sources...' . "\r\n";
    /**
    * Go throuh each source and download the file from FTP
    */
    ForEach ($Sources As $Source)
    {
        $NamesRemote = '/Names/' . StrToUpper($Source['name']) . '.txt';
        $StocksRemote = '/' . StrToUpper($Source['name']) . '_' . $Date . '.txt';

        Echo 'Dowloading...' . SCYLLA_ROOT . 'temp/' . StrToUpper($Source['name']) . '_names.txt' . ' from ' . $NamesRemote . "\r\n";
        /**
        * Get symbols
        */
        FTP_Get($FTPConnection, SCYLLA_ROOT . 'temp/' . StrToUpper($Source['name']) . '_names.txt', $NamesRemote, FTP_ASCII);

        /**
        * First get a directory list, check if the file is actually already available
        */
        $DirectoryList = FTP_NList($FTPConnection, '/');
        If (!In_Array(StrToUpper($Source['name']) . '_' . $Date . '.txt', $DirectoryList))
        {
            Die('Cannot download list, data not available on FTP server!');
        }

        Echo 'Dowloading...' . SCYLLA_ROOT . 'temp/' . StrToUpper($Source['name']) . '_stocks.txt' . 'from ' . $StocksRemote . "\r\n";
        /**
        * Get last results
        */
        If (!FTP_Get($FTPConnection, SCYLLA_ROOT . 'temp/' . StrToUpper($Source['name']) . '_stocks.txt', $StocksRemote, FTP_ASCII))
        {
            Die('Was not able to download latest data, error during transfer');
        }
    }
    Echo 'Closing FTP' . "\r\n";
    FTP_Close($FTPConnection);
    Echo 'Time spent downloading files: '. (float)(microtime_float() -$initMicroTime)."\r\n";


    /**
    * Empty the symbols table
    */
    $Query->SQL = 'TRUNCATE TABLE `symbols`';
    $Query->Execute();

    Echo 'Moving old stocks to history table' . "\r\n";
    /**
    * Move all stocks from the stocks table to the history table
    */
    $Query->SQL = 'SELECT
                        *
                   FROM
                        `stocks`';
    $Query->Execute();
    $Stocks = $Query->FetchObjectList();

    ForEach ($Stocks As $Stock)
    {
        $Query->SQL = 'INSERT INTO
                            `history`
                       SET
                            `history`.`symbol` = :symbol,
                            `history`.`sourceid` = :sourceid,
                            `history`.`date` = :date,
                            `history`.`open` = :open,
                            `history`.`high` = :high,
                            `history`.`low` = :low,
                            `history`.`close` = :close,
                            `history`.`volume` = :volume,
                            `history`.`changepoints` = :changepoints,
                            `history`.`changeperc` = :changeperc';
        $Query->Bind(Q_STRING, 'symbol', $Stock['symbol']);
        $Query->Bind(Q_INTEGER, 'sourceid', $Stock['sourceid']);
        $Query->Bind(Q_STRING, 'date', $Stock['date']);
        $Query->Bind(Q_FLOAT, 'open', $Stock['open']);
        $Query->Bind(Q_FLOAT, 'high', $Stock['high']);
        $Query->Bind(Q_FLOAT, 'low', $Stock['low']);
        $Query->Bind(Q_FLOAT, 'close', $Stock['close']);
        $Query->Bind(Q_INTEGER, 'volume', $Stock['volume']);
        $Query->Bind(Q_FLOAT, 'changepoints', $Stock['changepoints']);
        $Query->Bind(Q_FLOAT, 'changeperc', $Stock['changeperc']);
        $Query->Execute();
    }

    $Query->SQL = 'TRUNCATE TABLE `stocks`';
    $Query->Execute();

    Echo 'Importing daily stocks' . "\r\n";
    /**
    * Now we have all data, put it in the database
    */
    ForEach ($Sources As $Source)
    {
        /**
        * Reset the symbols
        */
        $File = File( SCYLLA_ROOT . 'temp/' . $Source['name'] . '_names.txt');

        For ($I = 1; $I < Count($File); $I++)
        {
            $Line = Trim($File[$I]);
            Preg_Match('/(.*?)\s(.*)/i', $Line, $Match);
            $Match[2] = Str_Replace('$', '', $Match[2]);

            $Query->SQL = 'INSERT INTO
                                `symbols`
                           SET
                                `symbolcode` = :symbolcode,
                                `sourceid` = :sourceid,
                                `name` = :name';
            $Query->Bind(Q_STRING, 'symbolcode', $Match[1]);
            $Query->Bind(Q_INTEGER, 'sourceid', $Source['sourceid']);
            $Query->Bind(Q_STRING, 'name', $Match[2]);
            $Query->Execute();
        }

        /**
        * Go through all stocks
        */
        $File = File( SCYLLA_ROOT . 'temp/' . $Source['name'] . '_stocks.txt');

        For ($I = 0; $I < Count($File); $I++)
        {
            $Line = Trim($File[$I]);
            $Line = Explode(',', $Line);

            $Symbol = Trim($Line[0]);
            $Date = Date('Y-m-d', StrToTime($Line[1]));
            $Open = FloatVal($Line[2]);
            $High = FloatVal($Line[3]);
            $Low = FloatVal($Line[4]);
            $Close = FloatVal($Line[5]);
            $Volume = IntVal($Line[6]);

            $Query->SQL = 'SELECT
                                `history`.`close`
                           FROM
                                `history`
                           WHERE
                                `history`.`sourceid` = :sourceid
                           AND
                                `history`.`symbol` = :symbol
                           ORDER BY
                                `history`.`date` DESC
                           LIMIT 0, 1';
            $Query->Bind(Q_STRING, 'symbol', $Symbol);
            $Query->Bind(Q_INTEGER, 'sourceid', $Source['sourceid']);
            $Query->Execute();

            $ChangeNr = 0;
            $ChangePerc = 0;
            If ($Query->NumRows() == 1)
            {
                $Prev = $Query->Result();
                $ChangeNr = Round($Close - $Prev, 2);
                $ChangePerc = Round(100 - (($Prev / $Close) * 100), 2);
            }

            $Query->SQL = 'INSERT INTO
                                `stocks`
                           SET
                                `symbol` = :symbol,
                                `sourceid` = :sourceid,
                                `date` = :date,
                                `open` = :open,
                                `high` = :high,
                                `low` = :low,
                                `close` = :close,
                                `volume` = :volume,
                                `changepoints` = :changepoints,
                                `changeperc` = :changeperc';
            $Query->Bind(Q_STRING, 'symbol', $Symbol);
            $Query->Bind(Q_INTEGER, 'sourceid', $Source['sourceid']);
            $Query->Bind(Q_STRING, 'date', $Date);
            $Query->Bind(Q_FLOAT, 'open', $Open);
            $Query->Bind(Q_FLOAT, 'high', $High);
            $Query->Bind(Q_FLOAT, 'low', $Low);
            $Query->Bind(Q_FLOAT, 'close', $Close);
            $Query->Bind(Q_FLOAT, 'volume', $Volume);
            $Query->Bind(Q_FLOAT, 'changepoints', $ChangeNr);
            $Query->Bind(Q_FLOAT, 'changeperc', $ChangePerc);
            $Query->Execute();
        }
    }
    Echo 'Time spent updating stocks: '.(float)(microtime_float() -$initMicroTime)."\r\n";
    Echo 'Starting to create corrolations' . "\r\n";
    /**
    * Create the correlations
    */
    $TimeB = Time();

    /**
    * Fetch the symbols + stock values
    */
    $Query->SQL = 'SELECT
                        `symbols`.`symbolcode`,
                        `symbols`.`sourceid`,
                        `stocks`.`close`
                   FROM
                        `symbols`
                   LEFT JOIN
                        `stocks`
                   ON
                        `stocks`.`symbol` = `symbols`.`symbolcode`
                   AND
                        `stocks`.`sourceid` = `symbols`.`sourceid`
                   ORDER BY
                        `symbols`.`symbolcode` ASC';
    $Query->Execute();
    $Symbols = $Query->FetchObjectList();

    /**
    * Store all values in $Stocks array to avoid having to overload the database
    */
    $Stocks = Array();
    //$ctrl1 = 0;

    ForEach ($Symbols As $Symbol)
    {
        $Query->SQL = 'SELECT
                            `history`.`close`
                       FROM
                            `history`
                       WHERE
                            `history`.`symbol` = :symbol
                       AND
                            `history`.`sourceid` = :sourceid
                       ORDER BY
                            `history`.`date` DESC';
        $Query->Bind(Q_STRING, 'symbol', $Symbol['symbolcode']);
        $Query->Bind(Q_STRING, 'sourceid', $Symbol['sourceid']);
        $Query->Execute();
        $Results = $Query->FetchObjectList();
        $Key = $Symbol['symbolcode'] . $Symbol['sourceid'];
        $Stocks[$Key] = Array();

        ForEach ($Results As $Result)
        {
            $Stocks[$Key][] = $Result['close'];
        }
        $Stocks[$Key][] = $Symbol['close'];

        /*
        $ctrl1++;
        if ($ctrl1 >= 200)
        {
            break;
        }
         *
         */
    }

    /*
    print_r($Stocks);
    exit;
    */

    /**
    * Write all correlations in a tmp file
    */
    $FileHandle = FOpen(SCYLLA_ROOT . 'temp/correlations.txt', 'w');

    /**
    * Create a N:M correlations entry
    */
    ForEach ($Stocks As $OrgKey => $Stock1)
    {
        ForEach ($Stocks As $ForKey => $Stock2)
        {
            /**
            * Skip if the symbols are the same
            */
            If ($OrgKey <> $ForKey)
            {
                /**
                * Check who as the shortest history
                */
                If (Count($Stock1) < Count($Stock2))
                    $Count = Count($Stock1);
                Else
                    $Count = Count($Stock2);

                /**
                * Only continue if there is a history available
                */
                If ($Count > 0)
                {
                    /**
                    * Calculate the iteration
                    */
                    For ($I = 0; $I < $Count; $I++)
                    {
                        $MultipleXY[$I] = $Stock1[$I] * $Stock2[$I];
                        $MultipleXX[$I] = $Stock1[$I] * $Stock1[$I];
                        $MultipleYY[$I] = $Stock2[$I] * $Stock2[$I];
                    }

                    /**
                    * Calculate the sigma's for the formula
                    */
                    $SigmaX = Array_Sum($Stock1);
                    $SigmaY = Array_Sum($Stock2);
                    $SigmaXY = Array_Sum($MultipleXY);
                    $SigmaXX = Array_Sum($MultipleXX);
                    $SigmaYY = Array_Sum($MultipleYY);

                    /**
                    * Calculate the correlation
                    */
                    $Calc1 = (($Count * $SigmaXY) - ($SigmaX * $SigmaY));
                    $Calc2 = Sqrt((($Count * $SigmaXX) - Pow($SigmaX, 2)) * (($Count * $SigmaYY) - Pow($SigmaY, 2)));
                    If ($Calc2 <> 0)
                        $Correlation = Round($Calc1 / $Calc2, 4);
                    Else
                        $Correlation = 0;

                    /**
                    * Retrieve the symbol and sourceID from the key of the array
                    */

                    $OrgSymbol = SubStr($OrgKey, 0, -1);
                    $OrgSource = SubStr($OrgKey, -1, 1);
                    $ForSymbol = SubStr($ForKey, 0, -1);
                    $ForSource = SubStr($ForKey, -1, 1);

                    /**
                    * Create a line to write out
                    */
                    $Line = "'" . $OrgSymbol . "',";
                    $Line.= "'" . $ForSymbol . "',";
                    $Line.= "'" . $Correlation . "'\r\n";

                    /**
                    * Write the line to the temp file
                    */
                    FWrite($FileHandle, $Line);
                }
            }
        }
    }
    FClose($FileHandle);
    Echo 'Time spent writing the correlations files: '. (float)(microtime_float() -$initMicroTime)."\r\n";
    //exit;
    Echo 'Correlations exported in ' . (Time() - $TimeB) . ' seconds' . "\r\n";
    Echo 'Start MySQL import of ' . "\r\n";
    /**
    * Empty the correlations table
    */
    $Query->SQL = 'TRUNCATE TABLE `correlations`';
    $Query->Execute();

    /**
    * Insert the data in the DB with the LOAD DATA LOCAL INFILE command
    * to speed up this process (about 10.000 records in ~3 seconds)
    */
    $Path = SCYLLA_ROOT;
    $Query->SQL = "LOAD DATA LOCAL INFILE '" . $Path . "temp/correlations.txt'
                   INTO TABLE correlations
                   FIELDS TERMINATED BY ','
                   ENCLOSED BY '\''
                   LINES TERMINATED BY '\r\n'
                   (firstsymbol, secondsymbol, correlation);";
    $Query->Execute();

    Echo 'Time spent dumping data to Mysql: '. (float)(microtime_float() -$initMicroTime)."\r\n";

    Echo 'Imported in ' . (Time() - $TimeB) . ' seconds' . "\r\n";
}



/*
 *
 * This function is added by mauricio.muriel@calitek.net
 *
 *
 */
function microtime_float()
{
list($useg, $seg) = explode(" ", microtime());
return ((float)$useg + (float)$seg);
}
?>
