-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.45 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.16.0.7229
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para tecnolp0_losmatusv4
CREATE DATABASE IF NOT EXISTS `tecnolp0_losmatusv4` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tecnolp0_losmatusv4`;

-- Volcando estructura para tabla tecnolp0_losmatusv4.admins
CREATE TABLE IF NOT EXISTS `admins` (
  `id_admin` bigint NOT NULL AUTO_INCREMENT,
  `nombre_admin` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `telefono_admin` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `id_nivel_admin` bigint DEFAULT NULL,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id_admin`),
  UNIQUE KEY `UK10n05c2oxup6px9bnv0vc479` (`id_usuario`),
  KEY `FKibwn2d5kpfd2j8o5jm41kth7d` (`id_nivel_admin`),
  CONSTRAINT `FKa9aie6tivfta75kngt117k6ev` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `FKibwn2d5kpfd2j8o5jm41kth7d` FOREIGN KEY (`id_nivel_admin`) REFERENCES `niveles_admin` (`id_nivel_admin`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.areas
CREATE TABLE IF NOT EXISTS `areas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKoxiafm7kisksgy3pncc6e458d` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.articulos_kb
CREATE TABLE IF NOT EXISTS `articulos_kb` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` enum('ARCHIVADO','BORRADOR','PUBLICADO') CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  `fecha_publicacion` datetime(6) DEFAULT NULL,
  `palabras_clave` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `solucion` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `titulo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `id_aprobador` bigint DEFAULT NULL,
  `id_autor` bigint DEFAULT NULL,
  `id_categoria` bigint DEFAULT NULL,
  `id_incidencia_origen` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKc4ni0n3dmgx5v0klbtlqcgqeu` (`id_incidencia_origen`),
  KEY `FKn2nudvqq63w14qqdbgalhovwu` (`id_aprobador`),
  KEY `FKdkjuier71fqfruyen6hnatj26` (`id_autor`),
  KEY `FKswvb23ivlcck1npm9c3l757fd` (`id_categoria`),
  CONSTRAINT `FKdkjuier71fqfruyen6hnatj26` FOREIGN KEY (`id_autor`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `FKmptiw8e5x2rhwtfc45vfjobw0` FOREIGN KEY (`id_incidencia_origen`) REFERENCES `incidencias` (`id_incidencia`),
  CONSTRAINT `FKn2nudvqq63w14qqdbgalhovwu` FOREIGN KEY (`id_aprobador`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `FKswvb23ivlcck1npm9c3l757fd` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.atenciones
CREATE TABLE IF NOT EXISTS `atenciones` (
  `id_atencion` bigint NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) NOT NULL,
  `observaciones` text COLLATE utf8mb3_unicode_ci,
  `id_incidencia` bigint NOT NULL,
  `id_modo_atencion` bigint DEFAULT NULL,
  `id_tecnico` bigint DEFAULT NULL,
  PRIMARY KEY (`id_atencion`),
  KEY `FKc3kwm0sk71x0vov923ymsfkhu` (`id_incidencia`),
  KEY `FK75ikna470b7m7jp753lr622va` (`id_modo_atencion`),
  KEY `FKc0tuniallubs5twkgi3gnlbp` (`id_tecnico`),
  CONSTRAINT `FK75ikna470b7m7jp753lr622va` FOREIGN KEY (`id_modo_atencion`) REFERENCES `modos_atencion` (`id_modo_atencion`),
  CONSTRAINT `FKc0tuniallubs5twkgi3gnlbp` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnicos` (`id_tecnico`),
  CONSTRAINT `FKc3kwm0sk71x0vov923ymsfkhu` FOREIGN KEY (`id_incidencia`) REFERENCES `incidencias` (`id_incidencia`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.auditoria_informes
CREATE TABLE IF NOT EXISTS `auditoria_informes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `accion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `datos_anteriores` json DEFAULT NULL,
  `datos_nuevos` json DEFAULT NULL,
  `fecha_cambio` datetime(6) DEFAULT NULL,
  `id_informe` bigint DEFAULT NULL,
  `usuario_responsable` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.categorias
CREATE TABLE IF NOT EXISTS `categorias` (
  `id_categoria` bigint NOT NULL AUTO_INCREMENT,
  `nombre_categoria` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_categoria`),
  UNIQUE KEY `UK29mosksismnta90dn19gl3gr2` (`nombre_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.chat_incidencia
CREATE TABLE IF NOT EXISTS `chat_incidencia` (
  `id_chat` bigint NOT NULL AUTO_INCREMENT,
  `fecha_envio` datetime(6) DEFAULT NULL,
  `mensaje` text COLLATE utf8mb3_unicode_ci,
  `id_incidencia` bigint NOT NULL,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id_chat`),
  KEY `FKd3lxgbw2rmjk1q1syevpu2drq` (`id_incidencia`),
  KEY `FKp6jbssyxhqvdx1lm2wypacael` (`id_usuario`),
  CONSTRAINT `FKd3lxgbw2rmjk1q1syevpu2drq` FOREIGN KEY (`id_incidencia`) REFERENCES `incidencias` (`id_incidencia`),
  CONSTRAINT `FKp6jbssyxhqvdx1lm2wypacael` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.configuracion_sla
CREATE TABLE IF NOT EXISTS `configuracion_sla` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tiempo_resolucion_horas` int NOT NULL,
  `tiempo_respuesta_horas` int DEFAULT NULL,
  `id_severidad` bigint NOT NULL,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK1rfm5vku80rcm3aerki77mrrk` (`id_usuario`,`id_severidad`),
  KEY `FK4g35jf5nb6oexy54awgc7q5af` (`id_severidad`),
  CONSTRAINT `FK4g35jf5nb6oexy54awgc7q5af` FOREIGN KEY (`id_severidad`) REFERENCES `severidades` (`id_severidad`),
  CONSTRAINT `FKoocyrwucuopruyuw1axxtvjxl` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.costos_incidencia
CREATE TABLE IF NOT EXISTS `costos_incidencia` (
  `id_costo` bigint NOT NULL AUTO_INCREMENT,
  `costo_produccion` double NOT NULL,
  `costo_total` double NOT NULL,
  `costo_venta` double NOT NULL,
  `fecha_registro` datetime(6) NOT NULL,
  `ganancia` double NOT NULL,
  `igv` double NOT NULL,
  `id_incidencia` bigint NOT NULL,
  `id_proveedor` bigint DEFAULT NULL,
  `id_tipo_costo` bigint NOT NULL,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id_costo`),
  KEY `FK36ikk6c23421jr46yl7qt5p6v` (`id_incidencia`),
  KEY `FKeiiawlhtqgovmdruivj0vlkhc` (`id_proveedor`),
  KEY `FK2tqcvj1ls4igk2gybobarg0t2` (`id_tipo_costo`),
  KEY `FKl3wvg5d8j16qnb3clwbqh7tpn` (`id_usuario`),
  CONSTRAINT `FK2tqcvj1ls4igk2gybobarg0t2` FOREIGN KEY (`id_tipo_costo`) REFERENCES `tipo_costo` (`id_tipo_costo`),
  CONSTRAINT `FK36ikk6c23421jr46yl7qt5p6v` FOREIGN KEY (`id_incidencia`) REFERENCES `incidencias` (`id_incidencia`),
  CONSTRAINT `FKeiiawlhtqgovmdruivj0vlkhc` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedores` (`id_proveedor`),
  CONSTRAINT `FKl3wvg5d8j16qnb3clwbqh7tpn` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.costos_solicitud
CREATE TABLE IF NOT EXISTS `costos_solicitud` (
  `id_costo` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `descripcion` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `fecha` datetime(6) DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `monto_total` double DEFAULT NULL,
  `titulo` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `id_producto` bigint DEFAULT NULL,
  `id_proveedor` bigint DEFAULT NULL,
  `id_solicitud` bigint NOT NULL,
  `id_tipo_costo` bigint NOT NULL,
  `costo_produccion` double DEFAULT NULL,
  PRIMARY KEY (`id_costo`),
  KEY `FKo9djbc9xxtl9tbokoebs0dg1w` (`id_producto`),
  KEY `FK2b4gphfdp2ydwar6uwbxyo7od` (`id_proveedor`),
  KEY `FK261eig5w0by5evg69idlij2w9` (`id_solicitud`),
  KEY `FKihi43tgqyx3vklpohtsfmyyxi` (`id_tipo_costo`),
  CONSTRAINT `FK261eig5w0by5evg69idlij2w9` FOREIGN KEY (`id_solicitud`) REFERENCES `solicitudes_servicio` (`id_solicitud`),
  CONSTRAINT `FK2b4gphfdp2ydwar6uwbxyo7od` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedores` (`id_proveedor`),
  CONSTRAINT `FKihi43tgqyx3vklpohtsfmyyxi` FOREIGN KEY (`id_tipo_costo`) REFERENCES `tipo_costo` (`id_tipo_costo`),
  CONSTRAINT `FKo9djbc9xxtl9tbokoebs0dg1w` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=284 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.empresa_detalle
CREATE TABLE IF NOT EXISTS `empresa_detalle` (
  `id_detalle_empresa` bigint NOT NULL AUTO_INCREMENT,
  `razon_social` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `representante_legal` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ruc_empresa` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_detalle_empresa`),
  UNIQUE KEY `UK4pndifayxj8goyfnmmgswmt2y` (`ruc_empresa`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.empresas
CREATE TABLE IF NOT EXISTS `empresas` (
  `id_empresa` bigint NOT NULL AUTO_INCREMENT,
  `direccion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `nombre_sede` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `representante_sede` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `telefono_empresa` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `url_direccion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `id_detalle_empresa` bigint NOT NULL,
  `usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id_empresa`),
  UNIQUE KEY `UKa1bdrk80mj5pb9r8emyhgf82n` (`usuario_id`),
  KEY `FKrxe9p9o8hrdgu3iktwfeubx1e` (`id_detalle_empresa`),
  CONSTRAINT `FK18r3jxa6l8a3wi8dngya6kx9t` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `FKrxe9p9o8hrdgu3iktwfeubx1e` FOREIGN KEY (`id_detalle_empresa`) REFERENCES `empresa_detalle` (`id_detalle_empresa`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.encuestas_satisfaccion
CREATE TABLE IF NOT EXISTS `encuestas_satisfaccion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comentario_general` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `fecha_encuesta` datetime(6) DEFAULT NULL,
  `respuesta_1` int NOT NULL,
  `respuesta_2` int NOT NULL,
  `respuesta_3` int NOT NULL,
  `id_incidencia` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKtrd5g73ywbamvouyrw20sjqco` (`id_incidencia`),
  CONSTRAINT `FKac9n929ccdoxoux5xsajhld8k` FOREIGN KEY (`id_incidencia`) REFERENCES `incidencias` (`id_incidencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.equipos
CREATE TABLE IF NOT EXISTS `equipos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo_inventario` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `estado` enum('OPERATIVO','EN_MANTENIMIENTO','INOPERATIVO','DE_BAJA') COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `fecha_adquisicion` date DEFAULT NULL,
  `fecha_ultimo_mantenimiento` date DEFAULT NULL,
  `marca` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `modelo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `numero_serie` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ubicacion_fisica` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `url_acceso` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `id_empresa` bigint DEFAULT NULL,
  `id_tipo_equipo` bigint NOT NULL,
  `id_usuario_asignado` bigint DEFAULT NULL,
  `frecuencia_mantenimiento` int DEFAULT NULL,
  `id_area` bigint NOT NULL,
  `id_sede` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdtn0is38nxdgc87gvvljxl63t` (`codigo_inventario`),
  KEY `FKgalmkx16cnu3mcuopgcrvmqxs` (`id_empresa`),
  KEY `FKl6hkuui41nspg26w1vt1tbu0q` (`id_tipo_equipo`),
  KEY `FKj8yu5m3isqm850oyw20hsfbex` (`id_usuario_asignado`),
  KEY `FK8s61aagxws2mtlypmsyf18695` (`id_area`),
  KEY `FKfdvjdxfnf6srnco29ly85ui35` (`id_sede`),
  CONSTRAINT `FK8s61aagxws2mtlypmsyf18695` FOREIGN KEY (`id_area`) REFERENCES `areas` (`id`),
  CONSTRAINT `FKfdvjdxfnf6srnco29ly85ui35` FOREIGN KEY (`id_sede`) REFERENCES `sedes` (`id`),
  CONSTRAINT `FKgalmkx16cnu3mcuopgcrvmqxs` FOREIGN KEY (`id_empresa`) REFERENCES `empresas` (`id_empresa`),
  CONSTRAINT `FKj8yu5m3isqm850oyw20hsfbex` FOREIGN KEY (`id_usuario_asignado`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `FKl6hkuui41nspg26w1vt1tbu0q` FOREIGN KEY (`id_tipo_equipo`) REFERENCES `tipos_equipo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.estado_solicitud_servicio
CREATE TABLE IF NOT EXISTS `estado_solicitud_servicio` (
  `id_estado` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.estados_incidencia
CREATE TABLE IF NOT EXISTS `estados_incidencia` (
  `id_estado_incidencia` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_estado_incidencia`),
  UNIQUE KEY `UK8du2ds9n36y1dckqqsa2742yw` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.estados_tecnico
CREATE TABLE IF NOT EXISTS `estados_tecnico` (
  `id_estado_tecnico` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_estado_tecnico`),
  UNIQUE KEY `UK5qqp1wph340yp9hp6q7xhsdon` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.historial_cotizacion
CREATE TABLE IF NOT EXISTS `historial_cotizacion` (
  `id_historial_cotizacion` bigint NOT NULL AUTO_INCREMENT,
  `detalles_json` json DEFAULT NULL,
  `fecha_generacion` datetime(6) DEFAULT NULL,
  `monto_total_historico` double DEFAULT NULL,
  `ruta_archivo_pdf` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `version` int DEFAULT NULL,
  `id_solicitud` bigint NOT NULL,
  `id_usuario` bigint NOT NULL,
  `anio` int DEFAULT NULL,
  `codigo` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `correlativo` int DEFAULT NULL,
  PRIMARY KEY (`id_historial_cotizacion`),
  KEY `FKdh3wy55xf5ugfsqlidku19woq` (`id_solicitud`),
  KEY `FK3p8pxy0eg214j08d7d1114v93` (`id_usuario`),
  CONSTRAINT `FK3p8pxy0eg214j08d7d1114v93` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `FKdh3wy55xf5ugfsqlidku19woq` FOREIGN KEY (`id_solicitud`) REFERENCES `solicitudes_servicio` (`id_solicitud`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.historial_incidencia
CREATE TABLE IF NOT EXISTS `historial_incidencia` (
  `id_historial` bigint NOT NULL AUTO_INCREMENT,
  `fecha_cambio` datetime(6) NOT NULL,
  `id_estado_incidencia` bigint DEFAULT NULL,
  `id_incidencia` bigint DEFAULT NULL,
  `id_usuario` bigint DEFAULT NULL,
  PRIMARY KEY (`id_historial`),
  KEY `FKs6d2ylsqt1yq5bs43m0sqnxuu` (`id_estado_incidencia`),
  KEY `FK9jlt12tow6ftxbwj1v76hy0y4` (`id_incidencia`),
  KEY `FK34v0xjq8b2p1eqan488qwhmis` (`id_usuario`),
  CONSTRAINT `FK34v0xjq8b2p1eqan488qwhmis` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `FK9jlt12tow6ftxbwj1v76hy0y4` FOREIGN KEY (`id_incidencia`) REFERENCES `incidencias` (`id_incidencia`),
  CONSTRAINT `FKs6d2ylsqt1yq5bs43m0sqnxuu` FOREIGN KEY (`id_estado_incidencia`) REFERENCES `estados_incidencia` (`id_estado_incidencia`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.historial_mantenimiento_equipos
CREATE TABLE IF NOT EXISTS `historial_mantenimiento_equipos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `fecha_evento` datetime(6) DEFAULT NULL,
  `id_informe` bigint DEFAULT NULL,
  `tecnico_responsable` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `tipo_evento` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `id_equipo` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkssjl8adk0ra2pflmjshnuk8x` (`id_equipo`),
  CONSTRAINT `FKkssjl8adk0ra2pflmjshnuk8x` FOREIGN KEY (`id_equipo`) REFERENCES `equipos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.historial_solicitud_servicio
CREATE TABLE IF NOT EXISTS `historial_solicitud_servicio` (
  `id_historial` bigint NOT NULL AUTO_INCREMENT,
  `detalle` text COLLATE utf8mb3_unicode_ci,
  `fecha_cambio` datetime(6) DEFAULT NULL,
  `id_estado` bigint DEFAULT NULL,
  `id_severidad` bigint DEFAULT NULL,
  `id_solicitud` bigint NOT NULL,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id_historial`),
  KEY `FKt9bcxc6dibx8b8be79j1oaxqh` (`id_estado`),
  KEY `FK4wjbcbjgg676fu2xc7o164bde` (`id_severidad`),
  KEY `FKogmroj0bevtn6m5w82r2hw7s3` (`id_solicitud`),
  KEY `FKiad834r4wq3iecuro590m4g9g` (`id_usuario`),
  CONSTRAINT `FK4wjbcbjgg676fu2xc7o164bde` FOREIGN KEY (`id_severidad`) REFERENCES `severidades` (`id_severidad`),
  CONSTRAINT `FKiad834r4wq3iecuro590m4g9g` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `FKogmroj0bevtn6m5w82r2hw7s3` FOREIGN KEY (`id_solicitud`) REFERENCES `solicitudes_servicio` (`id_solicitud`),
  CONSTRAINT `FKt9bcxc6dibx8b8be79j1oaxqh` FOREIGN KEY (`id_estado`) REFERENCES `estado_solicitud_servicio` (`id_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.imagenes_chat
CREATE TABLE IF NOT EXISTS `imagenes_chat` (
  `id_imagen_chat` bigint NOT NULL AUTO_INCREMENT,
  `fecha_subida` datetime(6) DEFAULT NULL,
  `url_imagen` text COLLATE utf8mb3_unicode_ci NOT NULL,
  `id_chat` bigint NOT NULL,
  PRIMARY KEY (`id_imagen_chat`),
  KEY `FKdnw7ualx48mdv3p9akjvsjsxc` (`id_chat`),
  CONSTRAINT `FKdnw7ualx48mdv3p9akjvsjsxc` FOREIGN KEY (`id_chat`) REFERENCES `chat_incidencia` (`id_chat`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.imagenes_incidencia
CREATE TABLE IF NOT EXISTS `imagenes_incidencia` (
  `id_imagen` bigint NOT NULL AUTO_INCREMENT,
  `fecha_subida` datetime(6) DEFAULT NULL,
  `url_imagen` text COLLATE utf8mb3_unicode_ci NOT NULL,
  `id_incidencia` bigint NOT NULL,
  PRIMARY KEY (`id_imagen`),
  KEY `FKd4i6d61vp7ulmfnvmau1dap2p` (`id_incidencia`),
  CONSTRAINT `FKd4i6d61vp7ulmfnvmau1dap2p` FOREIGN KEY (`id_incidencia`) REFERENCES `incidencias` (`id_incidencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.imagenes_solicitud
CREATE TABLE IF NOT EXISTS `imagenes_solicitud` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_subida` datetime(6) DEFAULT NULL,
  `url_imagen` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `solicitud_id` bigint NOT NULL,
  `tipo_documento` varchar(50) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpymo9wqfgpunnkd8ad1xmvuue` (`solicitud_id`),
  CONSTRAINT `FKpymo9wqfgpunnkd8ad1xmvuue` FOREIGN KEY (`solicitud_id`) REFERENCES `solicitudes_servicio` (`id_solicitud`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.incidencias
CREATE TABLE IF NOT EXISTS `incidencias` (
  `id_incidencia` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `cumple_sla` bit(1) DEFAULT NULL,
  `descripcion` text COLLATE utf8mb3_unicode_ci NOT NULL,
  `fecha_finalizacion` datetime(6) DEFAULT NULL,
  `fecha_inicio_atencion` datetime(6) DEFAULT NULL,
  `fecha_limite_resolucion` datetime(6) DEFAULT NULL,
  `fecha_reporte` datetime(6) DEFAULT NULL,
  `requiere_confirmacion_usuario` bit(1) DEFAULT NULL,
  `id_categoria` bigint DEFAULT NULL,
  `id_estado_incidencia` bigint DEFAULT NULL,
  `id_severidad` bigint DEFAULT NULL,
  `id_usuario_reporta` bigint DEFAULT NULL,
  PRIMARY KEY (`id_incidencia`),
  UNIQUE KEY `UKmuip9ybpsxryo7uidpqgkr1fi` (`codigo`),
  KEY `FK4nmkpmu00y1lwq5uathvmd8x2` (`id_categoria`),
  KEY `FKlp7njf8ed482xxp9jijaxnu60` (`id_estado_incidencia`),
  KEY `FKtex36yd9mg1jkcwntc2y3e4ps` (`id_severidad`),
  KEY `FK2k7mpp90w60ir8ksv3yrdq6mv` (`id_usuario_reporta`),
  CONSTRAINT `FK2k7mpp90w60ir8ksv3yrdq6mv` FOREIGN KEY (`id_usuario_reporta`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `FK4nmkpmu00y1lwq5uathvmd8x2` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id_categoria`),
  CONSTRAINT `FKlp7njf8ed482xxp9jijaxnu60` FOREIGN KEY (`id_estado_incidencia`) REFERENCES `estados_incidencia` (`id_estado_incidencia`),
  CONSTRAINT `FKtex36yd9mg1jkcwntc2y3e4ps` FOREIGN KEY (`id_severidad`) REFERENCES `severidades` (`id_severidad`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.informes_mantenimiento
CREATE TABLE IF NOT EXISTS `informes_mantenimiento` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `datos_dinamicos` json DEFAULT NULL,
  `estado` enum('BORRADOR','FINALIZADO') CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `fecha_ejecucion` datetime(6) DEFAULT NULL,
  `fotos_url` json DEFAULT NULL,
  `id_equipo` bigint DEFAULT NULL,
  `id_plantilla` bigint DEFAULT NULL,
  `id_tecnico` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqy45ix3sj9v1r5as9rnlmci3t` (`codigo`),
  KEY `FKntxiv89yvefxtcnlsqjqcj9d0` (`id_equipo`),
  KEY `FKsakajxxn1akkmrb5lii6wxrqv` (`id_plantilla`),
  KEY `FKi54lvkelsudkctm3pjmtfvlkg` (`id_tecnico`),
  CONSTRAINT `FKi54lvkelsudkctm3pjmtfvlkg` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnicos` (`id_tecnico`),
  CONSTRAINT `FKntxiv89yvefxtcnlsqjqcj9d0` FOREIGN KEY (`id_equipo`) REFERENCES `equipos` (`id`),
  CONSTRAINT `FKsakajxxn1akkmrb5lii6wxrqv` FOREIGN KEY (`id_plantilla`) REFERENCES `plantillas_checklist` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.modos_atencion
CREATE TABLE IF NOT EXISTS `modos_atencion` (
  `id_modo_atencion` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_modo_atencion`),
  UNIQUE KEY `UKax1mqeicta8qcf0val42uylso` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.modulos
CREATE TABLE IF NOT EXISTS `modulos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKed90qtnf0dgq16t95s0p6je1j` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.niveles_admin
CREATE TABLE IF NOT EXISTS `niveles_admin` (
  `id_nivel_admin` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_nivel_admin`),
  UNIQUE KEY `UKekdbr4a01ows9p55l7s9o62ed` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.otp_codes
CREATE TABLE IF NOT EXISTS `otp_codes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `expiration` datetime(6) NOT NULL,
  `used` bit(1) NOT NULL,
  `id_usuario` bigint NOT NULL,
  `type` enum('LOGIN','PASSWORD_CHANGE','PASSWORD_RESET') CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf0ht7mjkekftgj23oxj5sqi11` (`id_usuario`),
  CONSTRAINT `FKf0ht7mjkekftgj23oxj5sqi11` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=317 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.personas
CREATE TABLE IF NOT EXISTS `personas` (
  `id_persona` bigint NOT NULL AUTO_INCREMENT,
  `ape_materno_persona` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ape_paterno_persona` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `direccion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `dni` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `nombre_persona` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `telefono` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `url_direccion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id_persona`),
  UNIQUE KEY `UKnnr7w7h44crpy3wdqmk1724hp` (`dni`),
  UNIQUE KEY `UKho8eekk9ksk8ib2036f1somr7` (`usuario_id`),
  CONSTRAINT `FKn07bnub0pbsug2fxbetrdcutx` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.plantillas_checklist
CREATE TABLE IF NOT EXISTS `plantillas_checklist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estructura` json DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `tipo_equipo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.productos
CREATE TABLE IF NOT EXISTS `productos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `precio_produccion` double DEFAULT NULL,
  `precio_venta` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.proveedores
CREATE TABLE IF NOT EXISTS `proveedores` (
  `id_proveedor` bigint NOT NULL AUTO_INCREMENT,
  `contacto` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `direccion` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `estado` int NOT NULL,
  `nombre_proveedor` varchar(150) COLLATE utf8mb3_unicode_ci NOT NULL,
  `ruc_proveedor` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_proveedor`),
  UNIQUE KEY `UKe7wu55ngsdnniw75r14rtgbr2` (`ruc_proveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id_rol` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_rol`),
  UNIQUE KEY `UKldv0v52e0udsh2h1rs0r0gw1n` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.sedes
CREATE TABLE IF NOT EXISTS `sedes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `nombre_encargado_sede` varchar(150) DEFAULT NULL,
  `nombre_sede` varchar(150) NOT NULL,
  `ubicacion_sede` varchar(250) DEFAULT NULL,
  `ubicacion_sede_url` varchar(500) DEFAULT NULL,
  `id_empresa_legal` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKxwky297nm0w6p005fo6ufhjb` (`id_empresa_legal`),
  CONSTRAINT `FKxwky297nm0w6p005fo6ufhjb` FOREIGN KEY (`id_empresa_legal`) REFERENCES `empresa_detalle` (`id_detalle_empresa`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.severidades
CREATE TABLE IF NOT EXISTS `severidades` (
  `id_severidad` bigint NOT NULL AUTO_INCREMENT,
  `nivel` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_severidad`),
  UNIQUE KEY `UKpj3l0kx998gygw2fstqphdw7i` (`nivel`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.solicitudes_servicio
CREATE TABLE IF NOT EXISTS `solicitudes_servicio` (
  `id_solicitud` bigint NOT NULL AUTO_INCREMENT,
  `anio` int DEFAULT NULL,
  `codigo` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `correlativo` int DEFAULT NULL,
  `descripcion` text COLLATE utf8mb3_unicode_ci,
  `fecha_solicitud` datetime(6) DEFAULT NULL,
  `id_estado` bigint NOT NULL,
  `id_severidad` bigint NOT NULL,
  `id_tipo_servicio` bigint NOT NULL,
  `id_usuario` bigint NOT NULL,
  `requiere_cotizacion` bit(1) NOT NULL,
  PRIMARY KEY (`id_solicitud`),
  UNIQUE KEY `UKh3ep786g1odajxxopl95w1p20` (`codigo`),
  KEY `FKrydlv58qdobbtaob3mr356wup` (`id_estado`),
  KEY `FK7mjyuuvrys1dld5a4ump1v3ia` (`id_severidad`),
  KEY `FKn78uwih1kgegs1kc7p3ac5pc7` (`id_tipo_servicio`),
  KEY `FK2ro4voineepgxsxbjd39fpylf` (`id_usuario`),
  CONSTRAINT `FK2ro4voineepgxsxbjd39fpylf` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `FK7mjyuuvrys1dld5a4ump1v3ia` FOREIGN KEY (`id_severidad`) REFERENCES `severidades` (`id_severidad`),
  CONSTRAINT `FKn78uwih1kgegs1kc7p3ac5pc7` FOREIGN KEY (`id_tipo_servicio`) REFERENCES `tipo_servicio` (`id_tipo_servicio`),
  CONSTRAINT `FKrydlv58qdobbtaob3mr356wup` FOREIGN KEY (`id_estado`) REFERENCES `estado_solicitud_servicio` (`id_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.suscripciones
CREATE TABLE IF NOT EXISTS `suscripciones` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `fecha_fin` datetime(6) DEFAULT NULL,
  `fecha_inicio` datetime(6) DEFAULT NULL,
  `modulo_id` bigint NOT NULL,
  `usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKiib9vpykrll1qom0310as9nrw` (`usuario_id`,`modulo_id`),
  KEY `FKip880ro2dm2ci97es538288iq` (`modulo_id`),
  CONSTRAINT `FKip880ro2dm2ci97es538288iq` FOREIGN KEY (`modulo_id`) REFERENCES `modulos` (`id`),
  CONSTRAINT `FKrrsvcjm7l6p0pr4l8ehygsmuy` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.tecnico_especialidad
CREATE TABLE IF NOT EXISTS `tecnico_especialidad` (
  `id_tec_esp` bigint NOT NULL AUTO_INCREMENT,
  `id_especialidad` bigint NOT NULL,
  `id_tecnico` bigint NOT NULL,
  PRIMARY KEY (`id_tec_esp`),
  KEY `FKiyrodul7s3mkdxa0k80mg62m3` (`id_especialidad`),
  KEY `FKtimku67kpn4di8gb912vxbnn9` (`id_tecnico`),
  CONSTRAINT `FKiyrodul7s3mkdxa0k80mg62m3` FOREIGN KEY (`id_especialidad`) REFERENCES `tipo_especialidad` (`id_especialidad`),
  CONSTRAINT `FKtimku67kpn4di8gb912vxbnn9` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnicos` (`id_tecnico`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.tecnicos
CREATE TABLE IF NOT EXISTS `tecnicos` (
  `id_tecnico` bigint NOT NULL AUTO_INCREMENT,
  `ape_materno_tecnico` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ape_paterno_tecnico` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `dni_tecnico` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `nombre_tecnico` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `telefono` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `id_estado_tecnico` bigint DEFAULT NULL,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id_tecnico`),
  UNIQUE KEY `UKc8f6gbl9sa99ex87usxyn7qr0` (`id_usuario`),
  KEY `FKkaah5fem9kg5kdacg3qu98hib` (`id_estado_tecnico`),
  CONSTRAINT `FKaf1ukxv0915dldxk988xnhc7v` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `FKkaah5fem9kg5kdacg3qu98hib` FOREIGN KEY (`id_estado_tecnico`) REFERENCES `estados_tecnico` (`id_estado_tecnico`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.tipo_costo
CREATE TABLE IF NOT EXISTS `tipo_costo` (
  `id_tipo_costo` bigint NOT NULL AUTO_INCREMENT,
  `nombre_tipo_costo` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_tipo_costo`),
  UNIQUE KEY `UK1ydtpbfgn4orpwq5l9hbj1hd` (`nombre_tipo_costo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.tipo_especialidad
CREATE TABLE IF NOT EXISTS `tipo_especialidad` (
  `id_especialidad` bigint NOT NULL AUTO_INCREMENT,
  `nombre_especialidad` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_especialidad`),
  UNIQUE KEY `UKe2uq7hgtt06w3axnteg37qtue` (`nombre_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.tipo_servicio
CREATE TABLE IF NOT EXISTS `tipo_servicio` (
  `id_tipo_servicio` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_tipo_servicio`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.tipos_equipo
CREATE TABLE IF NOT EXISTS `tipos_equipo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `frecuencia_mantenimiento_sugerida` int DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKo8u204ik14t3gscjlhu1tvgmm` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.usuario_sede_area
CREATE TABLE IF NOT EXISTS `usuario_sede_area` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_area` bigint NOT NULL,
  `id_sede` bigint NOT NULL,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjg5b3snbpa7aog20bjtoyaif2` (`id_area`),
  KEY `FK87pmn349yj0ae88hjim9xc1eu` (`id_sede`),
  KEY `FKev6a201d6vvm76f0ri7c08d0j` (`id_usuario`),
  CONSTRAINT `FK87pmn349yj0ae88hjim9xc1eu` FOREIGN KEY (`id_sede`) REFERENCES `sedes` (`id`),
  CONSTRAINT `FKev6a201d6vvm76f0ri7c08d0j` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `FKjg5b3snbpa7aog20bjtoyaif2` FOREIGN KEY (`id_area`) REFERENCES `areas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tecnolp0_losmatusv4.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `email` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `id_rol` bigint NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UKkfsp0s1tflm1cwlj8idhqsad0` (`email`),
  KEY `FK3kl77pehgupicftwfreqnjkll` (`id_rol`),
  CONSTRAINT `FK3kl77pehgupicftwfreqnjkll` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- La exportación de datos fue deseleccionada.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
