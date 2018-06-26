-- MySQL dump 10.13  Distrib 5.1.41, for Win32 (ia32)
--
-- Host: localhost    Database: automation
-- ------------------------------------------------------
-- Server version	5.1.41-community

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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `device_id` int(11) NOT NULL AUTO_INCREMENT,
  `device_model` varchar(30) NOT NULL,
  `device_os` varchar(30) NOT NULL,
  `os_version` varchar(30) NOT NULL,
  `build_id` varchar(30) DEFAULT NULL,
  `serial_num` varchar(80) NOT NULL,
  `brand` varchar(80) NOT NULL,
  `manufacturer` varchar(80) NOT NULL,
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES (7,'Gionee P7 Max','android','8.0','23R5T','123345555','Gionee','Gionee');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER TRG_INSERT_DEVICE
AFTER INSERT 
ON DEVICE
FOR EACH ROW
BEGIN

INSERT INTO TEST_RUN (TEST_JOB_ID,DEVICE_ID) SELECT TESTJOB_ID,NEW.DEVICE_ID from TEST_JOB where auto_create_on_new_device=1 and STATUS <> 'COMPLETED';

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `features`
--

DROP TABLE IF EXISTS `features`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `features` (
  `feature_id` int(11) NOT NULL AUTO_INCREMENT,
  `feature_name` varchar(100) DEFAULT NULL,
  `feature_target` varchar(20) NOT NULL,
  PRIMARY KEY (`feature_id`),
  UNIQUE KEY `feature_name` (`feature_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `features`
--

LOCK TABLES `features` WRITE;
/*!40000 ALTER TABLE `features` DISABLE KEYS */;
INSERT INTO `features` VALUES (1,'Initialization on Mobile','MOBILE'),(3,'Self Registration','MOBILE');
/*!40000 ALTER TABLE `features` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `library`
--

DROP TABLE IF EXISTS `library`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `library` (
  `lib_id` int(11) NOT NULL AUTO_INCREMENT,
  `lib_name` varchar(20) NOT NULL,
  `lib_type` varchar(20) NOT NULL,
  `lib_version` varchar(30) NOT NULL,
  PRIMARY KEY (`lib_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library`
--

LOCK TABLES `library` WRITE;
/*!40000 ALTER TABLE `library` DISABLE KEYS */;
INSERT INTO `library` VALUES (1,'apisdk.so','ios','1.1');
/*!40000 ALTER TABLE `library` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `server`
--

DROP TABLE IF EXISTS `server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server` (
  `server_id` int(11) NOT NULL AUTO_INCREMENT,
  `gm_port` int(11) NOT NULL,
  `sdk_port` int(11) NOT NULL,
  `verify_port` int(11) NOT NULL,
  `api_port` int(11) NOT NULL,
  `ip_address` int(11) NOT NULL,
  `os_version` varchar(20) NOT NULL,
  `console_user` varchar(30) DEFAULT NULL,
  `console_password` varchar(30) NOT NULL,
  `enterprise_id` varchar(30) DEFAULT NULL,
  `enterprise_user` varchar(30) NOT NULL,
  `enterprise_password` varchar(30) NOT NULL,
  `server_user` varchar(30) NOT NULL,
  `server_password` varchar(30) NOT NULL,
  `agent_info` varchar(10000) NOT NULL,
  PRIMARY KEY (`server_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `server`
--

LOCK TABLES `server` WRITE;
/*!40000 ALTER TABLE `server` DISABLE KEYS */;
INSERT INTO `server` VALUES (7,8443,4443,8007,9080,100,'Linux 7','gmuser','uniken123$','CBC','sruser','uniken123$','sruser','uniken123$','AGENRT_INFO');
/*!40000 ALTER TABLE `server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_case`
--

DROP TABLE IF EXISTS `test_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_case` (
  `testcase_id` int(11) NOT NULL AUTO_INCREMENT,
  `testcase_name` varchar(30) NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `test_feature_id` int(11) NOT NULL,
  `testcase_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`testcase_id`),
  UNIQUE KEY `testcase_name` (`testcase_name`),
  KEY `test_case_fk0` (`test_feature_id`),
  CONSTRAINT `test_case_fk0` FOREIGN KEY (`test_feature_id`) REFERENCES `features` (`feature_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_case`
--

LOCK TABLES `test_case` WRITE;
/*!40000 ALTER TABLE `test_case` DISABLE KEYS */;
INSERT INTO `test_case` VALUES (1,'INIT','2018-06-26 17:10:43','0000-00-00 00:00:00',1,'Initialization on Mobile'),(3,'INIT_FAIL_WRONG_APPID','2018-06-26 17:11:22','0000-00-00 00:00:00',1,'Initialization failure on Mobile on incorrect APP_ID'),(4,'INIT_FAIL_WRONG_PORT','2018-06-26 17:11:38','0000-00-00 00:00:00',1,'Initialization failure on Mobile on incorrect port');
/*!40000 ALTER TABLE `test_case` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_case_job_mapping`
--

DROP TABLE IF EXISTS `test_case_job_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_case_job_mapping` (
  `tc_job_map_id` int(11) NOT NULL AUTO_INCREMENT,
  `testcase_id` int(11) NOT NULL,
  `testjob_id` int(11) NOT NULL,
  PRIMARY KEY (`tc_job_map_id`),
  KEY `tc_job_map_fk0` (`testcase_id`),
  KEY `tc_job_map_fk1` (`testjob_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_case_job_mapping`
--

LOCK TABLES `test_case_job_mapping` WRITE;
/*!40000 ALTER TABLE `test_case_job_mapping` DISABLE KEYS */;
INSERT INTO `test_case_job_mapping` VALUES (1,1,1),(2,3,1),(3,4,1);
/*!40000 ALTER TABLE `test_case_job_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_execution`
--

DROP TABLE IF EXISTS `test_execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_execution` (
  `execution_id` int(11) NOT NULL AUTO_INCREMENT,
  `testrun_id` int(11) NOT NULL,
  `testcase_id` int(11) NOT NULL,
  `execution_status` varchar(20) DEFAULT 'CREATED',
  `execution_start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `execution_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`execution_id`),
  KEY `test_execution_fk0` (`testrun_id`),
  KEY `test_execution_fk1` (`testcase_id`),
  CONSTRAINT `test_execution_fk0` FOREIGN KEY (`testrun_id`) REFERENCES `test_run` (`testrun_id`),
  CONSTRAINT `test_execution_fk1` FOREIGN KEY (`testcase_id`) REFERENCES `test_case` (`testcase_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_execution`
--

LOCK TABLES `test_execution` WRITE;
/*!40000 ALTER TABLE `test_execution` DISABLE KEYS */;
INSERT INTO `test_execution` VALUES (4,7,1,'CREATED','0000-00-00 00:00:00','0000-00-00 00:00:00','2018-06-26 19:08:11','0000-00-00 00:00:00'),(5,7,3,'CREATED','0000-00-00 00:00:00','0000-00-00 00:00:00','2018-06-26 19:08:11','0000-00-00 00:00:00'),(6,7,4,'CREATED','0000-00-00 00:00:00','0000-00-00 00:00:00','2018-06-26 19:08:11','0000-00-00 00:00:00');
/*!40000 ALTER TABLE `test_execution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_job`
--

DROP TABLE IF EXISTS `test_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_job` (
  `testjob_id` int(11) NOT NULL AUTO_INCREMENT,
  `test_job_description` varchar(30) NOT NULL,
  `created_time` time NOT NULL,
  `updated_time` time NOT NULL,
  `status` varchar(10) NOT NULL,
  `server_id` int(11) NOT NULL,
  `lib_id` int(11) NOT NULL,
  `auto_create_on_new_device` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`testjob_id`),
  UNIQUE KEY `testjob_id` (`testjob_id`),
  KEY `test_job_fk0` (`server_id`),
  KEY `test_job_fk1` (`lib_id`),
  CONSTRAINT `test_job_fk0` FOREIGN KEY (`server_id`) REFERENCES `server` (`server_id`),
  CONSTRAINT `test_job_fk1` FOREIGN KEY (`lib_id`) REFERENCES `library` (`lib_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_job`
--

LOCK TABLES `test_job` WRITE;
/*!40000 ALTER TABLE `test_job` DISABLE KEYS */;
INSERT INTO `test_job` VALUES (1,'TEST JOB1','20:21:31','20:21:31','CREATED',7,1,1);
/*!40000 ALTER TABLE `test_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_run`
--

DROP TABLE IF EXISTS `test_run`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_run` (
  `testrun_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(30) NOT NULL DEFAULT 'CREATED',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `test_job_id` int(11) NOT NULL,
  `device_id` int(11) NOT NULL,
  PRIMARY KEY (`testrun_id`),
  UNIQUE KEY `testrun_id` (`testrun_id`),
  KEY `test_run_fk0` (`test_job_id`),
  KEY `test_run_fk1` (`device_id`),
  CONSTRAINT `test_run_fk0` FOREIGN KEY (`test_job_id`) REFERENCES `test_job` (`testjob_id`),
  CONSTRAINT `test_run_fk1` FOREIGN KEY (`device_id`) REFERENCES `device` (`device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_run`
--

LOCK TABLES `test_run` WRITE;
/*!40000 ALTER TABLE `test_run` DISABLE KEYS */;
INSERT INTO `test_run` VALUES (7,'CREATED','2018-06-26 19:08:11','0000-00-00 00:00:00',1,7);
/*!40000 ALTER TABLE `test_run` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER TRG_INSERT_TEST_RUN
AFTER INSERT 
ON TEST_RUN
FOR EACH ROW
BEGIN

INSERT INTO TEST_EXECUTION (TESTRUN_ID,TESTCASE_ID) SELECT tr.testrun_id,tcjm.testcase_id from test_run tr, test_case_job_mapping tcjm where tr.test_job_id = tcjm.testjob_id and tr.testrun_id  = NEW.testrun_id;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-27  0:47:35
