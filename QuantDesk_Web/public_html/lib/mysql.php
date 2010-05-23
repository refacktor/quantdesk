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
Define('Q_STRING', 1);
Define('Q_INTEGER', 2);
Define('Q_FLOAT', 3);
Define('Q_FIELD', 4);

Class MySQLDriver Extends MySQLi
{
    /**
    * Do we want to show errors
    */
    Var $ErrorShow;

    /**
    * Do we want to log errors
    */
    Var $ErrorLog;

    /**
    * Store the database link created by MySQL
    */
    Var $DbLink;

    /**
    * Count the number of query's we are executing in 1 script
    */
    Var $QueryCount = 0;

    /**
    * Object of the result of a query as mysqli_result
    */
    Var $QueryResult;

    /**
    * The number of rows that return from the query
    */
    Var $NumRows = 0;

    /**
    * If we have an error, store it here for processing later
    */
    Var $Error = NULL;

    /**
    * Calculate the average time of all queries per script
    */
    Var $TimeAvg = 0;

    /**
    * Calculate the slowest query in this script
    */
    Var $TimeSlow = 0;

    /**
    * Are we connected or not?
    */
    Var $Connected = False;

    /**
    * Constructor
    *
    * Initialize the MySQL driver and try to connect to the database
    */
    Public Function __Construct()
    {
        Global $_CONFIG;

        /**
        * Set ErrorShow and ErrorLog from the config file
        */
        $this->ErrorShow = $_CONFIG['ErrorShowMySQL'];
        $this->ErrorLog = $_CONFIG['ErrorLogMySQL'];

        If ($_CONFIG['MySQLEnabled'])
        {
            /**
            * Create the connection
            */
            $this->DbLink = parent::__Construct($_CONFIG['MySQLHost'], $_CONFIG['MySQLUser'], $_CONFIG['MySQLPassword'], $_CONFIG['MySQLDatabase']);

            If ($this->connect_error)
            {
                $this->Error = Mysqli_Connect_Error();
                Trigger_Error('Error connecting to database. ' . $this->Error, E_USER_ERROR);
            }
            Else
            {
                $this->Error = NULL;
                $this->Connected = True;
                /**
                * Set the correct character set
                */
                $this->set_charset('UTF8');
            }
        }
        Else
        {
            $this->Connected = False;
        }
    }

    /**
    * Query
    *
    * Execute a MySQL query including all kinds of debug info etc.
    */
    Public Function Query($Query)
    {
        Global $_CONFIG;

        /**
        * Start the time to calculate the time used for this query
        */
        $TimeStart = MicroTime();

        /**
        * Run this query to set the lc_time_names to the current set
        */
        If (Preg_Match('/date_format/i', $Query))
        {
            parent::query("SET lc_time_names = 'en_EN';");
        }

        /**
        * Execute the given query and get the result
        */
        $Result = parent::query($Query);

        /**
        * If the result is not OK
        */
        If ($this->errno > 0)
        {
            /**
            * Store the error
            */
            $this->Error = $this->error;

            /**
            * We can format the error and show it
            */
            If ($this->ErrorShow)
            {
                $Query = Str_Replace('\t', '', $Query);
                Echo '<span style="color:red">' . $this->Error . '</span><br />';
                Echo 'Query in <strong> ' . $_SERVER['SCRIPT_FILENAME'] . '</strong><br />';
                Echo '<pre>' .$Query. '</pre>';
            }

            /**
            * We can also write the error to a logfile
            */
            If ($this->ErrorLog)
            {
                $Error = "<errorentry>\n";
                $Error .= "\t<datetime>" . Date('Y-m-d H:i:s') . "</datetime>\n";
                $Error .= "\t<error>" . $this->Error . "</error>\n";
                $Error .= "\t<query>" . $Query . "</query>\n";
                $Error .= "\t<script>" . $_SERVER['SCRIPT_FILENAME'] . "</script>\n";
                $Error .= "</errorentry>\n\n";

                /**
                * Write the error to the logfile
                */
                //Error_Log($Error, 3, $_CONFIG["ErrorLogFileMySQL"]);
                echo($Error);
                die;
            }

            /**
            * And return false
            */
            Return False;
        }
        /**
        * If query is OK
        */
        Else
        {
            /**
            * Increase the counter
            */
            $this->QueryCount++;

            /**
            * Empty the error var
            */
            $this->Error = NULL;

            /**
            * Calculate the time the query has taken
            */
            $TimeEnd = Microtime();
            $Time = $TimeEnd - $TimeStart;

            /**
            * If this is the slowest query, update TimeSlow
            */
            If ($Time > $this->TimeSlow)
                $this->TimeSlow = $Time;

            /**
            * Calculate the average times for all queries
            */
            $this->TimeAvg = ($this->TimeAvg + $Time) / $this->QueryCount;

            Return $Result;
        }
    }
}

/**
* Query class
*
* Build queries with this query parser, used to protect and parse SQL queries properly
*/
Class Query
{
    /**
    * Store the SQL
    */
    Var $SQL;

    /**
    * The DbLink, has to be an initialized database object
    */
    Var $DbLink;

    /**
    * Results of a query
    */
    Var $QueryResult;

    /**
    * Number of rows returned
    */
    Var $NumRows = 0;

    /**
    * Constructor
    *
    * Get the DB Object from the creator and put it in the current namespace
    */
    Public Function __Construct()
    {
        Global $_DB;
        $this->DbLink = $_DB;
    }

    /**
    * Bind
    *
    * Bind parameters in a query
    */
    Public Function Bind($Type, $Parameter, $Variable)
    {
        /**
        * If there is a : in the given variable, replace it with |:,
        * this is for the Execute function to see if all parameters have been bound,
        * so it will ignore this semicolum
        */
        $Variable = Str_Replace(':', '|:', $Variable);

        /**
        * Check if the given parameter is really a string
        */
        If ($Type == Q_STRING)
        {
            If (!Is_String($Variable))
            {
                $Variable = StrVal($Variable);
                $Variable = Trim($Variable);
            }
            $Variable = $this->Escape($Variable);
            $this->SQL = Preg_Replace('/\:' . $Parameter . '(\r|\n|\s|$|,)/', "'" . $Variable . "'\\1", $this->SQL);
        }
        /**
        * Or if the given parameter is really an integer
        */
        ElseIf ($Type == Q_INTEGER)
        {
            If (!Is_Int($Variable))
            {
                $Variable = IntVal($Variable);
            }
            $this->SQL = Preg_Replace('/\:' . $Parameter . '(\r|\n|\s|$|,|\))/', $Variable . "\\1", $this->SQL);
        }
        /**
        * Or if the given parameter is really a float
        */
        ElseIf ($Type == Q_FLOAT)
        {
            If (!Is_Float($Variable))
            {
                $Variable = FloatVal($Variable);
            }
            $this->SQL = Preg_Replace('/\:' . $Parameter . '(\r|\n|\s|$|,|\))/', $Variable . "\\1", $this->SQL);
        }
        /**
        * Or if the given parameter is a field type, print it as string with the ''
        * WARNING! THIS VALUE IS *NOT* ESCAPED! ONLY USE FOR VERIFIED INPUTS
        */
        ElseIf ($Type == Q_FIELD)
        {
            If (!Is_String($Variable))
            {
                $Variable = StrVal($Variable);
            }
            $this->SQL = Preg_Replace('/\:' . $Parameter . '(\r|\n|\s|$|,|\))/', $Variable . "\\1", $this->SQL);
        }
        /**
        * Unknown type? Don't bind this shit!
        */
        Else
        {
            Trigger_Error('Unknown type', E_USER_ERROR);
        }
    }

    /**
    * Execute
    *
    * Execute the query
    */
    Public Function Execute()
    {
        /**
        * Check if the SQL is not empty
        */
        If (Empty($this->SQL))
        {
            Trigger_Error('Query is empty', E_USER_ERROR);
        }
        /**
        * Check if we havent forgotten to set any binds
        */
        ElseIf (StrPos($this->SQL, ':') !== False &&
                IsSet($this->SQL[StrPos($this->SQL, ':') - 1]) &&
                $this->SQL[StrPos($this->SQL, ':') - 1] !== '|')
        {
            Trigger_Error('Not all parameters have been bound. Query was ' . $this->SQL, E_USER_ERROR);
        }

        /**
        * Execute the query and save the numrows, if we do a select, otherwise store affected rows
        */
        Else
        {
            /**
            * Check if we escaped any semicolumn, and replace it to it's original state
            */
            If (Preg_Match('/\|:/', $this->SQL))
            {
                $this->SQL = Str_Replace('|:', ':', $this->SQL);
            }
            $this->QueryResult = $this->DbLink->Query($this->SQL);
        }
    }

    /**
    * NumRows
    *
    * Get the rows returned
    */
    Public Function NumRows()
    {
        Return $this->QueryResult->num_rows;
    }

    /**
    * FetchObject
    *
    * Fetch resoluts in an assoc. array
    */
    Public Function FetchObject()
    {
        If (Empty($this->QueryResult))
        {
            Trigger_Error('Query is not yet executed', E_USER_ERROR);
        }
        Else
        {
            Return $this->QueryResult->fetch_assoc();
        }
    }

    /**
    * FetchObjectList
    *
    * Go through all the result and put them in an array
    */
    Public Function FetchObjectList()
    {
        If (Empty($this->QueryResult))
        {
            Trigger_Error('Query not executed yet', E_USER_ERROR);
        }
        Else
        {
            $ResultArray = Array();

            While($Result = $this->QueryResult->fetch_assoc())
            {
                $ResultArray[] = $Result;
            }
            Return $ResultArray;
        }
    }

    /**
    * Result
    *
    * Obtain the result of a query through the mysql function mysql_result
    */
    Public Function Result()
    {
        $Result = $this->FetchObject();

        If (Count($Result) > 0)
        {
            ForEach($Result as $R)
            {
                Return $R;
                Exit;
            }
        }
        Else
            Return '';
    }

    /**
    * InsertID
    *
    * Retrieve the last inserted ID from the mysql function mysql_insert_id
    */
    Public Function InsertID()
    {
       return $this->DbLink->insert_id;
    }

    /**
    * AffectedRows
    *
    * Retrieve the affected rows after and insert or update
    */
    Public Function AffectedRows()
    {
       return $this->DbLink->affected_rows;
    }

    /**
    * Escape
    *
    * Escape a string with real_escape_string
    */
    Public Function Escape($String)
    {
        If (Get_Magic_Quotes_Gpc())
        {
            $String = StripSlashes($String);
            //Trigger_Error('Magic Quotes is enabled in PHP.ini, please disable this!', E_USER_NOTICE);
        }
        $String = $this->DbLink->real_escape_string($String);
        Return $String;
    }
}
?>
