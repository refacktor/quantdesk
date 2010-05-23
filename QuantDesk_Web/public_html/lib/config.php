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

require($_SERVER['DOCUMENT_ROOT'] . '/../config/' . trim(`hostname`) . ".php");

/**
* Define the default language to be used
*/
$_CONFIG['SiteTitle']                =    'Stock';

/**
* Which PHP errors will be shown
* E_ALL is only for debugging!
*/
$_CONFIG['ErrorShowPHP']            =    E_ALL;

/**
* Log all PHP errors to a file
*/
$_CONFIG['ErrorLogPHP']                =    True;

/**
* Write all PHP errors to this file
*/
$_CONFIG['ErrorLogFilePHP']            =    'phperror.log';

/**
* Show MySQL parse errors
*/
$_CONFIG['ErrorShowMySQL']            =     False;

/**
* Log all MySQL errors to a file
*/
$_CONFIG['ErrorLogMySQL']            =    True;

/**
* Write all MySQL errors to this file
*/
$_CONFIG['ErrorLogFileMySQL']        =    'dberror.log';

/**
* Send an e-mail when 1 of these errors occur
*/
$_CONFIG['ErrorMail']                =    False;

/**
* The framework can run without MySQL, for fast and quick implentations
*/
$_CONFIG['MySQLEnabled']            =    True;

/**
* Set your timezone, timezones can be found here:
* @link http://www.php.net/timezones
*/
$_CONFIG['Timezone']                =    'US/Central';
?>
