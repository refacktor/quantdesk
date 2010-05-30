-- phpMyAdmin SQL Dump
-- version 2.10.0.2
-- http://www.phpmyadmin.net
-- 
-- Host: localhost
-- Generation Time: May 04, 2007 at 09:29 AM
-- Server version: 5.0.37
-- PHP Version: 5.2.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- Database: `db_ftp`
-- 

-- --------------------------------------------------------

-- 
-- Table structure for table `edgar_data`
-- 

CREATE TABLE `edgar_data` (
  `Id` int(4) unsigned NOT NULL auto_increment,
  `IndexFileId` int(4) unsigned NOT NULL,
  `CompanyName` varchar(60) collate utf8_bin NOT NULL,
  `Symbol` varchar(6) collate utf8_bin default NULL,
  `FormType` varchar(10) collate utf8_bin NOT NULL,
  `CIK` int(10) unsigned NOT NULL,
  `DateFiled` datetime NOT NULL,
  `FileName` varchar(43) collate utf8_bin NOT NULL,
  `FTPDate` datetime default NULL,
  `DownloadedDate` datetime default NULL,
  `Status` int(1) NOT NULL default '0',
  `FileSize` int(4) default NULL,
  `FileType` char(1) collate utf8_bin NOT NULL default 'U',
  PRIMARY KEY  (`Id`),
  UNIQUE KEY `FileName` (`FileName`),
  KEY `Symbol` (`Symbol`),
  KEY `FormType` (`FormType`),
  KEY `CompanyName` (`CompanyName`),
  KEY `DateFiled` (`DateFiled`),
  KEY `WorkQueue` (`DateFiled`,`Status`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;


-- 
-- Table structure for table `edgar_index`
-- 

CREATE TABLE `edgar_index` (
  `Id` int(4) unsigned NOT NULL auto_increment,
  `Path` varchar(26) collate utf8_bin default NULL,
  `Name` varchar(11) collate utf8_bin default NULL,
  `DateFiled` datetime default NULL,
  `FTPDate` datetime default NULL,
  `DownloadedDate` datetime default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `edgar_index`
-- 
-- 
-- Dumping data for table `edgar_data`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `dataitem`
-- 

CREATE TABLE `dataitem` (
  `Id` int(4) unsigned NOT NULL auto_increment,
  `DataFileId` int(4) unsigned NOT NULL,
  `PeriodEnd` date NOT NULL,
  `PeriodMonths` int(1) unsigned NOT NULL,
  `ItemType` char(1) collate utf8_bin NOT NULL COMMENT 'E for Earnings, D for Dividend, etc',
  `amount` decimal(14,2) NOT NULL,
  `isPerShare` tinyint(1) NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `DataFileId` (`DataFileId`,`PeriodEnd`,`ItemType`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `dataitem`
-- 


-- --------------------------------------------------------


-- --------------------------------------------------------

-- 
-- Table structure for table `sharedata`
-- 

CREATE TABLE `sharedata` (
  `Id` int(4) unsigned NOT NULL auto_increment,
  `DataFileId` int(4) unsigned NOT NULL,
  `EventDate` date NOT NULL,
  `SplitRatio` int(4) unsigned default NULL,
  `Denominator` int(4) unsigned default NULL,
  `NewSharesOut` int(5) unsigned default NULL,
  PRIMARY KEY  (`Id`),
  KEY `DataFileId` (`DataFileId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `sharedata`
-- 

