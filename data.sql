-- MySQL dump 10.13  Distrib 8.0.36, for macos14 (arm64)
--
-- Host: localhost    Database: thesisdb
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `role` enum('ADMIN','LECTURER','STUDENT','AFFAIR') CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'admin','$2a$10$PUqsQjbYOaGS3t98.LmcDuWqblrdC58pZFQVkfayxoRe4QT.oXg/a','https://res.cloudinary.com/dyuafq1hx/image/upload/v1716802738/b920ceyu6uoiix7g0h3c.jpg','ADMIN','2024-05-27 18:00:13','2024-05-27 18:01:16',1),(2,'admin2','$2a$10$a7ml30HaBOFLg2M8k4osTOpxPikbW4Ase6dQZe1r.uMNmenw5aWZC','https://res.cloudinary.com/dyuafq1hx/image/upload/v1716958768/hjodjwtoszmutscywdbw.png','ADMIN','2024-05-29 04:59:29','2024-05-29 04:59:29',1),(3,'stephen','$2a$10$7WuTRnOfKFrlYqPa8W3Mpu60VDuyRGP5h4Z8NYVwPHDAPI2oMYli2','https://res.cloudinary.com/dyuafq1hx/image/upload/v1716960626/o3hpru2levojjmnn7yhq.jpg','ADMIN','2024-05-29 05:30:27','2024-05-29 05:30:27',1),(4,'affair','$2a$10$bd6B1pgjooDMGyTzz2FTkeL28DuhhjUflVtPzj01IaFan.IpPO1ou','https://res.cloudinary.com/dyuafq1hx/image/upload/v1716970174/i9zv1bsr9ik8rdioazea.jpg','AFFAIR','2024-05-29 08:09:35','2024-06-13 16:18:08',1),(5,'affair2','$2a$10$4.OnsNsm7bw.S52PHV1rWO5pT4ri4UaNDVtZAm2HSuFmuGrwjXyD6','https://res.cloudinary.com/dyuafq1hx/image/upload/v1716971323/lml0fbgijbjo6upwhk0b.png','AFFAIR','2024-05-29 08:28:44','2024-05-29 08:28:44',1),(6,'lecturer','$2a$10$H88DkaoeBelU3D9c4qGrhu7Ns1B15kftGg2aVeYOLdkeTp8DgYHiK','https://res.cloudinary.com/dyuafq1hx/image/upload/v1716974901/xzrdugvmpwxivgf5ixmu.jpg','LECTURER','2024-05-29 09:28:22','2024-05-29 09:29:12',1),(7,'student','$2a$10$JAeKqxnMi3FSN3iYRThde.LC0C.lvJWovh6pGfZdtC0hUEtbgaZ4G','https://res.cloudinary.com/dyuafq1hx/image/upload/v1719048500/ye52gansseueaeotenfv.png','STUDENT','2024-05-29 10:55:18','2024-06-22 09:28:20',1),(8,'gvone','$2a$10$W68Ap.Lf96xXAhUEhom6TO43LM6MBusZLM91HRgR26jJG.Ddqthj6','https://res.cloudinary.com/dyuafq1hx/image/upload/v1719161095/icnsyiht7j03ehqcjbnh.png','LECTURER','2024-06-23 16:44:55','2024-06-23 16:44:55',1),(9,'gv2','$2a$10$HlOvhlEA8He2cHknSEYVqOlUFH.U/JP55f9GAgVtDqjBP2wmsKRaW','https://res.cloudinary.com/dyuafq1hx/image/upload/v1719161429/gm06dmqytnj4jn2ptggr.png','LECTURER','2024-06-23 16:50:29','2024-06-23 16:50:29',1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `gender` enum('male','female','other') CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `dob` date DEFAULT NULL,
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci,
  `account_id` int DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_id` (`account_id`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'Phạm Đỗ Minh','Vương','male','2151013110vuong@ou.edu.vn','2003-11-07','Tây Ninh',1,'2024-05-27 18:00:13','2024-05-27 18:01:16',1),(2,'Lê Duy Minh','Long','male','long@gmail.com','2003-01-01','Thành phố Hồ Chí Minh',2,'2024-05-29 04:59:29','2024-05-29 04:59:29',1),(3,'Stephen','Hawking','male','stephen@gmail.com','2000-01-01','Mỹ',3,'2024-05-29 05:30:27','2024-05-29 05:30:27',1);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `affair`
--

DROP TABLE IF EXISTS `affair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `affair` (
  `id` int NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `gender` enum('male','female','other') CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `dob` date DEFAULT NULL,
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci,
  `account_id` int DEFAULT NULL,
  `faculty_id` int DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_id` (`account_id`),
  KEY `faculty_id` (`faculty_id`),
  CONSTRAINT `affair_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `affair_ibfk_2` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `affair`
--

LOCK TABLES `affair` WRITE;
/*!40000 ALTER TABLE `affair` DISABLE KEYS */;
INSERT INTO `affair` VALUES (1,'Phạm','Vương','male','phamdominhvuong07112003@gmail.com','2000-11-07','Thành phố Hồ Chí Minh',4,2,'2024-05-29 08:09:35','2024-05-29 08:11:37',1),(2,'Lê','Long','male','longaffair@gmail.com','1999-11-11','Bạc Liêu',5,8,'2024-05-29 08:28:44','2024-05-29 08:28:44',1);
/*!40000 ALTER TABLE `affair` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `faculty_id` int DEFAULT NULL,
  `major_id` int DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `faculty_id` (`faculty_id`),
  KEY `major_id` (`major_id`),
  CONSTRAINT `class_ibfk_1` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`),
  CONSTRAINT `class_ibfk_2` FOREIGN KEY (`major_id`) REFERENCES `major` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,'DH21CS02',2,1,'2024-05-28 14:58:30','2024-05-28 14:58:42',1),(2,'DH21CS01',2,1,'2024-05-28 17:20:37','2024-05-28 17:20:37',1),(3,'DH21IT01',2,2,'2024-05-29 04:49:05','2024-05-29 04:49:05',1),(4,'DH21IT02',2,2,'2024-05-29 04:49:36','2024-05-29 04:49:36',1),(5,'DH21IT03',2,2,'2024-05-29 04:50:03','2024-05-29 04:50:03',1),(6,'DH21BL01',11,35,'2024-05-29 11:35:57','2024-05-29 11:35:57',1);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `council`
--

DROP TABLE IF EXISTS `council`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `council` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `status` enum('pending','blocked') CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT 'pending',
  `school_year_id` int DEFAULT NULL,
  `faculty_id` int DEFAULT NULL,
  `affair_id` int DEFAULT NULL,
  `meeting_date` datetime DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `school_year_id` (`school_year_id`),
  KEY `council_ibfk_2` (`faculty_id`),
  KEY `council_ibfk_3` (`affair_id`),
  CONSTRAINT `council_ibfk_1` FOREIGN KEY (`school_year_id`) REFERENCES `school_year` (`id`),
  CONSTRAINT `council_ibfk_2` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`),
  CONSTRAINT `council_ibfk_3` FOREIGN KEY (`affair_id`) REFERENCES `affair` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `council`
--

LOCK TABLES `council` WRITE;
/*!40000 ALTER TABLE `council` DISABLE KEYS */;
INSERT INTO `council` VALUES (2,'test','blocked',4,2,1,'2024-07-01 16:00:00','2024-06-20 14:16:24','2024-06-23 02:40:05',1),(3,'tét','blocked',3,2,1,'2024-06-30 08:11:00','2024-06-20 18:20:06','2024-06-23 02:42:53',1),(4,'tét','pending',3,2,1,'2024-06-21 08:25:00','2024-06-20 18:25:41','2024-06-20 18:25:41',1),(5,'Hội đồng','pending',3,2,1,'2024-06-24 04:53:00','2024-06-22 14:54:34','2024-06-22 14:54:34',1),(6,'Test email','pending',3,2,1,'2024-06-23 05:03:00','2024-06-22 15:03:52','2024-06-22 15:03:52',1),(7,'test mail again','pending',3,2,1,'2024-06-25 05:06:00','2024-06-22 15:06:24','2024-06-22 15:06:24',1),(8,'mail','pending',4,2,1,'2024-06-23 05:14:00','2024-06-22 15:14:57','2024-06-22 15:14:57',1),(9,'test mail again','pending',4,2,1,'2024-06-23 05:17:00','2024-06-22 15:17:34','2024-06-22 15:17:34',1),(10,'test mail again agian','pending',4,2,1,'2024-06-24 05:25:00','2024-06-22 15:25:57','2024-06-22 15:25:57',1);
/*!40000 ALTER TABLE `council` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `council_criterion`
--

DROP TABLE IF EXISTS `council_criterion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `council_criterion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `council_id` int DEFAULT NULL,
  `criterion_id` int DEFAULT NULL,
  `weight` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `council_id` (`council_id`),
  KEY `criterion_id` (`criterion_id`),
  CONSTRAINT `council_criterion_ibfk_1` FOREIGN KEY (`council_id`) REFERENCES `council` (`id`),
  CONSTRAINT `council_criterion_ibfk_2` FOREIGN KEY (`criterion_id`) REFERENCES `criterion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `council_criterion`
--

LOCK TABLES `council_criterion` WRITE;
/*!40000 ALTER TABLE `council_criterion` DISABLE KEYS */;
INSERT INTO `council_criterion` VALUES (3,2,1,50),(4,2,2,50),(5,3,1,100),(6,4,1,100),(7,5,4,20),(8,5,1,50),(9,5,3,30),(10,6,1,100),(11,7,1,100),(12,8,1,100),(13,9,5,100),(14,10,5,100);
/*!40000 ALTER TABLE `council_criterion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `council_lecturer`
--

DROP TABLE IF EXISTS `council_lecturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `council_lecturer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `council_id` int DEFAULT NULL,
  `lecturer_id` int DEFAULT NULL,
  `position` enum('president','secretary','critical','member') CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT 'critical',
  PRIMARY KEY (`id`),
  KEY `council_id` (`council_id`),
  KEY `lecturer_id` (`lecturer_id`),
  CONSTRAINT `council_lecturer_ibfk_1` FOREIGN KEY (`council_id`) REFERENCES `council` (`id`),
  CONSTRAINT `council_lecturer_ibfk_2` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `council_lecturer`
--

LOCK TABLES `council_lecturer` WRITE;
/*!40000 ALTER TABLE `council_lecturer` DISABLE KEYS */;
INSERT INTO `council_lecturer` VALUES (2,2,1,'president'),(3,3,1,'critical'),(4,3,1,'secretary'),(5,3,1,'president'),(6,4,1,'secretary'),(7,4,1,'president'),(8,4,1,'critical'),(9,5,1,'president'),(10,5,1,'member'),(11,5,1,'secretary'),(12,5,1,'critical'),(13,6,1,'president'),(14,6,1,'secretary'),(15,6,1,'critical'),(16,7,1,'critical'),(17,7,1,'secretary'),(18,7,1,'president'),(19,8,1,'critical'),(20,8,1,'president'),(21,8,1,'secretary'),(22,9,1,'president'),(23,9,1,'critical'),(24,9,1,'secretary'),(25,10,1,'president'),(26,10,1,'critical'),(27,10,1,'secretary');
/*!40000 ALTER TABLE `council_lecturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `council_thesis`
--

DROP TABLE IF EXISTS `council_thesis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `council_thesis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `council_id` int DEFAULT NULL,
  `thesis_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `council_id` (`council_id`),
  KEY `thesis_id` (`thesis_id`),
  CONSTRAINT `council_thesis_ibfk_1` FOREIGN KEY (`council_id`) REFERENCES `council` (`id`),
  CONSTRAINT `council_thesis_ibfk_2` FOREIGN KEY (`thesis_id`) REFERENCES `thesis` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `council_thesis`
--

LOCK TABLES `council_thesis` WRITE;
/*!40000 ALTER TABLE `council_thesis` DISABLE KEYS */;
INSERT INTO `council_thesis` VALUES (6,2,10),(7,2,2),(8,2,3),(9,2,1),(10,2,9),(11,3,11),(12,4,12),(13,5,13),(14,6,14),(15,7,15),(16,8,22),(17,9,16),(18,10,17);
/*!40000 ALTER TABLE `council_thesis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criterion`
--

DROP TABLE IF EXISTS `criterion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `criterion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci,
  `affair_id` int DEFAULT NULL,
  `faculty_id` int DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `affair_id` (`affair_id`),
  KEY `criterion_ibfk_2` (`faculty_id`),
  CONSTRAINT `criterion_ibfk_1` FOREIGN KEY (`affair_id`) REFERENCES `affair` (`id`),
  CONSTRAINT `criterion_ibfk_2` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criterion`
--

LOCK TABLES `criterion` WRITE;
/*!40000 ALTER TABLE `criterion` DISABLE KEYS */;
INSERT INTO `criterion` VALUES (1,'test 102','test create criterion',1,2,'2024-06-15 08:14:28','2024-06-16 17:49:39',1),(2,'test 2','test create criterion',1,2,'2024-06-15 08:35:02','2024-06-15 08:35:02',1),(3,'test 3','test create criterion',1,2,'2024-06-15 08:35:08','2024-06-15 08:35:08',1),(4,'test 4','test create criterion',1,2,'2024-06-15 08:35:15','2024-06-15 08:35:15',1),(5,'test ui','đây là mô tả của việc tạo tiêu chí bằng khoá luận',1,2,'2024-06-15 09:59:46','2024-06-15 09:59:46',1);
/*!40000 ALTER TABLE `criterion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (1,'Đào tạo Sau Đại học','2024-05-28 06:51:59','2024-05-28 15:25:50',1),(2,'Công Nghệ Thông Tin','2024-05-28 06:56:26','2024-05-28 15:25:55',1),(3,'Kinh Tế và Quản lý Công','2024-05-28 06:56:43','2024-05-28 15:26:01',1),(4,'Tài Chính - Ngân Hàng','2024-05-28 06:57:00','2024-05-28 15:26:07',1),(5,'Khoa học cơ bản','2024-05-28 06:57:11','2024-05-28 15:26:13',1),(6,'Đào Tạo Đặc Biệt','2024-05-28 06:57:33','2024-05-28 15:26:20',1),(7,'Kế Toán Kiểm Toán','2024-05-28 06:57:58','2024-05-28 15:26:25',1),(8,'Ngoại Ngữ','2024-05-28 06:58:18','2024-05-28 15:26:30',1),(9,'Xây Dựng','2024-05-28 06:58:30','2024-05-28 15:26:36',1),(10,'Công Nghệ Sinh Học','2024-05-28 06:58:47','2024-05-28 15:26:41',1),(11,'Luật','2024-05-28 06:58:56','2024-05-28 15:26:52',1),(12,'Quản Trị Kinh Doanh','2024-05-28 06:59:10','2024-05-28 15:26:57',1),(13,'XHH - CTXH - ĐNA','2024-05-28 07:00:07','2024-05-28 15:27:03',1);
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturer`
--

DROP TABLE IF EXISTS `lecturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecturer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `gender` enum('male','female','other') CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `dob` date DEFAULT NULL,
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci,
  `degree` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  `faculty_id` int DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_id` (`account_id`),
  KEY `faculty_id` (`faculty_id`),
  CONSTRAINT `lecturer_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `lecturer_ibfk_2` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturer`
--

LOCK TABLES `lecturer` WRITE;
/*!40000 ALTER TABLE `lecturer` DISABLE KEYS */;
INSERT INTO `lecturer` VALUES (1,'Đỗ','Vương','male','phamdominhvuong@icloud.com','1998-01-01','Long An','Thạc sỹ',6,2,'2024-05-29 09:28:22','2024-05-29 09:29:12',1),(2,'Giảng viên','1','male','phamdominhvuong07112003@gmail.com','1990-01-01','demo','demo',8,2,'2024-06-23 16:44:56','2024-06-23 16:44:56',1);
/*!40000 ALTER TABLE `lecturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major`
--

DROP TABLE IF EXISTS `major`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `major` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `faculty_id` int DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `faculty_id` (`faculty_id`),
  CONSTRAINT `major_ibfk_1` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major`
--

LOCK TABLES `major` WRITE;
/*!40000 ALTER TABLE `major` DISABLE KEYS */;
INSERT INTO `major` VALUES (1,'Khoa học máy tính',2,'2024-05-28 09:06:41','2024-05-28 13:59:33',1),(2,'Công nghệ thông tin',2,'2024-05-28 09:10:16','2024-05-28 09:10:16',1),(3,'Ngôn ngữ Anh',8,'2024-05-28 09:13:15','2024-05-28 09:13:15',1),(4,'Ngôn ngữ Trung',8,'2024-05-28 13:56:45','2024-05-28 13:56:45',1),(5,'Chương trình trong nước',1,'2024-05-28 15:01:21','2024-05-28 15:01:21',1),(6,'Chương trình liên kết với đại học nước ngoài',1,'2024-05-28 15:01:40','2024-05-28 15:01:40',1),(7,'Hệ thống thông tin quản lý',2,'2024-05-28 15:02:36','2024-05-28 15:02:36',1),(8,'Khoa học máy tính CLC',2,'2024-05-28 15:03:13','2024-05-28 15:03:13',1),(9,'Tài chính doanh nghiệp',4,'2024-05-28 15:07:18','2024-05-28 15:07:18',1),(10,'Đầu tư tài chính',4,'2024-05-28 15:07:31','2024-05-28 15:07:31',1),(11,'Ngân hàng',4,'2024-05-28 15:07:45','2024-05-28 15:07:45',1),(12,'Kế toán',6,'2024-05-28 15:11:40','2024-05-28 15:11:40',1),(13,'Luật Kinh tế',6,'2024-05-28 15:11:58','2024-05-28 15:11:58',1),(14,'CNKT Công trình xây dựng',6,'2024-05-28 15:12:18','2024-05-28 15:12:18',1),(15,'Quản trị kinh doanh',6,'2024-05-28 15:12:31','2024-05-28 15:12:31',1),(16,'Tài chính ngân hàng',6,'2024-05-28 15:12:53','2024-05-28 15:12:53',1),(17,'Ngôn ngữ Anh',6,'2024-05-28 15:13:06','2024-05-28 15:13:06',1),(18,'Công nghệ sinh học',6,'2024-05-28 15:13:29','2024-05-28 15:13:29',1),(19,'Ngôn ngữ Nhật',6,'2024-05-28 15:13:43','2024-05-28 15:13:43',1),(20,'Ngôn ngữ Trung Quốc',6,'2024-05-28 15:14:04','2024-05-28 15:14:04',1),(21,'Khoa học máy tính',6,'2024-05-28 15:14:16','2024-05-28 15:14:16',1),(22,'Kinh tế',6,'2024-05-28 15:14:26','2024-05-28 15:14:26',1),(23,'Kế toán',7,'2024-05-28 15:16:09','2024-05-28 15:16:09',1),(24,'Kiểm toán',7,'2024-05-28 15:16:23','2024-05-28 15:16:23',1),(25,'Khoa học dữ liệu',5,'2024-05-28 15:16:59','2024-05-28 15:16:59',1),(26,'Kinh tế',3,'2024-05-28 15:17:27','2024-05-28 15:17:27',1),(27,'Quản lý Công',3,'2024-05-28 15:17:47','2024-05-28 15:17:47',1),(28,'Ngôn ngữ Hàn',8,'2024-05-28 15:18:28','2024-05-28 15:18:28',1),(29,'Ngôn ngữ Nhật',8,'2024-05-28 15:18:43','2024-05-28 15:18:43',1),(30,'Quản lý Xây dựng',9,'2024-05-28 15:19:26','2024-05-28 15:19:26',1),(31,'CNKT Công trình Xây dựng',9,'2024-05-28 15:19:47','2024-05-28 15:19:47',1),(32,'Công nghệ Sinh học',10,'2024-05-28 15:20:28','2024-05-28 15:20:28',1),(33,'Công nghệ thực phẩm',10,'2024-05-28 15:20:45','2024-05-28 15:20:45',1),(34,'Luật',11,'2024-05-28 15:21:06','2024-05-28 15:21:06',1),(35,'Luật Kinh tế',11,'2024-05-28 15:21:19','2024-05-28 15:21:19',1),(36,'Du lịch',12,'2024-05-28 15:21:39','2024-05-28 15:21:39',1),(37,'Marketing',12,'2024-05-28 15:21:59','2024-05-28 15:21:59',1),(38,'Quản trị nhân lực',12,'2024-05-28 15:22:38','2024-05-28 15:22:38',1),(39,'Kinh doanh quốc tế',12,'2024-05-28 15:23:17','2024-05-28 15:23:17',1),(40,'Quản trị kinh doanh',12,'2024-05-28 15:23:37','2024-05-28 15:23:37',1),(41,'Logistics và Quản lý chuỗi cung ứng',12,'2024-05-28 15:24:08','2024-05-28 15:24:08',1),(42,'Xã hội học',13,'2024-05-28 15:24:37','2024-05-28 15:24:37',1),(43,'Tâm lý học',13,'2024-05-28 15:24:49','2024-05-28 15:24:49',1),(44,'Đông Nam Á học',13,'2024-05-28 15:25:03','2024-05-28 15:25:03',1),(45,'Công tác xã hội',13,'2024-05-28 15:25:15','2024-05-28 15:25:15',1);
/*!40000 ALTER TABLE `major` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school_year`
--

DROP TABLE IF EXISTS `school_year`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `school_year` (
  `id` int NOT NULL AUTO_INCREMENT,
  `start_year` year NOT NULL,
  `end_year` year NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school_year`
--

LOCK TABLES `school_year` WRITE;
/*!40000 ALTER TABLE `school_year` DISABLE KEYS */;
INSERT INTO `school_year` VALUES (1,2020,2021,'2024-05-28 06:43:17','2024-05-28 06:45:49',1),(2,2021,2022,'2024-05-28 06:45:58','2024-05-28 06:45:58',1),(3,2022,2023,'2024-05-28 06:46:08','2024-05-28 06:46:08',1),(4,2023,2024,'2024-05-28 06:46:16','2024-05-28 06:46:16',1);
/*!40000 ALTER TABLE `school_year` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `score` (
  `id` int NOT NULL AUTO_INCREMENT,
  `score` float DEFAULT NULL,
  `council_id` int DEFAULT NULL,
  `lecturer_id` int DEFAULT NULL,
  `thesis_id` int DEFAULT NULL,
  `criterion_id` int DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `council_id` (`council_id`),
  KEY `lecturer_id` (`lecturer_id`),
  KEY `thesis_id` (`thesis_id`),
  KEY `criterion_id` (`criterion_id`),
  CONSTRAINT `score_ibfk_1` FOREIGN KEY (`council_id`) REFERENCES `council` (`id`),
  CONSTRAINT `score_ibfk_2` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`id`),
  CONSTRAINT `score_ibfk_3` FOREIGN KEY (`thesis_id`) REFERENCES `thesis` (`id`),
  CONSTRAINT `score_ibfk_4` FOREIGN KEY (`criterion_id`) REFERENCES `criterion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
INSERT INTO `score` VALUES (1,5,2,1,2,2,'2024-06-21 02:01:24','2024-06-21 02:01:24',1),(2,5,2,1,2,1,'2024-06-21 02:01:24','2024-06-21 02:01:24',1),(3,5,2,1,9,1,'2024-06-21 02:08:12','2024-06-21 02:08:12',1),(4,5,2,1,9,2,'2024-06-21 02:08:12','2024-06-21 02:08:12',1),(5,10,4,1,12,1,'2024-06-21 02:33:45','2024-06-23 02:37:50',1),(6,4,2,1,10,1,'2024-06-21 03:40:08','2024-06-21 04:00:32',1),(7,4,2,1,10,2,'2024-06-21 03:40:08','2024-06-21 04:00:32',1),(8,10,2,1,3,1,'2024-06-21 03:40:20','2024-06-21 03:40:20',1),(9,9,2,1,3,2,'2024-06-21 03:40:20','2024-06-21 03:40:20',1),(10,7,2,1,1,2,'2024-06-21 03:40:32','2024-06-21 03:40:32',1),(11,7,2,1,1,1,'2024-06-21 03:40:32','2024-06-21 03:40:32',1),(12,10,3,1,11,1,'2024-06-22 14:50:03','2024-06-23 02:42:15',1);
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `gender` enum('male','female','other') CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `dob` date DEFAULT NULL,
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci,
  `account_id` int DEFAULT NULL,
  `faculty_id` int DEFAULT NULL,
  `major_id` int DEFAULT NULL,
  `class_id` int DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_id` (`account_id`),
  KEY `faculty_id` (`faculty_id`),
  KEY `major_id` (`major_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`),
  CONSTRAINT `student_ibfk_3` FOREIGN KEY (`major_id`) REFERENCES `major` (`id`),
  CONSTRAINT `student_ibfk_4` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'Đỗ Mạnh','Dũng','male','vuong.pdm@s1qq.onmicrosoft.com','2001-12-20','Đồng Nai',7,2,1,1,'2024-05-29 10:55:18','2024-06-22 09:28:02',1);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thesis`
--

DROP TABLE IF EXISTS `thesis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thesis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `report_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  `avg_score` float DEFAULT NULL,
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci,
  `status` enum('in_progress','submitted','under_review','defended','canceled') CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT 'in_progress',
  `affair_id` int DEFAULT NULL,
  `critical_lecturer_id` int DEFAULT NULL,
  `school_year_id` int DEFAULT NULL,
  `faculty_id` int DEFAULT NULL,
  `major_id` int DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `affair_id` (`affair_id`),
  KEY `critical_lecturer_id` (`critical_lecturer_id`),
  KEY `school_year_id` (`school_year_id`),
  KEY `thesis_ibfk_4` (`faculty_id`),
  KEY `thesis_ibfk_5` (`major_id`),
  CONSTRAINT `thesis_ibfk_1` FOREIGN KEY (`affair_id`) REFERENCES `affair` (`id`),
  CONSTRAINT `thesis_ibfk_2` FOREIGN KEY (`critical_lecturer_id`) REFERENCES `lecturer` (`id`),
  CONSTRAINT `thesis_ibfk_3` FOREIGN KEY (`school_year_id`) REFERENCES `school_year` (`id`),
  CONSTRAINT `thesis_ibfk_4` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`),
  CONSTRAINT `thesis_ibfk_5` FOREIGN KEY (`major_id`) REFERENCES `major` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thesis`
--

LOCK TABLES `thesis` WRITE;
/*!40000 ALTER TABLE `thesis` DISABLE KEYS */;
INSERT INTO `thesis` VALUES (1,'Tên luận văn 2','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1718218383/1718218380163_E_Prac.zip','2024-06-01','2024-12-31','2025-01-15',7,'Ghi chú về luận văn','defended',1,1,3,2,1,'2024-06-06 03:30:32','2024-06-21 07:01:40',1),(2,'Tên luận văn 3','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1718218431/1718218428649_E_Prac.zip','2024-06-01','2024-12-31','2025-01-15',5,'Ghi chú về luận văn','defended',1,1,3,2,1,'2024-06-06 06:19:30','2024-06-21 07:01:40',1),(3,'Tên luận văn 4','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1718864083/1718864080195_E_Prac.zip','2024-06-01','2024-12-31','2025-01-15',9.5,'Ghi chú về luận văn','defended',1,1,3,2,1,'2024-06-06 06:28:00','2024-06-21 07:01:40',1),(9,'Tên luận văn 5','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1718864668/1718864665166_E_Prac.zip','2024-07-01','2024-12-31','2025-01-15',5,'Ghi chú về luận văn','defended',1,1,3,2,1,'2024-06-13 06:51:15','2024-06-21 07:01:40',1),(10,'Tên luận văn 6','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1718867405/1718867401449_E_Prac.zip','2024-07-01','2024-12-31','2025-01-15',4,'Ghi chú về luận văn','defended',1,1,3,2,1,'2024-06-13 07:02:57','2024-06-21 07:01:40',1),(11,'Tên luận văn 7','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1718906476/1718906471291_E_Prac.zip','2024-07-01','2024-12-31','2025-01-15',10,'Ghi chú về luận văn','defended',1,1,3,2,1,'2024-06-13 07:04:40','2024-06-23 02:42:53',1),(12,'Tên luận văn 8','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1718907905/1718907901888_E_Prac.zip','2024-07-01','2024-12-31','2025-01-15',NULL,'Ghi chú về luận văn','under_review',1,1,3,2,1,'2024-06-13 07:12:40','2024-06-20 18:25:41',1),(13,'Tên luận văn 9','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1719068003/1719067998699_E_Prac.zip','2024-07-01','2024-12-31','2025-01-15',NULL,'Ghi chú về luận văn','under_review',1,1,3,2,1,'2024-06-13 07:20:27','2024-06-22 14:54:34',1),(14,'Tên luận văn 10','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1719068566/1719068561844_E_Prac.zip','2024-07-01','2024-12-31','2025-01-15',NULL,'Ghi chú về luận văn','under_review',1,1,3,2,1,'2024-06-13 07:23:19','2024-06-22 15:03:52',1),(15,'Tên luận văn 11','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1719068733/1719068728985_E_Prac.zip','2024-07-01','2024-12-31','2025-01-15',NULL,'Ghi chú về luận văn','under_review',1,1,3,2,1,'2024-06-13 08:03:11','2024-06-22 15:06:24',1),(16,'test ui','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1719069405/1719069401755_E_Prac.zip','2024-06-15','2025-03-15','2025-03-22',NULL,'test','under_review',1,1,4,2,1,'2024-06-14 17:57:05','2024-06-22 15:17:35',1),(17,'test major','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1719069921/1719069918157_E_Prac.zip','2024-06-20','2024-12-20','2024-12-29',NULL,'test thêm thuộc tính major, faculty','under_review',1,1,4,2,1,'2024-06-20 04:12:14','2024-06-22 15:25:57',1),(18,'test major',NULL,'2024-06-20','2024-12-20','2024-12-29',NULL,'test thêm thuộc tính major, faculty','in_progress',1,1,4,2,1,'2024-06-20 04:15:21','2024-06-22 15:24:48',1),(19,'test major',NULL,'2024-06-20','2024-12-20','2024-12-29',NULL,'test thêm thuộc tính major, faculty','in_progress',1,1,4,2,1,'2024-06-20 04:16:38','2024-06-22 15:24:48',1),(20,'test major',NULL,'2024-06-20','2024-12-20','2024-12-29',NULL,'test thêm thuộc tính major, faculty','in_progress',1,1,4,2,1,'2024-06-20 04:18:22','2024-06-22 15:24:48',1),(21,'test major',NULL,'2024-06-20','0024-12-20','2024-12-29',NULL,'test thêm thuộc tính major, faculty','in_progress',1,1,4,2,1,'2024-06-20 04:27:12','2024-06-20 04:27:12',1),(22,'mail','https://res.cloudinary.com/dyuafq1hx/raw/upload/v1719069237/1719069234371_E_Prac.zip','2024-06-23','2024-06-30','2024-07-31',NULL,'test mail','under_review',1,1,4,2,1,'2024-06-22 15:08:53','2024-06-22 15:14:57',1),(23,'filter',NULL,'2024-06-30','2024-06-30','2024-06-19',NULL,'','in_progress',1,1,1,2,1,'2024-06-22 15:55:08','2024-06-22 15:55:08',1);
/*!40000 ALTER TABLE `thesis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thesis_lecturer`
--

DROP TABLE IF EXISTS `thesis_lecturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thesis_lecturer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `thesis_id` int DEFAULT NULL,
  `lecturer_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `thesis_id` (`thesis_id`),
  KEY `lecturer_id` (`lecturer_id`),
  CONSTRAINT `thesis_lecturer_ibfk_1` FOREIGN KEY (`thesis_id`) REFERENCES `thesis` (`id`),
  CONSTRAINT `thesis_lecturer_ibfk_2` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thesis_lecturer`
--

LOCK TABLES `thesis_lecturer` WRITE;
/*!40000 ALTER TABLE `thesis_lecturer` DISABLE KEYS */;
INSERT INTO `thesis_lecturer` VALUES (1,1,1),(2,2,1),(3,3,1),(9,9,1),(10,10,1),(11,11,1),(12,12,1),(13,13,1),(14,14,1),(15,15,1),(16,16,1),(17,17,1),(18,18,1),(19,19,1),(20,20,1),(21,21,1),(22,22,1),(23,23,1);
/*!40000 ALTER TABLE `thesis_lecturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thesis_student`
--

DROP TABLE IF EXISTS `thesis_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thesis_student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `thesis_id` int DEFAULT NULL,
  `student_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `thesis_id` (`thesis_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `thesis_student_ibfk_1` FOREIGN KEY (`thesis_id`) REFERENCES `thesis` (`id`),
  CONSTRAINT `thesis_student_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thesis_student`
--

LOCK TABLES `thesis_student` WRITE;
/*!40000 ALTER TABLE `thesis_student` DISABLE KEYS */;
INSERT INTO `thesis_student` VALUES (1,1,1),(2,2,1),(3,3,1),(7,9,1),(8,10,1),(9,11,1),(10,12,1),(11,13,1),(12,14,1),(13,15,1),(14,16,1),(15,22,1),(16,17,1),(17,18,1),(18,19,1),(19,20,1),(20,21,1),(21,23,1);
/*!40000 ALTER TABLE `thesis_student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-23 23:55:14
