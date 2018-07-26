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
  `device_model` varchar(10000) DEFAULT NULL,
  `device_os` varchar(30) NOT NULL,
  `os_version` varchar(30) NOT NULL,
  `build_id` varchar(30) DEFAULT NULL,
  `serial_num` varchar(80) NOT NULL,
  `brand` varchar(80) NOT NULL,
  `manufacturer` varchar(80) NOT NULL,
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
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
  `feature_name` varchar(100) NOT NULL,
  `feature_target` varchar(20) NOT NULL,
  PRIMARY KEY (`feature_id`),
  UNIQUE KEY `feature_name` (`feature_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `features`
--

LOCK TABLES `features` WRITE;
/*!40000 ALTER TABLE `features` DISABLE KEYS */;
INSERT INTO `features` VALUES (1,'Initialization on Mobile','MOBILE'),(3,'Self Registration','MOBILE'),(4,'VERIFY_API','AUTOMATION_CLIENT');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library`
--

LOCK TABLES `library` WRITE;
/*!40000 ALTER TABLE `library` DISABLE KEYS */;
INSERT INTO `library` VALUES (3,'SERVER_API','SERVER_API','1.0');
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
  `gm_port` int(11) DEFAULT '8443',
  `sdk_port` int(11) DEFAULT '4443',
  `verify_port` int(11) DEFAULT '8007',
  `api_port` int(11) DEFAULT '9080',
  `ip_address` varchar(15) DEFAULT NULL,
  `os_version` varchar(20) NOT NULL,
  `console_user` varchar(30) DEFAULT 'gmuser',
  `console_password` varchar(30) DEFAULT 'Uniken123$',
  `enterprise_id` varchar(30) DEFAULT 'CBCVerify',
  `enterprise_user` varchar(30) DEFAULT 'uniken',
  `enterprise_password` varchar(30) NOT NULL,
  `server_user` varchar(30) DEFAULT 'sruser',
  `server_password` varchar(30) DEFAULT 'Uniken123$',
  `agent_info` varchar(10000) DEFAULT '',
  PRIMARY KEY (`server_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `server`
--

LOCK TABLES `server` WRITE;
/*!40000 ALTER TABLE `server` DISABLE KEYS */;
INSERT INTO `server` VALUES (7,8443,4443,8007,9080,'100','Linux 7','gmuser','uniken123$','CBC','sruser','aicvep','sruser','uniken123$','AGENRT_INFO'),(8,4415,4414,1353,1435,'3142','dummy','dumy','dummy','dummy','dummy','aicvep','dummy','dummy','dummy'),(9,4415,4414,1353,1435,'3142','dummy','dumy','dummy','dummy','dummy','aicvep','dummy','dummy','dummy'),(10,1111,0,3333,4444,'5555','null','dummy','dummy','dummy','dummy','aicvep','dummy','dummy','dummy'),(11,8443,4443,8007,9080,'10.0.5.15','CentOS 7','gmuser','Uniken123$','CBCVerify','uniken','aicvep','sruser','uniken123$',''),(12,8443,4443,8007,9080,'10.0.6.24','Linux 7.4','gmuser','Uniken123$','CBCVerify','uniken','5laqc1','sruser','Uniken123$','');
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
  `update_time` timestamp NULL DEFAULT NULL,
  `test_feature_id` int(11) NOT NULL,
  `testcase_desc` varchar(100) DEFAULT NULL,
  `response_code` int(11) DEFAULT NULL,
  `error_code` int(11) DEFAULT NULL,
  `error_message` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`testcase_id`),
  UNIQUE KEY `testcase_name` (`testcase_name`),
  KEY `test_case_fk0` (`test_feature_id`),
  CONSTRAINT `test_case_fk0` FOREIGN KEY (`test_feature_id`) REFERENCES `features` (`feature_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_case`
--

LOCK TABLES `test_case` WRITE;
/*!40000 ALTER TABLE `test_case` DISABLE KEYS */;
INSERT INTO `test_case` VALUES (5,'VERIFY_API_REQUEST_SUCCESS','2018-07-24 04:43:18',NULL,4,'Execute Verify requeest APIs with all correct params and get notificaiton_uuid',0,0,''),(6,'VERIFY_API_REQUEST_NOUSER','2018-07-24 08:45:16',NULL,4,'Execute Verify Request API with a REL-ID user ID that does not exist',1,3560,'User not present'),(7,'VERIFY_API_REQUEST_NOENTID','2018-07-24 12:39:22',NULL,4,'Execute Verify Request API with an invalid Enterprise ID',1,3527,'Invalid ENTERPRISE ID'),(9,'VERIFY_API_REQUEST_NOAUTH','2018-07-25 11:26:52',NULL,4,'Execute Verify Request API without Auth Header',1,2601,'Empty Authorization Header'),(10,'VERIFY_API_INVALID_CONTEXT','2018-07-25 11:48:30',NULL,4,'Execute Verify Request API with invalid URL context',1,2600,'Invalid Request uri');
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
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_case_job_mapping`
--

LOCK TABLES `test_case_job_mapping` WRITE;
/*!40000 ALTER TABLE `test_case_job_mapping` DISABLE KEYS */;
INSERT INTO `test_case_job_mapping` VALUES (7,9,50),(4,5,50),(5,6,50),(6,7,50);
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
  `params_used` text,
  `result_data` text,
  PRIMARY KEY (`execution_id`),
  KEY `test_execution_fk0` (`testrun_id`),
  KEY `test_execution_fk1` (`testcase_id`),
  CONSTRAINT `test_execution_fk0` FOREIGN KEY (`testrun_id`) REFERENCES `test_run` (`testrun_id`),
  CONSTRAINT `test_execution_fk1` FOREIGN KEY (`testcase_id`) REFERENCES `test_case` (`testcase_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_job` (
  `testjob_id` int(11) NOT NULL AUTO_INCREMENT,
  `test_job_description` varchar(30) NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NULL DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `server_id` int(11) NOT NULL,
  `lib_id` int(11) NOT NULL,
  `auto_create_on_new_device` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`testjob_id`),
  UNIQUE KEY `testjob_id` (`testjob_id`),
  UNIQUE KEY `test_job_description` (`test_job_description`),
  KEY `test_job_fk0` (`server_id`),
  KEY `test_job_fk1` (`lib_id`),
  CONSTRAINT `test_job_fk0` FOREIGN KEY (`server_id`) REFERENCES `server` (`server_id`),
  CONSTRAINT `test_job_fk1` FOREIGN KEY (`lib_id`) REFERENCES `library` (`lib_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_job`
--

LOCK TABLES `test_job` WRITE;
/*!40000 ALTER TABLE `test_job` DISABLE KEYS */;
INSERT INTO `test_job` VALUES (50,'TEST VERIFY API ON 10.0.5.15','2018-07-24 04:44:50',NULL,'CREATED',12,3,1);
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_run`
--

LOCK TABLES `test_run` WRITE;
/*!40000 ALTER TABLE `test_run` DISABLE KEYS */;
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

-- Dump completed on 2018-07-26 15:46:14
