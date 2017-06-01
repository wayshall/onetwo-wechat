/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : web-sample

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-01-22 18:04:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for web_user
-- ----------------------------
DROP TABLE IF EXISTS `web_user`;
CREATE TABLE `web_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `NICK_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `PASSWORD` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `EMAIL` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮件',
  `MOBILE` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `GENDER` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `STATUS` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '状态:\r\n            NORMAL-正常\r\n            STOP-停用\r\n            ',
  `BIRTHDAY` datetime DEFAULT NULL COMMENT '出生日期',
  `CREATE_AT` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_AT` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';
