-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: greenpay
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `agentpay_batch`
--

DROP TABLE IF EXISTS `agentpay_batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agentpay_batch` (
  `id` int NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(32) NOT NULL COMMENT '交易批次号',
  `out_batch_no` varchar(32) NOT NULL COMMENT '商户交易批次号',
  `mch_id` int NOT NULL COMMENT '商户ID',
  `total_amount` int NOT NULL DEFAULT '0' COMMENT '总金额（单位：分）',
  `total_count` int NOT NULL DEFAULT '0' COMMENT '总笔数',
  `notify_url` varchar(255) DEFAULT NULL COMMENT '订单回调地址',
  `extra` varchar(255) DEFAULT NULL COMMENT '扩展参数',
  `pay_type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `agentpay_passage_id` int NOT NULL COMMENT '代付通道ID',
  `agentpay_passage_acc_id` int NOT NULL COMMENT '代付通道账户ID',
  `pay_interface_id` int NOT NULL COMMENT '支付接口ID',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '订单状态（1：待处理，2：处理中，3：处理成功，4：部分成功，-1：处理失败）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代付批次';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agentpay_batch`
--

LOCK TABLES `agentpay_batch` WRITE;
/*!40000 ALTER TABLE `agentpay_batch` DISABLE KEYS */;
/*!40000 ALTER TABLE `agentpay_batch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agentpay_order`
--

DROP TABLE IF EXISTS `agentpay_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agentpay_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) NOT NULL COMMENT '订单号',
  `order_sn` varchar(32) NOT NULL COMMENT '订单流水号',
  `mch_id` int NOT NULL COMMENT '商户ID',
  `amount` int NOT NULL DEFAULT '0' COMMENT '订单金额（单位：分）',
  `fee` int NOT NULL DEFAULT '0' COMMENT '订单手续费（单位：分）',
  `account_name` varchar(32) NOT NULL COMMENT '账户名',
  `account_number` varchar(32) NOT NULL COMMENT '账户号',
  `bank_name` varchar(32) NOT NULL COMMENT '开户行',
  `bank_number` varchar(32) NOT NULL COMMENT '联行号',
  `notify_url` varchar(255) DEFAULT NULL COMMENT '订单回调地址',
  `extra` varchar(255) DEFAULT NULL COMMENT '扩展参数',
  `pay_type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `agentpay_passage_id` int NOT NULL COMMENT '支付通道ID',
  `agentpay_passage_acc_id` int NOT NULL COMMENT '支付通道ID',
  `pay_interface_id` int NOT NULL COMMENT '支付接口ID',
  `pay_interface_attr` varchar(255) NOT NULL COMMENT '支付接口参数',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '订单状态（1：待处理，2：处理中，3：处理成功，-1：处理失败）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代付订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agentpay_order`
--

LOCK TABLES `agentpay_order` WRITE;
/*!40000 ALTER TABLE `agentpay_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `agentpay_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agentpay_passage`
--

DROP TABLE IF EXISTS `agentpay_passage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agentpay_passage` (
  `id` int NOT NULL AUTO_INCREMENT,
  `passage_name` varchar(32) NOT NULL COMMENT '代付通道名称',
  `pay_type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `interface_code` varchar(32) NOT NULL COMMENT '支付接口编码',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代付通道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agentpay_passage`
--

LOCK TABLES `agentpay_passage` WRITE;
/*!40000 ALTER TABLE `agentpay_passage` DISABLE KEYS */;
INSERT INTO `agentpay_passage` (`id`, `passage_name`, `pay_type_code`, `interface_code`, `status`, `created_at`, `updated_at`) VALUES (1,'abc','wx_pay_biz','pay_wx_qiye',1,'2020-04-27 18:12:56','2020-04-27 18:12:56');
/*!40000 ALTER TABLE `agentpay_passage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agentpay_passage_account`
--

DROP TABLE IF EXISTS `agentpay_passage_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agentpay_passage_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pay_type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `passage_id` int NOT NULL COMMENT '代付通道ID',
  `account_name` varchar(32) NOT NULL COMMENT '通道账户名称',
  `interface_attr` varchar(255) DEFAULT NULL COMMENT '通道接口参数',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代付通道账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agentpay_passage_account`
--

LOCK TABLES `agentpay_passage_account` WRITE;
/*!40000 ALTER TABLE `agentpay_passage_account` DISABLE KEYS */;
INSERT INTO `agentpay_passage_account` (`id`, `pay_type_code`, `passage_id`, `account_name`, `interface_attr`, `status`, `created_at`, `updated_at`) VALUES (1,'wx_pay_biz',1,'abc','abc',1,'2020-04-27 18:13:10','2020-04-27 18:13:10');
/*!40000 ALTER TABLE `agentpay_passage_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchant`
--

DROP TABLE IF EXISTS `merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchant` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '商户ID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `name` varchar(32) NOT NULL COMMENT '商户名称',
  `email` varchar(32) NOT NULL DEFAULT '' COMMENT '电子邮箱',
  `phone` varchar(11) NOT NULL DEFAULT '' COMMENT '联系手机',
  `password` varchar(32) NOT NULL COMMENT '商户登录密码',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '商户状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant`
--

LOCK TABLES `merchant` WRITE;
/*!40000 ALTER TABLE `merchant` DISABLE KEYS */;
INSERT INTO `merchant` (`id`, `username`, `name`, `email`, `phone`, `password`, `status`, `created_at`, `updated_at`) VALUES (2,'militch','abc@abc','litch.me@gmail.com','','123',1,'2020-04-17 00:54:21','2020-04-22 19:43:53');
INSERT INTO `merchant` (`id`, `username`, `name`, `email`, `phone`, `password`, `status`, `created_at`, `updated_at`) VALUES (3,'abc','militch','abc@abc.com','','21232f297a57a5a743894a0e4a801fc3',1,'2020-04-17 21:55:40','2020-04-17 21:55:40');
INSERT INTO `merchant` (`id`, `username`, `name`, `email`, `phone`, `password`, `status`, `created_at`, `updated_at`) VALUES (4,'uniabc','abc@abc','abc@abc','','abc',1,'2020-04-21 01:57:44','2020-04-23 14:36:52');
/*!40000 ALTER TABLE `merchant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchant_api_config`
--

DROP TABLE IF EXISTS `merchant_api_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchant_api_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mch_id` int NOT NULL COMMENT '商户ID',
  `api_key` varchar(32) NOT NULL COMMENT '商户APIKEY',
  `api_security` varchar(32) NOT NULL COMMENT '商户API_SECURITY',
  `private_key` varchar(1024) DEFAULT NULL COMMENT '平台私钥',
  `pub_key` varchar(512) DEFAULT NULL COMMENT '平台公钥',
  `mch_pub_key` varchar(512) DEFAULT NULL COMMENT '商户公钥',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户密钥';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_api_config`
--

LOCK TABLES `merchant_api_config` WRITE;
/*!40000 ALTER TABLE `merchant_api_config` DISABLE KEYS */;
INSERT INTO `merchant_api_config` (`id`, `mch_id`, `api_key`, `api_security`, `private_key`, `pub_key`, `mch_pub_key`, `created_at`, `updated_at`) VALUES (1,2,'ec3136bbf7fd326277a7f675e500265f','06590e4980b76c53f952ea7fbcd6e78c','MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIIVl7aJVGduveuwxUATC2AJRy0fE5H6tiA2hNtlZE7HDhEg73n1RnMwcBYbCk0xEuunp1Fbw/dBcMrI6GYriba7ffi37gfoCxEt3la5qxx7i4839iFq1I7Jur2PWxipVdEBeo9letpsTmLg52t4oZHA5KWZK1FJnhgfgGPLnCoPAgMBAAECgYA+NfFePIL/DDkLtHhA0lrITOebLpd/YrUi5q/W9MBp5ExX6LZeTuyoPev8xmXA0M1Jod4kzIwFfWhhsn9iDURITHjM1T4K36IdLKQimtwnp3DChMJtRwqev0g8ZIkHdQqourYscq1VxOIwR97xGsH54WFQOJlopUizs+NosmzOcQJBANiZ2NSXMZEmP5mBijwL11UAuvTXSMEyr2HvrruPtz5w2FfJwH0yHwLVjE8DtMDaVFsib1dJTVc0VGZujw4708MCQQCZvww4IbpwuqpZ0ft/UJvIfjwII+CjjmK7CG9Ae2I3qgAmW+J8tsRg7b6nVBz3fu6Qz3KBQLy3eoAzqVus+qfFAkEAuUkyAPmtPxtXAHudwqvmciKDy3p2FD7FZKPh9zSDbniduMsvTGmZuZTvx4/GCcs6qhMU57ge3uA26sDcwzJOfQJAc8SLquiavq+P/jpDKcbExt9mzKpXSFC6vyLGwsMlXczAeCHQFSB6FpJucQjBFwuZD6llCzZ346B2UHBB+6pyEQJBAJJ3NlIAUQl9JgKdwjiBWiQIdV1TGP/jDw2E9Xv7yAbrrj0NLdL9bYcv2dFnl8J0yIObNvhrWdP1S2FPB3nEb0I=','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCFZe2iVRnbr3rsMVAEwtgCUctHxOR+rYgNoTbZWROxw4RIO959UZzMHAWGwpNMRLrp6dRW8P3QXDKyOhmK4m2u334t+4H6AsRLd5Wuasce4uPN/YhatSOybq9j1sYqVXRAXqPZXrabE5i4OdreKGRwOSlmStRSZ4YH4Bjy5wqDwIDAQAB',NULL,'2020-04-17 00:54:21','2020-04-17 00:54:21');
INSERT INTO `merchant_api_config` (`id`, `mch_id`, `api_key`, `api_security`, `private_key`, `pub_key`, `mch_pub_key`, `created_at`, `updated_at`) VALUES (2,3,'da8d3f4f24b8a8fab3ae804b8e29e377','37a6a3fe23eac4a01337b13b6903edb9','MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIeHTpKLbuPGX+b1vmI86IzdmyhjtGWmVfmKtM83cjT7DKFjC8cgp23n/f2PyRfCLkUqZBzcLCawmSaeDHvfke+d0oIOYG9cL2ZFKgAUokz+kvxDmBNU1JmwlZkxQsxV2/STuvEPBJSH6hBzs8DRBO2tTRMeGs2Kn7PVLp3h3g9LAgMBAAECgYBoMuR7EQ7ButwzA09L0k5tN1g6dmXVHUzOnhEjnBqHl1j/n9oHUBjKDGqgD3Npe9b9QHlWIwUi/fXR1gP9HUfiUwa028gmMFJpuqRbGTf+14maFvY/174UPPk1E2YOqP7w9KetMdbhZSkLNiX0DabW5WoHHFDtDPQWPoV45QDdOQJBANwZbn4PkD3nGbZ4fBQYxKcsCOVtakKL523+cfPQlQjFIb0MbUVE5AVe3Rtiaa/+1vc4EIn0qhQ9UfWYbE3QHycCQQCdon+cqr+1CH2XOTOTwSFOxJBiHNOlxCbZ5+un5p/I8qjanGXa7mZod63AswpsR3/nxX6tc1e1J8VIq/eiyiU9AkEAhsaf7VuUZQOVsDG5MQk1qnSBghPtBJDtB2LO3pD0IemszjnE06zqwAsl8/XgZ9/yPHI9VmzWlQwzmuNaNo/h8wJAOdAzRV5KWTb3+NJ93B7k/GKTIai45v790MWCZF3tFGILE8JwiM8OuBnMm/QOSpAelP6V6z92V3ZyoKiRdh71cQJBAKYse+8ICTGgBl/G6AhbO9wY230dcAAHrYQnVbCY6shFmDk3reUSGzd1/A8rwk4qkzZHKUOS4WPEQYmo7TT93Bo=','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHh06Si27jxl/m9b5iPOiM3ZsoY7RlplX5irTPN3I0+wyhYwvHIKdt5/39j8kXwi5FKmQc3CwmsJkmngx735HvndKCDmBvXC9mRSoAFKJM/pL8Q5gTVNSZsJWZMULMVdv0k7rxDwSUh+oQc7PA0QTtrU0THhrNip+z1S6d4d4PSwIDAQAB',NULL,'2020-04-17 21:55:41','2020-04-17 21:55:41');
INSERT INTO `merchant_api_config` (`id`, `mch_id`, `api_key`, `api_security`, `private_key`, `pub_key`, `mch_pub_key`, `created_at`, `updated_at`) VALUES (3,4,'cc48cbb82c24c7a9816b51778de66540','fe7da3f982b9a4313dbc5f4ecfe5bec2','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALp5mT8AuRiAaT+GyF3OKcLSOnw525FVQTqnyF2jkokmY3ru5AJy4MdgBryyBlS4Xx+dLjP8Zz8ByDLMhs+jUeWAk8UyCeLZTUc+NHa5vdXg8jZeeW831B+s73wJEbhqIif7+BcnsXWmcBvXk3Fi8HqlBuiZUUu3jSRn//6q2SKXAgMBAAECgYEAknGklAHxWvidrzIecOzThxiqrZySftRBYdsaU8996Q+o2IZ7siCdvnX/VHlK14XjzmQWQOVb//NaUeqlhLNyQD3+PXCNPg1QwI8i85hlxZfi7wkcSbPAibWy1L0mtf0VvmHsoCLMGKoVHay9ANTb0VgWVpBOShOLHizD2gPk5kECQQDuRtdMMp+4VcfdbVzX7qsiD9F4aB3kXjKudatnsxY9qLRXtDOPJyDhLCNABdO2QOeSNzPLzxxlv8P9rTZABR7/AkEAyFhfSlJOHlObJzixX1NOLAKwwuo/hN7tx1yTaS++RIej3vFpFH2KcFI801jI2/SFyJTcbBMDzFW7+gVk4SyUaQJANaCgCDhEllpIF1/ry3Wd8paY0H6ua51/zqARKc+2q73yiOK3z/pAfi1O6bPmCFjnRZNDBxXgEE5usANx+EquxQJAI36cq1M3qeHQHDvMGQJuWBfF71KHHGhOokKBj2I5CHUrkEUfA9VWIJ5BToRtWaLRzP6qqq8Xviz2V8561/YI+QJAYOdpdkZZoBH0syCsFXKl2UAcMEgKur4qbXy1PsL6RxoIUjNBpTiV99EFP4vXRcsX0sCJTh+GbVt4bbZH7hZfGA==','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6eZk/ALkYgGk/hshdzinC0jp8OduRVUE6p8hdo5KJJmN67uQCcuDHYAa8sgZUuF8fnS4z/Gc/AcgyzIbPo1HlgJPFMgni2U1HPjR2ub3V4PI2XnlvN9QfrO98CRG4aiIn+/gXJ7F1pnAb15NxYvB6pQbomVFLt40kZ//+qtkilwIDAQAB',NULL,'2020-04-21 01:57:44','2020-04-21 01:57:44');
/*!40000 ALTER TABLE `merchant_api_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchant_pay_account`
--

DROP TABLE IF EXISTS `merchant_pay_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchant_pay_account` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '商户支付账户ID',
  `merchant_id` int NOT NULL COMMENT '商户ID',
  `avail_balance` int NOT NULL DEFAULT '0' COMMENT '可用余额（分）',
  `freeze_balance` int NOT NULL DEFAULT '0' COMMENT '冻结余额（分）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户支付账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_pay_account`
--

LOCK TABLES `merchant_pay_account` WRITE;
/*!40000 ALTER TABLE `merchant_pay_account` DISABLE KEYS */;
INSERT INTO `merchant_pay_account` (`id`, `merchant_id`, `avail_balance`, `freeze_balance`, `created_at`, `updated_at`) VALUES (1,2,1200,0,'2020-04-17 00:54:21','2020-04-17 00:54:21');
INSERT INTO `merchant_pay_account` (`id`, `merchant_id`, `avail_balance`, `freeze_balance`, `created_at`, `updated_at`) VALUES (2,3,0,0,'2020-04-17 21:55:41','2020-04-17 21:55:41');
INSERT INTO `merchant_pay_account` (`id`, `merchant_id`, `avail_balance`, `freeze_balance`, `created_at`, `updated_at`) VALUES (3,4,10,9400,'2020-04-21 01:57:44','2020-04-21 01:57:44');
/*!40000 ALTER TABLE `merchant_pay_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchant_prepaid_account`
--

DROP TABLE IF EXISTS `merchant_prepaid_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchant_prepaid_account` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '账户ID',
  `merchant_id` int NOT NULL COMMENT '商户ID',
  `avail_balance` int NOT NULL DEFAULT '0' COMMENT '可用余额（分）',
  `freeze_balance` int NOT NULL DEFAULT '0' COMMENT '冻结金额（分）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户预充值账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_prepaid_account`
--

LOCK TABLES `merchant_prepaid_account` WRITE;
/*!40000 ALTER TABLE `merchant_prepaid_account` DISABLE KEYS */;
INSERT INTO `merchant_prepaid_account` (`id`, `merchant_id`, `avail_balance`, `freeze_balance`, `created_at`, `updated_at`) VALUES (1,2,0,0,'2020-04-17 00:54:21','2020-04-17 00:54:21');
INSERT INTO `merchant_prepaid_account` (`id`, `merchant_id`, `avail_balance`, `freeze_balance`, `created_at`, `updated_at`) VALUES (2,3,0,0,'2020-04-17 21:55:41','2020-04-17 21:55:41');
INSERT INTO `merchant_prepaid_account` (`id`, `merchant_id`, `avail_balance`, `freeze_balance`, `created_at`, `updated_at`) VALUES (3,4,0,0,'2020-04-21 01:57:44','2020-04-21 01:57:44');
/*!40000 ALTER TABLE `merchant_prepaid_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchant_product`
--

DROP TABLE IF EXISTS `merchant_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchant_product` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '商户产品ID',
  `merchant_id` int NOT NULL COMMENT '商户ID',
  `pay_type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `product_id` int DEFAULT NULL COMMENT '产品ID',
  `product_name` varchar(32) NOT NULL COMMENT '支付产品名称',
  `product_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '支付产品类型（1：收款，2：充值）',
  `interface_mode` tinyint(1) NOT NULL DEFAULT '1' COMMENT '接口模式（1:单独，2：轮训）',
  `rate` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '通道费率',
  `default_passage_id` int DEFAULT NULL COMMENT '默认通道ID',
  `default_passage_acc_id` int DEFAULT NULL COMMENT '默认通道账号ID',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户产品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_product`
--

LOCK TABLES `merchant_product` WRITE;
/*!40000 ALTER TABLE `merchant_product` DISABLE KEYS */;
INSERT INTO `merchant_product` (`id`, `merchant_id`, `pay_type_code`, `product_id`, `product_name`, `product_type`, `interface_mode`, `rate`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (14,2,'ali_jsapi',2,'支付宝服务窗_忆思然',1,1,0.11,NULL,NULL,1,'2020-04-28 02:09:09','2020-04-28 02:09:09');
INSERT INTO `merchant_product` (`id`, `merchant_id`, `pay_type_code`, `product_id`, `product_name`, `product_type`, `interface_mode`, `rate`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (15,2,'wx_jsapi',4,'abc',1,1,0.20,2,2,0,'2020-04-28 02:10:08','2020-04-28 02:10:08');
INSERT INTO `merchant_product` (`id`, `merchant_id`, `pay_type_code`, `product_id`, `product_name`, `product_type`, `interface_mode`, `rate`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (26,2,'wx_jsapi',3,'微信JSAPI_鸿宇通道',1,2,0.00,2,2,1,'2020-04-30 11:52:04','2020-04-30 11:52:04');
INSERT INTO `merchant_product` (`id`, `merchant_id`, `pay_type_code`, `product_id`, `product_name`, `product_type`, `interface_mode`, `rate`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (27,3,'ali_jsapi',2,'支付宝服务窗支付',1,1,0.02,NULL,NULL,0,'2020-04-30 11:58:23','2020-04-30 11:58:23');
INSERT INTO `merchant_product` (`id`, `merchant_id`, `pay_type_code`, `product_id`, `product_name`, `product_type`, `interface_mode`, `rate`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (29,3,'wx_jsapi',3,'微信公众号支付',1,2,0.03,NULL,NULL,1,'2020-04-30 11:59:44','2020-04-30 11:59:44');
/*!40000 ALTER TABLE `merchant_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchant_product_passage`
--

DROP TABLE IF EXISTS `merchant_product_passage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchant_product_passage` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mch_id` int NOT NULL COMMENT '商户ID',
  `product_id` int NOT NULL COMMENT '产品ID',
  `passage_id` int NOT NULL COMMENT '支付通道ID',
  `widget` int NOT NULL DEFAULT '0' COMMENT '权重',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户产品通道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_product_passage`
--

LOCK TABLES `merchant_product_passage` WRITE;
/*!40000 ALTER TABLE `merchant_product_passage` DISABLE KEYS */;
INSERT INTO `merchant_product_passage` (`id`, `mch_id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (4,2,3,2,1,'2020-04-30 11:52:04','2020-04-30 11:52:04');
INSERT INTO `merchant_product_passage` (`id`, `mch_id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (5,2,3,3,3,'2020-04-30 11:52:04','2020-04-30 11:52:04');
INSERT INTO `merchant_product_passage` (`id`, `mch_id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (9,3,3,2,0,'2020-04-30 11:59:44','2020-04-30 11:59:44');
INSERT INTO `merchant_product_passage` (`id`, `mch_id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (10,3,3,3,0,'2020-04-30 11:59:44','2020-04-30 11:59:44');
INSERT INTO `merchant_product_passage` (`id`, `mch_id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (11,3,3,4,0,'2020-04-30 11:59:44','2020-04-30 11:59:44');
/*!40000 ALTER TABLE `merchant_product_passage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchant_settle_account`
--

DROP TABLE IF EXISTS `merchant_settle_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchant_settle_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `merchant_id` int NOT NULL COMMENT '商户ID',
  `settle_fee_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '结算收费类型（1：百分比收费，2：固定收费）',
  `settle_fee_rate` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '结算费率（百分比）',
  `settle_fee_amount` int NOT NULL DEFAULT '0' COMMENT '结算费用（单位，分）',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户结算账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_settle_account`
--

LOCK TABLES `merchant_settle_account` WRITE;
/*!40000 ALTER TABLE `merchant_settle_account` DISABLE KEYS */;
INSERT INTO `merchant_settle_account` (`id`, `merchant_id`, `settle_fee_type`, `settle_fee_rate`, `settle_fee_amount`, `status`, `created_at`, `updated_at`) VALUES (1,2,1,0.02,1,1,'2020-04-21 01:25:24','2020-04-28 02:31:13');
INSERT INTO `merchant_settle_account` (`id`, `merchant_id`, `settle_fee_type`, `settle_fee_rate`, `settle_fee_amount`, `status`, `created_at`, `updated_at`) VALUES (2,3,1,0.00,0,0,'2020-04-21 01:25:29','2020-04-21 01:25:29');
INSERT INTO `merchant_settle_account` (`id`, `merchant_id`, `settle_fee_type`, `settle_fee_rate`, `settle_fee_amount`, `status`, `created_at`, `updated_at`) VALUES (3,4,1,0.00,0,0,'2020-04-21 01:57:45','2020-04-23 14:37:00');
/*!40000 ALTER TABLE `merchant_settle_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_extract`
--

DROP TABLE IF EXISTS `pay_extract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_extract` (
  `id` int NOT NULL AUTO_INCREMENT,
  `extract_no` varchar(32) NOT NULL COMMENT '订单编号',
  `mch_id` int NOT NULL COMMENT '商户id',
  `amount` int NOT NULL COMMENT '提现金额',
  `status` int NOT NULL COMMENT '提现状态 0 待审核 ，1提现成功，-1提现失败',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `end_at` datetime DEFAULT NULL COMMENT '结束时间',
  `updated_at` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户提现记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_extract`
--

LOCK TABLES `pay_extract` WRITE;
/*!40000 ALTER TABLE `pay_extract` DISABLE KEYS */;
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (1,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:49:19');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (2,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:49:49');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (3,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:49:53');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (4,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:49:55');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (5,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:49:58');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (6,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:00');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (7,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:05');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (8,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:08');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (9,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:11');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (10,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:15');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (11,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:17');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (12,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:20');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (13,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:24');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (14,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:27');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (15,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:30');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (16,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:32');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (17,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:35');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (18,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:38');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (19,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:40');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (20,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:43');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (21,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:46');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (22,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:49');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (23,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:51');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (24,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:54');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (25,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:50:59');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (26,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:51:01');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (27,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:51:04');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (28,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:51:07');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (29,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:51:09');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (30,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:51:12');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (31,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:51:15');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (32,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:51:17');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (33,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:51:19');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (34,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:51:22');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (35,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:51:24');
INSERT INTO `pay_extract` (`id`, `extract_no`, `mch_id`, `amount`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (36,'1',1111,1111,1,'2020-04-17 16:42:09','2020-04-30 16:42:11','2020-04-17 16:51:27');
/*!40000 ALTER TABLE `pay_extract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_interface`
--

DROP TABLE IF EXISTS `pay_interface`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_interface` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '支付接口ID',
  `interface_code` varchar(32) NOT NULL COMMENT '支付接口编码',
  `interface_name` varchar(32) NOT NULL COMMENT '支付接口名称',
  `pay_type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `interface_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '接口调用方式（1：实现类调用，2：插件调用，3：脚本调用）',
  `interface_impl` varchar(32) DEFAULT NULL COMMENT '实现类类名',
  `interface_plugin` varchar(32) DEFAULT NULL COMMENT '插件名称',
  `interface_script` varchar(255) DEFAULT NULL COMMENT '脚本内容',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付接口';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_interface`
--

LOCK TABLES `pay_interface` WRITE;
/*!40000 ALTER TABLE `pay_interface` DISABLE KEYS */;
INSERT INTO `pay_interface` (`id`, `interface_code`, `interface_name`, `pay_type_code`, `interface_type`, `interface_impl`, `interface_plugin`, `interface_script`, `status`, `created_at`, `updated_at`) VALUES (2,'wx_jsapi','微信JSAPI官方','wx_jsapi',1,'abc',NULL,NULL,1,'2020-04-14 02:05:00','2020-04-14 02:05:00');
INSERT INTO `pay_interface` (`id`, `interface_code`, `interface_name`, `pay_type_code`, `interface_type`, `interface_impl`, `interface_plugin`, `interface_script`, `status`, `created_at`, `updated_at`) VALUES (3,'jk_wx_jsapi','微信公众号支付-金控','wx_jsapi',1,'123',NULL,NULL,1,'2020-04-24 16:28:35','2020-04-24 16:28:35');
INSERT INTO `pay_interface` (`id`, `interface_code`, `interface_name`, `pay_type_code`, `interface_type`, `interface_impl`, `interface_plugin`, `interface_script`, `status`, `created_at`, `updated_at`) VALUES (4,'jk_ali_qr','支付宝二维码支付-金控','ali_scan',1,'',NULL,NULL,1,'2020-04-24 17:12:40','2020-04-24 17:12:40');
INSERT INTO `pay_interface` (`id`, `interface_code`, `interface_name`, `pay_type_code`, `interface_type`, `interface_impl`, `interface_plugin`, `interface_script`, `status`, `created_at`, `updated_at`) VALUES (5,'wx_scan','微信扫码支付_官方','wx_scan',1,'',NULL,NULL,1,'2020-04-24 17:26:12','2020-04-24 17:26:12');
INSERT INTO `pay_interface` (`id`, `interface_code`, `interface_name`, `pay_type_code`, `interface_type`, `interface_impl`, `interface_plugin`, `interface_script`, `status`, `created_at`, `updated_at`) VALUES (6,'pay_wx_qiye','微信支付-企业付款-官方','wx_pay_biz',1,'',NULL,NULL,1,'2020-04-27 09:01:35','2020-04-27 09:01:35');
/*!40000 ALTER TABLE `pay_interface` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_order`
--

DROP TABLE IF EXISTS `pay_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) NOT NULL COMMENT '订单编号',
  `order_sn` varchar(32) NOT NULL COMMENT '交易流水号',
  `mch_id` int NOT NULL COMMENT '商户ID',
  `app_id` int NOT NULL COMMENT '应用ID',
  `subject` varchar(32) NOT NULL COMMENT '商品标题',
  `out_order_no` varchar(32) NOT NULL COMMENT '商户订单号',
  `amount` int NOT NULL COMMENT '订单金额（单位：分）',
  `body` varchar(64) DEFAULT NULL COMMENT '商品描述',
  `client_ip` int NOT NULL DEFAULT '0' COMMENT '客户端IP',
  `notify_url` varchar(255) DEFAULT NULL COMMENT '回调地址',
  `redirect_url` varchar(255) DEFAULT NULL COMMENT '跳转地址',
  `fee` int NOT NULL DEFAULT '0' COMMENT '订单手续费（单位：分）',
  `pay_product_id` int NOT NULL COMMENT '支付产品ID',
  `pay_product_name` varchar(32) NOT NULL COMMENT '支付产品名称',
  `status` tinyint(1) NOT NULL COMMENT '订单状态（0：待付款，2：已支付，3：订单完成，-1：交易取消，-2：交易失败）',
  `paid_at` datetime DEFAULT NULL COMMENT '支付时间',
  `expired_at` datetime DEFAULT NULL COMMENT '过期时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_order`
--

LOCK TABLES `pay_order` WRITE;
/*!40000 ALTER TABLE `pay_order` DISABLE KEYS */;
INSERT INTO `pay_order` (`id`, `order_no`, `order_sn`, `mch_id`, `app_id`, `subject`, `out_order_no`, `amount`, `body`, `client_ip`, `notify_url`, `redirect_url`, `fee`, `pay_product_id`, `pay_product_name`, `status`, `paid_at`, `expired_at`, `created_at`, `updated_at`) VALUES (2,'1249942871872770048','202004140209381280292',2,123456,'abc','123456',78542,NULL,0,NULL,NULL,0,3,'微信公众号支付',1,NULL,NULL,'2020-04-14 14:09:38','2020-04-14 14:09:38');
INSERT INTO `pay_order` (`id`, `order_no`, `order_sn`, `mch_id`, `app_id`, `subject`, `out_order_no`, `amount`, `body`, `client_ip`, `notify_url`, `redirect_url`, `fee`, `pay_product_id`, `pay_product_name`, `status`, `paid_at`, `expired_at`, `created_at`, `updated_at`) VALUES (3,'1249943048562020352','202004140210202537008',2,1234567,'abc','123456',456123,NULL,0,NULL,NULL,0,3,'微信公众号支付',1,NULL,NULL,'2020-04-14 14:10:20','2020-04-14 14:10:20');
INSERT INTO `pay_order` (`id`, `order_no`, `order_sn`, `mch_id`, `app_id`, `subject`, `out_order_no`, `amount`, `body`, `client_ip`, `notify_url`, `redirect_url`, `fee`, `pay_product_id`, `pay_product_name`, `status`, `paid_at`, `expired_at`, `created_at`, `updated_at`) VALUES (4,'1249943081101430784','202004140210280110581',2,1234567,'abc','1234576',134435,NULL,0,NULL,NULL,0,3,'微信公众号支付',1,NULL,NULL,'2020-04-14 14:10:28','2020-04-14 14:10:28');
INSERT INTO `pay_order` (`id`, `order_no`, `order_sn`, `mch_id`, `app_id`, `subject`, `out_order_no`, `amount`, `body`, `client_ip`, `notify_url`, `redirect_url`, `fee`, `pay_product_id`, `pay_product_name`, `status`, `paid_at`, `expired_at`, `created_at`, `updated_at`) VALUES (5,'1249965518975275008','202004140339376183456',2,1234567,'abc','12345776',41534,NULL,0,NULL,NULL,0,3,'微信公众号支付',1,NULL,NULL,'2020-04-14 15:39:38','2020-04-14 15:39:38');
INSERT INTO `pay_order` (`id`, `order_no`, `order_sn`, `mch_id`, `app_id`, `subject`, `out_order_no`, `amount`, `body`, `client_ip`, `notify_url`, `redirect_url`, `fee`, `pay_product_id`, `pay_product_name`, `status`, `paid_at`, `expired_at`, `created_at`, `updated_at`) VALUES (6,'1249966907960987648','202004140345087778932',2,1234567,'abc','123457786',1523,NULL,0,NULL,NULL,0,3,'微信公众号支付',1,NULL,NULL,'2020-04-14 15:45:09','2020-04-14 15:45:09');
INSERT INTO `pay_order` (`id`, `order_no`, `order_sn`, `mch_id`, `app_id`, `subject`, `out_order_no`, `amount`, `body`, `client_ip`, `notify_url`, `redirect_url`, `fee`, `pay_product_id`, `pay_product_name`, `status`, `paid_at`, `expired_at`, `created_at`, `updated_at`) VALUES (7,'1249967549395898368','202004140347417071605',2,1234567,'abc','1234757786',152334,NULL,0,NULL,NULL,0,3,'微信公众号支付',1,NULL,NULL,'2020-04-14 15:47:42','2020-04-14 15:47:42');
INSERT INTO `pay_order` (`id`, `order_no`, `order_sn`, `mch_id`, `app_id`, `subject`, `out_order_no`, `amount`, `body`, `client_ip`, `notify_url`, `redirect_url`, `fee`, `pay_product_id`, `pay_product_name`, `status`, `paid_at`, `expired_at`, `created_at`, `updated_at`) VALUES (8,'1249968387975680000','202004140351016404322',2,1234567,'abc','12347572786',12345,NULL,0,NULL,NULL,0,3,'微信公众号支付',1,NULL,NULL,'2020-04-14 15:51:02','2020-04-14 15:51:02');
INSERT INTO `pay_order` (`id`, `order_no`, `order_sn`, `mch_id`, `app_id`, `subject`, `out_order_no`, `amount`, `body`, `client_ip`, `notify_url`, `redirect_url`, `fee`, `pay_product_id`, `pay_product_name`, `status`, `paid_at`, `expired_at`, `created_at`, `updated_at`) VALUES (11,'1249973459761631232','202004140411108494707',2,1234567,'abc','123475727186',12345,NULL,0,NULL,NULL,0,3,'微信公众号支付',1,NULL,NULL,'2020-04-14 16:11:11','2020-04-14 16:11:11');
INSERT INTO `pay_order` (`id`, `order_no`, `order_sn`, `mch_id`, `app_id`, `subject`, `out_order_no`, `amount`, `body`, `client_ip`, `notify_url`, `redirect_url`, `fee`, `pay_product_id`, `pay_product_name`, `status`, `paid_at`, `expired_at`, `created_at`, `updated_at`) VALUES (13,'1250458836519751680','202004161219536850645',2,123456,'123484','12346546',4567,NULL,0,NULL,NULL,0,3,'微信公众号支付',1,NULL,NULL,'2020-04-16 00:19:54','2020-04-16 00:19:54');
INSERT INTO `pay_order` (`id`, `order_no`, `order_sn`, `mch_id`, `app_id`, `subject`, `out_order_no`, `amount`, `body`, `client_ip`, `notify_url`, `redirect_url`, `fee`, `pay_product_id`, `pay_product_name`, `status`, `paid_at`, `expired_at`, `created_at`, `updated_at`) VALUES (14,'1252256726720319488','202004201124041122618',2,123456,'123484','1587396162157',1347,NULL,0,NULL,NULL,0,3,'微信公众号支付',1,NULL,NULL,'2020-04-20 23:24:04','2020-04-20 23:24:04');
INSERT INTO `pay_order` (`id`, `order_no`, `order_sn`, `mch_id`, `app_id`, `subject`, `out_order_no`, `amount`, `body`, `client_ip`, `notify_url`, `redirect_url`, `fee`, `pay_product_id`, `pay_product_name`, `status`, `paid_at`, `expired_at`, `created_at`, `updated_at`) VALUES (15,'1252257359594655744','202004201126350011901',2,123456,'123484','15873961621573',13,NULL,0,NULL,NULL,0,3,'微信公众号支付',1,NULL,NULL,'2020-04-20 23:26:35','2020-04-20 23:26:35');
INSERT INTO `pay_order` (`id`, `order_no`, `order_sn`, `mch_id`, `app_id`, `subject`, `out_order_no`, `amount`, `body`, `client_ip`, `notify_url`, `redirect_url`, `fee`, `pay_product_id`, `pay_product_name`, `status`, `paid_at`, `expired_at`, `created_at`, `updated_at`) VALUES (16,'1252258238792077312','202004201130046184519',2,123456,'123484','1587396593921',1,NULL,0,NULL,NULL,0,3,'微信公众号支付',1,NULL,NULL,'2020-04-20 23:30:05','2020-04-20 23:30:05');
/*!40000 ALTER TABLE `pay_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_order_detail`
--

DROP TABLE IF EXISTS `pay_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) NOT NULL COMMENT '订单编号',
  `pay_type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `pay_product_id` int NOT NULL COMMENT '支付产品ID',
  `pay_passage_id` int NOT NULL COMMENT '支付通道ID',
  `pay_passage_acc_id` int NOT NULL COMMENT '支付通道子账户ID',
  `pay_interface_id` int NOT NULL COMMENT '支付接口ID',
  `pay_interface_attr` varchar(255) NOT NULL COMMENT '支付接口参数',
  `upstream_order_no` varchar(64) DEFAULT NULL COMMENT '上游订单编号',
  `upstream_extra` varchar(64) DEFAULT NULL COMMENT '上游扩展参数',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_order_detail`
--

LOCK TABLES `pay_order_detail` WRITE;
/*!40000 ALTER TABLE `pay_order_detail` DISABLE KEYS */;
INSERT INTO `pay_order_detail` (`id`, `order_no`, `pay_type_code`, `pay_product_id`, `pay_passage_id`, `pay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `upstream_order_no`, `upstream_extra`, `created_at`, `updated_at`) VALUES (1,'1249942871872770048','wx_jsapi',2,2,2,2,'{}',NULL,NULL,'2020-04-14 14:09:38','2020-04-14 14:09:38');
INSERT INTO `pay_order_detail` (`id`, `order_no`, `pay_type_code`, `pay_product_id`, `pay_passage_id`, `pay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `upstream_order_no`, `upstream_extra`, `created_at`, `updated_at`) VALUES (2,'1249943048562020352','wx_jsapi',2,2,2,2,'{}',NULL,NULL,'2020-04-14 14:10:20','2020-04-14 14:10:20');
INSERT INTO `pay_order_detail` (`id`, `order_no`, `pay_type_code`, `pay_product_id`, `pay_passage_id`, `pay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `upstream_order_no`, `upstream_extra`, `created_at`, `updated_at`) VALUES (3,'1249943081101430784','wx_jsapi',2,2,2,2,'{}',NULL,NULL,'2020-04-14 14:10:28','2020-04-14 14:10:28');
INSERT INTO `pay_order_detail` (`id`, `order_no`, `pay_type_code`, `pay_product_id`, `pay_passage_id`, `pay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `upstream_order_no`, `upstream_extra`, `created_at`, `updated_at`) VALUES (4,'1249965518975275008','wx_jsapi',2,2,2,2,'{}',NULL,NULL,'2020-04-14 15:39:38','2020-04-14 15:39:38');
INSERT INTO `pay_order_detail` (`id`, `order_no`, `pay_type_code`, `pay_product_id`, `pay_passage_id`, `pay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `upstream_order_no`, `upstream_extra`, `created_at`, `updated_at`) VALUES (5,'1249966907960987648','wx_jsapi',2,2,2,2,'{}',NULL,NULL,'2020-04-14 15:45:09','2020-04-14 15:45:09');
INSERT INTO `pay_order_detail` (`id`, `order_no`, `pay_type_code`, `pay_product_id`, `pay_passage_id`, `pay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `upstream_order_no`, `upstream_extra`, `created_at`, `updated_at`) VALUES (6,'1249967549395898368','wx_jsapi',2,2,2,2,'{}',NULL,NULL,'2020-04-14 15:47:42','2020-04-14 15:47:42');
INSERT INTO `pay_order_detail` (`id`, `order_no`, `pay_type_code`, `pay_product_id`, `pay_passage_id`, `pay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `upstream_order_no`, `upstream_extra`, `created_at`, `updated_at`) VALUES (7,'1249968387975680000','wx_jsapi',2,2,2,2,'{}',NULL,NULL,'2020-04-14 15:51:02','2020-04-14 15:51:02');
INSERT INTO `pay_order_detail` (`id`, `order_no`, `pay_type_code`, `pay_product_id`, `pay_passage_id`, `pay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `upstream_order_no`, `upstream_extra`, `created_at`, `updated_at`) VALUES (10,'1249973459761631232','wx_jsapi',2,2,2,2,'{}',NULL,NULL,'2020-04-14 16:11:11','2020-04-14 16:11:11');
INSERT INTO `pay_order_detail` (`id`, `order_no`, `pay_type_code`, `pay_product_id`, `pay_passage_id`, `pay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `upstream_order_no`, `upstream_extra`, `created_at`, `updated_at`) VALUES (12,'1250458836519751680','wx_jsapi',2,2,2,2,'{}',NULL,NULL,'2020-04-16 00:19:54','2020-04-16 00:19:54');
INSERT INTO `pay_order_detail` (`id`, `order_no`, `pay_type_code`, `pay_product_id`, `pay_passage_id`, `pay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `upstream_order_no`, `upstream_extra`, `created_at`, `updated_at`) VALUES (13,'1252256726720319488','wx_jsapi',2,2,2,2,'{}',NULL,NULL,'2020-04-20 23:24:04','2020-04-20 23:24:04');
INSERT INTO `pay_order_detail` (`id`, `order_no`, `pay_type_code`, `pay_product_id`, `pay_passage_id`, `pay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `upstream_order_no`, `upstream_extra`, `created_at`, `updated_at`) VALUES (14,'1252257359594655744','wx_jsapi',2,2,2,2,'{}',NULL,NULL,'2020-04-20 23:26:35','2020-04-20 23:26:35');
INSERT INTO `pay_order_detail` (`id`, `order_no`, `pay_type_code`, `pay_product_id`, `pay_passage_id`, `pay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `upstream_order_no`, `upstream_extra`, `created_at`, `updated_at`) VALUES (15,'1252258238792077312','wx_jsapi',2,2,2,2,'{}',NULL,NULL,'2020-04-20 23:30:05','2020-04-20 23:30:05');
/*!40000 ALTER TABLE `pay_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_passage`
--

DROP TABLE IF EXISTS `pay_passage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_passage` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '支付通道ID',
  `passage_name` varchar(32) NOT NULL COMMENT '支付通道名称',
  `pay_type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `interface_code` varchar(32) NOT NULL COMMENT '支付接口编码',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付通道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_passage`
--

LOCK TABLES `pay_passage` WRITE;
/*!40000 ALTER TABLE `pay_passage` DISABLE KEYS */;
INSERT INTO `pay_passage` (`id`, `passage_name`, `pay_type_code`, `interface_code`, `status`, `created_at`, `updated_at`) VALUES (2,'微信JSAPI官方通道','wx_jsapi','wx_jsapi',1,'2020-04-14 02:06:20','2020-04-14 02:06:20');
INSERT INTO `pay_passage` (`id`, `passage_name`, `pay_type_code`, `interface_code`, `status`, `created_at`, `updated_at`) VALUES (3,'abc','wx_jsapi','jk_wx_jsapi',1,'2020-04-24 18:38:08','2020-04-24 18:38:08');
INSERT INTO `pay_passage` (`id`, `passage_name`, `pay_type_code`, `interface_code`, `status`, `created_at`, `updated_at`) VALUES (4,'abcdef','wx_jsapi','wx_jsapi',1,'2020-04-24 18:38:30','2020-04-24 18:38:30');
/*!40000 ALTER TABLE `pay_passage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_passage_account`
--

DROP TABLE IF EXISTS `pay_passage_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_passage_account` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '支付通道账户ID',
  `pay_type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `passage_id` int NOT NULL COMMENT '支付通道ID',
  `account_name` varchar(32) NOT NULL COMMENT '通道账户名称',
  `interface_attr` varchar(255) DEFAULT NULL COMMENT '通道接口参数',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付通道账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_passage_account`
--

LOCK TABLES `pay_passage_account` WRITE;
/*!40000 ALTER TABLE `pay_passage_account` DISABLE KEYS */;
INSERT INTO `pay_passage_account` (`id`, `pay_type_code`, `passage_id`, `account_name`, `interface_attr`, `status`, `created_at`, `updated_at`) VALUES (2,'wx_jsapi',2,'忆思然网络科技有限公司','{}',0,'2020-04-14 02:07:28','2020-04-14 02:07:28');
INSERT INTO `pay_passage_account` (`id`, `pay_type_code`, `passage_id`, `account_name`, `interface_attr`, `status`, `created_at`, `updated_at`) VALUES (3,'wx_jsapi',2,'ABC','123',1,'2020-04-24 19:59:21','2020-04-24 19:59:21');
INSERT INTO `pay_passage_account` (`id`, `pay_type_code`, `passage_id`, `account_name`, `interface_attr`, `status`, `created_at`, `updated_at`) VALUES (4,'wx_jsapi',3,'abcabc','abc',1,'2020-04-25 04:52:35','2020-04-25 04:52:35');
/*!40000 ALTER TABLE `pay_passage_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_product`
--

DROP TABLE IF EXISTS `pay_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_product` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '支付产品ID',
  `product_name` varchar(32) NOT NULL COMMENT '支付产品名称',
  `product_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '支付产品类型（1：收款，2：充值）',
  `pay_type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `interface_mode` tinyint(1) NOT NULL DEFAULT '1' COMMENT '支付接口模式（1：单独，2：轮询）',
  `default_passage_id` int DEFAULT NULL COMMENT '默认通道ID',
  `default_passage_acc_id` int DEFAULT NULL COMMENT '默认通道账户ID',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付产品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_product`
--

LOCK TABLES `pay_product` WRITE;
/*!40000 ALTER TABLE `pay_product` DISABLE KEYS */;
INSERT INTO `pay_product` (`id`, `product_name`, `product_type`, `pay_type_code`, `interface_mode`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (2,'支付宝服务窗支付',1,'ali_jsapi',1,2,2,1,'2020-04-14 02:08:38','2020-04-27 10:32:13');
INSERT INTO `pay_product` (`id`, `product_name`, `product_type`, `pay_type_code`, `interface_mode`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (3,'微信公众号支付',1,'wx_jsapi',2,2,2,1,'2020-04-21 20:02:43','2020-04-25 14:23:33');
INSERT INTO `pay_product` (`id`, `product_name`, `product_type`, `pay_type_code`, `interface_mode`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (4,'abc',1,'wx_jsapi',2,2,2,1,'2020-04-24 20:59:25','2020-04-25 04:46:16');
INSERT INTO `pay_product` (`id`, `product_name`, `product_type`, `pay_type_code`, `interface_mode`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (5,'微信公众号充值',1,'wx_jsapi',1,NULL,NULL,1,'2020-04-27 06:04:03','2020-04-27 13:56:27');
/*!40000 ALTER TABLE `pay_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_product_passage`
--

DROP TABLE IF EXISTS `pay_product_passage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_product_passage` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `product_id` int NOT NULL COMMENT '产品ID',
  `passage_id` int NOT NULL COMMENT '支付通道ID',
  `widget` int NOT NULL DEFAULT '0' COMMENT '权重',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付产品通道子账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_product_passage`
--

LOCK TABLES `pay_product_passage` WRITE;
/*!40000 ALTER TABLE `pay_product_passage` DISABLE KEYS */;
INSERT INTO `pay_product_passage` (`id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (13,4,2,10,'2020-04-25 04:46:15','2020-04-25 04:46:15');
INSERT INTO `pay_product_passage` (`id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (14,4,3,0,'2020-04-25 04:46:15','2020-04-25 04:46:15');
INSERT INTO `pay_product_passage` (`id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (15,4,4,0,'2020-04-25 04:46:15','2020-04-25 04:46:15');
INSERT INTO `pay_product_passage` (`id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (16,3,2,0,'2020-04-25 14:23:33','2020-04-25 14:23:33');
INSERT INTO `pay_product_passage` (`id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (17,3,3,50,'2020-04-25 14:23:33','2020-04-25 14:23:33');
INSERT INTO `pay_product_passage` (`id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (18,3,4,20,'2020-04-25 14:23:33','2020-04-25 14:23:33');
/*!40000 ALTER TABLE `pay_product_passage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_product_passage_acc`
--

DROP TABLE IF EXISTS `pay_product_passage_acc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_product_passage_acc` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `product_id` int NOT NULL COMMENT '产品ID',
  `passage_id` int NOT NULL COMMENT '支付通道ID',
  `acc_id` int NOT NULL COMMENT '子账户ID',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付产品通道子账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_product_passage_acc`
--

LOCK TABLES `pay_product_passage_acc` WRITE;
/*!40000 ALTER TABLE `pay_product_passage_acc` DISABLE KEYS */;
/*!40000 ALTER TABLE `pay_product_passage_acc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_type`
--

DROP TABLE IF EXISTS `pay_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_type` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `type_name` varchar(32) NOT NULL COMMENT '支付类型名称',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '类别(1：支付，2，代付)',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_type`
--

LOCK TABLES `pay_type` WRITE;
/*!40000 ALTER TABLE `pay_type` DISABLE KEYS */;
INSERT INTO `pay_type` (`id`, `type_code`, `type_name`, `type`, `status`, `created_at`, `updated_at`) VALUES (2,'wx_jsapi','微信公众号支付',1,1,'2020-04-14 01:54:17','2020-04-14 01:54:17');
INSERT INTO `pay_type` (`id`, `type_code`, `type_name`, `type`, `status`, `created_at`, `updated_at`) VALUES (3,'ali_jsapi','支付宝服务窗支付',1,1,'2020-04-21 20:05:34','2020-04-21 20:05:34');
INSERT INTO `pay_type` (`id`, `type_code`, `type_name`, `type`, `status`, `created_at`, `updated_at`) VALUES (4,'ali_scan','支付宝二维码',1,1,'2020-04-23 17:44:43','2020-04-23 17:44:43');
INSERT INTO `pay_type` (`id`, `type_code`, `type_name`, `type`, `status`, `created_at`, `updated_at`) VALUES (5,'wx_scan','微信扫码支付',1,1,'2020-04-24 17:25:25','2020-04-24 17:25:25');
INSERT INTO `pay_type` (`id`, `type_code`, `type_name`, `type`, `status`, `created_at`, `updated_at`) VALUES (6,'ali_wap','支付宝手机网页支付',1,1,'2020-04-24 22:10:59','2020-04-24 22:10:59');
INSERT INTO `pay_type` (`id`, `type_code`, `type_name`, `type`, `status`, `created_at`, `updated_at`) VALUES (7,'wx_wap','微信手机网页支付',1,1,'2020-04-24 22:11:34','2020-04-24 22:11:34');
INSERT INTO `pay_type` (`id`, `type_code`, `type_name`, `type`, `status`, `created_at`, `updated_at`) VALUES (8,'wx_pay_biz','微信支付-企业付款',2,1,'2020-04-27 09:00:58','2020-04-27 09:00:58');
/*!40000 ALTER TABLE `pay_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `replacepay_order`
--

DROP TABLE IF EXISTS `replacepay_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `replacepay_order` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `replace_id` varchar(255) NOT NULL COMMENT '代付订单id',
  `mch_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商户id',
  `bank_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '银行账号名',
  `account_number` bigint NOT NULL COMMENT '银行账号',
  `replace_money` bigint NOT NULL COMMENT '代付金额',
  `status` int NOT NULL COMMENT '订单状态 0 待审核 1 审核通过 -1 审核失败',
  `created_at` datetime NOT NULL COMMENT '订单创建时间',
  `end_at` datetime DEFAULT NULL COMMENT '订单结束时间',
  `updated_at` datetime NOT NULL COMMENT '订单更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代付订单表\r\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `replacepay_order`
--

LOCK TABLES `replacepay_order` WRITE;
/*!40000 ALTER TABLE `replacepay_order` DISABLE KEYS */;
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (1,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (27,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (28,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (29,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (30,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (31,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (32,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (33,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (34,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (35,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (36,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (37,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (38,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (39,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (40,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (41,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (42,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (43,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (44,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (45,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (46,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (47,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (48,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (49,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (50,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (51,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (52,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (53,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (54,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (55,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (56,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (57,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (58,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (59,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (60,'1111','111','1111',111,111,1,'2020-04-17 18:09:41','2020-04-23 18:09:44','2020-04-17 18:09:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (61,'202004181157305001251359172482580481','11111','战三',100000,10000,0,'2020-04-18 11:57:30',NULL,'2020-04-18 11:57:31');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (62,'202004181203241011251360655592730625','11111','11111',111111,10000,0,'2020-04-18 12:03:24',NULL,'2020-04-18 12:03:25');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (63,'202004181412200731251393102615265282','11111','张三',1111111,100000,0,'2020-04-18 14:12:20',NULL,'2020-04-18 14:12:21');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (64,'202004181414466541251393717420507137','11111','战三',11111111,100000,0,'2020-04-18 14:14:46',NULL,'2020-04-18 14:14:47');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (65,'202004181417086741251394313221312513','11111','张三',11111,10000,0,'2020-04-18 14:17:09',NULL,'2020-04-18 14:17:09');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (66,'202004181419569751251395018975895554','11111','战三',111111,10000,0,'2020-04-18 14:19:57',NULL,'2020-04-18 14:19:57');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (67,'202004181426047861251396561691303938','11111','11111',11111,10000,0,'2020-04-18 14:26:05',NULL,'2020-04-18 14:26:05');
INSERT INTO `replacepay_order` (`id`, `replace_id`, `mch_id`, `bank_name`, `account_number`, `replace_money`, `status`, `created_at`, `end_at`, `updated_at`) VALUES (68,'202004181431284541251397919244902402','11111','1111',1111,111100,0,'2020-04-18 14:31:28',NULL,'2020-04-18 14:31:28');
/*!40000 ALTER TABLE `replacepay_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `replacepay_recharge`
--

DROP TABLE IF EXISTS `replacepay_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `replacepay_recharge` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `recharge_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代付充值id',
  `mch_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商户id',
  `recharge_money` bigint NOT NULL COMMENT '充值金额',
  `status` int NOT NULL COMMENT '充值订单状态 0 待审核 1 充值成功 -1 未充值',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `updated_at` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户充值订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `replacepay_recharge`
--

LOCK TABLES `replacepay_recharge` WRITE;
/*!40000 ALTER TABLE `replacepay_recharge` DISABLE KEYS */;
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (1,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (43,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (44,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (45,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (46,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (47,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (48,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (49,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (50,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (51,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (52,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (53,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (54,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (55,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (56,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (57,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (58,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (59,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (60,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (61,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (62,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (63,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (64,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (65,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (66,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (67,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (68,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (69,'1111','1',111,1,'2020-04-17 18:30:34','2020-04-17 18:30:38','2020-04-17 18:30:41');
INSERT INTO `replacepay_recharge` (`id`, `recharge_id`, `mch_id`, `recharge_money`, `status`, `created_at`, `end_time`, `updated_at`) VALUES (70,'202004181503330801251405991715549185','1',10000,0,'2020-04-18 15:03:33',NULL,'2020-04-18 15:03:33');
/*!40000 ALTER TABLE `replacepay_recharge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settle_config`
--

DROP TABLE IF EXISTS `settle_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settle_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `audit_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审核状态（0：关闭，1：开启）',
  `settle_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '结算类型（1：人工结算，2：自动结算）',
  `amount_limit_min` int NOT NULL DEFAULT '0' COMMENT '金额限制（最小值，单位：分）',
  `amount_limit_max` int NOT NULL DEFAULT '0' COMMENT '金额限制（最大值，单位：分）',
  `day_amount_limit_max` int NOT NULL DEFAULT '0' COMMENT '每日金额最大值（单位：分）',
  `settle_fee_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '结算费率类型（1：比例收费，2：固定收费，3：比例收费+固定收费）',
  `settle_rate` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '结算比例（百分比）',
  `settle_fee` int NOT NULL DEFAULT '0' COMMENT '固定费率（单位：分）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='结算设置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settle_config`
--

LOCK TABLES `settle_config` WRITE;
/*!40000 ALTER TABLE `settle_config` DISABLE KEYS */;
INSERT INTO `settle_config` (`id`, `status`, `audit_status`, `settle_type`, `amount_limit_min`, `amount_limit_max`, `day_amount_limit_max`, `settle_fee_type`, `settle_rate`, `settle_fee`, `created_at`, `updated_at`) VALUES (1,0,1,2,1000,0,0,1,0.00,0,'2020-04-27 22:55:13','2020-04-27 22:55:13');
/*!40000 ALTER TABLE `settle_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settle_order`
--

DROP TABLE IF EXISTS `settle_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settle_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) NOT NULL COMMENT '结算订单号',
  `order_sn` varchar(32) NOT NULL COMMENT '结算流水号',
  `mch_id` int NOT NULL COMMENT '商户ID',
  `settle_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '结算类型（1：人工结算，2：自动结算）',
  `amount` int NOT NULL DEFAULT '0' COMMENT '订单金额（单位：分）',
  `fee` int NOT NULL DEFAULT '0' COMMENT '结算手续费（单位：分）',
  `settle_amount` int NOT NULL DEFAULT '0' COMMENT '结算金额（单位：分）',
  `account_name` varchar(32) NOT NULL COMMENT '结算账户名',
  `account_number` varchar(32) NOT NULL COMMENT '结算账号',
  `bank_name` varchar(32) NOT NULL COMMENT '开户行名称',
  `bank_code` varchar(32) NOT NULL COMMENT '银行联行号',
  `bank_address` varchar(64) DEFAULT NULL COMMENT '开户行地址（或支行名称）',
  `pay_type_code` varchar(32) DEFAULT NULL COMMENT '支付类型编码',
  `agentpay_passage_id` int DEFAULT NULL COMMENT '代付通道ID',
  `agentpay_passage_acc_id` int DEFAULT NULL COMMENT '代付通道账户ID',
  `pay_interface_id` int DEFAULT NULL COMMENT '支付接口ID',
  `pay_interface_attr` varchar(255) DEFAULT NULL COMMENT '支付接口参数',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（1：待审核，2：待处理，3：处理中，4：已结算，-1：已驳回，-2：结算失败）',
  `settled_at` datetime DEFAULT NULL COMMENT '结算时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户结算订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settle_order`
--

LOCK TABLES `settle_order` WRITE;
/*!40000 ALTER TABLE `settle_order` DISABLE KEYS */;
INSERT INTO `settle_order` (`id`, `order_no`, `order_sn`, `mch_id`, `settle_type`, `amount`, `fee`, `settle_amount`, `account_name`, `account_number`, `bank_name`, `bank_code`, `bank_address`, `pay_type_code`, `agentpay_passage_id`, `agentpay_passage_acc_id`, `pay_interface_id`, `pay_interface_attr`, `status`, `settled_at`, `created_at`, `updated_at`) VALUES (1,'151321515313','15615315815',1515,1,11515,151,15613,'欧阳娜娜','156156145165156','重庆银行','',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,'2020-04-27 19:24:32','2020-04-27 19:24:32');
/*!40000 ALTER TABLE `settle_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_menu`
--

DROP TABLE IF EXISTS `system_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单标题',
  `mark` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单标识',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '菜单类型（1:目录,2:菜单,3:按钮）',
  `icon` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '目录图标',
  `path` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单路由',
  `parent_id` int DEFAULT NULL COMMENT '上级菜单ID',
  `sorts` int NOT NULL DEFAULT '0' COMMENT '排序权重',
  `extra` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_menu`
--

LOCK TABLES `system_menu` WRITE;
/*!40000 ALTER TABLE `system_menu` DISABLE KEYS */;
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (1,'管理员列表','system',1,'','a',0,1,NULL,'2020-04-22 16:28:55','2020-04-22 16:28:55');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (2,'用户管理','a',1,NULL,'a',0,0,'','2020-04-22 17:10:11','2020-04-22 17:10:11');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (3,'添加','system:users:add',2,NULL,NULL,2,0,NULL,'2020-04-22 17:11:01','2020-04-22 17:11:01');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (4,'删除','system:users:del',2,NULL,NULL,2,0,NULL,'2020-04-22 17:11:23','2020-04-22 17:11:23');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (5,'修改','system:users:edit',2,NULL,NULL,2,0,NULL,'2020-04-22 17:11:45','2020-04-22 17:11:45');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (6,'查看','system:users:cat',2,NULL,NULL,2,0,NULL,'2020-04-22 17:12:13','2020-04-22 17:12:13');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (7,'角色管理','system:roles',1,NULL,'/admin/system/roles',0,0,NULL,'2020-04-22 17:13:01','2020-04-22 17:13:01');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (8,'添加','system:roles:add',2,NULL,NULL,7,0,NULL,'2020-04-22 17:13:29','2020-04-22 17:13:29');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (9,'删除','system:roles:del',2,NULL,NULL,7,0,NULL,'2020-04-22 17:13:52','2020-04-22 17:13:52');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (10,'修改','system:roles:edit',2,NULL,NULL,7,0,NULL,'2020-04-22 17:14:15','2020-04-22 17:14:15');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (11,'查看','system:roles:cat',2,NULL,NULL,7,0,NULL,'2020-04-22 17:14:40','2020-04-22 17:14:40');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (14,'管理员列表','system',1,NULL,'a',0,1,'null','2020-04-27 11:41:47','2020-04-27 11:41:47');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (15,'管理员1','a',1,NULL,'a',14,0,'','2020-04-27 12:44:34','2020-04-27 12:44:34');
INSERT INTO `system_menu` (`id`, `title`, `mark`, `type`, `icon`, `path`, `parent_id`, `sorts`, `extra`, `created_at`, `updated_at`) VALUES (16,'管理员2','a',1,NULL,'a',15,0,'','2020-04-27 12:45:01','2020-04-27 12:45:01');
/*!40000 ALTER TABLE `system_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_role`
--

DROP TABLE IF EXISTS `system_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_role`
--

LOCK TABLES `system_role` WRITE;
/*!40000 ALTER TABLE `system_role` DISABLE KEYS */;
INSERT INTO `system_role` (`id`, `name`, `role_code`, `created_at`, `updated_at`) VALUES (1,'admin','3,4,5,6,2','2020-04-21 17:59:10','2020-04-21 17:59:10');
INSERT INTO `system_role` (`id`, `name`, `role_code`, `created_at`, `updated_at`) VALUES (4,'a','1','2020-04-24 18:05:04','2020-04-24 18:05:04');
INSERT INTO `system_role` (`id`, `name`, `role_code`, `created_at`, `updated_at`) VALUES (6,'user1','3,4,5,6,2','2020-04-28 17:20:42','2020-04-28 17:20:42');
/*!40000 ALTER TABLE `system_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_role_menu`
--

DROP TABLE IF EXISTS `system_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL COMMENT '角色ID',
  `menu_id` int NOT NULL COMMENT '菜单ID',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_role_menu`
--

LOCK TABLES `system_role_menu` WRITE;
/*!40000 ALTER TABLE `system_role_menu` DISABLE KEYS */;
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `created_at`, `updated_at`) VALUES (9,1,3,'2020-04-27 19:04:49','2020-04-27 19:04:49');
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `created_at`, `updated_at`) VALUES (10,1,4,'2020-04-27 19:04:49','2020-04-27 19:04:49');
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `created_at`, `updated_at`) VALUES (11,1,5,'2020-04-27 19:04:49','2020-04-27 19:04:49');
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `created_at`, `updated_at`) VALUES (12,1,6,'2020-04-27 19:04:49','2020-04-27 19:04:49');
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `created_at`, `updated_at`) VALUES (13,1,2,'2020-04-27 19:04:49','2020-04-27 19:04:49');
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `created_at`, `updated_at`) VALUES (14,6,3,'2020-04-28 17:20:42','2020-04-28 17:20:42');
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `created_at`, `updated_at`) VALUES (15,6,4,'2020-04-28 17:20:42','2020-04-28 17:20:42');
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `created_at`, `updated_at`) VALUES (16,6,5,'2020-04-28 17:20:42','2020-04-28 17:20:42');
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `created_at`, `updated_at`) VALUES (17,6,6,'2020-04-28 17:20:42','2020-04-28 17:20:42');
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `created_at`, `updated_at`) VALUES (18,6,2,'2020-04-28 17:20:42','2020-04-28 17:20:42');
INSERT INTO `system_role_menu` (`id`, `role_id`, `menu_id`, `created_at`, `updated_at`) VALUES (24,4,1,'2020-04-28 18:03:01','2020-04-28 18:03:01');
/*!40000 ALTER TABLE `system_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_user`
--

DROP TABLE IF EXISTS `system_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户邮箱',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_user`
--

LOCK TABLES `system_user` WRITE;
/*!40000 ALTER TABLE `system_user` DISABLE KEYS */;
INSERT INTO `system_user` (`id`, `username`, `password`, `email`, `created_at`, `updated_at`) VALUES (1,'admin','900150983cd24fb0d6963f7d28e17f72','12@qq.com','2020-04-21 14:38:41','2020-04-29 16:20:59');
INSERT INTO `system_user` (`id`, `username`, `password`, `email`, `created_at`, `updated_at`) VALUES (4,'123456','123456','aa@qq.com','2020-04-27 17:06:09','2020-04-27 17:06:09');
INSERT INTO `system_user` (`id`, `username`, `password`, `email`, `created_at`, `updated_at`) VALUES (7,'user1','123456','test@qq.com','2020-04-28 17:23:50','2020-04-28 17:23:50');
INSERT INTO `system_user` (`id`, `username`, `password`, `email`, `created_at`, `updated_at`) VALUES (8,'user2','123456','ab@qq.com','2020-04-28 17:24:16','2020-04-28 17:24:16');
/*!40000 ALTER TABLE `system_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_user_role`
--

DROP TABLE IF EXISTS `system_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_user_role`
--

LOCK TABLES `system_user_role` WRITE;
/*!40000 ALTER TABLE `system_user_role` DISABLE KEYS */;
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `created_at`, `updated_at`) VALUES (1,1,1,'2020-04-22 17:34:38','2020-04-22 17:34:38');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `created_at`, `updated_at`) VALUES (2,4,4,'2020-04-27 17:06:09','2020-04-27 17:06:09');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `created_at`, `updated_at`) VALUES (3,5,1,'2020-04-27 17:09:44','2020-04-27 17:09:44');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `created_at`, `updated_at`) VALUES (4,6,1,'2020-04-28 16:51:05','2020-04-28 16:51:05');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `created_at`, `updated_at`) VALUES (5,7,1,'2020-04-28 17:23:50','2020-04-28 17:23:50');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `created_at`, `updated_at`) VALUES (6,8,6,'2020-04-28 17:24:16','2020-04-28 17:24:16');
/*!40000 ALTER TABLE `system_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-30 12:19:04
