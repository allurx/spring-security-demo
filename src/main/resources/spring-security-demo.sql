/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : spring-security-demo

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 01/07/2019 21:07:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `is_deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除状态，1：删除状态）',
  `creator` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modifier` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_ADMIN', 0, '', '2019-05-23 09:27:49', '', '2019-05-23 09:27:49');
INSERT INTO `role` VALUES (2, 'ROLE_USER', 0, '', '2019-05-23 09:27:57', '', '2019-05-23 09:27:57');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `is_deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除状态，1：删除状态）',
  `creator` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modifier` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$RAhaFX35jJ2b0QfEZ26P.eNECm9hvr52TA4hAuBd41BilB87KWIJu', 0, '', '2019-05-23 09:27:06', '', '2019-05-23 09:43:18');
INSERT INTO `user` VALUES (2, 'user1', '$2a$10$T/X4eGUzKdt6TL7ksAl63eFAt49s4CV3TmGLv62SHAqQuGNrue6dm', 0, '', '2019-05-23 09:27:24', '', '2019-05-23 09:43:28');
INSERT INTO `user` VALUES (3, 'user2', '$2a$10$zDqeCfhjRpGsG4.kcR6lEu.hFg8EwF7wSy87iUi9HLxbWdCUk2Tx6', 0, '', '2019-05-23 09:27:35', '', '2019-05-23 09:43:28');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '用户id',
  `role_id` int(10) UNSIGNED NOT NULL COMMENT '角色id',
  `is_deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除状态，1：删除状态）',
  `creator` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modifier` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1, 0, '', '2019-05-23 09:28:06', '', '2019-05-23 09:28:06');
INSERT INTO `user_role` VALUES (2, 1, 2, 0, '', '2019-05-23 09:28:12', '', '2019-05-23 09:28:12');
INSERT INTO `user_role` VALUES (3, 2, 2, 0, '', '2019-05-23 09:28:19', '', '2019-05-23 09:28:19');
INSERT INTO `user_role` VALUES (4, 3, 2, 0, '', '2019-05-23 09:28:22', '', '2019-05-23 09:28:22');

SET FOREIGN_KEY_CHECKS = 1;
