-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- 생성 시간: 19-12-18 22:35
-- 서버 버전: 5.5.62-MariaDB
-- PHP 버전: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `bobchin`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `images`
--

CREATE TABLE `images` (
  `id` varchar(30) NOT NULL,
  `path` text NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `meetings`
--

CREATE TABLE `meetings` (
  `meetID` int(30) NOT NULL,
  `headuser` varchar(30) NOT NULL,
  `users` text NOT NULL,
  `meetname` text NOT NULL,
  `meetmsg` text NOT NULL,
  `agemax` varchar(30) NOT NULL,
  `agemin` varchar(30) NOT NULL,
  `location` varchar(30) NOT NULL,
  `region` text NOT NULL,
  `starttime` datetime NOT NULL,
  `duration` varchar(30) NOT NULL,
  `maxpeople` varchar(30) NOT NULL,
  `photo` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `notice`
--

CREATE TABLE `notice` (
  `notinum` int(11) NOT NULL,
  `url` text NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `pushmsg`
--

CREATE TABLE `pushmsg` (
  `id` int(11) NOT NULL,
  `title` text NOT NULL,
  `body` text NOT NULL,
  `touser` text NOT NULL,
  `meetid` int(11) NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `user`
--

CREATE TABLE `user` (
  `userid` varchar(30) NOT NULL COMMENT 'OAuth ID',
  `accesstoken` varchar(200) NOT NULL,
  `refreshtoken` varchar(200) NOT NULL,
  `name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `photo` text NOT NULL,
  `auth_level` int(11) NOT NULL DEFAULT '1',
  `devicetoken` text NOT NULL,
  `last_accesstoken` varchar(200) NOT NULL,
  `last_renew` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='name';

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`id`);

--
-- 테이블의 인덱스 `meetings`
--
ALTER TABLE `meetings`
  ADD PRIMARY KEY (`meetID`);

--
-- 테이블의 인덱스 `notice`
--
ALTER TABLE `notice`
  ADD PRIMARY KEY (`notinum`);

--
-- 테이블의 인덱스 `pushmsg`
--
ALTER TABLE `pushmsg`
  ADD PRIMARY KEY (`id`);

--
-- 테이블의 인덱스 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`email`);

--
-- 덤프된 테이블의 AUTO_INCREMENT
--

--
-- 테이블의 AUTO_INCREMENT `meetings`
--
ALTER TABLE `meetings`
  MODIFY `meetID` int(30) NOT NULL AUTO_INCREMENT;

--
-- 테이블의 AUTO_INCREMENT `notice`
--
ALTER TABLE `notice`
  MODIFY `notinum` int(11) NOT NULL AUTO_INCREMENT;

--
-- 테이블의 AUTO_INCREMENT `pushmsg`
--
ALTER TABLE `pushmsg`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
