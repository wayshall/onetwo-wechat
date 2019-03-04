/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : localhost:3306
 Source Schema         : jormtest

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : 65001

 Date: 04/03/2019 23:31:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wx_access_token
-- ----------------------------
DROP TABLE IF EXISTS `wx_access_token`;
CREATE TABLE `wx_access_token`  (
  `wx_appid` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `access_token` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `expires_in` bigint(11) NOT NULL,
  `create_at` datetime(0) NOT NULL,
  `update_at` datetime(0) NOT NULL,
  PRIMARY KEY (`wx_appid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
