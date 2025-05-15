CREATE DATABASE  IF NOT EXISTS `qlsinhvien` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `qlsinhvien`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: qlsinhvien
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Table structure for table `mon`
--

DROP TABLE IF EXISTS `mon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tenmon` varchar(100) NOT NULL,
  `sotinchi` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenmon` (`tenmon`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mon`
--

LOCK TABLES `mon` WRITE;
/*!40000 ALTER TABLE `mon` DISABLE KEYS */;
INSERT INTO `mon` VALUES (1,'Toán rời rạc',3),(2,'Tiếng anh',4),(3,'Lập trình Java',3),(4,'Kế toán tài chính',3),(6,'Lập trình web',3),(8,'Kế toán chuyên ngành',4),(9,'Tài chính cao cấp',4),(10,'Lập trình hướng đối tượng',4),(11,'Công nghệ phần mềm',2),(12,'Phân tích báo cáo tài chính',3),(13,'Kinh tế lượng',2),(14,'Luật kế toán',3),(15,'Kế toán công ty chứng khoán',4),(16,'Toán cao cấp',4);
/*!40000 ALTER TABLE `mon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nguoidung`
--

DROP TABLE IF EXISTS `nguoidung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nguoidung` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tendn` varchar(50) NOT NULL,
  `matkhau` varchar(50) NOT NULL,
  `role` enum('admin','sinhvien') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tendn` (`tendn`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguoidung`
--

LOCK TABLES `nguoidung` WRITE;
/*!40000 ALTER TABLE `nguoidung` DISABLE KEYS */;
INSERT INTO `nguoidung` VALUES (1,'admin','12345','admin'),(5,'hoa123','hoa123','sinhvien'),(8,'long2410','long2410','sinhvien');
/*!40000 ALTER TABLE `nguoidung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sinhvien`
--

DROP TABLE IF EXISTS `sinhvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sinhvien` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nguoidung_id` int DEFAULT NULL,
  `ten` varchar(100) NOT NULL,
  `ngaysinh` date DEFAULT NULL,
  `gioitinh` enum('Nam','Nữ','Khác') DEFAULT NULL,
  `quequan` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `sodienthoai` varchar(15) DEFAULT NULL,
  `chuyennganh` varchar(100) DEFAULT NULL,
  `khoa` varchar(100) DEFAULT NULL,
  `lop` varchar(50) DEFAULT NULL,
  `trangthai` enum('Đang học','Đã tốt nghiệp') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `nguoidung_id_UNIQUE` (`nguoidung_id`),
  CONSTRAINT `sinhvien_ibfk_1` FOREIGN KEY (`nguoidung_id`) REFERENCES `nguoidung` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sinhvien`
--

LOCK TABLES `sinhvien` WRITE;
/*!40000 ALTER TABLE `sinhvien` DISABLE KEYS */;
INSERT INTO `sinhvien` VALUES (1,8,'Lê Hoàng Long','2003-10-24','Nam','Thanh Hóa','long@gmail.com','0382065338','IT','công nghệ thông tin','DHTI15A7HN','Đang học'),(2,5,'Lê Thị Hoa','2003-12-18','Nữ','Hà Nội','hoa1812@gmail.com','0362736273','Kế Toán','Kế Toán','DHKT15A2HN','Đang học');
/*!40000 ALTER TABLE `sinhvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sinhvien_mon`
--

DROP TABLE IF EXISTS `sinhvien_mon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sinhvien_mon` (
  `sinhvien_id` int NOT NULL,
  `mon_id` int NOT NULL,
  `diem` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`sinhvien_id`,`mon_id`),
  KEY `mon_id` (`mon_id`),
  CONSTRAINT `sinhvien_mon_ibfk_1` FOREIGN KEY (`sinhvien_id`) REFERENCES `sinhvien` (`id`),
  CONSTRAINT `sinhvien_mon_ibfk_2` FOREIGN KEY (`mon_id`) REFERENCES `mon` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sinhvien_mon`
--

LOCK TABLES `sinhvien_mon` WRITE;
/*!40000 ALTER TABLE `sinhvien_mon` DISABLE KEYS */;
INSERT INTO `sinhvien_mon` VALUES (1,1,8.00),(1,2,9.00),(1,3,8.00),(1,6,7.00),(1,8,10.00),(1,10,10.00),(1,11,9.00),(1,16,9.00),(2,4,9.00),(2,9,8.80),(2,12,7.60),(2,13,6.00),(2,14,8.00);
/*!40000 ALTER TABLE `sinhvien_mon` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-15 19:47:45
