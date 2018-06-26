-- MySQL dump 10.13  Distrib 5.1.30, for Win32 (ia32)
--
-- Host: localhost    Database: automation
-- ------------------------------------------------------
-- Server version	5.1.30-community

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
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `device` (
  `device_id` int(11) NOT NULL,
  `device_model` varchar(30) NOT NULL,
  `device_os` varchar(30) NOT NULL,
  `os_version` varchar(30) NOT NULL,
  `build_id` varchar(30) DEFAULT NULL,
  `serial_num` varchar(80) NOT NULL,
  `brand` varchar(80) NOT NULL,
  `manufacturer` varchar(80) NOT NULL,
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `features`
--

DROP TABLE IF EXISTS `features`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `features` (
  `feature_id` int(11) NOT NULL AUTO_INCREMENT,
  `feature_name` varchar(20) NOT NULL,
  `feature_target` varchar(20) NOT NULL,
  PRIMARY KEY (`feature_id`),
  UNIQUE KEY `feature_name` (`feature_name`),
  UNIQUE KEY `feature_target` (`feature_target`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `features`
--

LOCK TABLES `features` WRITE;
/*!40000 ALTER TABLE `features` DISABLE KEYS */;
/*!40000 ALTER TABLE `features` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `library`
--

DROP TABLE IF EXISTS `library`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `library` (
  `lib_id` int(11) NOT NULL,
  `lib_name` varchar(20) NOT NULL,
  `lib_type` varchar(20) NOT NULL,
  PRIMARY KEY (`lib_id`),
  UNIQUE KEY `lib_name` (`lib_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `library`
--

LOCK TABLES `library` WRITE;
/*!40000 ALTER TABLE `library` DISABLE KEYS */;
/*!40000 ALTER TABLE `library` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `server`
--

DROP TABLE IF EXISTS `server`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `server` (
  `server_id` int(11) NOT NULL,
  `port_no` int(11) NOT NULL,
  `gm_port` int(11) NOT NULL,
  `sdk_port` int(11) NOT NULL,
  `verify_port` int(11) NOT NULL,
  `api_port` int(11) NOT NULL,
  `ip_address` int(11) NOT NULL,
  `os_version` varchar(20) NOT NULL,
  `console_user` varchar(30) NOT NULL,
  `console_password` varchar(30) NOT NULL,
  `enterprise_id` int(11) NOT NULL,
  `enterprise_user` varchar(30) NOT NULL,
  `enterprise_password` varchar(30) NOT NULL,
  `server_user` varchar(30) NOT NULL,
  `server_password` varchar(30) NOT NULL,
  `agent_info` varchar(10000) NOT NULL,
  PRIMARY KEY (`server_id`),
  UNIQUE KEY `port_no` (`port_no`),
  UNIQUE KEY `enterprise_id` (`enterprise_id`),
  UNIQUE KEY `console_user` (`console_user`),
  UNIQUE KEY `server_user` (`server_user`),
  UNIQUE KEY `enterprise_user` (`enterprise_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `server`
--

LOCK TABLES `server` WRITE;
/*!40000 ALTER TABLE `server` DISABLE KEYS */;
/*!40000 ALTER TABLE `server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_case`
--

DROP TABLE IF EXISTS `test_case`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `test_case` (
  `testcase_id` int(11) NOT NULL,
  `testcase_name` varchar(30) NOT NULL,
  `created_time` time NOT NULL,
  `update_time` time NOT NULL,
  `test_feature_id` int(11) NOT NULL,
  PRIMARY KEY (`testcase_id`),
  UNIQUE KEY `testcase_name` (`testcase_name`),
  KEY `test_case_fk0` (`test_feature_id`),
  CONSTRAINT `test_case_fk0` FOREIGN KEY (`test_feature_id`) REFERENCES `features` (`feature_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `test_case`
--

LOCK TABLES `test_case` WRITE;
/*!40000 ALTER TABLE `test_case` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_case` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_execution`
--

DROP TABLE IF EXISTS `test_execution`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `test_execution` (
  `execution_id` int(11) NOT NULL AUTO_INCREMENT,
  `testrun_id` int(11) NOT NULL,
  `testcase_id` int(11) NOT NULL,
  `execution_status` varchar(30) NOT NULL,
  `execution_start_time` time NOT NULL,
  `execution_end_time` time NOT NULL,
  `created_time` time NOT NULL,
  `update_time` time NOT NULL,
  PRIMARY KEY (`execution_id`),
  KEY `test_execution_fk0` (`testrun_id`),
  KEY `test_execution_fk1` (`testcase_id`),
  CONSTRAINT `test_execution_fk1` FOREIGN KEY (`testcase_id`) REFERENCES `test_case` (`testcase_id`),
  CONSTRAINT `test_execution_fk0` FOREIGN KEY (`testrun_id`) REFERENCES `test_run` (`testrun_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `test_execution`
--

LOCK TABLES `test_execution` WRITE;
/*!40000 ALTER TABLE `test_execution` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_execution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_job`
--

DROP TABLE IF EXISTS `test_job`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `test_job` (
  `testjob_id` int(11) NOT NULL,
  `test_job_description` varchar(30) NOT NULL,
  `created_time` time NOT NULL,
  `updated_time` time NOT NULL,
  `status` varchar(10) NOT NULL,
  `server_id` int(11) NOT NULL,
  `lib_id` int(11) NOT NULL,
  PRIMARY KEY (`testjob_id`),
  UNIQUE KEY `testjob_id` (`testjob_id`),
  KEY `test_job_fk0` (`server_id`),
  KEY `test_job_fk1` (`lib_id`),
  CONSTRAINT `test_job_fk1` FOREIGN KEY (`lib_id`) REFERENCES `library` (`lib_id`),
  CONSTRAINT `test_job_fk0` FOREIGN KEY (`server_id`) REFERENCES `server` (`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `test_job`
--

LOCK TABLES `test_job` WRITE;
/*!40000 ALTER TABLE `test_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_run`
--

DROP TABLE IF EXISTS `test_run`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `test_run` (
  `testrun_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(30) NOT NULL,
  `created_time` time NOT NULL,
  `update_time` time NOT NULL,
  `test_job_id` int(11) NOT NULL,
  `device_id` int(11) NOT NULL,
  PRIMARY KEY (`testrun_id`),
  UNIQUE KEY `testrun_id` (`testrun_id`),
  KEY `test_run_fk0` (`test_job_id`),
  KEY `test_run_fk1` (`device_id`),
  CONSTRAINT `test_run_fk1` FOREIGN KEY (`device_id`) REFERENCES `device` (`device_id`),
  CONSTRAINT `test_run_fk0` FOREIGN KEY (`test_job_id`) REFERENCES `test_job` (`testjob_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `test_run`
--

LOCK TABLES `test_run` WRITE;
/*!40000 ALTER TABLE `test_run` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_run` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-26 12:48:58
