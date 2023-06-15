CREATE DATABASE NoticeBoard;
USE NoticeBoard;

CREATE TABLE `t_board` (
  `board_id` int NOT NULL AUTO_INCREMENT,
  `board_title` varchar(50) NOT NULL,
  `board_content` varchar(3000) DEFAULT NULL,
  `view_number` int NOT NULL,
  `register_user_id` varchar(18) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL,
  `update_user_id` varchar(45) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `delete_flag` varchar(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `t_comment` (
  `comment_no` int NOT NULL AUTO_INCREMENT,
  `board_id` int NOT NULL,
  `comment_content` varchar(45) DEFAULT NULL,
  `register_user_id` varchar(45) DEFAULT NULL,
  `register_date` timestamp NULL DEFAULT NULL,
  `update_user_id` timestamp NULL DEFAULT NULL,
  `update_date` timestamp NULL DEFAULT NULL,
  `delete_flag` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`comment_no`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `t_user` (
  `user_id` varchar(18) NOT NULL,
  `user_pass` varchar(16) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `user_birthday` datetime NOT NULL,
  `user_gender` varchar(2) NOT NULL,
  `user_mail` varchar(50) NOT NULL,
  `join_reason` varchar(2) NOT NULL,
  `register_user_id` varchar(18) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL,
  `update_user_id` varchar(18) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `delete_flg` varchar(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;