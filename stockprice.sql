-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 07, 2017 at 07:41 PM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stockmarket`
--

-- --------------------------------------------------------

--
-- Table structure for table `stockprice`
--

CREATE TABLE `stockprice` (
  `ID` int(11) NOT NULL,
  `StockName` varchar(30) NOT NULL,
  `StockPrice` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='StockMarket Table';

--
-- Dumping data for table `stockprice`
--

INSERT INTO `stockprice` (`ID`, `StockName`, `StockPrice`) VALUES
(1, 'Version1', 69),
(2, 'SAP', 56.6),
(3, 'Google', 866);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `stockprice`
--
ALTER TABLE `stockprice`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `stockprice`
--
ALTER TABLE `stockprice`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
