-- =============================================================================
-- BASE DE DATOS: LOSMATUS SAC (PostgreSQL 18+)
-- ARCHIVO: 03_verificacion.sql
-- DESCRIPCION: Verificacion del esquema, conteo de registros e integridad referencial
-- =============================================================================

-- 1. CONTEO DE REGISTROS POR TABLA (47 TABLAS)
SELECT 'admins' AS tabla, COUNT(*) AS registros FROM admins
UNION ALL SELECT 'areas', COUNT(*) FROM areas
UNION ALL SELECT 'articulos_kb', COUNT(*) FROM articulos_kb
UNION ALL SELECT 'atenciones', COUNT(*) FROM atenciones
UNION ALL SELECT 'auditoria_informes', COUNT(*) FROM auditoria_informes
UNION ALL SELECT 'categorias', COUNT(*) FROM categorias
UNION ALL SELECT 'chat_incidencia', COUNT(*) FROM chat_incidencia
UNION ALL SELECT 'configuracion_sla', COUNT(*) FROM configuracion_sla
UNION ALL SELECT 'costos_incidencia', COUNT(*) FROM costos_incidencia
UNION ALL SELECT 'costos_solicitud', COUNT(*) FROM costos_solicitud
UNION ALL SELECT 'empresa_detalle', COUNT(*) FROM empresa_detalle
UNION ALL SELECT 'empresas', COUNT(*) FROM empresas
UNION ALL SELECT 'encuestas_satisfaccion', COUNT(*) FROM encuestas_satisfaccion
UNION ALL SELECT 'equipos', COUNT(*) FROM equipos
UNION ALL SELECT 'estado_solicitud_servicio', COUNT(*) FROM estado_solicitud_servicio
UNION ALL SELECT 'estados_incidencia', COUNT(*) FROM estados_incidencia
UNION ALL SELECT 'estados_tecnico', COUNT(*) FROM estados_tecnico
UNION ALL SELECT 'historial_cotizacion', COUNT(*) FROM historial_cotizacion
UNION ALL SELECT 'historial_incidencia', COUNT(*) FROM historial_incidencia
UNION ALL SELECT 'historial_mantenimiento_equipos', COUNT(*) FROM historial_mantenimiento_equipos
UNION ALL SELECT 'historial_solicitud_servicio', COUNT(*) FROM historial_solicitud_servicio
UNION ALL SELECT 'imagenes_chat', COUNT(*) FROM imagenes_chat
UNION ALL SELECT 'imagenes_incidencia', COUNT(*) FROM imagenes_incidencia
UNION ALL SELECT 'imagenes_solicitud', COUNT(*) FROM imagenes_solicitud
UNION ALL SELECT 'incidencias', COUNT(*) FROM incidencias
UNION ALL SELECT 'informes_mantenimiento', COUNT(*) FROM informes_mantenimiento
UNION ALL SELECT 'modos_atencion', COUNT(*) FROM modos_atencion
UNION ALL SELECT 'modulos', COUNT(*) FROM modulos
UNION ALL SELECT 'niveles_admin', COUNT(*) FROM niveles_admin
UNION ALL SELECT 'otp_codes', COUNT(*) FROM otp_codes
UNION ALL SELECT 'personas', COUNT(*) FROM personas
UNION ALL SELECT 'plantillas_checklist', COUNT(*) FROM plantillas_checklist
UNION ALL SELECT 'productos', COUNT(*) FROM productos
UNION ALL SELECT 'proveedores', COUNT(*) FROM proveedores
UNION ALL SELECT 'roles', COUNT(*) FROM roles
UNION ALL SELECT 'sedes', COUNT(*) FROM sedes
UNION ALL SELECT 'severidades', COUNT(*) FROM severidades
UNION ALL SELECT 'solicitudes_servicio', COUNT(*) FROM solicitudes_servicio
UNION ALL SELECT 'suscripciones', COUNT(*) FROM suscripciones
UNION ALL SELECT 'tecnico_especialidad', COUNT(*) FROM tecnico_especialidad
UNION ALL SELECT 'tecnicos', COUNT(*) FROM tecnicos
UNION ALL SELECT 'tipo_costo', COUNT(*) FROM tipo_costo
UNION ALL SELECT 'tipo_especialidad', COUNT(*) FROM tipo_especialidad
UNION ALL SELECT 'tipo_servicio', COUNT(*) FROM tipo_servicio
UNION ALL SELECT 'tipos_equipo', COUNT(*) FROM tipos_equipo
UNION ALL SELECT 'usuario_sede_area', COUNT(*) FROM usuario_sede_area
UNION ALL SELECT 'usuarios', COUNT(*) FROM usuarios
ORDER BY tabla;

-- 2. VERIFICACION DE INTEGRIDAD REFERENCIAL (DETECCION DE HUERFANOS)
SELECT 'Huerfanos usuarios -> roles' AS check_name, COUNT(*) AS total
FROM usuarios u LEFT JOIN roles r ON u.id_rol = r.id_rol WHERE r.id_rol IS NULL
UNION ALL
SELECT 'Huerfanos admins -> usuarios', COUNT(*)
FROM admins a LEFT JOIN usuarios u ON a.id_usuario = u.id_usuario WHERE u.id_usuario IS NULL
UNION ALL
SELECT 'Huerfanos tecnicos -> usuarios', COUNT(*)
FROM tecnicos t LEFT JOIN usuarios u ON t.id_usuario = u.id_usuario WHERE u.id_usuario IS NULL
UNION ALL
SELECT 'Huerfanos personas -> usuarios', COUNT(*)
FROM personas p LEFT JOIN usuarios u ON p.usuario_id = u.id_usuario WHERE u.id_usuario IS NULL
UNION ALL
SELECT 'Huerfanos incidencias -> usuarios', COUNT(*)
FROM incidencias i LEFT JOIN usuarios u ON i.id_usuario_reporta = u.id_usuario WHERE u.id_usuario IS NULL
UNION ALL
SELECT 'Huerfanos solicitudes_servicio -> usuarios', COUNT(*)
FROM solicitudes_servicio s LEFT JOIN usuarios u ON s.id_usuario = u.id_usuario WHERE u.id_usuario IS NULL
UNION ALL
SELECT 'Huerfanos equipos -> tipos_equipo', COUNT(*)
FROM equipos e LEFT JOIN tipos_equipo t ON e.id_tipo_equipo = t.id WHERE t.id IS NULL
UNION ALL
SELECT 'Huerfanos equipos -> sedes', COUNT(*)
FROM equipos e LEFT JOIN sedes s ON e.id_sede = s.id WHERE s.id IS NULL;

-- 3. MUESTRA DE DATOS PRINCIPALES
SELECT id_incidencia, codigo, descripcion, id_estado_incidencia FROM incidencias ORDER BY id_incidencia LIMIT 10;
SELECT id_solicitud, codigo, descripcion, id_estado FROM solicitudes_servicio ORDER BY id_solicitud LIMIT 10;
SELECT id_usuario, email, activo, id_rol FROM usuarios ORDER BY id_usuario LIMIT 10;
