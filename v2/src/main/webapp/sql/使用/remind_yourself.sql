/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Schema         : remind_yourself

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 19/08/2019 11:40:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for event
-- ----------------------------
DROP TABLE IF EXISTS `event`;
CREATE TABLE `event`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `establish_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remind_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `for_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of event
-- ----------------------------
INSERT INTO `event` VALUES (19, '2019-06-03 18:51', '2019-06-03 20:19', '测试发送Email功能', '1', '7');
INSERT INTO `event` VALUES (20, '2019-06-03 19:02', '2019-06-03 19:05', '日了虎了', '1', '8');
INSERT INTO `event` VALUES (21, '2019-06-03 19:08', '2019-06-03 19:10', '邮件发送测试', '1', '7');
INSERT INTO `event` VALUES (23, '2019-06-03 20:21', '2019-06-03 20:25', '123456', '1', '8');
INSERT INTO `event` VALUES (24, '2019-06-03 20:29', '2019-06-03 20:30', 'hehushisb', '1', '9');
INSERT INTO `event` VALUES (25, '2019-06-03 22:04', '2019-06-03 23:55', '要睡觉了', '1', '7');
INSERT INTO `event` VALUES (26, '2019-06-04 15:44', '2019-06-04 15:45', 'test', '1', '7');
INSERT INTO `event` VALUES (27, '2019-06-07 22:24', '2019-06-08 09:30', '买一个开关', '1', '7');
INSERT INTO `event` VALUES (28, '2019-06-14 12:12', '2019-06-14 13:40', '买一叠信纸！！！', '1', '7');
INSERT INTO `event` VALUES (29, '2019-06-21 18:21', '2019-06-21 18:25', '揍王成龙', '1', '7');
INSERT INTO `event` VALUES (30, '2019-08-19 10:48', '2019-08-19 11:50', 'test服务器重启之后是否生效', '0', '7');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (7, 'summerki', '591593076', '591593076@qq.com');
INSERT INTO `user` VALUES (8, 'hehehehehe', '123456', '407451969@qq.com');
INSERT INTO `user` VALUES (9, 'injustice', '669008mr', '1723375404@qq.com');

SET FOREIGN_KEY_CHECKS = 1;
