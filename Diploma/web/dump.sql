CREATE DATABASE  IF NOT EXISTS `dnevnik` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `dnevnik`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: dnevnik
-- ------------------------------------------------------
-- Server version	5.1.53-community-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `podrobni_pregled`
--

DROP TABLE IF EXISTS `podrobni_pregled`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `podrobni_pregled` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UCNA_PRIPRAVA_ID` int(11) NOT NULL,
  `NAMEN` varchar(45) NOT NULL,
  `DEJAVNOST_UCIT` longtext NOT NULL,
  `DEJAVNOST_UCEN` longtext NOT NULL,
  `TABELSKA_SLIKA` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `UCNA_PRIPRAVA_PODROBNI_FK` (`UCNA_PRIPRAVA_ID`),
  CONSTRAINT `UCNA_PRIPRAVA_PODROBNI_FK` FOREIGN KEY (`UCNA_PRIPRAVA_ID`) REFERENCES `ucna_priprava` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `podrobni_pregled`
--

LOCK TABLES `podrobni_pregled` WRITE;
/*!40000 ALTER TABLE `podrobni_pregled` DISABLE KEYS */;
/*!40000 ALTER TABLE `podrobni_pregled` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cilji_ure`
--

DROP TABLE IF EXISTS `cilji_ure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cilji_ure` (
  `PODATKI_URE_ID` int(11) NOT NULL,
  `CILJ` varchar(150) NOT NULL,
  KEY `PODATKI_URE_CILJI_FK` (`PODATKI_URE_ID`),
  CONSTRAINT `PODATKI_URE_CILJI_FK` FOREIGN KEY (`PODATKI_URE_ID`) REFERENCES `podatki_ure` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cilji_ure`
--

LOCK TABLES `cilji_ure` WRITE;
/*!40000 ALTER TABLE `cilji_ure` DISABLE KEYS */;
INSERT INTO `cilji_ure` VALUES (17,'Cilj1'),(17,'Cilj2'),(17,'Cilj3'),(21,'sadf'),(21,'asdf'),(21,'asdf'),(21,'asdf'),(18,'asdf'),(18,'asdf'),(18,'njnjbb'),(16,'asdf'),(16,'1'),(19,'asdf'),(20,'SDFDASF'),(2,'1');
/*!40000 ALTER TABLE `cilji_ure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospitacija_vidik_a`
--

DROP TABLE IF EXISTS `hospitacija_vidik_a`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hospitacija_vidik_a` (
  `id_hospitacije` int(11) DEFAULT NULL,
  `opis_ucne_ure` blob,
  `pristop_ucne_ure` blob,
  `vprasanje_uciteljici` blob,
  `mnenje_o_organizaciji_hospitacije` blob
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospitacija_vidik_a`
--

LOCK TABLES `hospitacija_vidik_a` WRITE;
/*!40000 ALTER TABLE `hospitacija_vidik_a` DISABLE KEYS */;
INSERT INTO `hospitacija_vidik_a` VALUES (6,'<p>\n	asdf</p>\n','<p>\n	asdf</p>\n','<p>\n	asdf</p>\n','<p>\n	asdf</p>\n');
/*!40000 ALTER TABLE `hospitacija_vidik_a` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dodatne_dejavnosti`
--

DROP TABLE IF EXISTS `dodatne_dejavnosti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dodatne_dejavnosti` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UCNA_PRIPRAVA_ID` int(11) NOT NULL,
  `ZA` varchar(45) DEFAULT NULL,
  `DEJAVNOST` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `UCNA_PRIPRAVA_DODATNE_DEJ_FK` (`UCNA_PRIPRAVA_ID`),
  CONSTRAINT `UCNA_PRIPRAVA_DODATNE_DEJ_FK` FOREIGN KEY (`UCNA_PRIPRAVA_ID`) REFERENCES `ucna_priprava` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dodatne_dejavnosti`
--

LOCK TABLES `dodatne_dejavnosti` WRITE;
/*!40000 ALTER TABLE `dodatne_dejavnosti` DISABLE KEYS */;
INSERT INTO `dodatne_dejavnosti` VALUES (3,2,'SIBKI_UCENCI','asdf'),(4,2,'ZMOZNEJSI_UCENCI','asdf');
/*!40000 ALTER TABLE `dodatne_dejavnosti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `naloge_elementi`
--

DROP TABLE IF EXISTS `naloge_elementi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `naloge_elementi` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_KONTROLE` varchar(45) NOT NULL,
  `TIP` int(11) NOT NULL,
  `VREDNOST` blob NOT NULL,
  `NALOGE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_NALOGE_ID` (`NALOGE_ID`),
  CONSTRAINT `FK_NALOGE_ID` FOREIGN KEY (`NALOGE_ID`) REFERENCES `naloge` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `naloge_elementi`
--

LOCK TABLES `naloge_elementi` WRITE;
/*!40000 ALTER TABLE `naloge_elementi` DISABLE KEYS */;
INSERT INTO `naloge_elementi` VALUES (1,'0_fgh',0,'',15),(2,'4_aaaa',4,'<\0p\0>\0\n\0	\0T\0e\0s\0t\0n\0o\0 \0b\0e\0s\0e\0d\0i\0l\0o\0<\0/\0p\0>\0\n\0',21),(3,'1_bbbb',1,'',21),(4,'0_asdf',0,'',22),(5,'0_asdfasdf',0,'',23),(6,'2_asdfeefee',2,'',24),(7,'1_asdf_vve33',1,'',25),(8,'1_asdfa',1,'',26),(9,'0_asdfaasdf',0,'',26);
/*!40000 ALTER TABLE `naloge_elementi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smer_stud`
--

DROP TABLE IF EXISTS `smer_stud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smer_stud` (
  `ID` int(11) NOT NULL,
  `SMER` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smer_stud`
--

LOCK TABLES `smer_stud` WRITE;
/*!40000 ALTER TABLE `smer_stud` DISABLE KEYS */;
INSERT INTO `smer_stud` VALUES (1,'MA-RA');
/*!40000 ALTER TABLE `smer_stud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentje`
--

DROP TABLE IF EXISTS `studentje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studentje` (
  `ID` varchar(25) NOT NULL,
  `IME` varchar(25) NOT NULL,
  `PRIIMEK` varchar(25) NOT NULL,
  `SMER_STUD_ID` int(11) NOT NULL,
  `E_POSTA` varchar(75) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `SMER_STUD_STUDENTJE_FK` (`SMER_STUD_ID`),
  CONSTRAINT `SMER_STUD_STUDENTJE_FK` FOREIGN KEY (`SMER_STUD_ID`) REFERENCES `smer_stud` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentje`
--

LOCK TABLES `studentje` WRITE;
/*!40000 ALTER TABLE `studentje` DISABLE KEYS */;
INSERT INTO `studentje` VALUES ('01007569','JURE','SIMON',1,'JURE.SIMON@GMAIL.COM');
/*!40000 ALTER TABLE `studentje` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `dnevnik`.`TRG_STUDENTJE_BI` BEFORE INSERT
    ON dnevnik.studentje FOR EACH ROW
BEGIN
    INSERT INTO UPORABNIKI (ID, UIME, GESLO, UPOR_SKUPINA_ID)
    VALUES(NEW.ID, CONCAT(NEW.IME, NEW.PRIIMEK), CONCAT(NEW.IME, NEW.PRIIMEK), 1);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `upor_skupina`
--

DROP TABLE IF EXISTS `upor_skupina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upor_skupina` (
  `ID` int(11) NOT NULL,
  `SKUPINA` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upor_skupina`
--

LOCK TABLES `upor_skupina` WRITE;
/*!40000 ALTER TABLE `upor_skupina` DISABLE KEYS */;
INSERT INTO `upor_skupina` VALUES (1,'STUDENTJE'),(2,'PROFESORJI');
/*!40000 ALTER TABLE `upor_skupina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uporabniki`
--

DROP TABLE IF EXISTS `uporabniki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uporabniki` (
  `ID` varchar(25) NOT NULL,
  `UIME` varchar(15) NOT NULL,
  `GESLO` varchar(25) NOT NULL,
  `UPOR_SKUPINA_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`,`UIME`),
  KEY `STUD_UPOR_FK` (`ID`),
  KEY `PROF_UPOR_FK` (`ID`),
  KEY `UPOR_SKUPINA_UPOR_FK` (`UPOR_SKUPINA_ID`),
  CONSTRAINT `UPOR_SKUPINA_UPOR_FK` FOREIGN KEY (`UPOR_SKUPINA_ID`) REFERENCES `upor_skupina` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uporabniki`
--

LOCK TABLES `uporabniki` WRITE;
/*!40000 ALTER TABLE `uporabniki` DISABLE KEYS */;
INSERT INTO `uporabniki` VALUES ('01007569','JURESIMON','JURESIMON',1),('ZMAG','ZLATANM','ZLATANM',2);
/*!40000 ALTER TABLE `uporabniki` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sklop`
--

DROP TABLE IF EXISTS `sklop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sklop` (
  `ID` int(11) NOT NULL,
  `SKLOP` varchar(150) NOT NULL,
  `TEMA_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `TEMA_ID_FK` (`TEMA_ID`),
  CONSTRAINT `TEMA_ID_FK` FOREIGN KEY (`TEMA_ID`) REFERENCES `tema` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sklop`
--

LOCK TABLES `sklop` WRITE;
/*!40000 ALTER TABLE `sklop` DISABLE KEYS */;
INSERT INTO `sklop` VALUES (1,'SKLOP 1',1),(2,'SKLOP 2',2);
/*!40000 ALTER TABLE `sklop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `v_datoteke`
--

DROP TABLE IF EXISTS `v_datoteke`;
/*!50001 DROP VIEW IF EXISTS `v_datoteke`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `v_datoteke` (
  `Vrsta` varchar(11),
  `UPORABNIK_ID` varchar(25),
  `Datum` varchar(10),
  `Opis` varbinary(284),
  `Uredi` int(11)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `enota`
--

DROP TABLE IF EXISTS `enota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enota` (
  `ID` int(11) NOT NULL,
  `ENOTA` varchar(150) NOT NULL,
  `SKLOP_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `SKLOP_ID_FK` (`SKLOP_ID`),
  CONSTRAINT `SKLOP_ID_FK` FOREIGN KEY (`SKLOP_ID`) REFERENCES `sklop` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enota`
--

LOCK TABLES `enota` WRITE;
/*!40000 ALTER TABLE `enota` DISABLE KEYS */;
INSERT INTO `enota` VALUES (1,'ENOTA 1',1),(2,'ENOTA 2',2);
/*!40000 ALTER TABLE `enota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ucitelj`
--

DROP TABLE IF EXISTS `ucitelj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ucitelj` (
  `ID` int(11) NOT NULL,
  `IME` varchar(25) NOT NULL,
  `PRIIMEK` varchar(25) NOT NULL,
  `NAZIV` varchar(50) DEFAULT NULL,
  `SOLA_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `SOLA_UCITELJ_FK` (`SOLA_ID`),
  CONSTRAINT `SOLA_UCITELJ_FK` FOREIGN KEY (`SOLA_ID`) REFERENCES `sola` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ucitelj`
--

LOCK TABLES `ucitelj` WRITE;
/*!40000 ALTER TABLE `ucitelj` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucitelj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ostale_aktivnosti`
--

DROP TABLE IF EXISTS `ostale_aktivnosti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ostale_aktivnosti` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRAKSA_ID` int(11) NOT NULL,
  `AKTIVNOST` varchar(150) NOT NULL,
  `OPIS` longtext NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `PRAKSA_AKTIVNOSTI_FK` (`PRAKSA_ID`),
  CONSTRAINT `PRAKSA_AKTIVNOSTI_FK` FOREIGN KEY (`PRAKSA_ID`) REFERENCES `praksa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ostale_aktivnosti`
--

LOCK TABLES `ostale_aktivnosti` WRITE;
/*!40000 ALTER TABLE `ostale_aktivnosti` DISABLE KEYS */;
/*!40000 ALTER TABLE `ostale_aktivnosti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesorji`
--

DROP TABLE IF EXISTS `profesorji`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profesorji` (
  `ID` varchar(25) NOT NULL,
  `IME` varchar(25) NOT NULL,
  `PRIIMEK` varchar(25) NOT NULL,
  `E_POSTA` varchar(75) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesorji`
--

LOCK TABLES `profesorji` WRITE;
/*!40000 ALTER TABLE `profesorji` DISABLE KEYS */;
INSERT INTO `profesorji` VALUES ('ZMAG','ZLATAN','MAGAJNA','ZLATAN.MAGAJNA');
/*!40000 ALTER TABLE `profesorji` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospitacija_vidik_b`
--

DROP TABLE IF EXISTS `hospitacija_vidik_b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hospitacija_vidik_b` (
  `id_hospitacije` int(11) DEFAULT NULL,
  `opazovani_vidik` blob,
  `opazovani_vidik_opazanja` blob,
  `ravnanje_z_opazovanim_vidikom` blob,
  `razlaganje_opazanja` blob,
  `zakaj_je_vidkik_pomemben` blob,
  `utemeljitev_premislekov` blob
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospitacija_vidik_b`
--

LOCK TABLES `hospitacija_vidik_b` WRITE;
/*!40000 ALTER TABLE `hospitacija_vidik_b` DISABLE KEYS */;
INSERT INTO `hospitacija_vidik_b` VALUES (6,'<p>\n	asdf</p>\n','<p>\n	asdf</p>\n','<p>\n	asdf</p>\n','<p>\n	asdf</p>\n','<p>\n	asdf</p>\n','<p>\n	asdf</p>\n');
/*!40000 ALTER TABLE `hospitacija_vidik_b` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pregled_ure`
--

DROP TABLE IF EXISTS `pregled_ure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pregled_ure` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UCNA_PRIPRAVA_ID` int(11) NOT NULL,
  `CILJ` varchar(45) NOT NULL,
  `STRATEGIJA` varchar(200) NOT NULL,
  `NACIN` varchar(200) NOT NULL,
  `METODE` varchar(200) NOT NULL,
  `CAS` int(11) NOT NULL,
  `PRIPOMOCKI` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `UCNA_PRIPRAVA_PREGLD_FK` (`UCNA_PRIPRAVA_ID`),
  CONSTRAINT `UCNA_PRIPRAVA_PREGLD_FK` FOREIGN KEY (`UCNA_PRIPRAVA_ID`) REFERENCES `ucna_priprava` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pregled_ure`
--

LOCK TABLES `pregled_ure` WRITE;
/*!40000 ALTER TABLE `pregled_ure` DISABLE KEYS */;
INSERT INTO `pregled_ure` VALUES (2,2,'<p>asdf</p>','<p>asdf</p>','<p>adf</p>','asdf',1,'sadf'),(3,2,'<p>asdf</p>','<p>asdf</p>','<p>asdf</p>','sdf',2,'asdf');
/*!40000 ALTER TABLE `pregled_ure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sola_slike`
--

DROP TABLE IF EXISTS `sola_slike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sola_slike` (
  `ID` int(11) NOT NULL,
  `SOLA_ID` int(11) NOT NULL,
  `IME_SLIKE` varchar(150) NOT NULL,
  PRIMARY KEY (`ID`,`IME_SLIKE`),
  KEY `SOLA_SLIKE_FK` (`SOLA_ID`),
  CONSTRAINT `SOLA_SLIKE_FK` FOREIGN KEY (`SOLA_ID`) REFERENCES `sola` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sola_slike`
--

LOCK TABLES `sola_slike` WRITE;
/*!40000 ALTER TABLE `sola_slike` DISABLE KEYS */;
/*!40000 ALTER TABLE `sola_slike` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ucna_priprava`
--

DROP TABLE IF EXISTS `ucna_priprava`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ucna_priprava` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UPORABNIK_ID` varchar(25) NOT NULL,
  `PODATKI_URE_ID` int(11) NOT NULL,
  `PRAKSA_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `PRAKSA_PRIPRAVA_FK` (`PRAKSA_ID`),
  KEY `UPORABNIK_PRIPRAVA_FK` (`UPORABNIK_ID`),
  KEY `PODATKI_URE_PRIPRAVA_FK` (`PODATKI_URE_ID`),
  CONSTRAINT `PODATKI_URE_PRIPRAVA_FK` FOREIGN KEY (`PODATKI_URE_ID`) REFERENCES `podatki_ure` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `PRAKSA_PRIPRAVA_FK` FOREIGN KEY (`PRAKSA_ID`) REFERENCES `praksa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `UPORABNIK_PRIPRAVA_FK` FOREIGN KEY (`UPORABNIK_ID`) REFERENCES `uporabniki` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ucna_priprava`
--

LOCK TABLES `ucna_priprava` WRITE;
/*!40000 ALTER TABLE `ucna_priprava` DISABLE KEYS */;
INSERT INTO `ucna_priprava` VALUES (1,'01007569',2,NULL),(2,'01007569',16,NULL),(3,'01007569',17,NULL),(4,'01007569',18,NULL),(5,'01007569',19,NULL),(6,'01007569',20,NULL),(7,'01007569',21,NULL);
/*!40000 ALTER TABLE `ucna_priprava` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospitacije`
--

DROP TABLE IF EXISTS `hospitacije`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hospitacije` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UPORABNIK_ID` varchar(25) NOT NULL,
  `IZVAJALEC` varchar(100) NOT NULL,
  `DATUM` datetime NOT NULL,
  `URA` int(11) NOT NULL,
  `RAZRED` int(11) NOT NULL,
  `TEMA_ID` int(11) NOT NULL,
  `SKLOP_ID` int(11) NOT NULL,
  `ENOTA_ID` int(11) NOT NULL,
  `VIDIK` varchar(45) DEFAULT NULL,
  `OPIS` longtext,
  `RAZLAGA` longtext,
  `V_BODOCE` longtext,
  `PRAKSA_ID` int(11) DEFAULT NULL,
  `MENTOR` varchar(150) NOT NULL,
  `SOLA` varchar(300) NOT NULL,
  `NASLOV_URE` varchar(300) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `UPORABNIK_HOSP_FK` (`UPORABNIK_ID`),
  KEY `TEMA_HOST_FK` (`TEMA_ID`),
  KEY `SKLOP_HOST_FK` (`SKLOP_ID`),
  KEY `ENOTA_HOSP_FK` (`ENOTA_ID`),
  KEY `PRAKSA_HOSP_ID` (`PRAKSA_ID`),
  CONSTRAINT `ENOTA_HOSP_FK` FOREIGN KEY (`ENOTA_ID`) REFERENCES `enota` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `PRAKSA_HOSP_ID` FOREIGN KEY (`PRAKSA_ID`) REFERENCES `praksa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `SKLOP_HOST_FK` FOREIGN KEY (`SKLOP_ID`) REFERENCES `sklop` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `TEMA_HOST_FK` FOREIGN KEY (`TEMA_ID`) REFERENCES `tema` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `UPORABNIK_HOSP_FK` FOREIGN KEY (`UPORABNIK_ID`) REFERENCES `uporabniki` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospitacije`
--

LOCK TABLES `hospitacije` WRITE;
/*!40000 ALTER TABLE `hospitacije` DISABLE KEYS */;
INSERT INTO `hospitacije` VALUES (6,'01007569','a','2012-10-02 00:00:00',2,2,1,1,1,NULL,NULL,NULL,NULL,NULL,'a','a','a');
/*!40000 ALTER TABLE `hospitacije` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sola`
--

DROP TABLE IF EXISTS `sola`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sola` (
  `ID` int(11) NOT NULL,
  `NAZIV` varchar(150) NOT NULL,
  `SEDEZ` varchar(150) NOT NULL,
  `TELEFON` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sola`
--

LOCK TABLES `sola` WRITE;
/*!40000 ALTER TABLE `sola` DISABLE KEYS */;
/*!40000 ALTER TABLE `sola` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nal_21`
--

DROP TABLE IF EXISTS `nal_21`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nal_21` (
  `UPORABNIK` varchar(25) DEFAULT NULL,
  `bbbb` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nal_21`
--

LOCK TABLES `nal_21` WRITE;
/*!40000 ALTER TABLE `nal_21` DISABLE KEYS */;
/*!40000 ALTER TABLE `nal_21` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nal_22`
--

DROP TABLE IF EXISTS `nal_22`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nal_22` (
  `UPORABNIK` varchar(25) DEFAULT NULL,
  `asdf` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nal_22`
--

LOCK TABLES `nal_22` WRITE;
/*!40000 ALTER TABLE `nal_22` DISABLE KEYS */;
/*!40000 ALTER TABLE `nal_22` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nal_23`
--

DROP TABLE IF EXISTS `nal_23`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nal_23` (
  `UPORABNIK` varchar(25) DEFAULT NULL,
  `asdfasdf` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nal_23`
--

LOCK TABLES `nal_23` WRITE;
/*!40000 ALTER TABLE `nal_23` DISABLE KEYS */;
/*!40000 ALTER TABLE `nal_23` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nal_24`
--

DROP TABLE IF EXISTS `nal_24`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nal_24` (
  `UPORABNIK` varchar(25) DEFAULT NULL,
  `asdfeefee` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nal_24`
--

LOCK TABLES `nal_24` WRITE;
/*!40000 ALTER TABLE `nal_24` DISABLE KEYS */;
/*!40000 ALTER TABLE `nal_24` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nal_25`
--

DROP TABLE IF EXISTS `nal_25`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nal_25` (
  `UPORABNIK` varchar(25) DEFAULT NULL,
  `asdf_vve33` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nal_25`
--

LOCK TABLES `nal_25` WRITE;
/*!40000 ALTER TABLE `nal_25` DISABLE KEYS */;
/*!40000 ALTER TABLE `nal_25` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nal_26`
--

DROP TABLE IF EXISTS `nal_26`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nal_26` (
  `UPORABNIK` varchar(25) DEFAULT NULL,
  `asdfa` varchar(255) DEFAULT NULL,
  `asdfaasdf` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nal_26`
--

LOCK TABLES `nal_26` WRITE;
/*!40000 ALTER TABLE `nal_26` DISABLE KEYS */;
INSERT INTO `nal_26` VALUES ('01007569','asdf','asdf'),('01007569','asdf','asdf');
/*!40000 ALTER TABLE `nal_26` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentje_predmet`
--

DROP TABLE IF EXISTS `studentje_predmet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studentje_predmet` (
  `STUDENTJE_ID` varchar(25) NOT NULL,
  `PREDMET_ID` int(11) NOT NULL,
  UNIQUE KEY `STUDENTJE_PREDMET` (`STUDENTJE_ID`,`PREDMET_ID`),
  KEY `PREDMET_FK` (`PREDMET_ID`),
  KEY `STUDENTJE_FK` (`STUDENTJE_ID`),
  CONSTRAINT `PREDMET_FK` FOREIGN KEY (`PREDMET_ID`) REFERENCES `predmet` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `STUDENTJE_FK` FOREIGN KEY (`STUDENTJE_ID`) REFERENCES `studentje` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentje_predmet`
--

LOCK TABLES `studentje_predmet` WRITE;
/*!40000 ALTER TABLE `studentje_predmet` DISABLE KEYS */;
INSERT INTO `studentje_predmet` VALUES ('01007569',1);
/*!40000 ALTER TABLE `studentje_predmet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `predmet`
--

DROP TABLE IF EXISTS `predmet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `predmet` (
  `ID` int(11) NOT NULL,
  `IME_PREDMETA` varchar(25) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `predmet`
--

LOCK TABLES `predmet` WRITE;
/*!40000 ALTER TABLE `predmet` DISABLE KEYS */;
INSERT INTO `predmet` VALUES (1,'DIDAKTIKA MATEMATIKE'),(2,'Test');
/*!40000 ALTER TABLE `predmet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sola_mnenje`
--

DROP TABLE IF EXISTS `sola_mnenje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sola_mnenje` (
  `ID` int(11) NOT NULL,
  `SOLA_ID` int(11) NOT NULL,
  `MNENJE` varchar(300) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `SOLA_MNENJE_FK` (`SOLA_ID`),
  CONSTRAINT `SOLA_MNENJE_FK` FOREIGN KEY (`SOLA_ID`) REFERENCES `sola` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sola_mnenje`
--

LOCK TABLES `sola_mnenje` WRITE;
/*!40000 ALTER TABLE `sola_mnenje` DISABLE KEYS */;
/*!40000 ALTER TABLE `sola_mnenje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ucitelj_mnenje`
--

DROP TABLE IF EXISTS `ucitelj_mnenje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ucitelj_mnenje` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENENJE` varchar(300) NOT NULL,
  `UCITELJ_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `UCITELJ_MNENJE_FK` (`UCITELJ_ID`),
  CONSTRAINT `UCITELJ_MNENJE_FK` FOREIGN KEY (`UCITELJ_ID`) REFERENCES `ucitelj` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ucitelj_mnenje`
--

LOCK TABLES `ucitelj_mnenje` WRITE;
/*!40000 ALTER TABLE `ucitelj_mnenje` DISABLE KEYS */;
/*!40000 ALTER TABLE `ucitelj_mnenje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `podatki_ure`
--

DROP TABLE IF EXISTS `podatki_ure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `podatki_ure` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IZVAJALEC` varchar(100) NOT NULL,
  `MENTOR` varchar(100) NOT NULL,
  `DATUM` date NOT NULL,
  `URA` int(11) NOT NULL,
  `RAZRED` int(11) NOT NULL,
  `SOLA` varchar(150) NOT NULL,
  `TEMA_ID` int(11) NOT NULL,
  `SKLOP_ID` int(11) NOT NULL,
  `ENOTA_ID` int(11) NOT NULL,
  `PRISTOP` varchar(200) NOT NULL,
  `OBLIKE` varchar(200) NOT NULL,
  `METODE` varchar(200) NOT NULL,
  `PRIPOMOCKI` varchar(300) NOT NULL,
  `VIRI` varchar(300) NOT NULL,
  `NASLOV_URE` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `TEMA_URA_FK` (`TEMA_ID`),
  KEY `SKLOP_URA_FK` (`SKLOP_ID`),
  KEY `ENOTA_URA_FK` (`ENOTA_ID`),
  CONSTRAINT `ENOTA_URA_FK` FOREIGN KEY (`ENOTA_ID`) REFERENCES `enota` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `SKLOP_URA_FK` FOREIGN KEY (`SKLOP_ID`) REFERENCES `sklop` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `TEMA_URA_FK` FOREIGN KEY (`TEMA_ID`) REFERENCES `tema` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `podatki_ure`
--

LOCK TABLES `podatki_ure` WRITE;
/*!40000 ALTER TABLE `podatki_ure` DISABLE KEYS */;
INSERT INTO `podatki_ure` VALUES (2,'1','1','2012-02-01',1,999,'1',1,1,1,'1','1','1','1','1','Prepoznavanje kotne funkcije na podalgi grafov'),(16,'Jure','Danica','2012-02-03',3,3,'kakanj2',1,1,1,'123','123','123','23','23','Naslov 2'),(17,'Jure','Danica','2012-02-09',1,8,'OŠ Višnja gora',2,2,2,'prisojtlaj','lasdjflaj','jalsdjfl','alsdjflaj','l lasdjflasdjf','Naslov 3'),(18,'asdf','asdf','2003-02-01',1,12,'asdf',2,2,2,'asdf','asdf','af','asdf','asdf','Naslov 4'),(19,'asdf','asdf','2003-02-01',12,99999,'asdf',1,1,1,'asdf','sadf','asdf','asdf','asdf','Naslov 5'),(20,'1234234234','qwerqwerwer','1955-05-01',12312,12454215,'asdfasdf',1,1,1,'asdf','asdf','sdf','sadf','asdf','Naslov 6'),(21,'jfasld','lasdfj','2012-02-09',12,12,'sdf',1,1,1,'asd','asd','asd','sad','asd','Naslov 7');
/*!40000 ALTER TABLE `podatki_ure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sola_opis`
--

DROP TABLE IF EXISTS `sola_opis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sola_opis` (
  `ID` int(11) NOT NULL,
  `SOLA_ID` int(11) NOT NULL,
  `RAVNATELJ` varchar(100) NOT NULL,
  `POMOC_RAVN` varchar(100) NOT NULL,
  `PREC_SVET_SOLE` varchar(100) NOT NULL,
  `PREC_SVET_STARS` varchar(100) NOT NULL,
  `TAJNICA` varchar(100) NOT NULL,
  `RACUNOVODJA` varchar(100) NOT NULL,
  `HISNIK` varchar(100) NOT NULL,
  `DRUGO_OSEBJE` longtext,
  `OPIS` longtext NOT NULL,
  `ZGODOVINA` longtext,
  PRIMARY KEY (`ID`),
  KEY `SOLA_OPIS_FK` (`SOLA_ID`),
  CONSTRAINT `SOLA_OPIS_FK` FOREIGN KEY (`SOLA_ID`) REFERENCES `sola` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sola_opis`
--

LOCK TABLES `sola_opis` WRITE;
/*!40000 ALTER TABLE `sola_opis` DISABLE KEYS */;
/*!40000 ALTER TABLE `sola_opis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tema`
--

DROP TABLE IF EXISTS `tema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tema` (
  `ID` int(11) NOT NULL,
  `TEMA` varchar(150) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tema`
--

LOCK TABLES `tema` WRITE;
/*!40000 ALTER TABLE `tema` DISABLE KEYS */;
INSERT INTO `tema` VALUES (1,'TEMA 1'),(2,'TEMA 2');
/*!40000 ALTER TABLE `tema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `analiza_ure`
--

DROP TABLE IF EXISTS `analiza_ure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `analiza_ure` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UCNA_PRIPRAVA_ID` int(11) NOT NULL,
  `ZADOVOLJNI` longtext NOT NULL,
  `IZBOLJSAVE` longtext NOT NULL,
  `POMEMBEN_VIDIK` varchar(45) DEFAULT NULL,
  `OPIS` longtext,
  `RAZLAGA` longtext,
  `V_BODOCE` longtext,
  PRIMARY KEY (`ID`),
  KEY `UCNA_PRIPRAVA_ANALIZA_FK` (`UCNA_PRIPRAVA_ID`),
  CONSTRAINT `UCNA_PRIPRAVA_ANALIZA_FK` FOREIGN KEY (`UCNA_PRIPRAVA_ID`) REFERENCES `ucna_priprava` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `analiza_ure`
--

LOCK TABLES `analiza_ure` WRITE;
/*!40000 ALTER TABLE `analiza_ure` DISABLE KEYS */;
/*!40000 ALTER TABLE `analiza_ure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `praksa`
--

DROP TABLE IF EXISTS `praksa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `praksa` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PREDMET_ID` int(11) NOT NULL,
  `STUDENTJE_ID` varchar(25) NOT NULL,
  `UCITELJ_ID` int(11) NOT NULL,
  `SOLA_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `SOLA_PRAKSA_FK` (`SOLA_ID`),
  KEY `UCITELJ_PRAKSA_FK` (`UCITELJ_ID`),
  KEY `STUD_PRAKSA_FK` (`STUDENTJE_ID`),
  KEY `PREDMET_PRAKSA_FK` (`PREDMET_ID`),
  CONSTRAINT `PREDMET_PRAKSA_FK` FOREIGN KEY (`PREDMET_ID`) REFERENCES `predmet` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `SOLA_PRAKSA_FK` FOREIGN KEY (`SOLA_ID`) REFERENCES `sola` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `STUD_PRAKSA_FK` FOREIGN KEY (`STUDENTJE_ID`) REFERENCES `studentje` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `UCITELJ_PRAKSA_FK` FOREIGN KEY (`UCITELJ_ID`) REFERENCES `ucitelj` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `praksa`
--

LOCK TABLES `praksa` WRITE;
/*!40000 ALTER TABLE `praksa` DISABLE KEYS */;
/*!40000 ALTER TABLE `praksa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `naloge`
--

DROP TABLE IF EXISTS `naloge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `naloge` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IME` varchar(100) NOT NULL,
  `OPIS` varchar(200) NOT NULL,
  `DATUM_ZAKLJ` varchar(30) NOT NULL,
  `PREDMET_ID` int(11) NOT NULL,
  `UPORABNIK` varchar(25) NOT NULL,
  `INSERT` varchar(150) DEFAULT NULL,
  `SELECT` varchar(150) DEFAULT NULL,
  `UPDATE` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PREDMET_ID` (`ID`),
  KEY `FK_UPORABNIK` (`UPORABNIK`),
  KEY `FK_PREDMET_ID_NALOGE` (`PREDMET_ID`),
  CONSTRAINT `FK_PREDMET_ID_NALOGE` FOREIGN KEY (`PREDMET_ID`) REFERENCES `predmet` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_UPORABNIK` FOREIGN KEY (`UPORABNIK`) REFERENCES `profesorji` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `naloge`
--

LOCK TABLES `naloge` WRITE;
/*!40000 ALTER TABLE `naloge` DISABLE KEYS */;
INSERT INTO `naloge` VALUES (15,'bla','blla','2012-07-06 00:00:00',1,'ZMAG',NULL,NULL,NULL),(21,'Test naloga','Navodilo testne naloge','2012-07-06 00:00:00',1,'ZMAG',NULL,NULL,NULL),(22,'teST','teST','2012-07-16 00:00:00',1,'ZMAG','INSERT INTO NAL_22 VALUES();','SELECT * FROM NAL_22;','UPDATE NAL_22 SET asdf = ? WHERE ID = 22;'),(23,'asdf','asdf','2012-07-15 00:00:00',1,'ZMAG','INSERT INTO NAL_23 VALUES(?);','SELECT * FROM NAL_23;','UPDATE NAL_23 SET asdfasdf  WHERE ID = 23;'),(24,'sadfasdfee','asdfdsfasdfaf','2012-07-19 00:00:00',1,'ZMAG','INSERT INTO NAL_24 VALUES(?);','SELECT * FROM NAL_24;','UPDATE NAL_24 SET asdfeefee  WHERE ID = 24;'),(25,'asdfe asfasdf','asdfasdfee','2012-07-20 00:00:00',1,'ZMAG','INSERT INTO NAL_25 VALUES(?);','SELECT * FROM NAL_25;','UPDATE NAL_25 SET asdf_vve33 = ? WHERE ID = 25;'),(26,'sadfasdfasd','asdfasdf','2012-07-20 00:00:00',1,'ZMAG','INSERT INTO NAL_26 VALUES(?,?,?);','SELECT * FROM NAL_26;','UPDATE NAL_26 SET asdfa = ?,asdfaasdf = ? WHERE ID = 26;');
/*!40000 ALTER TABLE `naloge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `v_datoteke`
--

/*!50001 DROP TABLE IF EXISTS `v_datoteke`*/;
/*!50001 DROP VIEW IF EXISTS `v_datoteke`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_datoteke` AS select 'Priprave' AS `Vrsta`,`up`.`UPORABNIK_ID` AS `UPORABNIK_ID`,date_format(`pu`.`DATUM`,'%d.%m.%Y') AS `Datum`,concat(`e`.`ENOTA`,'; ',`pu`.`RAZRED`,'. razred;') AS `Opis`,`up`.`ID` AS `Uredi` from ((`ucna_priprava` `up` join `podatki_ure` `pu`) join `enota` `e`) where ((`up`.`PODATKI_URE_ID` = `pu`.`ID`) and (`pu`.`ENOTA_ID` = `e`.`ID`)) union select 'Hospitacije' AS `Vrsta`,`h`.`UPORABNIK_ID` AS `UPORABNIK_ID`,date_format(`h`.`DATUM`,'%d.%m.%Y') AS `date_format(h.DATUM,'%d.%m.%Y')`,concat(`en`.`ENOTA`,'; ',`h`.`RAZRED`,'. razred; Izvajalec: ',`h`.`IZVAJALEC`) AS `opis`,`h`.`ID` AS `uredi` from (`hospitacije` `h` join `enota` `en`) where (`h`.`ENOTA_ID` = `en`.`ID`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-03-23 12:00:17
