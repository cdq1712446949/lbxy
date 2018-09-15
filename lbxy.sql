/*
Navicat MySQL Data Transfer

Source Server         : local_ubuntu_3306
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : lbxy

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-09-15 17:33:22
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of bill
-- ----------------------------

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
-- Records of community
-- ----------------------------
INSERT INTO `community` VALUES ('13', '2', null, null, '2018-08-10 13:09:39.620000', '第一条测试帖子评论', '0', '0');
INSERT INTO `community` VALUES ('14', '3', '2', null, '2018-08-10 13:09:40.620000', '第一条测试帖子回复', '1', '0');
INSERT INTO `community` VALUES ('16', '1', null, null, '2018-08-10 14:13:04.979000', '第三条测试帖子', '1', '0');
INSERT INTO `community` VALUES ('17', '2', null, '16', '2018-08-10 14:18:42.320000', '第一条测试帖子评论', '1', '0');
INSERT INTO `community` VALUES ('18', '3', '2', '16', '2018-08-10 14:19:25.908000', '第一条测试帖子回复', '1', '0');

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
  `status` tinyint(2) DEFAULT '0' COMMENT '0未删除，1删除',
  `postDate` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `pUserId` (`pUserId`),
  KEY `pId` (`pId`),
  CONSTRAINT `flea_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `flea_ibfk_2` FOREIGN KEY (`pUserId`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `flea_ibfk_3` FOREIGN KEY (`pId`) REFERENCES `flea` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of flea
-- ----------------------------
INSERT INTO `flea` VALUES ('1', '5', null, null, '测试', '0', '2018-09-14 13:45:09.305000');
INSERT INTO `flea` VALUES ('3', '5', null, null, '哈哈哈哈', '0', '2018-09-14 14:03:28.286000');
INSERT INTO `flea` VALUES ('4', '5', null, null, 'cesjo测试机哦', '-1', '2018-09-14 14:04:45.339000');
INSERT INTO `flea` VALUES ('5', '5', null, null, '再试一下', '-1', '2018-09-14 14:05:41.683000');
INSERT INTO `flea` VALUES ('6', '5', null, null, '再试一下', '-1', '2018-09-14 14:06:43.978000');
INSERT INTO `flea` VALUES ('7', '5', null, null, '再试一下', '0', '2018-09-14 14:06:45.558000');
INSERT INTO `flea` VALUES ('8', '5', null, null, 'ceshi', '-1', '2018-09-14 14:08:07.396000');
INSERT INTO `flea` VALUES ('9', '5', null, null, '11111', '-1', '2018-09-14 14:08:38.276000');
INSERT INTO `flea` VALUES ('10', '5', null, null, '跳转测试', '-1', '2018-09-14 14:11:04.310000');
INSERT INTO `flea` VALUES ('12', '5', null, '10', '111', '-1', '2018-09-14 14:43:34.814000');
INSERT INTO `flea` VALUES ('13', '5', null, '10', '222', '-1', '2018-09-14 14:51:45.676000');
INSERT INTO `flea` VALUES ('14', '5', null, null, '测试', '0', '2018-09-14 15:29:39.594000');
INSERT INTO `flea` VALUES ('15', '5', null, '14', 'hhh', '-1', '2018-09-14 16:27:04.650000');
INSERT INTO `flea` VALUES ('16', '5', null, '14', 'aaa', '0', '2018-09-14 16:27:29.083000');
INSERT INTO `flea` VALUES ('17', '5', null, '14', 'qqq', '0', '2018-09-14 17:40:06.409000');
INSERT INTO `flea` VALUES ('18', '5', null, null, '图片', '0', '2018-09-14 18:40:17.906000');
INSERT INTO `flea` VALUES ('19', '5', null, null, 'asd', '0', '2018-09-14 18:41:38.788000');
INSERT INTO `flea` VALUES ('20', '5', null, null, '哈哈哈', '0', '2018-09-15 00:39:02.426000');
INSERT INTO `flea` VALUES ('21', '5', null, '20', '哈哈哈', '0', '2018-09-15 00:47:39.649000');

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint(2) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `contentId` int(10) unsigned DEFAULT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of image
-- ----------------------------
INSERT INTO `image` VALUES ('1', '3', 'http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg', null, '0');
INSERT INTO `image` VALUES ('2', '3', 'http://img06.tooopen.com/images/20160818/tooopen_sy_175866434296.jpg', null, '0');
INSERT INTO `image` VALUES ('3', '3', 'http://img06.tooopen.com/images/20160818/tooopen_sy_175833047715.jpg', null, '0');
INSERT INTO `image` VALUES ('15', '1', 'https://i.loli.net/2018/09/14/5b9bb0ace403d.jpg', '21', '0');
INSERT INTO `image` VALUES ('16', '1', 'https://i.loli.net/2018/09/14/5b9bb13598f6d.jpg', '22', '0');
INSERT INTO `image` VALUES ('19', '0', 'https://i.loli.net/2018/09/15/5b9be427b0811.jpg', '20', '0');
INSERT INTO `image` VALUES ('20', '1', 'https://i.loli.net/2018/09/15/5b9beaee419bc.jpg', '27', '0');
INSERT INTO `image` VALUES ('21', '1', 'https://i.loli.net/2018/09/15/5b9beaef92b20.jpg', '27', '0');
INSERT INTO `image` VALUES ('22', '1', 'https://i.loli.net/2018/09/15/5b9beaf1328c3.jpg', '27', '0');
INSERT INTO `image` VALUES ('23', '1', 'https://i.loli.net/2018/09/15/5b9beaf2ac476.jpg', '27', '0');
INSERT INTO `image` VALUES ('24', '1', 'https://i.loli.net/2018/09/15/5b9beaf484647.jpg', '27', '0');
INSERT INTO `image` VALUES ('25', '1', 'https://i.loli.net/2018/09/15/5b9beaf5ccf28.jpg', '27', '0');
INSERT INTO `image` VALUES ('26', '1', 'https://i.loli.net/2018/09/15/5b9beaf76580c.jpg', '27', '0');
INSERT INTO `image` VALUES ('27', '1', 'https://i.loli.net/2018/09/15/5b9beaf8f3769.jpg', '27', '0');
INSERT INTO `image` VALUES ('28', '1', 'https://i.loli.net/2018/09/15/5b9beafa8907e.jpg', '27', '0');
INSERT INTO `image` VALUES ('29', '1', 'https://i.loli.net/2018/09/15/5b9beafc28d89.jpg', '27', '0');
INSERT INTO `image` VALUES ('33', '1', 'https://i.loli.net/2018/09/15/5b9beb029e5cb.jpg', '27', '0');
INSERT INTO `image` VALUES ('34', '1', 'https://i.loli.net/2018/09/15/5b9beb044bf0c.jpg', '27', '0');
INSERT INTO `image` VALUES ('35', '1', 'https://i.loli.net/2018/09/15/5b9beb05aa3e2.jpg', '27', '0');
INSERT INTO `image` VALUES ('36', '1', 'https://i.loli.net/2018/09/15/5b9beb072b279.jpg', '27', '0');
INSERT INTO `image` VALUES ('37', '1', 'https://i.loli.net/2018/09/15/5b9beb08c71bb.jpg', '27', '0');
INSERT INTO `image` VALUES ('38', '1', 'https://i.loli.net/2018/09/15/5b9beb0a4a1ea.jpg', '27', '0');
INSERT INTO `image` VALUES ('39', '1', 'https://i.loli.net/2018/09/15/5b9beb0bcc1a8.jpg', '27', '0');
INSERT INTO `image` VALUES ('40', '1', 'https://i.loli.net/2018/09/15/5b9beb0d61f06.jpg', '27', '0');
INSERT INTO `image` VALUES ('41', '1', 'https://i.loli.net/2018/09/15/5b9beb0ee371d.jpg', '27', '0');
INSERT INTO `image` VALUES ('42', '1', 'https://i.loli.net/2018/09/15/5b9beb106eb87.jpg', '27', '0');
INSERT INTO `image` VALUES ('46', '1', 'https://i.loli.net/2018/09/15/5b9beb16bc107.jpg', '27', '0');
INSERT INTO `image` VALUES ('47', '1', 'https://i.loli.net/2018/09/15/5b9beb1836432.jpg', '27', '0');
INSERT INTO `image` VALUES ('48', '1', 'https://i.loli.net/2018/09/15/5b9beb19b0a84.jpg', '27', '0');
INSERT INTO `image` VALUES ('49', '1', 'https://i.loli.net/2018/09/15/5b9beb1b6e6c3.jpg', '27', '0');
INSERT INTO `image` VALUES ('50', '1', 'https://i.loli.net/2018/09/15/5b9beb1cca1aa.jpg', '27', '0');
INSERT INTO `image` VALUES ('51', '1', 'https://i.loli.net/2018/09/15/5b9c6a7511487.jpg', '28', '0');
INSERT INTO `image` VALUES ('52', '1', 'https://i.loli.net/2018/09/15/5b9c6a77438f7.jpg', '28', '0');
INSERT INTO `image` VALUES ('53', '1', 'https://i.loli.net/2018/09/15/5b9c6a794b4b9.jpg', '28', '0');
INSERT INTO `image` VALUES ('54', '1', 'https://i.loli.net/2018/09/15/5b9c6a7b5f728.jpg', '28', '0');
INSERT INTO `image` VALUES ('55', '1', 'https://i.loli.net/2018/09/15/5b9c6a7d8d9cf.jpg', '28', '0');
INSERT INTO `image` VALUES ('56', '1', 'https://i.loli.net/2018/09/15/5b9c6a7f9eecc.jpg', '28', '0');
INSERT INTO `image` VALUES ('57', '1', 'https://i.loli.net/2018/09/15/5b9c6a81a59ba.jpg', '28', '0');
INSERT INTO `image` VALUES ('58', '1', 'https://i.loli.net/2018/09/15/5b9c6d0234b08.jpg', '29', '0');
INSERT INTO `image` VALUES ('59', '1', 'https://i.loli.net/2018/09/15/5b9c6d2c8891e.png', '30', '0');
INSERT INTO `image` VALUES ('60', '1', 'https://i.loli.net/2018/09/15/5b9c6d307090b.png', '30', '0');
INSERT INTO `image` VALUES ('61', '1', 'https://i.loli.net/2018/09/15/5b9c6e21a4c61.jpg', '31', '0');
INSERT INTO `image` VALUES ('62', '1', 'https://i.loli.net/2018/09/15/5b9c6e316e82d.jpg', '31', '0');

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
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `pUserId` (`pUserId`),
  KEY `pId` (`pId`),
  CONSTRAINT `lostfound_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `lostfound_ibfk_2` FOREIGN KEY (`pUserId`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `lostfound_ibfk_3` FOREIGN KEY (`pId`) REFERENCES `lostfound` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of lostfound
-- ----------------------------
INSERT INTO `lostfound` VALUES ('1', '5', null, null, '测试得失', '-1', '2018-09-14 14:55:47.789000');
INSERT INTO `lostfound` VALUES ('2', '5', null, '1', '测试回复', '-1', '2018-09-14 15:48:50.507000');
INSERT INTO `lostfound` VALUES ('3', '5', null, '1', '测试回复', '-1', '2018-09-14 16:07:11.717000');
INSERT INTO `lostfound` VALUES ('4', '5', null, '1', '测试回复2', '-1', '2018-09-14 17:35:23.826000');

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
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('admin', '1000:a92506ce1e293441a43a2abdca2de1b400ba28ba041fa844:7d9bc03953244579fc85dcdd9d49b259bddd16dc5340f6fc', '6');

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
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('1', 'admin', '第一条公告', null, '1', '2018-08-14 19:09:19.000000', '公告');

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `content` text,
  `createdDate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(2) DEFAULT '0' COMMENT '启用为1，不启用为0',
  `title` varchar(255) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES ('1', 'ssss', '2018-09-15 14:33:39', '0', null, '0');
INSERT INTO `notification` VALUES ('2', 'asdasdasda', '2018-09-15 15:19:50', '0', null, '0');
INSERT INTO `notification` VALUES ('3', '这是一条测试公告', '2018-09-15 15:20:55', '0', null, '0');
INSERT INTO `notification` VALUES ('4', '测试公告', '2018-09-15 15:20:55', '1', null, '0');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `status` tinyint(2) DEFAULT '0' COMMENT '订单状态',
  `createdDate` datetime NOT NULL COMMENT '订单创建时间',
  `completedDate` datetime DEFAULT NULL COMMENT '订单完成时间',
  `acceptUserId` int(10) unsigned DEFAULT NULL COMMENT '接单人id',
  `acceptUserPhoneNumber` varchar(11) DEFAULT NULL COMMENT '接单人手机号',
  `reward` decimal(5,2) DEFAULT NULL COMMENT '订单价格',
  `userId` int(10) unsigned NOT NULL COMMENT '发单人id',
  `userName` varchar(225) DEFAULT NULL COMMENT '发单人姓名',
  `userPhoneNumber` varchar(11) DEFAULT NULL COMMENT '发单人手机号',
  `fromAddress` varchar(255) NOT NULL,
  `toAddress` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `detail` varchar(255) NOT NULL COMMENT '简要概述',
  `availableDateDesc` varchar(255) DEFAULT NULL COMMENT '送达时间段',
  `availableDate` datetime DEFAULT NULL,
  `acceptDate` datetime DEFAULT NULL,
  `settledDate` datetime DEFAULT NULL COMMENT '订单结算时间',
  `canPayBack` tinyint(2) DEFAULT '0' COMMENT '是否可以退款，1是0否',
  PRIMARY KEY (`id`),
  KEY `acceptUserId` (`acceptUserId`),
  KEY `userId` (`userId`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`acceptUserId`) REFERENCES `user` (`id`),
  CONSTRAINT `order_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('1', '5', '2018-09-12 22:52:08', null, null, null, '1.00', '5', '哈哈哈哈', '13156140690', '哈哈哈', '哈哈哈', null, '哈哈哈', '09/12 | 任意时段', '2018-09-12 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('2', '1', '2018-09-13 22:24:05', null, null, null, '2.00', '5', '哈哈哈哈', '13156140690', '哈哈哈', '哈哈哈', null, '哈哈哈', '09/13 | 任意时段', '2018-09-13 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('3', '1', '2018-09-13 22:31:42', null, null, null, '1.00', '5', '哈哈哈', '13156140690', '哈哈哈', '哈哈哈', null, '哈哈哈', '09/13 | 任意时段', '2018-09-13 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('4', '1', '2018-09-13 22:38:58', null, null, null, '1.00', '5', '哈哈哈', '13156140690', '哈哈哈', '哈哈哈', null, '哈哈哈哈', '09/13 | 任意时段', '2018-09-13 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('5', '1', '2018-09-13 22:42:51', null, null, null, '1.00', '5', 'g\'gghg', '13156140690', '111', '111', null, '111', '09/13 | 任意时段', '2018-09-13 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('6', '1', '2018-09-14 00:24:20', null, null, null, '1.00', '5', '哈哈哈', '13156140690', '哈哈哈', '哈哈哈', null, '哈哈哈', '09/14 | 任意时段', '2018-09-14 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('7', '1', '2018-09-14 00:28:46', null, null, null, '1.00', '5', '哈哈哈', '13156140690', '哈哈哈', '哈哈哈', null, '哈哈哈', '09/14 | 任意时段', '2018-09-14 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('8', '1', '2018-09-14 00:31:14', null, null, null, '1.00', '5', '哈哈哈', '13156140690', 'hhh', 'hhh', null, 'hhh', '09/14 | 任意时段', '2018-09-14 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('9', '1', '2018-09-14 00:32:39', null, null, null, '1.00', '5', '，哈哈哈', '13156140690', '韩国', '韩国', null, '韩国', '09/14 | 任意时段', '2018-09-14 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('10', '1', '2018-09-14 00:35:30', null, null, null, '1.00', '5', '啦啦啦啦啦', '13156140690', '哈哈哈', '哈哈哈', null, '哈哈哈', '09/14 | 任意时段', '2018-09-14 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('11', '1', '2018-09-14 00:52:07', null, null, null, '1.00', '5', '啦啦啦啦啦', '13156140690', '哈哈哈', '哈哈哈', null, '哈哈哈', '09/14 | 任意时段', '2018-09-14 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('12', '1', '2018-09-14 10:35:32', null, null, null, '1.00', '5', 'wyxy', '13156140690', '我也想要', '我也想要', null, '11111', '09/14 | 任意时段', '2018-09-14 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('13', '1', '2018-09-14 10:43:45', null, null, null, '20.00', '5', '哈哈哈哈', '13156140690', '22222', '33333', null, '111111', '09/14 | 任意时段', '2018-09-14 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('14', '0', '2018-09-14 10:44:45', null, null, null, '1.00', '5', '？哈哈哈哈哈', '13156140690', '得到', '得到', null, '哈哈哈', '09/14 | 任意时段', '2018-09-14 00:00:00', null, null, '0');
INSERT INTO `order` VALUES ('15', '1', '2018-09-15 14:48:57', null, null, null, '2.00', '7', '李志伟', '19974811435', '后市圆通', '14号楼', '书包', '书包', '09/15 | 08:30-10:30', '2018-09-15 08:30:00', null, null, '0');

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
  `status` tinyint(2) DEFAULT '0',
  `avatarUrl` varchar(255) DEFAULT NULL COMMENT '匿名用户的头像',
  `nameColor` varchar(255) DEFAULT NULL COMMENT '匿名用户昵称的颜色',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `pUserId` (`pUserId`),
  KEY `pId` (`pId`),
  CONSTRAINT `treehole_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `treehole_ibfk_2` FOREIGN KEY (`pUserId`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `treehole_ibfk_3` FOREIGN KEY (`pId`) REFERENCES `treehole` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of treehole
-- ----------------------------
INSERT INTO `treehole` VALUES ('1', '5', null, null, '测试树洞', '2018-09-14 14:54:49.911000', '-1', 'http://www.gravatar.com/avatar/1756457992?s=100&d=robohash', '#F02DF4');
INSERT INTO `treehole` VALUES ('2', '5', null, null, '测试树洞', '2018-09-14 15:02:53.782000', '-1', 'http://www.gravatar.com/avatar/1138939250?s=100&d=wavatar', '#5D0320');
INSERT INTO `treehole` VALUES ('3', '5', null, null, '哈哈哈哈', '2018-09-14 15:29:21.658000', '-1', 'http://www.gravatar.com/avatar/1114952785?s=100&d=wavatar', '#4DD0F7');
INSERT INTO `treehole` VALUES ('4', '5', null, '3', '测试回复', '2018-09-14 15:49:21.462000', '-1', null, null);
INSERT INTO `treehole` VALUES ('5', '5', null, '3', '测试回复', '2018-09-14 16:16:09.523000', '-1', null, null);
INSERT INTO `treehole` VALUES ('6', '5', null, null, '再来一个', '2018-09-14 17:42:57.994000', '0', 'http://www.gravatar.com/avatar/357244347?s=100&d=identicon', '#9FD7FC');
INSERT INTO `treehole` VALUES ('7', '5', null, null, '333', '2018-09-14 18:42:51.702000', '-1', 'http://www.gravatar.com/avatar/1482599955?s=100&d=wavatar', '#D238CC');
INSERT INTO `treehole` VALUES ('8', '5', null, null, '333', '2018-09-14 18:48:18.139000', '-1', 'http://www.gravatar.com/avatar/1626584503?s=100&d=robohash', '#2372F3');
INSERT INTO `treehole` VALUES ('9', '5', null, null, '333', '2018-09-14 19:41:24.465000', '-1', 'http://www.gravatar.com/avatar/1812809771?s=100&d=identicon', '#CE0FEA');
INSERT INTO `treehole` VALUES ('10', '5', null, null, 'come baby', '2018-09-14 19:47:02.983000', '0', 'http://www.gravatar.com/avatar/1209662782?s=100&d=monsterid', '#E00E5F');
INSERT INTO `treehole` VALUES ('13', '5', null, null, '6666', '2018-09-14 20:07:58.621000', '-1', 'http://www.gravatar.com/avatar/1540405786?s=100&d=robohash', '#60C014');
INSERT INTO `treehole` VALUES ('14', '5', null, null, 'aaaaaa', '2018-09-14 20:15:41.158000', '-1', 'http://www.gravatar.com/avatar/1325893986?s=100&d=monsterid', '#9E6015');
INSERT INTO `treehole` VALUES ('15', '5', null, null, '5555', '2018-09-14 20:17:30.758000', '-1', 'http://www.gravatar.com/avatar/2118038403?s=100&d=retro', '#2EC7F9');
INSERT INTO `treehole` VALUES ('16', '5', null, null, '5555', '2018-09-14 20:20:07.288000', '-1', 'http://www.gravatar.com/avatar/1556297635?s=100&d=retro', '#356E8B');
INSERT INTO `treehole` VALUES ('17', '5', null, null, 'eeeeeeeeeeeee', '2018-09-14 20:22:22.066000', '-1', 'http://www.gravatar.com/avatar/1116722618?s=100&d=monsterid', '#411E02');
INSERT INTO `treehole` VALUES ('18', '5', null, null, 'qqqqq', '2018-09-14 20:24:46.879000', '-1', 'http://www.gravatar.com/avatar/323569001?s=100&d=monsterid', '#5B159B');
INSERT INTO `treehole` VALUES ('19', '5', null, null, '111', '2018-09-14 20:32:10.304000', '-1', 'http://www.gravatar.com/avatar/158026157?s=100&d=retro', '#68C10B');
INSERT INTO `treehole` VALUES ('20', '5', null, null, 'bbbbb', '2018-09-14 20:35:54.961000', '-1', 'http://www.gravatar.com/avatar/319852513?s=100&d=identicon', '#3D8309');
INSERT INTO `treehole` VALUES ('21', '5', null, null, 'bbbbb', '2018-09-14 20:59:21.275000', '0', 'http://www.gravatar.com/avatar/619988461?s=100&d=monsterid', '#275308');
INSERT INTO `treehole` VALUES ('22', '5', null, null, 'hhhhhhh', '2018-09-14 21:01:39.155000', '0', 'http://www.gravatar.com/avatar/1628078837?s=100&d=robohash', '#354CC0');
INSERT INTO `treehole` VALUES ('23', '5', null, '22', '怎么样', '2018-09-15 00:37:10.052000', '0', null, null);
INSERT INTO `treehole` VALUES ('24', '5', null, '21', '1234567', '2018-09-15 00:50:01.628000', '0', null, null);
INSERT INTO `treehole` VALUES ('25', '5', null, '21', '这个小伙子睡得真香', '2018-09-15 00:50:12.444000', '0', null, null);
INSERT INTO `treehole` VALUES ('26', '5', null, '22', '这个傻逼是谁', '2018-09-15 00:50:28.404000', '0', null, null);
INSERT INTO `treehole` VALUES ('27', '5', null, null, '哈哈哈哈哈😂😂😂😃😃😃😄😄😄😁😁😁😀😀😀😄😄😉😋😊😌😑😔😐', '2018-09-15 01:07:56.130000', '0', 'http://www.gravatar.com/avatar/1130041723?s=100&d=identicon', '#61DDAB');
INSERT INTO `treehole` VALUES ('28', '5', null, null, 'qsqwsq', '2018-09-15 10:12:02.218000', '0', 'http://www.gravatar.com/avatar/1050194671?s=100&d=retro', '#EBC2D2');
INSERT INTO `treehole` VALUES ('29', '5', null, null, '爱是', '2018-09-15 10:22:47.063000', '0', 'http://www.gravatar.com/avatar/450920504?s=100&d=monsterid', '#3A95F2');
INSERT INTO `treehole` VALUES ('30', '5', null, null, '实打实打算', '2018-09-15 10:23:36.664000', '0', 'http://www.gravatar.com/avatar/586165784?s=100&d=robohash', '#B90D02');
INSERT INTO `treehole` VALUES ('31', '5', null, null, '144', '2018-09-15 10:27:30.972000', '0', 'http://www.gravatar.com/avatar/483515061?s=100&d=wavatar', '#874509');

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
  `realName` varchar(255) DEFAULT NULL,
  `avatarUrl` varchar(255) DEFAULT '/favoicon',
  `gender` int(11) DEFAULT NULL,
  `studentNumber` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', ']dfsad', null, '17753521574', '流年', '11.00', '0', null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('2', 'sdf', null, '10086', '穷奇', '50.00', '1', null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('3', 'dlkfjl', null, '10010', '梼杌', '30.00', '0', null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('4', 'sdfasd', null, '10001', '混沌', '61.00', '-1', null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('5', 'owIql5INIvWo2h_sOJhrBL9UV4_A', 'ltmEMEAS05KfMlx9XyxIWg==', '15115151111', 'laumgjyu', '11.22', '0', null, 'https://i.loli.net/2018/09/15/5b9be11f29777.jpg', '刘明宇', 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaELqmXEhcrKbJeDAM0zhjBkd75vMqpuiciaYGWIDPuRFkUb1E7SMzDDPibn3cgCpgFTicK4FNFalOQkF0Q/132', '1', '201600300000', '山东济南');
INSERT INTO `user` VALUES ('6', 'owIql5IPTz4rgrCGJu5ID0Meljyk', 'wd2sKFYj1m44yB1ncjhNLw==', null, '随缘', '0.00', '0', null, null, null, 'https://wx.qlogo.cn/mmopen/vi_32/cK9VnhBrRaEiaH7nBQ9iaTWs8EIy9lsPNrhUMW25xV12ibK3icj6cEOH4K85Yico88BiadhSGN1iaf9d4KZAVZ6LuZz9w/132', '1', null, null);
INSERT INTO `user` VALUES ('7', 'owIql5BbD4-CiJGR8vpNIVocCRag', 'XF517Z6gW6USMdtAklHJCg==', null, '李志伟', '0.00', '0', null, null, null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKqyXqre4Xp01rFPGKgKQYbU4S8hGgQSPTuEUTdZ7h3gtHmsowvKV82gjjoJ5JT8wEMEKbcFOUMQw/132', '1', null, null);
INSERT INTO `user` VALUES ('8', 'owIql5O8N74TicQjliU0XIs1izmk', 'gbczIKKa2CY09k9XVHC1IA==', null, null, '0.00', '0', null, null, null, '/favoicon', null, null, null);
