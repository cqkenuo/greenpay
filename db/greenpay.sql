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
  `out_order_no` varchar(32) NOT NULL COMMENT '商户订单号',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '代付批次号',
  `mch_id` int NOT NULL COMMENT '商户ID',
  `amount` int NOT NULL DEFAULT '0' COMMENT '订单金额（单位：分）',
  `fee` int NOT NULL DEFAULT '0' COMMENT '订单手续费（单位：分）',
  `account_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1：对私，2：对公',
  `account_name` varchar(32) NOT NULL COMMENT '账户名',
  `account_number` varchar(32) NOT NULL COMMENT '账户号',
  `bank_name` varchar(32) NOT NULL COMMENT '开户行',
  `bank_number` varchar(32) NOT NULL COMMENT '联行号',
  `notify_url` varchar(255) DEFAULT NULL COMMENT '订单回调地址',
  `extra` varchar(255) DEFAULT NULL COMMENT '扩展参数',
  `pay_type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `agentpay_passage_id` int NOT NULL COMMENT '支付通道ID',
  `agentpay_passage_name` varchar(32) NOT NULL COMMENT '代付通道名称',
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代付通道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agentpay_passage`
--

LOCK TABLES `agentpay_passage` WRITE;
/*!40000 ALTER TABLE `agentpay_passage` DISABLE KEYS */;
INSERT INTO `agentpay_passage` (`id`, `passage_name`, `pay_type_code`, `interface_code`, `status`, `created_at`, `updated_at`) VALUES (2,'通联代付通道','b2c','b2c_all_in_pay',1,'2020-05-06 15:46:22','2020-05-06 15:46:22');
INSERT INTO `agentpay_passage` (`id`, `passage_name`, `pay_type_code`, `interface_code`, `status`, `created_at`, `updated_at`) VALUES (3,'银联代付通道_B2C','b2c','b2c_unipay',1,'2020-05-06 19:00:28','2020-05-06 19:00:28');
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
  `weight` int NOT NULL DEFAULT '0' COMMENT '轮询权重',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代付通道账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agentpay_passage_account`
--

LOCK TABLES `agentpay_passage_account` WRITE;
/*!40000 ALTER TABLE `agentpay_passage_account` DISABLE KEYS */;
INSERT INTO `agentpay_passage_account` (`id`, `pay_type_code`, `passage_id`, `account_name`, `interface_attr`, `weight`, `status`, `created_at`, `updated_at`) VALUES (2,'b2c',2,'重庆网络科技有限公司忆思然','123456',1,1,'2020-05-06 15:46:46','2020-05-06 15:46:46');
INSERT INTO `agentpay_passage_account` (`id`, `pay_type_code`, `passage_id`, `account_name`, `interface_attr`, `weight`, `status`, `created_at`, `updated_at`) VALUES (3,'b2c',2,'a','123',0,1,'2020-05-07 16:00:12','2020-05-07 16:00:12');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant`
--

LOCK TABLES `merchant` WRITE;
/*!40000 ALTER TABLE `merchant` DISABLE KEYS */;
INSERT INTO `merchant` (`id`, `username`, `name`, `email`, `phone`, `password`, `status`, `created_at`, `updated_at`) VALUES (2,'test','测试商户','test@test.com','','7fef6171469e80d32c0559f88b377245',1,'2020-05-10 13:41:11','2020-05-10 13:41:11');
INSERT INTO `merchant` (`id`, `username`, `name`, `email`, `phone`, `password`, `status`, `created_at`, `updated_at`) VALUES (3,'abc','abc','abc@abc','','900150983cd24fb0d6963f7d28e17f72',0,'2020-05-10 16:43:44','2020-05-10 16:43:44');
/*!40000 ALTER TABLE `merchant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchant_agentpay_passage`
--

DROP TABLE IF EXISTS `merchant_agentpay_passage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchant_agentpay_passage` (
  `id` int NOT NULL AUTO_INCREMENT,
  `merchant_id` int NOT NULL COMMENT '商户ID',
  `passage_id` int NOT NULL COMMENT '通道ID',
  `passage_name` varchar(32) NOT NULL COMMENT '通道名称',
  `fee_type` int NOT NULL DEFAULT '1' COMMENT '手续费类型（1：百分比收费，2：固定收费，3：百分比加固定收费）',
  `fee_rate` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '通道费率',
  `fee_amount` int NOT NULL DEFAULT '0' COMMENT '固定费用（单位：分）',
  `weight` int NOT NULL DEFAULT '0' COMMENT '轮询权重',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户代付通道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_agentpay_passage`
--

LOCK TABLES `merchant_agentpay_passage` WRITE;
/*!40000 ALTER TABLE `merchant_agentpay_passage` DISABLE KEYS */;
/*!40000 ALTER TABLE `merchant_agentpay_passage` ENABLE KEYS */;
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
  `private_key` varchar(2048) DEFAULT NULL COMMENT '平台私钥',
  `pub_key` varchar(512) DEFAULT NULL COMMENT '平台公钥',
  `mch_pub_key` varchar(512) DEFAULT NULL COMMENT '商户公钥',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户密钥';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_api_config`
--

LOCK TABLES `merchant_api_config` WRITE;
/*!40000 ALTER TABLE `merchant_api_config` DISABLE KEYS */;
INSERT INTO `merchant_api_config` (`id`, `mch_id`, `api_key`, `api_security`, `private_key`, `pub_key`, `mch_pub_key`, `created_at`, `updated_at`) VALUES (1,2,'214534289370ee63a56a393e8169f53c','560716da2cb73bd7148ca0dcfce38352','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','2020-05-10 13:41:11','2020-05-10 13:41:11');
INSERT INTO `merchant_api_config` (`id`, `mch_id`, `api_key`, `api_security`, `private_key`, `pub_key`, `mch_pub_key`, `created_at`, `updated_at`) VALUES (2,3,'5f2d2e53b9edae2f667b24ffd410e6e4','82d127193a8a7a487048fe0ee181da20','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',NULL,'2020-05-10 16:43:45','2020-05-10 16:43:45');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户支付账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_pay_account`
--

LOCK TABLES `merchant_pay_account` WRITE;
/*!40000 ALTER TABLE `merchant_pay_account` DISABLE KEYS */;
INSERT INTO `merchant_pay_account` (`id`, `merchant_id`, `avail_balance`, `freeze_balance`, `created_at`, `updated_at`) VALUES (1,2,0,0,'2020-05-10 13:41:11','2020-05-10 13:41:11');
INSERT INTO `merchant_pay_account` (`id`, `merchant_id`, `avail_balance`, `freeze_balance`, `created_at`, `updated_at`) VALUES (2,3,0,0,'2020-05-10 16:43:45','2020-05-10 16:43:45');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户预充值账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_prepaid_account`
--

LOCK TABLES `merchant_prepaid_account` WRITE;
/*!40000 ALTER TABLE `merchant_prepaid_account` DISABLE KEYS */;
INSERT INTO `merchant_prepaid_account` (`id`, `merchant_id`, `avail_balance`, `freeze_balance`, `created_at`, `updated_at`) VALUES (1,2,0,0,'2020-05-10 13:41:12','2020-05-10 13:41:12');
INSERT INTO `merchant_prepaid_account` (`id`, `merchant_id`, `avail_balance`, `freeze_balance`, `created_at`, `updated_at`) VALUES (2,3,0,0,'2020-05-10 16:43:45','2020-05-10 16:43:45');
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
  `product_code` varchar(32) NOT NULL COMMENT '支付产品编码',
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户产品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_product`
--

LOCK TABLES `merchant_product` WRITE;
/*!40000 ALTER TABLE `merchant_product` DISABLE KEYS */;
INSERT INTO `merchant_product` (`id`, `merchant_id`, `pay_type_code`, `product_id`, `product_code`, `product_name`, `product_type`, `interface_mode`, `rate`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (1,2,'upacp_qr',8,'upacp_qr','银联扫码支付',1,1,4.50,7,6,1,'2020-05-10 17:13:23','2020-05-10 17:13:23');
INSERT INTO `merchant_product` (`id`, `merchant_id`, `pay_type_code`, `product_id`, `product_code`, `product_name`, `product_type`, `interface_mode`, `rate`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (3,2,'ali_jsapi',7,'ali_jsapi','支付宝服务窗支付',1,2,5.60,NULL,NULL,1,'2020-05-11 00:11:03','2020-05-11 00:11:03');
INSERT INTO `merchant_product` (`id`, `merchant_id`, `pay_type_code`, `product_id`, `product_code`, `product_name`, `product_type`, `interface_mode`, `rate`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (5,2,'wx_jsapi',6,'wx_jsapi','微信公众号支付',1,2,4.80,NULL,NULL,1,'2020-05-11 00:11:16','2020-05-11 00:11:16');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户产品通道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_product_passage`
--

LOCK TABLES `merchant_product_passage` WRITE;
/*!40000 ALTER TABLE `merchant_product_passage` DISABLE KEYS */;
INSERT INTO `merchant_product_passage` (`id`, `mch_id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (2,2,7,6,0,'2020-05-11 00:11:03','2020-05-11 00:11:03');
INSERT INTO `merchant_product_passage` (`id`, `mch_id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (4,2,6,5,0,'2020-05-11 00:11:16','2020-05-11 00:11:16');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户结算账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_settle_account`
--

LOCK TABLES `merchant_settle_account` WRITE;
/*!40000 ALTER TABLE `merchant_settle_account` DISABLE KEYS */;
INSERT INTO `merchant_settle_account` (`id`, `merchant_id`, `settle_fee_type`, `settle_fee_rate`, `settle_fee_amount`, `status`, `created_at`, `updated_at`) VALUES (1,2,1,0.00,0,0,'2020-05-10 13:41:12','2020-05-10 13:41:12');
INSERT INTO `merchant_settle_account` (`id`, `merchant_id`, `settle_fee_type`, `settle_fee_rate`, `settle_fee_amount`, `status`, `created_at`, `updated_at`) VALUES (2,3,1,0.00,0,0,'2020-05-10 16:43:45','2020-05-10 16:43:45');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户提现记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_extract`
--

LOCK TABLES `pay_extract` WRITE;
/*!40000 ALTER TABLE `pay_extract` DISABLE KEYS */;
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
  `interface_impl` varchar(255) DEFAULT NULL COMMENT '实现类类名',
  `interface_plugin` varchar(32) DEFAULT NULL COMMENT '插件名称',
  `interface_script` varchar(255) DEFAULT NULL COMMENT '脚本内容',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付接口';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_interface`
--

LOCK TABLES `pay_interface` WRITE;
/*!40000 ALTER TABLE `pay_interface` DISABLE KEYS */;
INSERT INTO `pay_interface` (`id`, `interface_code`, `interface_name`, `pay_type_code`, `interface_type`, `interface_impl`, `interface_plugin`, `interface_script`, `status`, `created_at`, `updated_at`) VALUES (7,'wx_jsapi','微信公众号支付_官方','wx_jsapi',1,'wxJSAPIPlugin',NULL,NULL,1,'2020-05-02 19:43:31','2020-05-02 19:43:31');
INSERT INTO `pay_interface` (`id`, `interface_code`, `interface_name`, `pay_type_code`, `interface_type`, `interface_impl`, `interface_plugin`, `interface_script`, `status`, `created_at`, `updated_at`) VALUES (8,'ali_jsapi','支付宝服务窗支付_官方','ali_jsapi',1,'bbb',NULL,NULL,1,'2020-05-02 20:50:05','2020-05-02 20:50:05');
INSERT INTO `pay_interface` (`id`, `interface_code`, `interface_name`, `pay_type_code`, `interface_type`, `interface_impl`, `interface_plugin`, `interface_script`, `status`, `created_at`, `updated_at`) VALUES (9,'b2c_all_in_pay','通联代付','b2c',1,'tl',NULL,NULL,1,'2020-05-06 15:45:59','2020-05-06 15:45:59');
INSERT INTO `pay_interface` (`id`, `interface_code`, `interface_name`, `pay_type_code`, `interface_type`, `interface_impl`, `interface_plugin`, `interface_script`, `status`, `created_at`, `updated_at`) VALUES (10,'b2c_unipay','银联代付_B2C','b2c',1,'unipay',NULL,NULL,1,'2020-05-06 18:59:59','2020-05-06 18:59:59');
INSERT INTO `pay_interface` (`id`, `interface_code`, `interface_name`, `pay_type_code`, `interface_type`, `interface_impl`, `interface_plugin`, `interface_script`, `status`, `created_at`, `updated_at`) VALUES (12,'upacp_qr_jk','银联扫码支付-金控','upacp_qr',1,'upacpQrJKPlugin',NULL,NULL,1,'2020-05-09 13:38:35','2020-05-09 13:38:35');
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
  `app_id` varchar(32) NOT NULL COMMENT '应用ID',
  `subject` varchar(32) NOT NULL COMMENT '商品标题',
  `out_order_no` varchar(32) NOT NULL COMMENT '商户订单号',
  `amount` int NOT NULL COMMENT '订单金额（单位：分）',
  `body` varchar(64) DEFAULT NULL COMMENT '商品描述',
  `client_ip` int NOT NULL DEFAULT '0' COMMENT '客户端IP',
  `notify_url` varchar(255) DEFAULT NULL COMMENT '回调地址',
  `redirect_url` varchar(255) DEFAULT NULL COMMENT '跳转地址',
  `fee` int NOT NULL DEFAULT '0' COMMENT '订单手续费（单位：分）',
  `pay_product_id` int NOT NULL COMMENT '支付产品ID',
  `pay_product_code` varchar(32) NOT NULL COMMENT '支付产品编码',
  `pay_product_name` varchar(32) NOT NULL COMMENT '支付产品名称',
  `status` tinyint(1) NOT NULL COMMENT '订单状态（1：待付款，2：已支付，3：订单完成，-1：交易取消，-2：交易失败）',
  `paid_at` datetime DEFAULT NULL COMMENT '支付时间',
  `expired_at` datetime DEFAULT NULL COMMENT '过期时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_order`
--

LOCK TABLES `pay_order` WRITE;
/*!40000 ALTER TABLE `pay_order` DISABLE KEYS */;
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
  `pay_interface_attr` varchar(4096) NOT NULL COMMENT '支付接口参数',
  `upstream_order_no` varchar(64) DEFAULT NULL COMMENT '上游订单编号',
  `pay_credential` varchar(4096) DEFAULT NULL COMMENT '支付凭证',
  `upstream_extra` varchar(64) DEFAULT NULL COMMENT '上游扩展参数',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_order_detail`
--

LOCK TABLES `pay_order_detail` WRITE;
/*!40000 ALTER TABLE `pay_order_detail` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付通道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_passage`
--

LOCK TABLES `pay_passage` WRITE;
/*!40000 ALTER TABLE `pay_passage` DISABLE KEYS */;
INSERT INTO `pay_passage` (`id`, `passage_name`, `pay_type_code`, `interface_code`, `status`, `created_at`, `updated_at`) VALUES (5,'微信公众号支付官方通道','wx_jsapi','wx_jsapi',1,'2020-05-02 19:47:46','2020-05-02 19:47:46');
INSERT INTO `pay_passage` (`id`, `passage_name`, `pay_type_code`, `interface_code`, `status`, `created_at`, `updated_at`) VALUES (6,'支付宝服务窗支付官方通道','ali_jsapi','ali_jsapi',1,'2020-05-02 20:50:36','2020-05-02 20:50:36');
INSERT INTO `pay_passage` (`id`, `passage_name`, `pay_type_code`, `interface_code`, `status`, `created_at`, `updated_at`) VALUES (7,'银联扫码支付-金控','upacp_qr','upacp_qr_jk',1,'2020-05-09 13:39:03','2020-05-09 13:39:03');
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
  `interface_attr` varchar(4096) DEFAULT NULL COMMENT '通道接口参数',
  `weight` int NOT NULL DEFAULT '0' COMMENT '轮询权重',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付通道账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_passage_account`
--

LOCK TABLES `pay_passage_account` WRITE;
/*!40000 ALTER TABLE `pay_passage_account` DISABLE KEYS */;
INSERT INTO `pay_passage_account` (`id`, `pay_type_code`, `passage_id`, `account_name`, `interface_attr`, `weight`, `status`, `created_at`, `updated_at`) VALUES (1,'wx_jsapi',5,'忆思然网络科技有限公司','{\"appId\":\"wx2aeda339f56138bf\",\"mchId\":\"1519342911\",\"mchKey\":\"2624eedd356624bc7a662c29ceb0a20e\"}',9,1,'2020-05-02 19:49:15','2020-05-02 19:49:15');
INSERT INTO `pay_passage_account` (`id`, `pay_type_code`, `passage_id`, `account_name`, `interface_attr`, `weight`, `status`, `created_at`, `updated_at`) VALUES (2,'ali_jsapi',6,'重庆忆思然网络科技有限公司','ababa',1,1,'2020-05-02 20:51:05','2020-05-02 20:51:05');
INSERT INTO `pay_passage_account` (`id`, `pay_type_code`, `passage_id`, `account_name`, `interface_attr`, `weight`, `status`, `created_at`, `updated_at`) VALUES (3,'ali_jsapi',6,'重庆鸿与科技有限公司','aaaa',1,1,'2020-05-02 20:51:51','2020-05-02 20:51:51');
INSERT INTO `pay_passage_account` (`id`, `pay_type_code`, `passage_id`, `account_name`, `interface_attr`, `weight`, `status`, `created_at`, `updated_at`) VALUES (4,'wx_jsapi',5,'重庆鸿与科技有限公司','{\"appId\":\"wx2aeda339f56138bf\",\"mchId\":\"1519342911\",\"mchKey\":\"2624eedd356624bc7a662c29ceb0a20e\"}',5,1,'2020-05-02 20:52:08','2020-05-02 20:52:08');
INSERT INTO `pay_passage_account` (`id`, `pay_type_code`, `passage_id`, `account_name`, `interface_attr`, `weight`, `status`, `created_at`, `updated_at`) VALUES (5,'wx_jsapi',5,'重庆艾美尼克网络科技有限公司','{\"appId\":\"wx2aeda339f56138bf\",\"mchId\":\"1519342911\",\"mchKey\":\"2624eedd356624bc7a662c29ceb0a20e\"}',3,1,'2020-05-02 23:20:45','2020-05-02 23:20:45');
INSERT INTO `pay_passage_account` (`id`, `pay_type_code`, `passage_id`, `account_name`, `interface_attr`, `weight`, `status`, `created_at`, `updated_at`) VALUES (6,'upacp_qr',7,'测试账户','{\"merchantNo\":\"QF08613\",\"apiServerPubKey\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7aiVsV49KTK2HoVXW7MlyP3DVkhQvUJcFHy3ktTBayelzTsLIDvknphD39AUNiSTnZOzz84zrl5Gb/WRt5T2qI2fLALCOJkPqV27Y7pqY5EvL59oVeZ14N8QXTbXNnD/ChwLO0PTJ0fcje8Vo9rNKxOARLQOIuvai1QxPtq3/SwIDAQAB\",\"apiClientPrivKey\":\"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALtqJWxXj0pMrYehVdbsyXI/cNWSFC9QlwUfLeS1MFrJ6XNOwsgO+SemEPf0BQ2JJOdk7PPzjOuXkZv9ZG3lPaojZ8sAsI4mQ+pXbtjumpjkS8vn2hV5nXg3xBdNtc2cP8KHAs7Q9MnR9yN7xWj2s0rE4BEtA4i69qLVDE+2rf9LAgMBAAECgYASojceUoZYqM8IES5rtBdDWLNrSJ7hJW4egD8b4jfwjuq7IDWVloeHPaeqP4TRF7WnnA1DJA/6zJMZo1P86TH8MQIDNFuTFX7gKqpQdmuLoTjn60lK5zyWJpU6Hun1RUKHVIFzVd58aT6bFp7hNgVgNzukxgpd9UNbKbh/wePqoQJBAN0m6TMBV9uK7MPbQjfKmzxBFFl7FhehEWp74t/2bzoC8eZ7wpahVij9bOsdN1ioIG8UHA8CsNfiycspZZi8fi0CQQDY8kxCVsH1J7eVXWfapIlVhCZ/ZOIhuyEPJyzhQDnMUqLG14QBDR2NMr8AyTPINzyyrci7+XUNnS58ns9OrFZXAkA9up+7GfPhFv0RikEIe6grAGtisqWvAMlwtJXWN95CDhJhaEgfbXc0R9DAK86IE53CA5X1ZqXgDLCQ4cQ3Bt4dAkAbm5oAxzGflRJTu7M/q7ieXVbY12m/iSTH3OBlOdshGVMFot91ksmz8kVEQFaKoBzlUCA/a5ttxBbqXPtcs6m5AkEAniZVPLYAVfNKMamkeyTJKcEFvEuFECKGH2xcMRuWiUQ0564ezQ/dJW98ThuuDVREN2rAvc+ezGCaaNaUwhXQLg\\u003d\\u003d\",\"aesKey\":\"a10cd58edc8e4519bca268ec7423f25f\"}',1,1,'2020-05-09 13:39:44','2020-05-09 13:39:44');
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
  `product_code` varchar(32) NOT NULL COMMENT '支付产品编码',
  `pay_type_code` varchar(32) NOT NULL COMMENT '支付类型编码',
  `interface_mode` tinyint(1) NOT NULL DEFAULT '1' COMMENT '支付接口模式（1：单独，2：轮询）',
  `default_passage_id` int DEFAULT NULL COMMENT '默认通道ID',
  `default_passage_acc_id` int DEFAULT NULL COMMENT '默认通道账户ID',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：关闭，1：开启）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付产品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_product`
--

LOCK TABLES `pay_product` WRITE;
/*!40000 ALTER TABLE `pay_product` DISABLE KEYS */;
INSERT INTO `pay_product` (`id`, `product_name`, `product_type`, `product_code`, `pay_type_code`, `interface_mode`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (6,'微信公众号支付',1,'wx_jsapi','wx_jsapi',1,5,1,1,'2020-05-02 19:48:40','2020-05-02 21:01:47');
INSERT INTO `pay_product` (`id`, `product_name`, `product_type`, `product_code`, `pay_type_code`, `interface_mode`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (7,'支付宝服务窗支付',1,'ali_jsapi','ali_jsapi',1,6,3,1,'2020-05-02 20:52:27','2020-05-02 21:13:49');
INSERT INTO `pay_product` (`id`, `product_name`, `product_type`, `product_code`, `pay_type_code`, `interface_mode`, `default_passage_id`, `default_passage_acc_id`, `status`, `created_at`, `updated_at`) VALUES (8,'银联扫码支付',1,'upacp_qr','upacp_qr',2,NULL,NULL,1,'2020-05-09 13:40:17','2020-05-09 13:40:39');
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付产品通道子账户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_product_passage`
--

LOCK TABLES `pay_product_passage` WRITE;
/*!40000 ALTER TABLE `pay_product_passage` DISABLE KEYS */;
INSERT INTO `pay_product_passage` (`id`, `product_id`, `passage_id`, `widget`, `created_at`, `updated_at`) VALUES (19,8,7,1,'2020-05-09 13:40:41','2020-05-09 13:40:41');
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_type`
--

LOCK TABLES `pay_type` WRITE;
/*!40000 ALTER TABLE `pay_type` DISABLE KEYS */;
INSERT INTO `pay_type` (`id`, `type_code`, `type_name`, `type`, `status`, `created_at`, `updated_at`) VALUES (9,'wx_jsapi','微信公众号支付',1,1,'2020-05-02 19:30:08','2020-05-02 19:30:08');
INSERT INTO `pay_type` (`id`, `type_code`, `type_name`, `type`, `status`, `created_at`, `updated_at`) VALUES (10,'ali_jsapi','支付宝服务窗支付',1,1,'2020-05-02 20:49:30','2020-05-02 20:49:30');
INSERT INTO `pay_type` (`id`, `type_code`, `type_name`, `type`, `status`, `created_at`, `updated_at`) VALUES (11,'b2c','企业对个人',2,1,'2020-05-06 15:44:39','2020-05-06 15:44:39');
INSERT INTO `pay_type` (`id`, `type_code`, `type_name`, `type`, `status`, `created_at`, `updated_at`) VALUES (13,'upacp_qr','银联扫码支付',1,1,'2020-05-09 13:36:50','2020-05-09 13:36:50');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代付订单表\r\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `replacepay_order`
--

LOCK TABLES `replacepay_order` WRITE;
/*!40000 ALTER TABLE `replacepay_order` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户充值订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `replacepay_recharge`
--

LOCK TABLES `replacepay_recharge` WRITE;
/*!40000 ALTER TABLE `replacepay_recharge` DISABLE KEYS */;
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
  `bank_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '银行联行号',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户结算订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settle_order`
--

LOCK TABLES `settle_order` WRITE;
/*!40000 ALTER TABLE `settle_order` DISABLE KEYS */;
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

-- Dump completed on 2020-05-11  2:32:58
