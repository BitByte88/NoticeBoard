DROP DATABASE NoticeBoard;
CREATE DATABASE NoticeBoard;
USE NoticeBoard;

DROP TABLE IF EXISTS t_board;
CREATE TABLE `t_board` (
  `board_id` int NOT NULL AUTO_INCREMENT,
  `board_title` varchar(50) NOT NULL,
  `board_content` varchar(3000) DEFAULT NULL,
  `view_number` int NOT NULL,
  `register_user_id` varchar(18) DEFAULT NULL,
  `register_date` timestamp DEFAULT NULL,
  `update_user_id` varchar(45) DEFAULT NULL,
  `update_date` timestamp DEFAULT NULL,
  `delete_flag` varchar(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `t_board` VALUES (1,'テスト','内容',4,'testid11','2023-06-09 19:02:46',NULL,NULL,'0'),(2,'テスト２','あああああああああああああああああああああ',0,'testid11','2023-06-09 19:03:17',NULL,NULL,'0'),(3,'あああ','ああああ',1,'testid11','2023-06-09 19:03:26',NULL,NULL,'0'),(4,'あああ','ああああ',0,'testid11','2023-06-09 19:03:34',NULL,NULL,'0'),(5,'ああああああ','あああああ',0,'testid11','2023-06-09 19:03:44',NULL,NULL,'0'),(6,'あああ','ああああ',1,'testid11','2023-06-09 19:03:53',NULL,NULL,'0'),(7,'aaaaaa','aaa',6,'testid11','2023-06-13 10:13:19',NULL,NULL,'0');

DROP TABLE IF EXISTS t_comment;
CREATE TABLE `t_comment` (
  `comment_no` int NOT NULL AUTO_INCREMENT,
  `board_id` int NOT NULL,
  `comment_content` varchar(100) DEFAULT NULL,
  `register_user_id` varchar(45) DEFAULT NULL,
  `register_date` timestamp NULL DEFAULT NULL,
  `update_user_id` varchar(45) DEFAULT NULL,
  `update_date` timestamp NULL DEFAULT NULL,
  `delete_flag` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`comment_no`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `t_comment` VALUES (1,13,'金金金金金','123','2023-06-08 15:00:00',NULL,NULL,'0'),(2,13,'テスト','456','2023-06-09 15:00:00',NULL,NULL,'0'),(3,13,'テストコメント','789','2023-06-12 15:00:00',NULL,NULL,'0'),(4,13,'dd','testuser','2023-06-12 15:00:00',NULL,NULL,'0'),(5,13,'aaaaa','testuser','2023-06-13 00:57:16',NULL,NULL,'0'),(6,13,'aaaa','testuser','2023-06-13 00:59:22',NULL,NULL,'0'),(7,13,'test','testuser','2023-06-13 01:03:57',NULL,NULL,'0'),(8,13,'sssss','testuser','2023-06-13 01:06:10',NULL,NULL,'0'),(9,13,'ddddd','testuser','2023-06-13 05:55:29',NULL,NULL,'0'),(10,5,'テストコメント','testuser','2023-06-13 08:05:28',NULL,NULL,'0'),(11,12,'あああ！','testuser','2023-06-13 08:05:54',NULL,NULL,'0'),(12,13,'ｄｄｄｄｄ','testuser','2023-06-13 08:09:20',NULL,NULL,'0'),(13,14,'dd','testuser','2023-06-13 08:13:21',NULL,NULL,'0'),(14,13,'aa','testuser','2023-06-13 08:14:30',NULL,NULL,'0'),(15,14,'できる？','testuser','2023-06-13 08:36:42',NULL,NULL,'0'),(16,14,'aaaa','testuser','2023-06-13 08:38:57',NULL,NULL,'0'),(17,14,'d','testuser','2023-06-13 08:40:18',NULL,NULL,'0'),(18,13,'dd','testuser','2023-06-13 08:43:27',NULL,NULL,'0'),(19,2,'test','testuser','2023-06-14 00:05:32',NULL,NULL,'0'),(20,16,'dddddd','testuser','2023-06-14 08:55:24',NULL,NULL,'0'),(21,16,'ddddddd','testuser','2023-06-14 08:55:27',NULL,NULL,'0'),(22,16,'ddddddd','testuser','2023-06-14 08:55:30',NULL,NULL,'0'),(23,16,'dddddddd','testuser','2023-06-14 08:55:34',NULL,NULL,'0'),(24,16,'ddddddd','testuser','2023-06-14 08:55:37',NULL,NULL,'0'),(25,16,'d','testuser','2023-06-14 23:48:52',NULL,NULL,'0'),(26,16,'d','testuser','2023-06-15 02:18:28',NULL,NULL,'0'),(27,16,'d','testuser','2023-06-15 02:19:09',NULL,NULL,'0'),(28,16,'d','testuser','2023-06-15 02:23:23',NULL,NULL,'0'),(29,16,'d','testuser','2023-06-15 02:24:29',NULL,NULL,'0'),(30,16,'test','testuser','2023-06-15 02:30:42',NULL,NULL,'0'),(31,13,'te','testuser','2023-06-15 02:33:44',NULL,NULL,'0');

DROP TABLE IF EXISTS t_user;
CREATE TABLE `t_user` (
  `user_id` varchar(18) NOT NULL,
  `user_pass` varchar(100) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `user_birthday` varchar(8) NOT NULL,
  `user_gender` varchar(2) NOT NULL,
  `user_mail` varchar(50) NOT NULL,
  `join_reason` varchar(2) NOT NULL,
  `register_user_id` varchar(18) DEFAULT NULL,
  `register_date` timestamp DEFAULT NULL,
  `update_user_id` varchar(18) DEFAULT NULL,
  `update_date` timestamp DEFAULT NULL,
  `delete_flg` varchar(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `t_user` VALUES ('testid','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','ipsg','20000101','01','test@test.com','01','SYSTEM','2023-06-09 19:00:14',NULL,NULL,'0');
