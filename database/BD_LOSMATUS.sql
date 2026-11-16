CREATE DATABASE IF NOT EXISTS `losmatus_db`
USE `losmatus_db`;

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

CREATE TABLE IF NOT EXISTS `areas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKoxiafm7kisksgy3pncc6e458d` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


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

CREATE TABLE IF NOT EXISTS `categorias` (
  `id_categoria` bigint NOT NULL AUTO_INCREMENT,
  `nombre_categoria` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_categoria`),
  UNIQUE KEY `UK29mosksismnta90dn19gl3gr2` (`nombre_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

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

CREATE TABLE IF NOT EXISTS `empresa_detalle` (
  `id_detalle_empresa` bigint NOT NULL AUTO_INCREMENT,
  `razon_social` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `representante_legal` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ruc_empresa` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_detalle_empresa`),
  UNIQUE KEY `UK4pndifayxj8goyfnmmgswmt2y` (`ruc_empresa`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

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

CREATE TABLE IF NOT EXISTS `estado_solicitud_servicio` (
  `id_estado` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

CREATE TABLE IF NOT EXISTS `estados_incidencia` (
  `id_estado_incidencia` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_estado_incidencia`),
  UNIQUE KEY `UK8du2ds9n36y1dckqqsa2742yw` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

CREATE TABLE IF NOT EXISTS `estados_tecnico` (
  `id_estado_tecnico` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_estado_tecnico`),
  UNIQUE KEY `UK5qqp1wph340yp9hp6q7xhsdon` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

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

CREATE TABLE IF NOT EXISTS `imagenes_chat` (
  `id_imagen_chat` bigint NOT NULL AUTO_INCREMENT,
  `fecha_subida` datetime(6) DEFAULT NULL,
  `url_imagen` text COLLATE utf8mb3_unicode_ci NOT NULL,
  `id_chat` bigint NOT NULL,
  PRIMARY KEY (`id_imagen_chat`),
  KEY `FKdnw7ualx48mdv3p9akjvsjsxc` (`id_chat`),
  CONSTRAINT `FKdnw7ualx48mdv3p9akjvsjsxc` FOREIGN KEY (`id_chat`) REFERENCES `chat_incidencia` (`id_chat`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

CREATE TABLE IF NOT EXISTS `imagenes_incidencia` (
  `id_imagen` bigint NOT NULL AUTO_INCREMENT,
  `fecha_subida` datetime(6) DEFAULT NULL,
  `url_imagen` text COLLATE utf8mb3_unicode_ci NOT NULL,
  `id_incidencia` bigint NOT NULL,
  PRIMARY KEY (`id_imagen`),
  KEY `FKd4i6d61vp7ulmfnvmau1dap2p` (`id_incidencia`),
  CONSTRAINT `FKd4i6d61vp7ulmfnvmau1dap2p` FOREIGN KEY (`id_incidencia`) REFERENCES `incidencias` (`id_incidencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

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

CREATE TABLE IF NOT EXISTS `modos_atencion` (
  `id_modo_atencion` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_modo_atencion`),
  UNIQUE KEY `UKax1mqeicta8qcf0val42uylso` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

CREATE TABLE IF NOT EXISTS `modulos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKed90qtnf0dgq16t95s0p6je1j` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

CREATE TABLE IF NOT EXISTS `niveles_admin` (
  `id_nivel_admin` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_nivel_admin`),
  UNIQUE KEY `UKekdbr4a01ows9p55l7s9o62ed` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

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

CREATE TABLE IF NOT EXISTS `plantillas_checklist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estructura` json DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `tipo_equipo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

CREATE TABLE IF NOT EXISTS `productos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `precio_produccion` double DEFAULT NULL,
  `precio_venta` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

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

CREATE TABLE IF NOT EXISTS `roles` (
  `id_rol` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_rol`),
  UNIQUE KEY `UKldv0v52e0udsh2h1rs0r0gw1n` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

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

CREATE TABLE IF NOT EXISTS `severidades` (
  `id_severidad` bigint NOT NULL AUTO_INCREMENT,
  `nivel` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_severidad`),
  UNIQUE KEY `UKpj3l0kx998gygw2fstqphdw7i` (`nivel`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

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

CREATE TABLE IF NOT EXISTS `tipo_costo` (
  `id_tipo_costo` bigint NOT NULL AUTO_INCREMENT,
  `nombre_tipo_costo` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_tipo_costo`),
  UNIQUE KEY `UK1ydtpbfgn4orpwq5l9hbj1hd` (`nombre_tipo_costo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

CREATE TABLE IF NOT EXISTS `tipo_especialidad` (
  `id_especialidad` bigint NOT NULL AUTO_INCREMENT,
  `nombre_especialidad` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id_especialidad`),
  UNIQUE KEY `UKe2uq7hgtt06w3axnteg37qtue` (`nombre_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

CREATE TABLE IF NOT EXISTS `tipo_servicio` (
  `id_tipo_servicio` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_tipo_servicio`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

CREATE TABLE IF NOT EXISTS `tipos_equipo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `frecuencia_mantenimiento_sugerida` int DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKo8u204ik14t3gscjlhu1tvgmm` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

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


USE `losmatus_db`;
START TRANSACTION;

INSERT INTO `roles` (`id_rol`, `nombre`) VALUES
  (1, 'ADMIN'),
  (2, 'TECNICO'),
  (3, 'CLIENTE');

INSERT INTO `usuarios` (`id_usuario`, `activo`, `email`, `fecha_creacion`, `password`, `id_rol`) VALUES
  (1, b'1', 'admin.demo@example.com', '2026-10-09 09:30:00', 'CUENTA_DEMOSTRACION_SIN_LOGIN', 1),
  (2, b'1', 'tecnico1.demo@example.com', '2026-10-10 09:30:00', 'CUENTA_DEMOSTRACION_SIN_LOGIN', 2),
  (3, b'1', 'cliente1.demo@example.com', '2026-10-11 09:30:00', 'CUENTA_DEMOSTRACION_SIN_LOGIN', 3),
  (4, b'1', 'tecnico2.demo@example.com', '2026-10-12 09:30:00', 'CUENTA_DEMOSTRACION_SIN_LOGIN', 2),
  (5, b'1', 'cliente2.demo@example.com', '2026-10-13 09:30:00', 'CUENTA_DEMOSTRACION_SIN_LOGIN', 3),
  (6, b'1', 'supervisor.demo@example.com', '2026-10-14 09:30:00', 'CUENTA_DEMOSTRACION_SIN_LOGIN', 1);

INSERT INTO `niveles_admin` (`id_nivel_admin`, `nombre`) VALUES
  (1, 'Administrador'),
  (2, 'Supervisor'),
  (3, 'Coordinador');

INSERT INTO `admins` (`id_admin`, `nombre_admin`, `telefono_admin`, `id_nivel_admin`, `id_usuario`) VALUES
  (1, 'Andrea Demo', '000000001', 1, 1),
  (2, 'Marcos Supervisor', '000000002', 2, 6);

INSERT INTO `areas` (`id`, `nombre`) VALUES
  (1, 'Sistemas'),
  (2, 'Operaciones'),
  (3, 'Administración');

INSERT INTO `categorias` (`id_categoria`, `nombre_categoria`) VALUES
  (1, 'Hardware'),
  (2, 'Software'),
  (3, 'Redes');

INSERT INTO `estados_incidencia` (`id_estado_incidencia`, `nombre`) VALUES
  (1, 'Abierta'),
  (2, 'En atención'),
  (3, 'Resuelta');

INSERT INTO `severidades` (`id_severidad`, `nivel`) VALUES
  (1, 'Baja'),
  (2, 'Media'),
  (3, 'Alta');

INSERT INTO `incidencias` (`id_incidencia`, `codigo`, `cumple_sla`, `descripcion`, `fecha_finalizacion`, `fecha_inicio_atencion`, `fecha_limite_resolucion`, `fecha_reporte`, `requiere_confirmacion_usuario`, `id_categoria`, `id_estado_incidencia`, `id_severidad`, `id_usuario_reporta`) VALUES
  (1, 'INC-2026-001', b'1', 'La laptop de recepción no enciende al iniciar la jornada.', '2026-10-14 09:30:00', '2026-10-09 09:30:00', '2026-10-14 09:30:00', '2026-10-11 09:30:00', b'1', 1, 1, 3, 3),
  (2, 'INC-2026-002', b'1', 'No se puede acceder al sistema de tickets desde el navegador.', '2026-10-15 09:30:00', '2026-10-10 09:30:00', '2026-10-15 09:30:00', '2026-10-12 09:30:00', b'1', 2, 2, 2, 5),
  (3, 'INC-2026-003', b'0', 'La impresora de administración presenta atascos frecuentes.', '2026-10-16 09:30:00', '2026-10-11 09:30:00', '2026-10-16 09:30:00', '2026-10-13 09:30:00', b'1', 1, 3, 1, 3),
  (4, 'INC-2026-004', b'1', 'La red de la sede Norte presenta interrupciones intermitentes.', '2026-10-17 09:30:00', '2026-10-12 09:30:00', '2026-10-17 09:30:00', '2026-10-14 09:30:00', b'1', 3, 2, 3, 5),
  (5, 'INC-2026-005', b'1', 'Un equipo de trabajo funciona con mucha lentitud.', '2026-10-18 09:30:00', '2026-10-13 09:30:00', '2026-10-18 09:30:00', '2026-10-15 09:30:00', b'1', 1, 1, 2, 3),
  (6, 'INC-2026-006', b'0', 'No es posible abrir una carpeta compartida de la oficina.', '2026-10-19 09:30:00', '2026-10-14 09:30:00', '2026-10-19 09:30:00', '2026-10-16 09:30:00', b'1', 3, 3, 1, 5);

INSERT INTO `articulos_kb` (`id`, `estado`, `fecha_creacion`, `fecha_publicacion`, `palabras_clave`, `solucion`, `titulo`, `id_aprobador`, `id_autor`, `id_categoria`, `id_incidencia_origen`) VALUES
  (1, 'ARCHIVADO', '2026-10-09 09:30:00', '2026-10-11 09:30:00', 'soporte, ayuda, ejemplo', 'Registro ficticio de articulos kb para demostración número 1.', 'Articulos Kb demo 1', 1, 1, 1, 1),
  (2, 'BORRADOR', '2026-10-10 09:30:00', '2026-10-12 09:30:00', 'soporte, ayuda, ejemplo', 'Registro ficticio de articulos kb para demostración número 2.', 'Articulos Kb demo 2', 2, 2, 2, 2),
  (3, 'PUBLICADO', '2026-10-11 09:30:00', '2026-10-13 09:30:00', 'soporte, ayuda, ejemplo', 'Registro ficticio de articulos kb para demostración número 3.', 'Articulos Kb demo 3', 3, 3, 3, 3);

INSERT INTO `modos_atencion` (`id_modo_atencion`, `nombre`) VALUES
  (1, 'Remoto'),
  (2, 'Presencial'),
  (3, 'Telefónico');

INSERT INTO `estados_tecnico` (`id_estado_tecnico`, `nombre`) VALUES
  (1, 'Disponible'),
  (2, 'Ocupado'),
  (3, 'No disponible');

INSERT INTO `tecnicos` (`id_tecnico`, `ape_materno_tecnico`, `ape_paterno_tecnico`, `dni_tecnico`, `nombre_tecnico`, `telefono`, `id_estado_tecnico`, `id_usuario`) VALUES
  (1, 'Demo 1', 'Ejemplo', '00000001', 'Luis', '000000001', 1, 2),
  (2, 'Demo 2', 'Ficticio', '00000002', 'Pedro', '000000002', 2, 4);

INSERT INTO `atenciones` (`id_atencion`, `fecha`, `observaciones`, `id_incidencia`, `id_modo_atencion`, `id_tecnico`) VALUES
  (1, '2026-10-11 09:30:00', 'Registro ficticio de atenciones para demostración número 1.', 1, 1, 1),
  (2, '2026-10-12 09:30:00', 'Registro ficticio de atenciones para demostración número 2.', 2, 2, 2),
  (3, '2026-10-13 09:30:00', 'Registro ficticio de atenciones para demostración número 3.', 3, 3, 1),
  (4, '2026-10-14 09:30:00', 'Registro ficticio de atenciones para demostración número 4.', 4, 1, 2),
  (5, '2026-10-15 09:30:00', 'Registro ficticio de atenciones para demostración número 5.', 5, 2, 1),
  (6, '2026-10-16 09:30:00', 'Registro ficticio de atenciones para demostración número 6.', 6, 3, 2);

INSERT INTO `auditoria_informes` (`id`, `accion`, `datos_anteriores`, `datos_nuevos`, `fecha_cambio`, `id_informe`, `usuario_responsable`) VALUES
  (1, 'Demo 1', '{"tipo": "demostracion", "registro": 1, "validado": true}', '{"tipo": "demostracion", "registro": 1, "validado": true}', '2026-10-11 09:30:00', 1, 'Demo 1'),
  (2, 'Demo 2', '{"tipo": "demostracion", "registro": 2, "validado": true}', '{"tipo": "demostracion", "registro": 2, "validado": true}', '2026-10-12 09:30:00', 2, 'Demo 2'),
  (3, 'Demo 3', '{"tipo": "demostracion", "registro": 3, "validado": true}', '{"tipo": "demostracion", "registro": 3, "validado": true}', '2026-10-13 09:30:00', 3, 'Demo 3');

INSERT INTO `chat_incidencia` (`id_chat`, `fecha_envio`, `mensaje`, `id_incidencia`, `id_usuario`) VALUES
  (1, '2026-10-11 09:30:00', 'Registro ficticio de chat incidencia para demostración número 1.', 1, 1),
  (2, '2026-10-12 09:30:00', 'Registro ficticio de chat incidencia para demostración número 2.', 2, 2),
  (3, '2026-10-13 09:30:00', 'Registro ficticio de chat incidencia para demostración número 3.', 3, 3),
  (4, '2026-10-14 09:30:00', 'Registro ficticio de chat incidencia para demostración número 4.', 4, 4),
  (5, '2026-10-15 09:30:00', 'Registro ficticio de chat incidencia para demostración número 5.', 5, 5),
  (6, '2026-10-16 09:30:00', 'Registro ficticio de chat incidencia para demostración número 6.', 6, 6);

INSERT INTO `configuracion_sla` (`id`, `tiempo_resolucion_horas`, `tiempo_respuesta_horas`, `id_severidad`, `id_usuario`) VALUES
  (1, 4, 4, 1, 1),
  (2, 8, 8, 2, 2),
  (3, 12, 12, 3, 3);

INSERT INTO `tipo_costo` (`id_tipo_costo`, `nombre_tipo_costo`) VALUES
  (1, 'Mano de obra'),
  (2, 'Repuestos'),
  (3, 'Traslado');

INSERT INTO `proveedores` (`id_proveedor`, `contacto`, `direccion`, `estado`, `nombre_proveedor`, `ruc_proveedor`) VALUES
  (1, '000000001', 'Av. Demostración 101, Lima', 1, 'Proveedor Hardware Demo', '00000000001'),
  (2, '000000002', 'Av. Demostración 102, Lima', 1, 'Distribuidora Digital Demo', '00000000002'),
  (3, '000000003', 'Av. Demostración 103, Lima', 1, 'Servicios TI Demo', '00000000003');

INSERT INTO `costos_incidencia` (`id_costo`, `costo_produccion`, `costo_total`, `costo_venta`, `fecha_registro`, `ganancia`, `igv`, `id_incidencia`, `id_proveedor`, `id_tipo_costo`, `id_usuario`) VALUES
  (1, 100.0, 150.0, 130.0, '2026-10-11 09:30:00', 30.0, 18.0, 1, 1, 1, 1),
  (2, 200.0, 300.0, 260.0, '2026-10-12 09:30:00', 60.0, 18.0, 2, 2, 2, 2),
  (3, 300.0, 450.0, 390.0, '2026-10-13 09:30:00', 90.0, 18.0, 3, 3, 3, 3),
  (4, 400.0, 600.0, 520.0, '2026-10-14 09:30:00', 120.0, 18.0, 4, 1, 1, 4),
  (5, 500.0, 750.0, 650.0, '2026-10-15 09:30:00', 150.0, 18.0, 5, 2, 2, 5),
  (6, 600.0, 900.0, 780.0, '2026-10-16 09:30:00', 180.0, 18.0, 6, 3, 3, 6);

INSERT INTO `tipo_servicio` (`id_tipo_servicio`, `descripcion`, `nombre`) VALUES
  (1, 'Registro ficticio de tipo servicio para demostración número 1.', 'Mantenimiento'),
  (2, 'Registro ficticio de tipo servicio para demostración número 2.', 'Instalación'),
  (3, 'Registro ficticio de tipo servicio para demostración número 3.', 'Soporte técnico');

INSERT INTO `estado_solicitud_servicio` (`id_estado`, `descripcion`, `nombre`) VALUES
  (1, 'Registro ficticio de estado solicitud servicio para demostración número 1.', 'Pendiente'),
  (2, 'Registro ficticio de estado solicitud servicio para demostración número 2.', 'En proceso'),
  (3, 'Registro ficticio de estado solicitud servicio para demostración número 3.', 'Finalizada');

INSERT INTO `solicitudes_servicio` (`id_solicitud`, `anio`, `codigo`, `correlativo`, `descripcion`, `fecha_solicitud`, `id_estado`, `id_severidad`, `id_tipo_servicio`, `id_usuario`, `requiere_cotizacion`) VALUES
  (1, 2026, 'SOL-2026-001', 1, 'Instalación de impresora para el área de recepción.', '2026-10-11 09:30:00', 1, 2, 1, 3, b'1'),
  (2, 2026, 'SOL-2026-002', 2, 'Mantenimiento preventivo de equipos de contabilidad.', '2026-10-12 09:30:00', 2, 1, 2, 5, b'1'),
  (3, 2026, 'SOL-2026-003', 3, 'Configuración de acceso remoto al sistema interno.', '2026-10-13 09:30:00', 3, 3, 3, 3, b'1'),
  (4, 2026, 'SOL-2026-004', 4, 'Revisión de cableado de red del segundo piso.', '2026-10-14 09:30:00', 2, 2, 1, 5, b'1'),
  (5, 2026, 'SOL-2026-005', 5, 'Cotización de monitores para nuevas estaciones de trabajo.', '2026-10-15 09:30:00', 1, 1, 2, 3, b'1'),
  (6, 2026, 'SOL-2026-006', 6, 'Actualización del software del equipo de gerencia.', '2026-10-16 09:30:00', 3, 2, 3, 5, b'1');

INSERT INTO `productos` (`id`, `descripcion`, `nombre`, `precio_produccion`, `precio_venta`) VALUES
  (1, 'Registro ficticio de productos para demostración número 1.', 'Memoria RAM 8 GB', 65.0, 95.0),
  (2, 'Registro ficticio de productos para demostración número 2.', 'Unidad SSD 512 GB', 130.0, 190.0),
  (3, 'Registro ficticio de productos para demostración número 3.', 'Cable de red Cat 6', 195.0, 285.0);

INSERT INTO `costos_solicitud` (`id_costo`, `cantidad`, `descripcion`, `fecha`, `monto`, `monto_total`, `titulo`, `id_producto`, `id_proveedor`, `id_solicitud`, `id_tipo_costo`, `costo_produccion`) VALUES
  (1, 2, 'Registro ficticio de costos solicitud para demostración número 1.', '2026-10-11 09:30:00', 75.0, 150.0, 'Costos Solicitud demo 1', 1, 1, 1, 1, 100.0),
  (2, 3, 'Registro ficticio de costos solicitud para demostración número 2.', '2026-10-12 09:30:00', 150.0, 300.0, 'Costos Solicitud demo 2', 2, 2, 2, 2, 200.0),
  (3, 4, 'Registro ficticio de costos solicitud para demostración número 3.', '2026-10-13 09:30:00', 225.0, 450.0, 'Costos Solicitud demo 3', 3, 3, 3, 3, 300.0),
  (4, 5, 'Registro ficticio de costos solicitud para demostración número 4.', '2026-10-14 09:30:00', 300.0, 600.0, 'Costos Solicitud demo 4', 1, 1, 4, 1, 400.0),
  (5, 6, 'Registro ficticio de costos solicitud para demostración número 5.', '2026-10-15 09:30:00', 375.0, 750.0, 'Costos Solicitud demo 5', 2, 2, 5, 2, 500.0),
  (6, 7, 'Registro ficticio de costos solicitud para demostración número 6.', '2026-10-16 09:30:00', 450.0, 900.0, 'Costos Solicitud demo 6', 3, 3, 6, 3, 600.0);

INSERT INTO `empresa_detalle` (`id_detalle_empresa`, `razon_social`, `representante_legal`, `ruc_empresa`) VALUES
  (1, 'Los Matus Demo S.A.C.', 'Representante Demo 1', '00000000001'),
  (2, 'Tecnología Ejemplo S.A.C.', 'Representante Demo 2', '00000000002'),
  (3, 'Servicios Prueba S.A.C.', 'Representante Demo 3', '00000000003');

INSERT INTO `empresas` (`id_empresa`, `direccion`, `nombre_sede`, `representante_sede`, `telefono_empresa`, `url_direccion`, `id_detalle_empresa`, `usuario_id`) VALUES
  (1, 'Av. Demostración 101, Lima', 'Oficina Principal', 'Demo 1', '000000001', 'https://example.com/archivos-demo/empresas-1.pdf', 1, 1),
  (2, 'Av. Demostración 102, Lima', 'Sucursal Norte', 'Demo 2', '000000002', 'https://example.com/archivos-demo/empresas-2.pdf', 2, 6),
  (3, 'Av. Demostración 103, Lima', 'Sucursal Sur', 'Demo 3', '000000003', 'https://example.com/archivos-demo/empresas-3.pdf', 3, 5);

INSERT INTO `encuestas_satisfaccion` (`id`, `comentario_general`, `fecha_encuesta`, `respuesta_1`, `respuesta_2`, `respuesta_3`, `id_incidencia`) VALUES
  (1, 'Atención clara y rápida.', '2026-10-11 09:30:00', 4, 4, 4, 1),
  (2, 'La solución fue útil.', '2026-10-12 09:30:00', 5, 5, 5, 2),
  (3, 'Servicio conforme.', '2026-10-13 09:30:00', 3, 3, 3, 3);

INSERT INTO `sedes` (`id`, `activo`, `nombre_encargado_sede`, `nombre_sede`, `ubicacion_sede`, `ubicacion_sede_url`, `id_empresa_legal`) VALUES
  (1, b'1', 'Demo 1', 'Sede Lima', 'Av. Demostración 101, Lima', 'https://example.com/archivos-demo/sedes-1.pdf', 1),
  (2, b'1', 'Demo 2', 'Sede Norte', 'Av. Demostración 102, Lima', 'https://example.com/archivos-demo/sedes-2.pdf', 2),
  (3, b'1', 'Demo 3', 'Sede Sur', 'Av. Demostración 103, Lima', 'https://example.com/archivos-demo/sedes-3.pdf', 3);

INSERT INTO `tipos_equipo` (`id`, `descripcion`, `frecuencia_mantenimiento_sugerida`, `nombre`) VALUES
  (1, 'Registro ficticio de tipos equipo para demostración número 1.', 1, 'Laptop'),
  (2, 'Registro ficticio de tipos equipo para demostración número 2.', 2, 'Impresora'),
  (3, 'Registro ficticio de tipos equipo para demostración número 3.', 3, 'Router');

INSERT INTO `equipos` (`id`, `codigo_inventario`, `estado`, `fecha_adquisicion`, `fecha_ultimo_mantenimiento`, `marca`, `modelo`, `nombre`, `numero_serie`, `ubicacion_fisica`, `url_acceso`, `id_empresa`, `id_tipo_equipo`, `id_usuario_asignado`, `frecuencia_mantenimiento`, `id_area`, `id_sede`) VALUES
  (1, 'EQ-DEMO-001', 'OPERATIVO', '2026-09-10', '2026-09-10', 'Lenovo', 'ThinkPad E14', 'Laptop Lenovo', 'SN-DEMO-000001', 'Av. Demostración 101, Lima', 'https://example.com/archivos-demo/equipos-1.pdf', 1, 1, 3, 90, 1, 1),
  (2, 'EQ-DEMO-002', 'EN_MANTENIMIENTO', '2026-09-11', '2026-09-11', 'HP', 'LaserJet M404', 'Impresora HP', 'SN-DEMO-000002', 'Av. Demostración 102, Lima', 'https://example.com/archivos-demo/equipos-2.pdf', 2, 2, 5, 90, 2, 2),
  (3, 'EQ-DEMO-003', 'INOPERATIVO', '2026-09-12', '2026-09-12', 'TP-Link', 'Archer AX10', 'Router de oficina', 'SN-DEMO-000003', 'Av. Demostración 103, Lima', 'https://example.com/archivos-demo/equipos-3.pdf', 3, 3, 3, 90, 3, 3),
  (4, 'EQ-DEMO-004', 'DE_BAJA', '2026-09-13', '2026-09-13', 'Dell', 'Latitude 5420', 'Laptop Dell', 'SN-DEMO-000004', 'Av. Demostración 104, Lima', 'https://example.com/archivos-demo/equipos-4.pdf', 1, 1, 5, 90, 1, 1),
  (5, 'EQ-DEMO-005', 'OPERATIVO', '2026-09-14', '2026-09-14', 'Epson', 'EcoTank L3250', 'Impresora Epson', 'SN-DEMO-000005', 'Av. Demostración 105, Lima', 'https://example.com/archivos-demo/equipos-5.pdf', 2, 2, 3, 90, 2, 2);

INSERT INTO `historial_cotizacion` (`id_historial_cotizacion`, `detalles_json`, `fecha_generacion`, `monto_total_historico`, `ruta_archivo_pdf`, `version`, `id_solicitud`, `id_usuario`, `anio`, `codigo`, `correlativo`) VALUES
  (1, '{"tipo": "demostracion", "registro": 1, "validado": true}', '2026-10-11 09:30:00', 150.0, 'https://example.com/archivos-demo/historial_cotizacion-1.pdf', 1, 1, 1, 2026, 'COT-2026-001', 1),
  (2, '{"tipo": "demostracion", "registro": 2, "validado": true}', '2026-10-12 09:30:00', 300.0, 'https://example.com/archivos-demo/historial_cotizacion-2.pdf', 2, 2, 2, 2026, 'COT-2026-002', 2),
  (3, '{"tipo": "demostracion", "registro": 3, "validado": true}', '2026-10-13 09:30:00', 450.0, 'https://example.com/archivos-demo/historial_cotizacion-3.pdf', 3, 3, 3, 2026, 'COT-2026-003', 3);

INSERT INTO `historial_incidencia` (`id_historial`, `fecha_cambio`, `id_estado_incidencia`, `id_incidencia`, `id_usuario`) VALUES
  (1, '2026-10-11 09:30:00', 1, 1, 1),
  (2, '2026-10-12 09:30:00', 2, 2, 2),
  (3, '2026-10-13 09:30:00', 3, 3, 3),
  (4, '2026-10-14 09:30:00', 1, 4, 4),
  (5, '2026-10-15 09:30:00', 2, 5, 5),
  (6, '2026-10-16 09:30:00', 3, 6, 6);

INSERT INTO `historial_mantenimiento_equipos` (`id`, `descripcion`, `fecha_evento`, `id_informe`, `tecnico_responsable`, `tipo_evento`, `id_equipo`) VALUES
  (1, 'Registro ficticio de historial mantenimiento equipos para demostración número 1.', '2026-10-11 09:30:00', 1, 'Demo 1', 'Demo 1', 1),
  (2, 'Registro ficticio de historial mantenimiento equipos para demostración número 2.', '2026-10-12 09:30:00', 2, 'Demo 2', 'Demo 2', 2),
  (3, 'Registro ficticio de historial mantenimiento equipos para demostración número 3.', '2026-10-13 09:30:00', 3, 'Demo 3', 'Demo 3', 3),
  (4, 'Registro ficticio de historial mantenimiento equipos para demostración número 4.', '2026-10-14 09:30:00', 4, 'Demo 4', 'Demo 4', 4),
  (5, 'Registro ficticio de historial mantenimiento equipos para demostración número 5.', '2026-10-15 09:30:00', 5, 'Demo 5', 'Demo 5', 5);

INSERT INTO `historial_solicitud_servicio` (`id_historial`, `detalle`, `fecha_cambio`, `id_estado`, `id_severidad`, `id_solicitud`, `id_usuario`) VALUES
  (1, 'Registro ficticio de historial solicitud servicio para demostración número 1.', '2026-10-11 09:30:00', 1, 1, 1, 1),
  (2, 'Registro ficticio de historial solicitud servicio para demostración número 2.', '2026-10-12 09:30:00', 2, 2, 2, 2),
  (3, 'Registro ficticio de historial solicitud servicio para demostración número 3.', '2026-10-13 09:30:00', 3, 3, 3, 3),
  (4, 'Registro ficticio de historial solicitud servicio para demostración número 4.', '2026-10-14 09:30:00', 1, 1, 4, 4),
  (5, 'Registro ficticio de historial solicitud servicio para demostración número 5.', '2026-10-15 09:30:00', 2, 2, 5, 5),
  (6, 'Registro ficticio de historial solicitud servicio para demostración número 6.', '2026-10-16 09:30:00', 3, 3, 6, 6);

INSERT INTO `imagenes_chat` (`id_imagen_chat`, `fecha_subida`, `url_imagen`, `id_chat`) VALUES
  (1, '2026-10-11 09:30:00', 'https://example.com/archivos-demo/imagenes_chat-1.pdf', 1),
  (2, '2026-10-12 09:30:00', 'https://example.com/archivos-demo/imagenes_chat-2.pdf', 2),
  (3, '2026-10-13 09:30:00', 'https://example.com/archivos-demo/imagenes_chat-3.pdf', 3);

INSERT INTO `imagenes_incidencia` (`id_imagen`, `fecha_subida`, `url_imagen`, `id_incidencia`) VALUES
  (1, '2026-10-11 09:30:00', 'https://example.com/archivos-demo/imagenes_incidencia-1.pdf', 1),
  (2, '2026-10-12 09:30:00', 'https://example.com/archivos-demo/imagenes_incidencia-2.pdf', 2),
  (3, '2026-10-13 09:30:00', 'https://example.com/archivos-demo/imagenes_incidencia-3.pdf', 3),
  (4, '2026-10-14 09:30:00', 'https://example.com/archivos-demo/imagenes_incidencia-4.pdf', 4);

INSERT INTO `imagenes_solicitud` (`id`, `fecha_subida`, `url_imagen`, `solicitud_id`, `tipo_documento`) VALUES
  (1, '2026-10-11 09:30:00', 'https://example.com/archivos-demo/imagenes_solicitud-1.pdf', 1, 'Demo 1'),
  (2, '2026-10-12 09:30:00', 'https://example.com/archivos-demo/imagenes_solicitud-2.pdf', 2, 'Demo 2'),
  (3, '2026-10-13 09:30:00', 'https://example.com/archivos-demo/imagenes_solicitud-3.pdf', 3, 'Demo 3'),
  (4, '2026-10-14 09:30:00', 'https://example.com/archivos-demo/imagenes_solicitud-4.pdf', 4, 'Demo 4');

INSERT INTO `plantillas_checklist` (`id`, `estructura`, `nombre`, `tipo_equipo`) VALUES
  (1, '{"titulo": "Checklist de demostración", "items": ["Inspección", "Prueba funcional"]}', 'Plantillas Checklist demo 1', 'Demo 1'),
  (2, '{"titulo": "Checklist de demostración", "items": ["Inspección", "Prueba funcional"]}', 'Plantillas Checklist demo 2', 'Demo 2'),
  (3, '{"titulo": "Checklist de demostración", "items": ["Inspección", "Prueba funcional"]}', 'Plantillas Checklist demo 3', 'Demo 3');

INSERT INTO `informes_mantenimiento` (`id`, `codigo`, `datos_dinamicos`, `estado`, `fecha_ejecucion`, `fotos_url`, `id_equipo`, `id_plantilla`, `id_tecnico`) VALUES
  (1, 'INF-2026-001', '{"tipo": "demostracion", "registro": 1, "validado": true}', 'BORRADOR', '2026-10-11 09:30:00', '["https://example.com/demo/foto-1.jpg"]', 1, 1, 1),
  (2, 'INF-2026-002', '{"tipo": "demostracion", "registro": 2, "validado": true}', 'FINALIZADO', '2026-10-12 09:30:00', '["https://example.com/demo/foto-2.jpg"]', 2, 2, 2),
  (3, 'INF-2026-003', '{"tipo": "demostracion", "registro": 3, "validado": true}', 'BORRADOR', '2026-10-13 09:30:00', '["https://example.com/demo/foto-3.jpg"]', 3, 3, 1);

INSERT INTO `modulos` (`id`, `codigo`, `nombre`) VALUES
  (1, 'INCIDENCIAS', 'Gestión de incidencias'),
  (2, 'SOLICITUDES', 'Servicios'),
  (3, 'INVENTARIO', 'Equipos');

INSERT INTO `otp_codes` (`id`, `code`, `expiration`, `used`, `id_usuario`, `type`) VALUES
  (1, 'OTP-DEMO-001', '2026-10-03 09:30:00', b'1', 1, 'LOGIN'),
  (2, 'OTP-DEMO-002', '2026-10-03 09:30:00', b'1', 2, 'PASSWORD_CHANGE'),
  (3, 'OTP-DEMO-003', '2026-10-03 09:30:00', b'0', 3, 'PASSWORD_RESET');

INSERT INTO `personas` (`id_persona`, `ape_materno_persona`, `ape_paterno_persona`, `direccion`, `dni`, `fecha_nacimiento`, `nombre_persona`, `telefono`, `url_direccion`, `usuario_id`) VALUES
  (1, 'López', 'Demostración', 'Av. Demostración 101, Lima', '00000001', '1991-06-15', 'Andrea', '000000001', 'https://example.com/archivos-demo/personas-1.pdf', 1),
  (2, 'Ramos', 'Ejemplo', 'Av. Demostración 102, Lima', '00000002', '1992-06-15', 'Luis', '000000002', 'https://example.com/archivos-demo/personas-2.pdf', 2),
  (3, 'García', 'Prueba', 'Av. Demostración 103, Lima', '00000003', '1993-06-15', 'Camila', '000000003', 'https://example.com/archivos-demo/personas-3.pdf', 3),
  (4, 'Pérez', 'Ficticio', 'Av. Demostración 104, Lima', '00000004', '1994-06-15', 'Pedro', '000000004', 'https://example.com/archivos-demo/personas-4.pdf', 4),
  (5, 'Vega', 'Muestra', 'Av. Demostración 105, Lima', '00000005', '1995-06-15', 'Lucía', '000000005', 'https://example.com/archivos-demo/personas-5.pdf', 5),
  (6, 'Flores', 'Simulado', 'Av. Demostración 106, Lima', '00000006', '1996-06-15', 'Marcos', '000000006', 'https://example.com/archivos-demo/personas-6.pdf', 6);

INSERT INTO `suscripciones` (`id`, `activo`, `fecha_fin`, `fecha_inicio`, `modulo_id`, `usuario_id`) VALUES
  (1, b'1', '2026-10-14 09:30:00', '2026-10-09 09:30:00', 1, 1),
  (2, b'1', '2026-10-15 09:30:00', '2026-10-10 09:30:00', 2, 2),
  (3, b'1', '2026-10-16 09:30:00', '2026-10-11 09:30:00', 3, 3);

INSERT INTO `tipo_especialidad` (`id_especialidad`, `nombre_especialidad`) VALUES
  (1, 'Redes'),
  (2, 'Hardware'),
  (3, 'Software');

INSERT INTO `tecnico_especialidad` (`id_tec_esp`, `id_especialidad`, `id_tecnico`) VALUES
  (1, 1, 1),
  (2, 2, 2),
  (3, 3, 1);

INSERT INTO `usuario_sede_area` (`id`, `id_area`, `id_sede`, `id_usuario`) VALUES
  (1, 1, 1, 1),
  (2, 2, 2, 2),
  (3, 3, 3, 3);

COMMIT;

