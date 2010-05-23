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
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset={$Charset}">
    <meta http-equiv="Content-Language" content="en">
    <base href="<?php echo SCYLLA_URL;?>"><!--[if lte IE 6]></base><![endif]-->
    <title><?php echo $_CONFIG['SiteTitle'];?></title>
    <link rel="shortcut icon" type="image/png" href="favicon.png">
    <link rel="stylesheet" type="text/css" href="css/global.css">
</head>
<body>
<div class="Wrapper">
    <div class="Header">
        <form action="search/">
            <input type="text" name="search" id="Search" />
            <input type="submit" value="Get Quotes" />
        </form>
        <div class="Date">
            <?php echo StrFTime2('%a, %e %h, %Y, %l:%M%p');?> ET
        </div>
    </div>