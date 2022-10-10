# Learn to claim

## 1、Introduction

This project is about 'learn2claim' conception frame.

Use Tomcat(Java) to complete the basic web-application and with some web3 elements(ethers.js,solidity)in. 

There are three roles(admin,verifier,scholar) in the app,admin is the app's manager and smart contract's owner.

Verifier has the ability to add new scholar,publish some tasks or exams,view the participating status,review the results of each scholar and give each passer mint-Permission.It's also have incentive mechanism to verifiers.

Scholar has the ability to learn some lessons relative to task&exam and finish the task&exam,mint the 'proof of knowledge' SoulBoundToken while had mint-Permission.

##  2、Deploy

My Tomcat version(8.5.81), install Idea EE

refer to [How to config tomcat project in idea](https://blog.csdn.net/m0_67401606/article/details/123742214)

### Edit configuration

![1](E:\Users\LEGION\Desktop\1.png)

![2](E:\Users\LEGION\Desktop\2.png)

### Project structure

![屏幕截图 2022-10-10 190440](E:\Users\LEGION\Desktop\屏幕截图 2022-10-10 190440.png)

![屏幕截图 2022-10-10 190453](E:\Users\LEGION\Desktop\屏幕截图 2022-10-10 190453.png)

![屏幕截图 2022-10-10 190503](E:\Users\LEGION\Desktop\屏幕截图 2022-10-10 190503.png)

![屏幕截图 2022-10-10 190516](E:\Users\LEGION\Desktop\屏幕截图 2022-10-10 190516.png)

![屏幕截图 2022-10-10 190526](E:\Users\LEGION\Desktop\屏幕截图 2022-10-10 190526.png)

## 3、Database(Mysql)

create a database and execute the follow shell

```
--
-- Table structure for table `roleInfo`
--

DROP TABLE IF EXISTS `roleInfo`;

CREATE TABLE `roleInfo` (
  `roleid` int NOT NULL,
  `roleName` varchar(30) NOT NULL,
  PRIMARY KEY (`roleid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


--
-- Dumping data for table `roleInfo`
--

LOCK TABLES `roleInfo` WRITE;

INSERT INTO `roleInfo` VALUES (1,'admin'),(2,'用户'),(3,'Verifier'),(4,'Scholar');

UNLOCK TABLES;

--
-- Table structure for table `exams_description`
--

DROP TABLE IF EXISTS `exam_description`;

CREATE TABLE `exam_description` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `exam_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `exam_time` int ,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

--
-- Table structure for table `exams_description`
--

DROP TABLE IF EXISTS `adminInfo`;
CREATE TABLE `adminInfo` (
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


--
-- Dumping data for table `adminInfo`
--

LOCK TABLES `adminInfo` WRITE;
/*!40000 ALTER TABLE `adminInfo` DISABLE KEYS */;
INSERT INTO `adminInfo` VALUES ('your_address');
/*!40000 ALTER TABLE `adminInfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examrecord`
--

DROP TABLE IF EXISTS `examrecord`;

CREATE TABLE `examrecord` (
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `exam_description_id` varchar(255) NOT NULL,
  `total_score` int DEFAULT NULL,
  `obtain_score` int DEFAULT NULL,
  KEY `exam_description_id` (`exam_description_id`) USING BTREE,
  CONSTRAINT `exam_description_id` FOREIGN KEY (`exam_description_id`) REFERENCES `exam_description` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;

CREATE TABLE `questions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `exam_description_id` varchar(255) NOT NULL,
  `question` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `options` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `correct_answer` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `min_score` decimal(5,0) DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `question_mapping_id` (`exam_description_id`) USING BTREE,
  CONSTRAINT `question_mapping_id` FOREIGN KEY (`exam_description_id`) REFERENCES `exam_description` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


--
-- Table structure for table `verifierInfo`
--

DROP TABLE IF EXISTS `verifierInfo`;

CREATE TABLE `verifierInfo` (
  `address` varchar(255) NOT NULL,
  `roleid` int NOT NULL,
  PRIMARY KEY (`address`) USING BTREE,
  KEY `roleid1` (`roleid`) USING BTREE,
  CONSTRAINT `roleid1` FOREIGN KEY (`roleid`) REFERENCES `roleInfo`(`roleid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `readInfo`
--

DROP TABLE IF EXISTS `readInfo`;

CREATE TABLE `readInfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `readInfo`
--

LOCK TABLES `readInfo` WRITE;

INSERT INTO `readInfo` VALUES (1,'了解以太坊'),(2,'了解合并以及未来升级'),(3,'ETH质押'),(4,'以太坊效能瓶颈与改善方案');

UNLOCK TABLES;


--
-- Table structure for table `readrecord`
--

DROP TABLE IF EXISTS `readrecord`;

CREATE TABLE `readrecord` (
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `id` int NOT NULL,
  KEY `id` (`id`) USING BTREE,
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `readInfo`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

--
-- Table structure for table `scholarInfo`
--

DROP TABLE IF EXISTS `scholarInfo`;

CREATE TABLE `scholarInfo` (
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `roleid` int NOT NULL,
  `status` int NOT NULL,
  `mintnum` int NOT NULL,
  PRIMARY KEY (`address`) USING BTREE,
  KEY `roleid2` (`roleid`) USING BTREE,
  CONSTRAINT `roleid2` FOREIGN KEY (`roleid`) REFERENCES `roleInfo` (`roleid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


```

## 4、Smart contract

smart contract refer to ['SoulBoundToken'](https://github.com/auroraug/ERC721-alter)

use remix(0.8.4 version) to compile and deploy like this follow

![66](E:\Users\LEGION\Desktop\66.png)

then click the deployed blockchain browser link to publish contract (select 0.8.4 solidity compiler)

## 5、Final step

Turn to my jsp page,edit the contract ABI code with your own contract ABI code and other that just published on blockchain browser 

![666](E:\Users\LEGION\Desktop\666.png)