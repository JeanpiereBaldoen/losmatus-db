-- =============================================================================
-- BASE DE DATOS: LOSMATUS SAC (PostgreSQL 18+)
-- ARCHIVO: 02_datos.sql
-- DESCRIPCION: Insercion de datos semilla y sincronizacion de secuencias IDENTITY
-- =============================================================================

BEGIN;

INSERT INTO roles (id_rol, nombre) VALUES
  (1, 'ADMIN'),
  (2, 'TECNICO'),
  (3, 'CLIENTE');

INSERT INTO usuarios (id_usuario, activo, email, fecha_creacion, password, id_rol) VALUES
  (1, TRUE, 'admin.demo@example.com', '2026-10-09 09:30:00', 'CUENTA_DEMOSTRACION_SIN_LOGIN', 1),
  (2, TRUE, 'tecnico1.demo@example.com', '2026-10-10 09:30:00', 'CUENTA_DEMOSTRACION_SIN_LOGIN', 2),
  (3, TRUE, 'cliente1.demo@example.com', '2026-10-11 09:30:00', 'CUENTA_DEMOSTRACION_SIN_LOGIN', 3),
  (4, TRUE, 'tecnico2.demo@example.com', '2026-10-12 09:30:00', 'CUENTA_DEMOSTRACION_SIN_LOGIN', 2),
  (5, TRUE, 'cliente2.demo@example.com', '2026-10-13 09:30:00', 'CUENTA_DEMOSTRACION_SIN_LOGIN', 3),
  (6, TRUE, 'supervisor.demo@example.com', '2026-10-14 09:30:00', 'CUENTA_DEMOSTRACION_SIN_LOGIN', 1);

INSERT INTO niveles_admin (id_nivel_admin, nombre) VALUES
  (1, 'Administrador'),
  (2, 'Supervisor'),
  (3, 'Coordinador');

INSERT INTO admins (id_admin, nombre_admin, telefono_admin, id_nivel_admin, id_usuario) VALUES
  (1, 'Andrea Demo', '000000001', 1, 1),
  (2, 'Marcos Supervisor', '000000002', 2, 6);

INSERT INTO areas (id, nombre) VALUES
  (1, 'Sistemas'),
  (2, 'Operaciones'),
  (3, 'Administración');

INSERT INTO categorias (id_categoria, nombre_categoria) VALUES
  (1, 'Hardware'),
  (2, 'Software'),
  (3, 'Redes');

INSERT INTO estados_incidencia (id_estado_incidencia, nombre) VALUES
  (1, 'Abierta'),
  (2, 'En atención'),
  (3, 'Resuelta');

INSERT INTO severidades (id_severidad, nivel) VALUES
  (1, 'Baja'),
  (2, 'Media'),
  (3, 'Alta');

INSERT INTO incidencias (id_incidencia, codigo, cumple_sla, descripcion, fecha_finalizacion, fecha_inicio_atencion, fecha_limite_resolucion, fecha_reporte, requiere_confirmacion_usuario, id_categoria, id_estado_incidencia, id_severidad, id_usuario_reporta) VALUES
  (1, 'INC-2026-001', TRUE, 'La laptop de recepción no enciende al iniciar la jornada.', '2026-10-14 09:30:00', '2026-10-09 09:30:00', '2026-10-14 09:30:00', '2026-10-11 09:30:00', TRUE, 1, 1, 3, 3),
  (2, 'INC-2026-002', TRUE, 'No se puede acceder al sistema de tickets desde el navegador.', '2026-10-15 09:30:00', '2026-10-10 09:30:00', '2026-10-15 09:30:00', '2026-10-12 09:30:00', TRUE, 2, 2, 2, 5),
  (3, 'INC-2026-003', FALSE, 'La impresora de administración presenta atascos frecuentes.', '2026-10-16 09:30:00', '2026-10-11 09:30:00', '2026-10-16 09:30:00', '2026-10-13 09:30:00', TRUE, 1, 3, 1, 3),
  (4, 'INC-2026-004', TRUE, 'La red de la sede Norte presenta interrupciones intermitentes.', '2026-10-17 09:30:00', '2026-10-12 09:30:00', '2026-10-17 09:30:00', '2026-10-14 09:30:00', TRUE, 3, 2, 3, 5),
  (5, 'INC-2026-005', TRUE, 'Un equipo de trabajo funciona con mucha lentitud.', '2026-10-18 09:30:00', '2026-10-13 09:30:00', '2026-10-18 09:30:00', '2026-10-15 09:30:00', TRUE, 1, 1, 2, 3),
  (6, 'INC-2026-006', FALSE, 'No es posible abrir una carpeta compartida de la oficina.', '2026-10-19 09:30:00', '2026-10-14 09:30:00', '2026-10-19 09:30:00', '2026-10-16 09:30:00', TRUE, 3, 3, 1, 5);

INSERT INTO articulos_kb (id, estado, fecha_creacion, fecha_publicacion, palabras_clave, solucion, titulo, id_aprobador, id_autor, id_categoria, id_incidencia_origen) VALUES
  (1, 'ARCHIVADO', '2026-10-09 09:30:00', '2026-10-11 09:30:00', 'soporte, ayuda, ejemplo', 'Registro ficticio de articulos kb para demostración número 1.', 'Articulos Kb demo 1', 1, 1, 1, 1),
  (2, 'BORRADOR', '2026-10-10 09:30:00', '2026-10-12 09:30:00', 'soporte, ayuda, ejemplo', 'Registro ficticio de articulos kb para demostración número 2.', 'Articulos Kb demo 2', 2, 2, 2, 2),
  (3, 'PUBLICADO', '2026-10-11 09:30:00', '2026-10-13 09:30:00', 'soporte, ayuda, ejemplo', 'Registro ficticio de articulos kb para demostración número 3.', 'Articulos Kb demo 3', 3, 3, 3, 3);

INSERT INTO modos_atencion (id_modo_atencion, nombre) VALUES
  (1, 'Remoto'),
  (2, 'Presencial'),
  (3, 'Telefónico');

INSERT INTO estados_tecnico (id_estado_tecnico, nombre) VALUES
  (1, 'Disponible'),
  (2, 'Ocupado'),
  (3, 'No disponible');

INSERT INTO tecnicos (id_tecnico, ape_materno_tecnico, ape_paterno_tecnico, dni_tecnico, nombre_tecnico, telefono, id_estado_tecnico, id_usuario) VALUES
  (1, 'Demo 1', 'Ejemplo', '00000001', 'Luis', '000000001', 1, 2),
  (2, 'Demo 2', 'Ficticio', '00000002', 'Pedro', '000000002', 2, 4);

INSERT INTO atenciones (id_atencion, fecha, observaciones, id_incidencia, id_modo_atencion, id_tecnico) VALUES
  (1, '2026-10-11 09:30:00', 'Registro 1.', 1, 1, 1),
  (2, '2026-10-12 09:30:00', 'Registro 2.', 2, 2, 2),
  (3, '2026-10-13 09:30:00', 'Registro 3.', 3, 3, 1),
  (4, '2026-10-14 09:30:00', 'Registro 4.', 4, 1, 2),
  (5, '2026-10-15 09:30:00', 'Registro 5.', 5, 2, 1),
  (6, '2026-10-16 09:30:00', 'Registro 6.', 6, 3, 2);

INSERT INTO auditoria_informes (id, accion, datos_anteriores, datos_nuevos, fecha_cambio, id_informe, usuario_responsable) VALUES
  (1, 'Demo 1', '{"tipo": "prueba", "registro": 1, "validado": true}', '{"tipo": "prueba", "registro": 1, "validado": true}', '2026-10-11 09:30:00', 1, 'Demo 1'),
  (2, 'Demo 2', '{"tipo": "prueba", "registro": 2, "validado": true}', '{"tipo": "prueba", "registro": 2, "validado": true}', '2026-10-12 09:30:00', 2, 'Demo 2'),
  (3, 'Demo 3', '{"tipo": "prueba", "registro": 3, "validado": true}', '{"tipo": "prueba", "registro": 3, "validado": true}', '2026-10-13 09:30:00', 3, 'Demo 3');

INSERT INTO chat_incidencia (id_chat, fecha_envio, mensaje, id_incidencia, id_usuario) VALUES
  (1, '2026-10-11 09:30:00', 'Registro 1.', 1, 1),
  (2, '2026-10-12 09:30:00', 'Registro 2.', 2, 2),
  (3, '2026-10-13 09:30:00', 'Registro 3.', 3, 3),
  (4, '2026-10-14 09:30:00', 'Registro 4.', 4, 4),
  (5, '2026-10-15 09:30:00', 'Registro 5.', 5, 5),
  (6, '2026-10-16 09:30:00', 'Registro 6.', 6, 6);

INSERT INTO configuracion_sla (id, tiempo_resolucion_horas, tiempo_respuesta_horas, id_severidad, id_usuario) VALUES
  (1, 4, 4, 1, 1),
  (2, 8, 8, 2, 2),
  (3, 12, 12, 3, 3);

INSERT INTO tipo_costo (id_tipo_costo, nombre_tipo_costo) VALUES
  (1, 'Mano de obra'),
  (2, 'Repuestos'),
  (3, 'Traslado');

INSERT INTO proveedores (id_proveedor, contacto, direccion, estado, nombre_proveedor, ruc_proveedor) VALUES
  (1, '000000001', 'Av. Demostración 101, Lima', 1, 'Proveedor Hardware Demo', '00000000001'),
  (2, '000000002', 'Av. Demostración 102, Lima', 1, 'Distribuidora Digital Demo', '00000000002'),
  (3, '000000003', 'Av. Demostración 103, Lima', 1, 'Servicios TI Demo', '00000000003');

INSERT INTO costos_incidencia (id_costo, costo_produccion, costo_total, costo_venta, fecha_registro, ganancia, igv, id_incidencia, id_proveedor, id_tipo_costo, id_usuario) VALUES
  (1, 100.0, 150.0, 130.0, '2026-10-11 09:30:00', 30.0, 18.0, 1, 1, 1, 1),
  (2, 200.0, 300.0, 260.0, '2026-10-12 09:30:00', 60.0, 18.0, 2, 2, 2, 2),
  (3, 300.0, 450.0, 390.0, '2026-10-13 09:30:00', 90.0, 18.0, 3, 3, 3, 3),
  (4, 400.0, 600.0, 520.0, '2026-10-14 09:30:00', 120.0, 18.0, 4, 1, 1, 4),
  (5, 500.0, 750.0, 650.0, '2026-10-15 09:30:00', 150.0, 18.0, 5, 2, 2, 5),
  (6, 600.0, 900.0, 780.0, '2026-10-16 09:30:00', 180.0, 18.0, 6, 3, 3, 6);

INSERT INTO tipo_servicio (id_tipo_servicio, descripcion, nombre) VALUES
  (1, 'Registro 1.', 'Mantenimiento'),
  (2, 'Registro 2.', 'Instalación'),
  (3, 'Registro 3.', 'Soporte técnico');

INSERT INTO estado_solicitud_servicio (id_estado, descripcion, nombre) VALUES
  (1, 'Registro 1.', 'Pendiente'),
  (2, 'Registro 2.', 'En proceso'),
  (3, 'Registro 3.', 'Finalizada');

INSERT INTO solicitudes_servicio (id_solicitud, anio, codigo, correlativo, descripcion, fecha_solicitud, id_estado, id_severidad, id_tipo_servicio, id_usuario, requiere_cotizacion) VALUES
  (1, 2026, 'SOL-2026-001', 1, 'Instalación de impresora para el área de recepción.', '2026-10-11 09:30:00', 1, 2, 1, 3, TRUE),
  (2, 2026, 'SOL-2026-002', 2, 'Mantenimiento preventivo de equipos de contabilidad.', '2026-10-12 09:30:00', 2, 1, 2, 5, TRUE),
  (3, 2026, 'SOL-2026-003', 3, 'Configuración de acceso remoto al sistema interno.', '2026-10-13 09:30:00', 3, 3, 3, 3, TRUE),
  (4, 2026, 'SOL-2026-004', 4, 'Revisión de cableado de red del segundo piso.', '2026-10-14 09:30:00', 2, 2, 1, 5, TRUE),
  (5, 2026, 'SOL-2026-005', 5, 'Cotización de monitores para nuevas estaciones de trabajo.', '2026-10-15 09:30:00', 1, 1, 2, 3, TRUE),
  (6, 2026, 'SOL-2026-006', 6, 'Actualización del software del equipo de gerencia.', '2026-10-16 09:30:00', 3, 2, 3, 5, TRUE);

INSERT INTO productos (id, descripcion, nombre, precio_produccion, precio_venta) VALUES
  (1, 'Registro 1.', 'Memoria RAM 8 GB', 65.0, 95.0),
  (2, 'Registro 2.', 'Unidad SSD 512 GB', 130.0, 190.0),
  (3, 'Registro 3.', 'Cable de red Cat 6', 195.0, 285.0);

INSERT INTO costos_solicitud (id_costo, cantidad, descripcion, fecha, monto, monto_total, titulo, id_producto, id_proveedor, id_solicitud, id_tipo_costo, costo_produccion) VALUES
  (1, 2, 'Registro 1.', '2026-10-11 09:30:00', 75.0, 150.0, 'Costos Solicitud demo 1', 1, 1, 1, 1, 100.0),
  (2, 3, 'Registro 2.', '2026-10-12 09:30:00', 150.0, 300.0, 'Costos Solicitud demo 2', 2, 2, 2, 2, 200.0),
  (3, 4, 'Registro 3.', '2026-10-13 09:30:00', 225.0, 450.0, 'Costos Solicitud demo 3', 3, 3, 3, 3, 300.0),
  (4, 5, 'Registro 4.', '2026-10-14 09:30:00', 300.0, 600.0, 'Costos Solicitud demo 4', 1, 1, 4, 1, 400.0),
  (5, 6, 'Registro 5.', '2026-10-15 09:30:00', 375.0, 750.0, 'Costos Solicitud demo 5', 2, 2, 5, 2, 500.0),
  (6, 7, 'Registro 6.', '2026-10-16 09:30:00', 450.0, 900.0, 'Costos Solicitud demo 6', 3, 3, 6, 3, 600.0);

INSERT INTO empresa_detalle (id_detalle_empresa, razon_social, representante_legal, ruc_empresa) VALUES
  (1, 'Los Matus Demo S.A.C.', 'Representante Demo 1', '00000000001'),
  (2, 'Tecnología Ejemplo S.A.C.', 'Representante Demo 2', '00000000002'),
  (3, 'Servicios Prueba S.A.C.', 'Representante Demo 3', '00000000003');

INSERT INTO empresas (id_empresa, direccion, nombre_sede, representante_sede, telefono_empresa, url_direccion, id_detalle_empresa, usuario_id) VALUES
  (1, 'Av. Demostración 101, Lima', 'Oficina Principal', 'Demo 1', '000000001', 'https://example.com/archivos-demo/empresas-1.pdf', 1, 1),
  (2, 'Av. Demostración 102, Lima', 'Sucursal Norte', 'Demo 2', '000000002', 'https://example.com/archivos-demo/empresas-2.pdf', 2, 6),
  (3, 'Av. Demostración 103, Lima', 'Sucursal Sur', 'Demo 3', '000000003', 'https://example.com/archivos-demo/empresas-3.pdf', 3, 5);

INSERT INTO encuestas_satisfaccion (id, comentario_general, fecha_encuesta, respuesta_1, respuesta_2, respuesta_3, id_incidencia) VALUES
  (1, 'Atención clara y rápida.', '2026-10-11 09:30:00', 4, 4, 4, 1),
  (2, 'La solución fue útil.', '2026-10-12 09:30:00', 5, 5, 5, 2),
  (3, 'Servicio conforme.', '2026-10-13 09:30:00', 3, 3, 3, 3);

INSERT INTO sedes (id, activo, nombre_encargado_sede, nombre_sede, ubicacion_sede, ubicacion_sede_url, id_empresa_legal) VALUES
  (1, TRUE, 'Demo 1', 'Sede Lima', 'Av. Demostración 101, Lima', 'https://example.com/archivos-demo/sedes-1.pdf', 1),
  (2, TRUE, 'Demo 2', 'Sede Norte', 'Av. Demostración 102, Lima', 'https://example.com/archivos-demo/sedes-2.pdf', 2),
  (3, TRUE, 'Demo 3', 'Sede Sur', 'Av. Demostración 103, Lima', 'https://example.com/archivos-demo/sedes-3.pdf', 3);

INSERT INTO tipos_equipo (id, descripcion, frecuencia_mantenimiento_sugerida, nombre) VALUES
  (1, 'Registro 1.', 1, 'Laptop'),
  (2, 'Registro 2.', 2, 'Impresora'),
  (3, 'Registro 3.', 3, 'Router');

INSERT INTO equipos (id, codigo_inventario, estado, fecha_adquisicion, fecha_ultimo_mantenimiento, marca, modelo, nombre, numero_serie, ubicacion_fisica, url_acceso, id_empresa, id_tipo_equipo, id_usuario_asignado, frecuencia_mantenimiento, id_area, id_sede) VALUES
  (1, 'EQ-DEMO-001', 'OPERATIVO', '2026-09-10', '2026-09-10', 'Lenovo', 'ThinkPad E14', 'Laptop Lenovo', 'SN-DEMO-000001', 'Av. Demostración 101, Lima', 'https://example.com/archivos-demo/equipos-1.pdf', 1, 1, 3, 90, 1, 1),
  (2, 'EQ-DEMO-002', 'EN_MANTENIMIENTO', '2026-09-11', '2026-09-11', 'HP', 'LaserJet M404', 'Impresora HP', 'SN-DEMO-000002', 'Av. Demostración 102, Lima', 'https://example.com/archivos-demo/equipos-2.pdf', 2, 2, 5, 90, 2, 2),
  (3, 'EQ-DEMO-003', 'INOPERATIVO', '2026-09-12', '2026-09-12', 'TP-Link', 'Archer AX10', 'Router de oficina', 'SN-DEMO-000003', 'Av. Demostración 103, Lima', 'https://example.com/archivos-demo/equipos-3.pdf', 3, 3, 3, 90, 3, 3),
  (4, 'EQ-DEMO-004', 'DE_BAJA', '2026-09-13', '2026-09-13', 'Dell', 'Latitude 5420', 'Laptop Dell', 'SN-DEMO-000004', 'Av. Demostración 104, Lima', 'https://example.com/archivos-demo/equipos-4.pdf', 1, 1, 5, 90, 1, 1),
  (5, 'EQ-DEMO-005', 'OPERATIVO', '2026-09-14', '2026-09-14', 'Epson', 'EcoTank L3250', 'Impresora Epson', 'SN-DEMO-000005', 'Av. Demostración 105, Lima', 'https://example.com/archivos-demo/equipos-5.pdf', 2, 2, 3, 90, 2, 2);

INSERT INTO historial_cotizacion (id_historial_cotizacion, detalles_json, fecha_generacion, monto_total_historico, ruta_archivo_pdf, version, id_solicitud, id_usuario, anio, codigo, correlativo) VALUES
  (1, '{"tipo": "demostracion", "registro": 1, "validado": true}', '2026-10-11 09:30:00', 150.0, 'https://example.com/archivos-demo/historial_cotizacion-1.pdf', 1, 1, 1, 2026, 'COT-2026-001', 1),
  (2, '{"tipo": "demostracion", "registro": 2, "validado": true}', '2026-10-12 09:30:00', 300.0, 'https://example.com/archivos-demo/historial_cotizacion-2.pdf', 2, 2, 2, 2026, 'COT-2026-002', 2),
  (3, '{"tipo": "demostracion", "registro": 3, "validado": true}', '2026-10-13 09:30:00', 450.0, 'https://example.com/archivos-demo/historial_cotizacion-3.pdf', 3, 3, 3, 2026, 'COT-2026-003', 3);

INSERT INTO historial_incidencia (id_historial, fecha_cambio, id_estado_incidencia, id_incidencia, id_usuario) VALUES
  (1, '2026-10-11 09:30:00', 1, 1, 1),
  (2, '2026-10-12 09:30:00', 2, 2, 2),
  (3, '2026-10-13 09:30:00', 3, 3, 3),
  (4, '2026-10-14 09:30:00', 1, 4, 4),
  (5, '2026-10-15 09:30:00', 2, 5, 5),
  (6, '2026-10-16 09:30:00', 3, 6, 6);

INSERT INTO historial_mantenimiento_equipos (id, descripcion, fecha_evento, id_informe, tecnico_responsable, tipo_evento, id_equipo) VALUES
  (1, 'Registro 1.', '2026-10-11 09:30:00', 1, 'Demo 1', 'Demo 1', 1),
  (2, 'Registro 2.', '2026-10-12 09:30:00', 2, 'Demo 2', 'Demo 2', 2),
  (3, 'Registro 3.', '2026-10-13 09:30:00', 3, 'Demo 3', 'Demo 3', 3),
  (4, 'Registro 4.', '2026-10-14 09:30:00', 4, 'Demo 4', 'Demo 4', 4),
  (5, 'Registro 5.', '2026-10-15 09:30:00', 5, 'Demo 5', 'Demo 5', 5);

INSERT INTO historial_solicitud_servicio (id_historial, detalle, fecha_cambio, id_estado, id_severidad, id_solicitud, id_usuario) VALUES
  (1, 'Registro ficticio de historial solicitud servicio para demostración número 1.', '2026-10-11 09:30:00', 1, 1, 1, 1),
  (2, 'Registro ficticio de historial solicitud servicio para demostración número 2.', '2026-10-12 09:30:00', 2, 2, 2, 2),
  (3, 'Registro ficticio de historial solicitud servicio para demostración número 3.', '2026-10-13 09:30:00', 3, 3, 3, 3),
  (4, 'Registro ficticio de historial solicitud servicio para demostración número 4.', '2026-10-14 09:30:00', 1, 1, 4, 4),
  (5, 'Registro ficticio de historial solicitud servicio para demostración número 5.', '2026-10-15 09:30:00', 2, 2, 5, 5),
  (6, 'Registro ficticio de historial solicitud servicio para demostración número 6.', '2026-10-16 09:30:00', 3, 3, 6, 6);

INSERT INTO imagenes_chat (id_imagen_chat, fecha_subida, url_imagen, id_chat) VALUES
  (1, '2026-10-11 09:30:00', 'https://example.com/archivos-demo/imagenes_chat-1.pdf', 1),
  (2, '2026-10-12 09:30:00', 'https://example.com/archivos-demo/imagenes_chat-2.pdf', 2),
  (3, '2026-10-13 09:30:00', 'https://example.com/archivos-demo/imagenes_chat-3.pdf', 3);

INSERT INTO imagenes_incidencia (id_imagen, fecha_subida, url_imagen, id_incidencia) VALUES
  (1, '2026-10-11 09:30:00', 'https://example.com/archivos-demo/imagenes_incidencia-1.pdf', 1),
  (2, '2026-10-12 09:30:00', 'https://example.com/archivos-demo/imagenes_incidencia-2.pdf', 2),
  (3, '2026-10-13 09:30:00', 'https://example.com/archivos-demo/imagenes_incidencia-3.pdf', 3),
  (4, '2026-10-14 09:30:00', 'https://example.com/archivos-demo/imagenes_incidencia-4.pdf', 4);

INSERT INTO imagenes_solicitud (id, fecha_subida, url_imagen, solicitud_id, tipo_documento) VALUES
  (1, '2026-10-11 09:30:00', 'https://example.com/archivos-demo/imagenes_solicitud-1.pdf', 1, 'Demo 1'),
  (2, '2026-10-12 09:30:00', 'https://example.com/archivos-demo/imagenes_solicitud-2.pdf', 2, 'Demo 2'),
  (3, '2026-10-13 09:30:00', 'https://example.com/archivos-demo/imagenes_solicitud-3.pdf', 3, 'Demo 3'),
  (4, '2026-10-14 09:30:00', 'https://example.com/archivos-demo/imagenes_solicitud-4.pdf', 4, 'Demo 4');

INSERT INTO plantillas_checklist (id, estructura, nombre, tipo_equipo) VALUES
  (1, '{"titulo": "Checklist de demostración", "items": ["Inspección", "Prueba funcional"]}', 'Plantillas Checklist demo 1', 'Demo 1'),
  (2, '{"titulo": "Checklist de demostración", "items": ["Inspección", "Prueba funcional"]}', 'Plantillas Checklist demo 2', 'Demo 2'),
  (3, '{"titulo": "Checklist de demostración", "items": ["Inspección", "Prueba funcional"]}', 'Plantillas Checklist demo 3', 'Demo 3');

INSERT INTO informes_mantenimiento (id, codigo, datos_dinamicos, estado, fecha_ejecucion, fotos_url, id_equipo, id_plantilla, id_tecnico) VALUES
  (1, 'INF-2026-001', '{"tipo": "demostracion", "registro": 1, "validado": true}', 'BORRADOR', '2026-10-11 09:30:00', '["https://example.com/demo/foto-1.jpg"]', 1, 1, 1),
  (2, 'INF-2026-002', '{"tipo": "demostracion", "registro": 2, "validado": true}', 'FINALIZADO', '2026-10-12 09:30:00', '["https://example.com/demo/foto-2.jpg"]', 2, 2, 2),
  (3, 'INF-2026-003', '{"tipo": "demostracion", "registro": 3, "validado": true}', 'BORRADOR', '2026-10-13 09:30:00', '["https://example.com/demo/foto-3.jpg"]', 3, 3, 1);

INSERT INTO modulos (id, codigo, nombre) VALUES
  (1, 'INCIDENCIAS', 'Gestión de incidencias'),
  (2, 'SOLICITUDES', 'Servicios'),
  (3, 'INVENTARIO', 'Equipos');

INSERT INTO otp_codes (id, code, expiration, used, id_usuario, type) VALUES
  (1, 'OTP-DEMO-001', '2026-10-03 09:30:00', TRUE, 1, 'LOGIN'),
  (2, 'OTP-DEMO-002', '2026-10-03 09:30:00', TRUE, 2, 'PASSWORD_CHANGE'),
  (3, 'OTP-DEMO-003', '2026-10-03 09:30:00', FALSE, 3, 'PASSWORD_RESET');

INSERT INTO personas (id_persona, ape_materno_persona, ape_paterno_persona, direccion, dni, fecha_nacimiento, nombre_persona, telefono, url_direccion, usuario_id) VALUES
  (1, 'López', 'Demostración', 'Av. Demostración 101, Lima', '00000001', '1991-06-15', 'Andrea', '000000001', 'https://example.com/archivos-demo/personas-1.pdf', 1),
  (2, 'Ramos', 'Ejemplo', 'Av. Demostración 102, Lima', '00000002', '1992-06-15', 'Luis', '000000002', 'https://example.com/archivos-demo/personas-2.pdf', 2),
  (3, 'García', 'Prueba', 'Av. Demostración 103, Lima', '00000003', '1993-06-15', 'Camila', '000000003', 'https://example.com/archivos-demo/personas-3.pdf', 3),
  (4, 'Pérez', 'Ficticio', 'Av. Demostración 104, Lima', '00000004', '1994-06-15', 'Pedro', '000000004', 'https://example.com/archivos-demo/personas-4.pdf', 4),
  (5, 'Vega', 'Muestra', 'Av. Demostración 105, Lima', '00000005', '1995-06-15', 'Lucía', '000000005', 'https://example.com/archivos-demo/personas-5.pdf', 5),
  (6, 'Flores', 'Simulado', 'Av. Demostración 106, Lima', '00000006', '1996-06-15', 'Marcos', '000000006', 'https://example.com/archivos-demo/personas-6.pdf', 6);

INSERT INTO suscripciones (id, activo, fecha_fin, fecha_inicio, modulo_id, usuario_id) VALUES
  (1, TRUE, '2026-10-14 09:30:00', '2026-10-09 09:30:00', 1, 1),
  (2, TRUE, '2026-10-15 09:30:00', '2026-10-10 09:30:00', 2, 2),
  (3, TRUE, '2026-10-16 09:30:00', '2026-10-11 09:30:00', 3, 3);

INSERT INTO tipo_especialidad (id_especialidad, nombre_especialidad) VALUES
  (1, 'Redes'),
  (2, 'Hardware'),
  (3, 'Software');

INSERT INTO tecnico_especialidad (id_tec_esp, id_especialidad, id_tecnico) VALUES
  (1, 1, 1),
  (2, 2, 2),
  (3, 3, 1);

INSERT INTO usuario_sede_area (id, id_area, id_sede, id_usuario) VALUES
  (1, 1, 1, 1),
  (2, 2, 2, 2),
  (3, 3, 3, 3);

-- Sincronizacion de secuencias IDENTITY para evitar colisiones en futuras inserciones
SELECT setval(pg_get_serial_sequence('roles', 'id_rol'), coalesce(max(id_rol), 1)) FROM roles;
SELECT setval(pg_get_serial_sequence('usuarios', 'id_usuario'), coalesce(max(id_usuario), 1)) FROM usuarios;
SELECT setval(pg_get_serial_sequence('niveles_admin', 'id_nivel_admin'), coalesce(max(id_nivel_admin), 1)) FROM niveles_admin;
SELECT setval(pg_get_serial_sequence('admins', 'id_admin'), coalesce(max(id_admin), 1)) FROM admins;
SELECT setval(pg_get_serial_sequence('areas', 'id'), coalesce(max(id), 1)) FROM areas;
SELECT setval(pg_get_serial_sequence('categorias', 'id_categoria'), coalesce(max(id_categoria), 1)) FROM categorias;
SELECT setval(pg_get_serial_sequence('estados_incidencia', 'id_estado_incidencia'), coalesce(max(id_estado_incidencia), 1)) FROM estados_incidencia;
SELECT setval(pg_get_serial_sequence('severidades', 'id_severidad'), coalesce(max(id_severidad), 1)) FROM severidades;
SELECT setval(pg_get_serial_sequence('incidencias', 'id_incidencia'), coalesce(max(id_incidencia), 1)) FROM incidencias;
SELECT setval(pg_get_serial_sequence('articulos_kb', 'id'), coalesce(max(id), 1)) FROM articulos_kb;
SELECT setval(pg_get_serial_sequence('modos_atencion', 'id_modo_atencion'), coalesce(max(id_modo_atencion), 1)) FROM modos_atencion;
SELECT setval(pg_get_serial_sequence('estados_tecnico', 'id_estado_tecnico'), coalesce(max(id_estado_tecnico), 1)) FROM estados_tecnico;
SELECT setval(pg_get_serial_sequence('tecnicos', 'id_tecnico'), coalesce(max(id_tecnico), 1)) FROM tecnicos;
SELECT setval(pg_get_serial_sequence('atenciones', 'id_atencion'), coalesce(max(id_atencion), 1)) FROM atenciones;
SELECT setval(pg_get_serial_sequence('auditoria_informes', 'id'), coalesce(max(id), 1)) FROM auditoria_informes;
SELECT setval(pg_get_serial_sequence('chat_incidencia', 'id_chat'), coalesce(max(id_chat), 1)) FROM chat_incidencia;
SELECT setval(pg_get_serial_sequence('configuracion_sla', 'id'), coalesce(max(id), 1)) FROM configuracion_sla;
SELECT setval(pg_get_serial_sequence('tipo_costo', 'id_tipo_costo'), coalesce(max(id_tipo_costo), 1)) FROM tipo_costo;
SELECT setval(pg_get_serial_sequence('proveedores', 'id_proveedor'), coalesce(max(id_proveedor), 1)) FROM proveedores;
SELECT setval(pg_get_serial_sequence('costos_incidencia', 'id_costo'), coalesce(max(id_costo), 1)) FROM costos_incidencia;
SELECT setval(pg_get_serial_sequence('tipo_servicio', 'id_tipo_servicio'), coalesce(max(id_tipo_servicio), 1)) FROM tipo_servicio;
SELECT setval(pg_get_serial_sequence('estado_solicitud_servicio', 'id_estado'), coalesce(max(id_estado), 1)) FROM estado_solicitud_servicio;
SELECT setval(pg_get_serial_sequence('solicitudes_servicio', 'id_solicitud'), coalesce(max(id_solicitud), 1)) FROM solicitudes_servicio;
SELECT setval(pg_get_serial_sequence('productos', 'id'), coalesce(max(id), 1)) FROM productos;
SELECT setval(pg_get_serial_sequence('costos_solicitud', 'id_costo'), coalesce(max(id_costo), 1)) FROM costos_solicitud;
SELECT setval(pg_get_serial_sequence('empresa_detalle', 'id_detalle_empresa'), coalesce(max(id_detalle_empresa), 1)) FROM empresa_detalle;
SELECT setval(pg_get_serial_sequence('empresas', 'id_empresa'), coalesce(max(id_empresa), 1)) FROM empresas;
SELECT setval(pg_get_serial_sequence('encuestas_satisfaccion', 'id'), coalesce(max(id), 1)) FROM encuestas_satisfaccion;
SELECT setval(pg_get_serial_sequence('sedes', 'id'), coalesce(max(id), 1)) FROM sedes;
SELECT setval(pg_get_serial_sequence('tipos_equipo', 'id'), coalesce(max(id), 1)) FROM tipos_equipo;
SELECT setval(pg_get_serial_sequence('equipos', 'id'), coalesce(max(id), 1)) FROM equipos;
SELECT setval(pg_get_serial_sequence('historial_cotizacion', 'id_historial_cotizacion'), coalesce(max(id_historial_cotizacion), 1)) FROM historial_cotizacion;
SELECT setval(pg_get_serial_sequence('historial_incidencia', 'id_historial'), coalesce(max(id_historial), 1)) FROM historial_incidencia;
SELECT setval(pg_get_serial_sequence('historial_mantenimiento_equipos', 'id'), coalesce(max(id), 1)) FROM historial_mantenimiento_equipos;
SELECT setval(pg_get_serial_sequence('historial_solicitud_servicio', 'id_historial'), coalesce(max(id_historial), 1)) FROM historial_solicitud_servicio;
SELECT setval(pg_get_serial_sequence('imagenes_chat', 'id_imagen_chat'), coalesce(max(id_imagen_chat), 1)) FROM imagenes_chat;
SELECT setval(pg_get_serial_sequence('imagenes_incidencia', 'id_imagen'), coalesce(max(id_imagen), 1)) FROM imagenes_incidencia;
SELECT setval(pg_get_serial_sequence('imagenes_solicitud', 'id'), coalesce(max(id), 1)) FROM imagenes_solicitud;
SELECT setval(pg_get_serial_sequence('plantillas_checklist', 'id'), coalesce(max(id), 1)) FROM plantillas_checklist;
SELECT setval(pg_get_serial_sequence('informes_mantenimiento', 'id'), coalesce(max(id), 1)) FROM informes_mantenimiento;
SELECT setval(pg_get_serial_sequence('modulos', 'id'), coalesce(max(id), 1)) FROM modulos;
SELECT setval(pg_get_serial_sequence('otp_codes', 'id'), coalesce(max(id), 1)) FROM otp_codes;
SELECT setval(pg_get_serial_sequence('personas', 'id_persona'), coalesce(max(id_persona), 1)) FROM personas;
SELECT setval(pg_get_serial_sequence('suscripciones', 'id'), coalesce(max(id), 1)) FROM suscripciones;
SELECT setval(pg_get_serial_sequence('tipo_especialidad', 'id_especialidad'), coalesce(max(id_especialidad), 1)) FROM tipo_especialidad;
SELECT setval(pg_get_serial_sequence('tecnico_especialidad', 'id_tec_esp'), coalesce(max(id_tec_esp), 1)) FROM tecnico_especialidad;
SELECT setval(pg_get_serial_sequence('usuario_sede_area', 'id'), coalesce(max(id), 1)) FROM usuario_sede_area;

COMMIT;
