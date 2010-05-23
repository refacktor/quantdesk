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
 *  Foobar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */?>
 <?php
/**
* Sorry we only work on PHP 5.0 >
*/
If (Version_Compare(PHP_VERSION, '5.0.0', '<'))
    Die('Sorry but the Scylla framework only works with a PHP version of 5.0 or higher!');

/**
* Start error reporting
*/
Error_Reporting(E_ALL);

/**
* ROOT path of scripts
* Used to safely include other files, used for storing when uploading etc.
*/
Define('__DIR__', Dirname(__FILE__));
$Root = SubStr(__DIR__, 0, -3);
Define('SCYLLA_ROOT', $Root);

/**
* Get the relative link of the site
*/
$RelativeURL = Str_Replace('\\', '/', __DIR__);
$RelativeURL = Str_Replace($_SERVER['DOCUMENT_ROOT'], '', $RelativeURL);
$RelativeURL = SubStr($RelativeURL, 0, -4);
If ($RelativeURL <> '')
    $RelativeURL.= '/';
Define('SCYLLA_RELATIVE', '/' . $RelativeURL);

/**
* Get the full link to the website, relative from the root of the site
*/
If (IsSet($_SERVER['HTTP_HOST']))
    Define('SCYLLA_URL', 'http://' . $_SERVER['HTTP_HOST'] . SCYLLA_RELATIVE);
Else
    Define('SCYLLA_URL', '');

/**
* Get the current script name
*/
Preg_Match('/(.*)\/(.*?)\./', $_SERVER['PHP_SELF'], $ThisFile);
$ThisFile = IsSet($ThisFile[Count($ThisFile) -1]) ? $ThisFile[Count($ThisFile) -1] : Str_Replace('.php', '', $_SERVER['PHP_SELF']);
Define('SCYLLA_FILENAME', $ThisFile);

/**
* Get the subfolder
*/
$ScyllaFolder = Str_Replace('\\', '/', $_SERVER['PHP_SELF']);
$ScyllaFolder = Str_Replace(SCYLLA_RELATIVE, '', $ScyllaFolder);
$ScyllaFolder = Str_Replace(SCYLLA_FILENAME . '.php', '', $ScyllaFolder);
If ($ScyllaFolder <> '/')
    $ScyllaFolder.= '/';

Define('SCYLLA_FOLDER', $ScyllaFolder);

/**
* Read the configuration file and parse it's values in $this->Config
*/
Require_Once(SCYLLA_ROOT . 'lib' . DIRECTORY_SEPARATOR . 'config.php');

/**
* Start error reporting
*/
Error_Reporting($_CONFIG['ErrorShowPHP']);

/**
* Set the timezone if timezone in php.ini is not defined
*/
If (Ini_Get('date.timezone') == '' || Ini_Get('date.timezone') <> $_CONFIG['Timezone'])
{
    Ini_Set('date.timezone', $_CONFIG['Timezone']);
    Date_Default_Timezone_Set($_CONFIG['Timezone']);
}
Else
{
    Date_Default_Timezone_Set(Ini_Get('date.timezone'));
}

/**
* Load some common used functions
*/
Require_Once(SCYLLA_ROOT . 'lib' . DIRECTORY_SEPARATOR . 'functions.php');

/**
* Set locals to make sure everything is in the correct formatting
*/
SetLocale(LC_TIME, 'en_EN.utf8');
SetLocale(LC_TIME, 'eng_eng');
SetLocale(LC_NUMERIC, 'en_EN.utf8');

/**
* Set the character set in PHP
*/
Header('Content-type: text/html; charset=UTF-8');

/**
* Load MySQL
*/
Require_Once(SCYLLA_ROOT . 'lib' . DIRECTORY_SEPARATOR . 'mysql.php');
$_DB = New MySQLDriver();


/**
* The following are constants often used by the framework
* $this->Nav['task'] = Task, used to switch pages
* $this->Nav['id'] = ID number for basic pages
* $this->Nav['page'] = Page for pagination (CAN ALSO BE THE STRING 'last')
*/
$_NAV['task'] = IsSet($_GET['task']) ? StrVal($_GET['task'])   : '';
$_NAV['id'] =   IsSet($_GET['id'])   ? IntVal($_GET['id'])     : 0;

If (IsSet($_GET['page']) && $_GET['page'] === 'last')
     $_NAV['page'] = 'last';
ElseIf (IsSet($_GET['page']) && $_GET['page'] === 'all')
     $_NAV['page'] = 'all';
ElseIf (IsSet($_GET['page']))
    $_NAV['page'] = IntVal($_GET['page']);
Else
    $_NAV['page'] = 0;

/**
* Now we will call the appropiate function of the pages
* according to the _NAV['t'] variable.
*
* If no variable is set then we will use the script filename
* to call the default function
*
*/
If (Function_Exists('Show' . $_NAV['task']))
{
    Call_User_Func('Show' . $_NAV['task']);
}
ElseIf (Function_Exists('Show' . SCYLLA_FILENAME))
{
    Call_User_Func('Show' . SCYLLA_FILENAME);
}
Else
{
    Trigger_Error('No function was defined for "<strong>Show' . SCYLLA_FILENAME . '" - "' . $_NAV['task'] . '</strong>"', E_USER_ERROR);
}
?>
