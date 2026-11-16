USE `losmatus_db`;

SELECT 'admins' AS tabla, COUNT(*) AS registros FROM `admins`
UNION ALL SELECT 'areas' AS tabla, COUNT(*) AS registros FROM `areas`
UNION ALL SELECT 'articulos_kb' AS tabla, COUNT(*) AS registros FROM `articulos_kb`
UNION ALL SELECT 'atenciones' AS tabla, COUNT(*) AS registros FROM `atenciones`
UNION ALL SELECT 'auditoria_informes' AS tabla, COUNT(*) AS registros FROM `auditoria_informes`
UNION ALL SELECT 'categorias' AS tabla, COUNT(*) AS registros FROM `categorias`
UNION ALL SELECT 'chat_incidencia' AS tabla, COUNT(*) AS registros FROM `chat_incidencia`
UNION ALL SELECT 'configuracion_sla' AS tabla, COUNT(*) AS registros FROM `configuracion_sla`
UNION ALL SELECT 'costos_incidencia' AS tabla, COUNT(*) AS registros FROM `costos_incidencia`
UNION ALL SELECT 'costos_solicitud' AS tabla, COUNT(*) AS registros FROM `costos_solicitud`
UNION ALL SELECT 'empresa_detalle' AS tabla, COUNT(*) AS registros FROM `empresa_detalle`
UNION ALL SELECT 'empresas' AS tabla, COUNT(*) AS registros FROM `empresas`
UNION ALL SELECT 'encuestas_satisfaccion' AS tabla, COUNT(*) AS registros FROM `encuestas_satisfaccion`
UNION ALL SELECT 'equipos' AS tabla, COUNT(*) AS registros FROM `equipos`
UNION ALL SELECT 'estado_solicitud_servicio' AS tabla, COUNT(*) AS registros FROM `estado_solicitud_servicio`
UNION ALL SELECT 'estados_incidencia' AS tabla, COUNT(*) AS registros FROM `estados_incidencia`
UNION ALL SELECT 'estados_tecnico' AS tabla, COUNT(*) AS registros FROM `estados_tecnico`
UNION ALL SELECT 'historial_cotizacion' AS tabla, COUNT(*) AS registros FROM `historial_cotizacion`
UNION ALL SELECT 'historial_incidencia' AS tabla, COUNT(*) AS registros FROM `historial_incidencia`
UNION ALL SELECT 'historial_mantenimiento_equipos' AS tabla, COUNT(*) AS registros FROM `historial_mantenimiento_equipos`
UNION ALL SELECT 'historial_solicitud_servicio' AS tabla, COUNT(*) AS registros FROM `historial_solicitud_servicio`
UNION ALL SELECT 'imagenes_chat' AS tabla, COUNT(*) AS registros FROM `imagenes_chat`
UNION ALL SELECT 'imagenes_incidencia' AS tabla, COUNT(*) AS registros FROM `imagenes_incidencia`
UNION ALL SELECT 'imagenes_solicitud' AS tabla, COUNT(*) AS registros FROM `imagenes_solicitud`
UNION ALL SELECT 'incidencias' AS tabla, COUNT(*) AS registros FROM `incidencias`
UNION ALL SELECT 'informes_mantenimiento' AS tabla, COUNT(*) AS registros FROM `informes_mantenimiento`
UNION ALL SELECT 'modos_atencion' AS tabla, COUNT(*) AS registros FROM `modos_atencion`
UNION ALL SELECT 'modulos' AS tabla, COUNT(*) AS registros FROM `modulos`
UNION ALL SELECT 'niveles_admin' AS tabla, COUNT(*) AS registros FROM `niveles_admin`
UNION ALL SELECT 'otp_codes' AS tabla, COUNT(*) AS registros FROM `otp_codes`
UNION ALL SELECT 'personas' AS tabla, COUNT(*) AS registros FROM `personas`
UNION ALL SELECT 'plantillas_checklist' AS tabla, COUNT(*) AS registros FROM `plantillas_checklist`
UNION ALL SELECT 'productos' AS tabla, COUNT(*) AS registros FROM `productos`
UNION ALL SELECT 'proveedores' AS tabla, COUNT(*) AS registros FROM `proveedores`
UNION ALL SELECT 'roles' AS tabla, COUNT(*) AS registros FROM `roles`
UNION ALL SELECT 'sedes' AS tabla, COUNT(*) AS registros FROM `sedes`
UNION ALL SELECT 'severidades' AS tabla, COUNT(*) AS registros FROM `severidades`
UNION ALL SELECT 'solicitudes_servicio' AS tabla, COUNT(*) AS registros FROM `solicitudes_servicio`
UNION ALL SELECT 'suscripciones' AS tabla, COUNT(*) AS registros FROM `suscripciones`
UNION ALL SELECT 'tecnico_especialidad' AS tabla, COUNT(*) AS registros FROM `tecnico_especialidad`
UNION ALL SELECT 'tecnicos' AS tabla, COUNT(*) AS registros FROM `tecnicos`
UNION ALL SELECT 'tipo_costo' AS tabla, COUNT(*) AS registros FROM `tipo_costo`
UNION ALL SELECT 'tipo_especialidad' AS tabla, COUNT(*) AS registros FROM `tipo_especialidad`
UNION ALL SELECT 'tipo_servicio' AS tabla, COUNT(*) AS registros FROM `tipo_servicio`
UNION ALL SELECT 'tipos_equipo' AS tabla, COUNT(*) AS registros FROM `tipos_equipo`
UNION ALL SELECT 'usuario_sede_area' AS tabla, COUNT(*) AS registros FROM `usuario_sede_area`
UNION ALL SELECT 'usuarios' AS tabla, COUNT(*) AS registros FROM `usuarios`
ORDER BY tabla;

SELECT id_incidencia, codigo, descripcion, id_estado_incidencia FROM incidencias ORDER BY id_incidencia;
SELECT id_solicitud, codigo, descripcion, id_estado FROM solicitudes_servicio ORDER BY id_solicitud;
