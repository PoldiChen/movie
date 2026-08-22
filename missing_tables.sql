-- 补充缺失的表结构（SQL 脚本 movie_db_struct_20201227.sql 中没有，但代码 Mapper 需要）

USE `movie`;

-- 1. proxy_address 表
DROP TABLE IF EXISTS `proxy_address`;
CREATE TABLE `proxy_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(255) DEFAULT NULL,
  `locate` varchar(255) DEFAULT NULL,
  `protocol` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `speed` varchar(255) DEFAULT NULL,
  `connect_time` varchar(255) DEFAULT NULL,
  `available_time` varchar(255) DEFAULT NULL,
  `verify_time` varchar(255) DEFAULT NULL,
  `available` varchar(255) DEFAULT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2. resource 表
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(500) NOT NULL,
  `show_name` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `share_date` varchar(255) DEFAULT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 3. job 表
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(255) NOT NULL,
  `class_name` varchar(500) NOT NULL,
  `arguments` varchar(2000) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 4. movie_director 表（参照 movie_actor）
DROP TABLE IF EXISTS `movie_director`;
CREATE TABLE `movie_director` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `movie_id` int(11) NOT NULL,
  `celebrity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `movie_director` (`movie_id`,`celebrity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 5. movie_writer 表（参照 movie_actor）
DROP TABLE IF EXISTS `movie_writer`;
CREATE TABLE `movie_writer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `movie_id` int(11) NOT NULL,
  `celebrity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `movie_writer` (`movie_id`,`celebrity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 6. movie_resource 表（参照 movie_actor）
DROP TABLE IF EXISTS `movie_resource`;
CREATE TABLE `movie_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `movie_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `movie_resource` (`movie_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 验证所有表已创建
SELECT table_name, table_rows FROM information_schema.tables WHERE table_schema = 'movie' ORDER BY table_name;
