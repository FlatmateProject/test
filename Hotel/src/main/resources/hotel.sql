-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Czas wygenerowania: 21 Lip 2012, 18:33
-- Wersja serwera: 5.5.8
-- Wersja PHP: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `hotel`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `firmy`
--

DROP TABLE IF EXISTS `firmy`;
CREATE TABLE IF NOT EXISTS `firmy` (
  `IDF_KRS` bigint(10) NOT NULL AUTO_INCREMENT,
  `NAZWA` varchar(20) DEFAULT NULL,
  `WOJEWODZTWO` varchar(20) DEFAULT NULL,
  `MIASTO` varchar(20) DEFAULT NULL,
  `ULICA` varchar(20) DEFAULT NULL,
  `NR_LOKALU` bigint(4) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `UWAGI` varchar(100) DEFAULT NULL,
  `REGON` bigint(9) DEFAULT NULL,
  `NIP` bigint(11) DEFAULT NULL,
  `TELEFON` bigint(9) DEFAULT NULL,
  `DATA_ZALOZENIA` date DEFAULT NULL,
  PRIMARY KEY (`IDF_KRS`),
  UNIQUE KEY `IDF_KRS` (`IDF_KRS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `firmy`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `grafik`
--

DROP TABLE IF EXISTS `grafik`;
CREATE TABLE IF NOT EXISTS `grafik` (
  `ID_GRAF` bigint(2) NOT NULL AUTO_INCREMENT,
  `IDK_PESEL` bigint(11) DEFAULT NULL,
  `ZMIANA` bigint(1) DEFAULT NULL,
  `DATA` date DEFAULT NULL,
  `NADGODZINY` bigint(4) DEFAULT NULL,
  `ID_POKOJU` bigint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_GRAF`),
  UNIQUE KEY `ID_GRAF` (`ID_GRAF`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `grafik`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `grafik_sal`
--

DROP TABLE IF EXISTS `grafik_sal`;
CREATE TABLE IF NOT EXISTS `grafik_sal` (
  `ID_GRAF_S` bigint(2) NOT NULL AUTO_INCREMENT,
  `IDF_KRS` bigint(10) DEFAULT NULL,
  `IDK_PESEL` bigint(11) DEFAULT NULL,
  `DATA` date DEFAULT NULL,
  `ID_POKOJU` bigint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_GRAF_S`),
  UNIQUE KEY `ID_GRAF_S` (`ID_GRAF_S`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `grafik_sal`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `kantor`
--

DROP TABLE IF EXISTS `kantor`;
CREATE TABLE IF NOT EXISTS `kantor` (
  `ID_TRANS` bigint(10) NOT NULL AUTO_INCREMENT,
  `IDK_PESEL` bigint(11) DEFAULT NULL,
  `TYP` varchar(1) DEFAULT NULL,
  `W_KU` bigint(20) DEFAULT NULL,
  `W_SP` bigint(20) DEFAULT NULL,
  `WARTOSC` bigint(20) DEFAULT NULL,
  `DATA` date DEFAULT NULL,
  PRIMARY KEY (`ID_TRANS`),
  UNIQUE KEY `ID_TRANS` (`ID_TRANS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `kantor`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `klasy`
--

DROP TABLE IF EXISTS `klasy`;
CREATE TABLE IF NOT EXISTS `klasy` (
  `ID_KLASY` bigint(2) NOT NULL AUTO_INCREMENT,
  `IL_OSOB` bigint(1) DEFAULT NULL,
  `CENA` bigint(4) DEFAULT NULL,
  `OPIS` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID_KLASY`),
  UNIQUE KEY `ID_KLASY` (`ID_KLASY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `klasy`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `klienci`
--

DROP TABLE IF EXISTS `klienci`;
CREATE TABLE IF NOT EXISTS `klienci` (
  `IDK_PESEL` bigint(11) NOT NULL AUTO_INCREMENT,
  `IMIE` varchar(20) DEFAULT NULL,
  `NAZWISKO` varchar(20) DEFAULT NULL,
  `WOJEWODZTWO` varchar(20) DEFAULT NULL,
  `MIASTO` varchar(20) DEFAULT NULL,
  `ULICA` varchar(20) DEFAULT NULL,
  `BLOK` varchar(4) DEFAULT NULL,
  `NR_LOKALU` bigint(4) DEFAULT NULL,
  `STATUS` varchar(5) DEFAULT NULL,
  `UWAGI` varchar(20) DEFAULT NULL,
  `TELEFON` bigint(9) DEFAULT NULL,
  `NIP` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`IDK_PESEL`),
  UNIQUE KEY `IDK_PESEL` (`IDK_PESEL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `klienci`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `pokoje`
--

DROP TABLE IF EXISTS `pokoje`;
CREATE TABLE IF NOT EXISTS `pokoje` (
  `ID_POKOJU` bigint(4) NOT NULL AUTO_INCREMENT,
  `ID_KLASY` bigint(2) DEFAULT NULL,
  `IDK_PESEL` bigint(11) DEFAULT NULL,
  `IDF_KRS` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`ID_POKOJU`),
  UNIQUE KEY `ID_POKOJU` (`ID_POKOJU`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `pokoje`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `pracownicy`
--

DROP TABLE IF EXISTS `pracownicy`;
CREATE TABLE IF NOT EXISTS `pracownicy` (
  `IDP_PESEL` bigint(11) NOT NULL AUTO_INCREMENT,
  `IMIE` varchar(20) DEFAULT NULL,
  `NAZWISKO` varchar(20) DEFAULT NULL,
  `HASLO` varchar(40) DEFAULT NULL,
  `WOJEWODZTWO` varchar(20) DEFAULT NULL,
  `MIASTO` varchar(20) DEFAULT NULL,
  `ULICA` varchar(20) DEFAULT NULL,
  `NR_LOKALU` bigint(4) DEFAULT NULL,
  `ID_STANOWISKA` bigint(2) DEFAULT NULL,
  PRIMARY KEY (`IDP_PESEL`),
  UNIQUE KEY `IDP_PESEL` (`IDP_PESEL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `pracownicy`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `rachunki`
--

DROP TABLE IF EXISTS `rachunki`;
CREATE TABLE IF NOT EXISTS `rachunki` (
  `ID_RACHUNKU` bigint(10) NOT NULL AUTO_INCREMENT,
  `ID_REZ` bigint(10) DEFAULT NULL,
  `DATA` date DEFAULT NULL,
  `IDR` varchar(20) DEFAULT NULL,
  `PODATEK` bigint(20) DEFAULT NULL,
  `WARTOSC` bigint(20) DEFAULT NULL,
  `NAZWA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID_RACHUNKU`),
  UNIQUE KEY `ID_RACHUNKU` (`ID_RACHUNKU`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `rachunki`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `rekreacja`
--

DROP TABLE IF EXISTS `rekreacja`;
CREATE TABLE IF NOT EXISTS `rekreacja` (
  `IDK_PESEL` bigint(11) NOT NULL AUTO_INCREMENT,
  `ID_USLUGI` bigint(2) DEFAULT NULL,
  `ID_REZ` bigint(10) DEFAULT NULL,
  `CZAS` bigint(2) DEFAULT NULL,
  PRIMARY KEY (`IDK_PESEL`),
  UNIQUE KEY `IDK_PESEL` (`IDK_PESEL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `rekreacja`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `rezerwacje`
--

DROP TABLE IF EXISTS `rezerwacje`;
CREATE TABLE IF NOT EXISTS `rezerwacje` (
  `ID_REZ` bigint(10) NOT NULL AUTO_INCREMENT,
  `IDK_PESEL` bigint(11) DEFAULT NULL,
  `IDF_KRS` bigint(10) DEFAULT NULL,
  `ID_USLUGI` bigint(2) DEFAULT NULL,
  `ID_POKOJU` bigint(4) DEFAULT NULL,
  `TYP` int(1) DEFAULT NULL,
  `DATA_Z` date NOT NULL,
  `DATA_W` date,
  PRIMARY KEY (`ID_REZ`),
  UNIQUE KEY `ID_REZ` (`ID_REZ`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `rezerwacje`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `stanowiska`
--

DROP TABLE IF EXISTS `stanowiska`;
CREATE TABLE IF NOT EXISTS `stanowiska` (
  `ID_STANOWISKA` bigint(4) NOT NULL AUTO_INCREMENT,
  `NAZWA` varchar(20) DEFAULT NULL,
  `PODSTAWA` bigint(6) DEFAULT NULL,
  `PREMIA` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`ID_STANOWISKA`),
  UNIQUE KEY `ID_STANOWISKA` (`ID_STANOWISKA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `stanowiska`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `uslugi`
--

DROP TABLE IF EXISTS `uslugi`;
CREATE TABLE IF NOT EXISTS `uslugi` (
  `ID_USLUGI` bigint(2) NOT NULL AUTO_INCREMENT,
  `NAZWA` varchar(20) DEFAULT NULL,
  `CENA` bigint(4) DEFAULT NULL,
  `TYP` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID_USLUGI`),
  UNIQUE KEY `ID_USLUGI` (`ID_USLUGI`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `uslugi`
--


-- --------------------------------------------------------

--
-- Struktura tabeli dla  `waluty`
--

DROP TABLE IF EXISTS `waluty`;
CREATE TABLE IF NOT EXISTS `waluty` (
  `ID_WALUTY` bigint(2) NOT NULL AUTO_INCREMENT,
  `NAZWA` varchar(5) DEFAULT NULL,
  `CENA_SP` bigint(20) DEFAULT NULL,
  `CENA_KU` bigint(20) DEFAULT NULL,
  `ILOSC` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID_WALUTY`),
  UNIQUE KEY `ID_WALUTY` (`ID_WALUTY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `waluty`
--

