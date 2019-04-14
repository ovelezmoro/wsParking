/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : db_parking_00

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2019-04-14 16:04:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for table_2
-- ----------------------------
DROP TABLE IF EXISTS `table_2`;
CREATE TABLE `table_2` (
  `local` int(11) DEFAULT NULL,
  `dia` int(11) DEFAULT NULL,
  `hora` int(11) DEFAULT NULL,
  `ingreso` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_autorizacion
-- ----------------------------
DROP TABLE IF EXISTS `t_autorizacion`;
CREATE TABLE `t_autorizacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dni` varchar(20) DEFAULT NULL,
  `nombres` varchar(100) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `telefono` varchar(80) DEFAULT NULL,
  `brevete` varchar(50) DEFAULT NULL,
  `tpropiedad` varchar(50) DEFAULT NULL,
  `marca` varchar(50) DEFAULT NULL,
  `modelo` varchar(50) DEFAULT NULL,
  `placa` varchar(50) DEFAULT NULL,
  `color` varchar(60) DEFAULT NULL,
  `ano` varchar(4) DEFAULT NULL,
  `hsalida` varchar(10) DEFAULT NULL,
  `firma` varchar(30) DEFAULT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for t_parqueo
-- ----------------------------
DROP TABLE IF EXISTS `t_parqueo`;
CREATE TABLE `t_parqueo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `placa` varchar(7) NOT NULL,
  `marca` varchar(100) NOT NULL,
  `color` varchar(50) DEFAULT NULL,
  `modelo` varchar(100) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `entrada` varchar(8) DEFAULT NULL,
  `zona` varchar(100) DEFAULT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `id_usuario` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for t_playa
-- ----------------------------
DROP TABLE IF EXISTS `t_playa`;
CREATE TABLE `t_playa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `referencia` varchar(150) DEFAULT NULL,
  `distrito` int(11) DEFAULT NULL,
  `provincia` int(11) DEFAULT NULL,
  `latitud` decimal(10,5) DEFAULT NULL,
  `longitud` decimal(10,5) DEFAULT NULL,
  `capacidad` varchar(45) DEFAULT NULL,
  `abonados` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_reserva
-- ----------------------------
DROP TABLE IF EXISTS `t_reserva`;
CREATE TABLE `t_reserva` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `id_playa` int(11) NOT NULL,
  `fecha_reserva` datetime NOT NULL,
  `precio_reserva` decimal(10,3) DEFAULT NULL,
  `sha_reserva` varchar(255) NOT NULL,
  `id_vehiculo` int(11) NOT NULL,
  `estado` varchar(1) NOT NULL DEFAULT 'R',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_t_reserva_t_usuario1_idx` (`id_usuario`) USING BTREE,
  KEY `fk_t_reserva_t_playa1_idx` (`id_playa`) USING BTREE,
  CONSTRAINT `fk_t_reserva_t_playa1` FOREIGN KEY (`id_playa`) REFERENCES `t_playa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_reserva_t_usuario1` FOREIGN KEY (`id_usuario`) REFERENCES `t_usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_sugerencia_playa
-- ----------------------------
DROP TABLE IF EXISTS `t_sugerencia_playa`;
CREATE TABLE `t_sugerencia_playa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_playa` int(11) NOT NULL,
  `dia` date DEFAULT NULL,
  `hora` int(11) DEFAULT NULL,
  `ingresos` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_t_data_t_playa1_idx` (`id_playa`) USING BTREE,
  CONSTRAINT `fk_t_data_t_playa1` FOREIGN KEY (`id_playa`) REFERENCES `t_playa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1985 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_tarifa
-- ----------------------------
DROP TABLE IF EXISTS `t_tarifa`;
CREATE TABLE `t_tarifa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_playa` int(11) NOT NULL,
  `tarifa_estacionamiento` decimal(15,3) DEFAULT NULL,
  `tarifa_reserva` decimal(15,3) DEFAULT NULL,
  `nueva_tarifa` decimal(15,3) DEFAULT NULL,
  `vigencia` int(11) DEFAULT NULL,
  `fregistro` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id_playa_UNIQUE` (`id_playa`) USING BTREE,
  KEY `fk_table1_t_playa_idx` (`id_playa`) USING BTREE,
  CONSTRAINT `fk_table1_t_playa` FOREIGN KEY (`id_playa`) REFERENCES `t_playa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_usuario
-- ----------------------------
DROP TABLE IF EXISTS `t_usuario`;
CREATE TABLE `t_usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `photoURL` text DEFAULT NULL,
  `uid` text DEFAULT NULL,
  `loginWith` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `tipo` varchar(1) NOT NULL DEFAULT 'U',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_vehiculo
-- ----------------------------
DROP TABLE IF EXISTS `t_vehiculo`;
CREATE TABLE `t_vehiculo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `placa` varchar(255) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_placa` (`placa`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
