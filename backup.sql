-- MySQL dump 10.13  Distrib 8.4.9, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: THOU_Laiheng-db
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `end_date` date NOT NULL,
  `renter_id` bigint NOT NULL,
  `start_date` date NOT NULL,
  `status` enum('pending','approved','rejected','cancelled','completed') NOT NULL,
  `terrain_id` bigint NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (1,'2026-06-15 07:23:43.730287','2026-06-18',3,'2026-06-16','pending',1,450.00,'2026-06-15 07:23:43.730306'),(2,'2026-06-15 07:23:43.785449','2026-06-21',3,'2026-06-20','approved',2,240.00,'2026-06-15 07:23:43.785461');
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorites` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `terrain_id` bigint NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKtkwsfcto4f50snyjodt15y218` (`user_id`,`terrain_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorites`
--

LOCK TABLES `favorites` WRITE;
/*!40000 ALTER TABLE `favorites` DISABLE KEYS */;
INSERT INTO `favorites` VALUES (1,'2026-06-15 07:23:43.954020',1,'2026-06-15 07:23:43.954081',3);
/*!40000 ALTER TABLE `favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount_paid` decimal(10,2) NOT NULL,
  `booking_id` bigint NOT NULL,
  `payment_date` datetime(6) NOT NULL,
  `payment_method` varchar(255) NOT NULL,
  `status` enum('paid','failed','refunded') NOT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,240.00,2,'2026-06-15 07:23:43.821014','ABA Pay','paid','TXN123456');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` text,
  `created_at` datetime(6) NOT NULL,
  `rating` int NOT NULL,
  `terrain_id` bigint NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,'Excellent field!','2026-06-15 07:23:43.872242',5,1,'2026-06-15 07:23:43.872268',3),(2,'Good field, well maintained','2026-06-15 07:23:43.906287',4,2,'2026-06-15 07:23:43.906334',3);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terrain_images`
--

DROP TABLE IF EXISTS `terrain_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `terrain_images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_path` varchar(255) NOT NULL,
  `terrain_id` bigint NOT NULL,
  `uploaded_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terrain_images`
--

LOCK TABLES `terrain_images` WRITE;
/*!40000 ALTER TABLE `terrain_images` DISABLE KEYS */;
INSERT INTO `terrain_images` VALUES (1,'/images/olympic1.jpg',1,'2026-06-15 07:23:43.633596'),(2,'/images/olympic2.jpg',1,'2026-06-15 07:23:43.674887'),(3,'/images/sunset1.jpg',2,'2026-06-15 07:23:43.705225');
/*!40000 ALTER TABLE `terrain_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terrains`
--

DROP TABLE IF EXISTS `terrains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `terrains` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `area_size` decimal(10,2) NOT NULL,
  `available_from` datetime(6) DEFAULT NULL,
  `available_to` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` text,
  `is_available` bit(1) NOT NULL,
  `location` varchar(255) NOT NULL,
  `main_image_id` bigint DEFAULT NULL,
  `owner_id` bigint NOT NULL,
  `price_per_day` decimal(10,2) NOT NULL,
  `title` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terrains`
--

LOCK TABLES `terrains` WRITE;
/*!40000 ALTER TABLE `terrains` DISABLE KEYS */;
INSERT INTO `terrains` VALUES (1,5000.00,'2026-06-15 07:23:43.426893','2026-12-15 07:23:43.426919','2026-06-15 07:23:43.426956','Professional football field with grass',_binary '','Phnom Penh',NULL,1,150.00,'Olympic Field','2026-06-15 07:23:43.426965'),(2,4000.00,'2026-06-15 07:23:43.569006','2026-09-15 07:23:43.569033','2026-06-15 07:23:43.569094','Indoor football field',_binary '','Siem Reap',NULL,1,120.00,'Sunset Stadium','2026-06-15 07:23:43.569101'),(3,6000.00,NULL,NULL,'2026-06-15 07:23:43.601549','Community football field',_binary '','Battambang',NULL,2,100.00,'Victory Ground','2026-06-15 07:23:43.601575');
/*!40000 ALTER TABLE `terrains` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-15 17:35:50
