/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : lbxy

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-08-18 19:47:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `orderId` int(10) unsigned NOT NULL COMMENT '账单对应的订单id',
  `userId` int(10) unsigned NOT NULL COMMENT '账单对应的用户',
  `money` decimal(5,2) NOT NULL COMMENT '订单的金额',
  `status` tinyint(2) NOT NULL COMMENT '0支出，1收入，2待结算（即还未完成的订单，此时金额在平台上，最后结算到接单人的收入中）',
  `createdDate` datetime NOT NULL COMMENT '账单创建时间',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `orderId` (`orderId`),
  CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bill_ibfk_2` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL COMMENT '发帖人id',
  `pUserId` int(10) unsigned DEFAULT NULL COMMENT '被回复者的id',
  `pId` int(10) unsigned DEFAULT NULL COMMENT '标识评论属于哪个帖子',
  `postDate` datetime(6) NOT NULL COMMENT '发帖时间',
  `content` text CHARACTER SET utf8mb4 NOT NULL COMMENT '帖子内容',
  `status` tinyint(2) DEFAULT '0',
  `type` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `pId` (`pId`),
  KEY `community_ibfk_2` (`pUserId`),
  CONSTRAINT `community_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `community_ibfk_2` FOREIGN KEY (`pUserId`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `community_ibfk_3` FOREIGN KEY (`pId`) REFERENCES `community` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for flea
-- ----------------------------
DROP TABLE IF EXISTS `flea`;
CREATE TABLE `flea` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL,
  `pUserId` int(10) unsigned DEFAULT NULL,
  `pId` int(10) unsigned DEFAULT NULL,
  `content` text NOT NULL,
  `pictureUrl` varchar(255) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '0' COMMENT '0未删除，1删除',
  `postDate` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `pUserId` (`pUserId`),
  KEY `pId` (`pId`),
  CONSTRAINT `flea_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `flea_ibfk_2` FOREIGN KEY (`pUserId`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `flea_ibfk_3` FOREIGN KEY (`pId`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for lostfound
-- ----------------------------
DROP TABLE IF EXISTS `lostfound`;
CREATE TABLE `lostfound` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL,
  `pUserId` int(10) unsigned DEFAULT NULL,
  `pId` int(10) unsigned DEFAULT NULL,
  `content` text NOT NULL,
  `status` tinyint(2) DEFAULT '0' COMMENT '0未删除，-1删除',
  `postDate` datetime(6) NOT NULL,
  `pictureUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `pUserId` (`pUserId`),
  KEY `pId` (`pId`),
  CONSTRAINT `lostfound_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `lostfound_ibfk_2` FOREIGN KEY (`pUserId`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `lostfound_ibfk_3` FOREIGN KEY (`pId`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `userName` varchar(255) NOT NULL,
  `passWord` varchar(255) NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int(10) NOT NULL,
  `userId` varchar(255) NOT NULL COMMENT '管理员userName',
  `content` text NOT NULL COMMENT '内容',
  `pictureUrl` varchar(255) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '0' COMMENT '0未删除，-1删除',
  `postDate` datetime(6) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `notice_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `manager` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `status` tinyint(2) DEFAULT '0' COMMENT '订单状态，是否完成等. 0未完成，1待完成，2完成，-1删除',
  `createdDate` datetime NOT NULL COMMENT '订单创建时间',
  `completedDate` datetime DEFAULT NULL COMMENT '订单完成时间',
  `acceptUserId` int(10) unsigned DEFAULT NULL COMMENT '接单人id',
  `acceptUserPhoneNumber` varchar(11) DEFAULT NULL COMMENT '接单人手机号',
  `reward` decimal(5,2) DEFAULT NULL COMMENT '订单价格',
  `userId` int(10) unsigned NOT NULL COMMENT '发单人id',
  `userPhoneNumber` varchar(11) DEFAULT NULL COMMENT '发单人手机号',
  `fromAddress` varchar(255) NOT NULL,
  `toAddress` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `detail` varchar(255) NOT NULL,
  `availableDate` varchar(255) DEFAULT NULL COMMENT '送达时间段',
  `acceptDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `acceptUserId` (`acceptUserId`),
  KEY `userId` (`userId`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`acceptUserId`) REFERENCES `user` (`id`),
  CONSTRAINT `order_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for treehole
-- ----------------------------
DROP TABLE IF EXISTS `treehole`;
CREATE TABLE `treehole` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL COMMENT '用户id',
  `pUserId` int(10) unsigned DEFAULT NULL COMMENT '被回复者id',
  `pId` int(10) unsigned DEFAULT NULL COMMENT '评论者id',
  `content` text NOT NULL COMMENT '内容',
  `postDate` datetime(6) NOT NULL COMMENT '发帖时间',
  `status` tinyint(2) DEFAULT NULL COMMENT '是否删除，0未删除，-1删除',
  `pictureUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `pUserId` (`pUserId`),
  KEY `pId` (`pId`),
  CONSTRAINT `treehole_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `treehole_ibfk_2` FOREIGN KEY (`pUserId`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `treehole_ibfk_3` FOREIGN KEY (`pId`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `openId` varchar(255) NOT NULL,
  `sessionKey` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(11) DEFAULT NULL,
  `username` varchar(22) DEFAULT NULL,
  `balance` decimal(5,2) DEFAULT '0.00' COMMENT '账户余额',
  `status` tinyint(2) DEFAULT '0' COMMENT '用户状态，例如 是否实名认证,实名认证为1，未实名认证为0,待审核为2',
  `idNoPic` varchar(255) DEFAULT NULL,
  `stuNoPic` varchar(255) DEFAULT NULL,
  `readName` varchar(255) DEFAULT NULL,
  `avatarUrl` varchar(255) DEFAULT '/favoicon',
  `gender` int(11) DEFAULT NULL,
  `studentNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
