CREATE DATABASE  IF NOT EXISTS `quiz_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `quiz_management`;
-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: quiz_management
-- ------------------------------------------------------
-- Server version	8.0.31-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `question` varchar(1000) NOT NULL,
  `option_a` varchar(45) NOT NULL,
  `option_b` varchar(45) NOT NULL,
  `option_c` varchar(45) NOT NULL,
  `option_d` varchar(45) NOT NULL,
  `right_option` varchar(1) NOT NULL,
  `marks` int NOT NULL,
  `deleted` varchar(1) NOT NULL DEFAULT 'N',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `category` varchar(45) NOT NULL,
  `deleted` varchar(1) NOT NULL DEFAULT 'N',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz-category`
--

DROP TABLE IF EXISTS `quiz-category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz-category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(45) NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz-category`
--

LOCK TABLES `quiz-category` WRITE;
/*!40000 ALTER TABLE `quiz-category` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz-category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_question`
--

DROP TABLE IF EXISTS `quiz_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quiz_id` int NOT NULL,
  `question_id` int NOT NULL,
  `deleted` varchar(1) NOT NULL DEFAULT 'N',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_QUIZ_ID_idx` (`quiz_id`),
  KEY `FK_QUESTION_ID_idx` (`question_id`),
  CONSTRAINT `FK_QUESTION_ID` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_QUIZ_ID` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_question`
--

LOCK TABLES `quiz_question` WRITE;
/*!40000 ALTER TABLE `quiz_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'ATTENDEE');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email_id` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `mobile_number` varchar(10) NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_id_UNIQUE` (`email_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Rahul','Dubey','rd@gmail.com','$2a$10$RGGoC5vruSVa7xSdv2e3k.AW./r72VQ.XdOJtbWdaP2i1g64pLB4G','9876543210','2023-01-03 09:30:56','2023-01-04 10:29:46'),(2,'Rahul','Dubey','rd2@gmail.com','$2a$10$WVnt6uK/lR7d6s3AEySazOxRURe55HzOoHhyNO68MZrxPl/xLmMs2','9876543210','2023-01-04 11:19:29','2023-01-04 11:19:29');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_USER_ID_idx` (`user_id`),
  KEY `FK_ROLE_ROLE_ID_idx` (`role_id`),
  CONSTRAINT `FK_ROLE_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_USER_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,2),(2,2,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_score`
--

DROP TABLE IF EXISTS `user_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_score` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `score` int NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_USER_ID_idx` (`user_id`),
  CONSTRAINT `FK_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_score`
--

LOCK TABLES `user_score` WRITE;
/*!40000 ALTER TABLE `user_score` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_score` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-04 19:20:35
