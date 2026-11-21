# LOSMATUS — Backend Spring Boot 3 & Java 21 (PostgreSQL 18)

API REST empresarial para la gestión integral de soporte técnico, incidencias, solicitudes de servicio, inventario de equipos y administración de usuarios para la organización **Los Matus**.

Desarrollado con **Java 21**, **Spring Boot 3.3.4**, **Spring Security con JWT y BCrypt**, **Spring Data JPA**, **Hibernate (ddl-auto=validate)** y **PostgreSQL 18**.

---

## 📋 Características Principales

- **Arquitectura Limpia en Capas**: Controladores REST, Servicios transaccionales, DTOs inmutables con Records y validaciones Jakarta, Repositorios Spring Data JPA con métodos tipados.
- **Seguridad Robusta**:
  - Autenticación mediante tokens JWT (firmados con algoritmo HMAC-SHA256).
  - Cifrado unidireccional de contraseñas estrictamente con **BCrypt** (costo 10). Se descarta autenticación en texto plano legacy.
  - Autorización basada en roles del sistema: `ADMIN`, `TECNICO`, `CLIENTE`.
  - **Aislamiento Estricto de Datos (Multi-Tenancy por Cliente)**: Los clientes solo pueden acceder a sus propias incidencias, solicitudes, chats y perfiles.
  - **Restricción de Registro**: El registro público (`/api/auth/registro`) asigna únicamente el rol `CLIENTE`. La creación de administradores y técnicos está restringida a administradores autenticados.
  - **Protección de Datos Sensibles**: Ningún endpoint expone hashes, contraseñas ni códigos OTP en las respuestas JSON.
- **Manejo Centralizado de Excepciones**: Respuestas JSON estructuradas con formato uniforme (`ApiError`) para errores 400 (Bad Request), 401 (Unauthorized), 403 (Forbidden), 404 (Not Found), 409 (Conflict) y 500 (Internal Server Error).
- **CORS Configurable**: Compatible con frontends en Next.js, React, Angular, Vue o entornos de desarrollo mediante orígenes permitidos configurables por variable de entorno.
- **Base de Datos Validada**: Mapeo exacto de las **47 tablas**, 272 columnas y 66 claves foráneas en PostgreSQL 18 con tipos estándar (`IDENTITY`, `boolean`, `timestamp(6)`, `jsonb`), sin uso de Flyway/Liquibase y manteniendo Hibernate con `ddl-auto=validate`.

---

## 🛠️ Tecnologías y Dependencias

| Tecnología | Versión / Componente | Propósito |
|---|---|---|
| **Java** | 21 LTS | Plataforma y runtime de ejecución |
| **Spring Boot** | 3.3.4 | Framework backend base |
| **Spring Security** | 6.x | Control de acceso, filtros de seguridad y autorización |
| **JJWT (io.jsonwebtoken)** | 0.12.6 | Generación y validación de tokens JWT |
| **Spring Data JPA / Hibernate** | 6.5.3 | Persistencia con validación de esquema (`ddl-auto=validate`) |
| **PostgreSQL JDBC Driver** | 42.7.x | Driver JDBC oficial para PostgreSQL 18 |
| **Lombok** | 1.18.x | Reducción de código boilerplate en entidades |
| **Jakarta Validation** | 3.x | Validación declarativa de peticiones (`@NotBlank`, `@Email`, etc.) |
| **JUnit 5 & Mockito** | 5.10.x / 5.11.x | Pruebas unitarias y de integración completas |

---

## ⚙️ Variables de Entorno y Configuración

El proyecto se configura mediante `src/main/resources/application.properties` y variables de entorno del sistema:

| Variable | Descripción | Valor por Defecto |
|---|---|---|
| `DB_URL` | URL de conexión JDBC a PostgreSQL | `jdbc:postgresql://localhost:5432/losmatus_db` |
| `DB_USER` | Usuario de PostgreSQL | `postgres` |
| `DB_PASSWORD` | Contraseña del usuario PostgreSQL | *(Definida por variable de entorno)* |
| `TEST_DB_URL` | URL de conexión para pruebas | `jdbc:postgresql://localhost:5432/losmatus_test_db` |
| `TEST_DB_USER` | Usuario para pruebas | `postgres` |
| `JPA_SHOW_SQL` | Mostrar sentencias SQL en consola | `false` |
| `JWT_SECRET` | Clave secreta hexadecimal para firma JWT (mínimo 256 bits) | *(Definida por variable de entorno)* |
| `JWT_EXPIRATION_MS` | Tiempo de vida del token JWT en milisegundos | `86400000` (24 horas) |
| `CORS_ALLOWED_ORIGINS` | Orígenes permitidos separados por coma | `http://localhost:3000,http://localhost:5173,http://localhost:4200` |

---

## 🚀 Instrucciones de Ejecución y Migración

### 1. Prerrequisitos
- JDK 21 instalado y configurado en `JAVA_HOME`.
- Apache Maven 3.9+ instalado y disponible en `PATH`.
- PostgreSQL Server 18 en ejecución en el puerto 5432.

### 2. Estructura y Carga de la Base de Datos
El directorio `database/` contiene los scripts adaptados para PostgreSQL:
- `01_estructura.sql`: Definición de las 47 tablas, tipos de datos PostgreSQL, PKs, restricciones UNIQUE, CHECKs y las 66 claves foráneas desacopladas.
- `02_datos.sql`: Datos iniciales de demostración, configuración y sincronización de secuencias `IDENTITY`.
- `03_verificacion.sql`: Consultas de comprobación de conteos e integridad referencial (detección de registros huérfanos).

#### Creación de Bases de Datos e Importación:
```powershell
# 1. Crear la base principal y la base de pruebas en PostgreSQL
psql -U postgres -h localhost -p 5432 -W -c "CREATE DATABASE losmatus_db;"
psql -U postgres -h localhost -p 5432 -W -c "CREATE DATABASE losmatus_test_db;"

# 2. Cargar estructura y datos en la base principal
psql -U postgres -h localhost -p 5432 -d losmatus_db -W -f database/01_estructura.sql
psql -U postgres -h localhost -p 5432 -d losmatus_db -W -f database/02_datos.sql

# 3. Cargar estructura y datos en la base de pruebas
psql -U postgres -h localhost -p 5432 -d losmatus_test_db -W -f database/01_estructura.sql
psql -U postgres -h localhost -p 5432 -d losmatus_test_db -W -f database/02_datos.sql

# 4. Verificar integridad y conteo de tablas
psql -U postgres -h localhost -p 5432 -d losmatus_db -W -f database/03_verificacion.sql
```

#### Respaldo y Restauración (PostgreSQL):
```powershell
# Generar copia de seguridad (Dump)
pg_dump -U postgres -h localhost -p 5432 -d losmatus_db -F p -f backup_losmatus_db.sql

# Restaurar copia de seguridad
psql -U postgres -h localhost -p 5432 -d losmatus_db -f backup_losmatus_db.sql
```

### 3. Compilación y Ejecución de Pruebas
Desde la carpeta `backend/` definiendo las variables de entorno obligatorias:
```powershell
$env:DB_PASSWORD = "tu_password_postgres"
$env:JWT_SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
mvn clean test
```
*Las pruebas de integración se ejecutan contra la base de datos independiente `losmatus_test_db` validando Hibernate (`ddl-auto=validate`), autorización por roles, aislamiento de clientes y contratos REST.*

### 4. Iniciar el Servidor de Desarrollo
Desde la carpeta `backend/`:
```powershell
$env:DB_PASSWORD = "tu_password_postgres"
$env:JWT_SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
mvn spring-boot:run
```
El servidor iniciará en: `http://localhost:8080`

---

## 📡 Catálogo Completo de Endpoints REST

Todas las peticiones protegidas deben incluir la cabecera:
`Authorization: Bearer <TOKEN_JWT>`

### 1. Autenticación Pública (`/api/auth`)
| Método | Endpoint | Acceso | Descripción |
|---|---|---|---|
| `POST` | `/api/auth/login` | Público | Autenticación de usuario con email y contraseña. Devuelve token JWT, idUsuario y rol. |
| `POST` | `/api/auth/registro` | Público | Registro público de nuevos usuarios (**únicamente rol CLIENTE**). Requiere email, password, DNI y nombre. |

### 2. Gestión de Usuarios y Personas (`/api/usuarios`, `/api/personas`)
| Método | Endpoint | Rol Requerido | Descripción |
|---|---|---|---|
| `GET` | `/api/usuarios` | Autenticado | Listar usuarios (sin exponer contraseñas ni hashes). |
| `GET` | `/api/usuarios/{id}` | Autenticado | Obtener usuario por ID. |
| `GET` | `/api/usuarios/perfil` | Autenticado | Obtener perfil del usuario actualmente autenticado. |
| `POST` | `/api/usuarios` | `ADMIN` | Crear nuevo usuario con rol asignado (`ADMIN`, `TECNICO`, `CLIENTE`) y contraseña cifrada con BCrypt. |
| `PUT` | `/api/usuarios/{id}` | `ADMIN` | Actualizar datos de usuario (email, rol, estado activo/inactivo). |
| `PUT` | `/api/usuarios/cambiar-password` | Autenticado | Cambiar contraseña del usuario en sesión validando la contraseña actual. |
| `GET` | `/api/personas` | `ADMIN`, `TECNICO` | Listar todas las personas registradas. |
| `GET` | `/api/personas/{id}` | Autenticado | Obtener datos de persona por ID (clientes solo su propio registro). |
| `GET` | `/api/personas/mi-perfil` | Autenticado | Obtener información personal del usuario autenticado. |
| `PUT` | `/api/personas/{id}` | Autenticado | Actualizar información personal (clientes solo su propio registro). |

### 3. Administradores y Técnicos (`/api/admins`, `/api/tecnicos`)
| Método | Endpoint | Rol Requerido | Descripción |
|---|---|---|---|
| `GET` | `/api/admins` | `ADMIN` | Listar administradores del sistema. |
| `GET` | `/api/admins/{id}` | `ADMIN` | Obtener administrador por ID. |
| `POST` | `/api/admins` | `ADMIN` | Crear registro de administrador vinculado a un usuario. |
| `PUT` | `/api/admins/{id}` | `ADMIN` | Actualizar información del administrador. |
| `GET` | `/api/tecnicos` | Autenticado | Listar técnicos. |
| `GET` | `/api/tecnicos/{id}` | Autenticado | Obtener técnico por ID. |
| `POST` | `/api/tecnicos` | `ADMIN` | Registrar técnico vinculado a un usuario. |
| `PUT` | `/api/tecnicos/{id}` | `ADMIN` | Actualizar información del técnico. |
| `GET` | `/api/tecnicos/{id}/especialidades` | Autenticado | Listar especialidades asignadas a un técnico. |
| `POST` | `/api/tecnicos/{id}/especialidades` | `ADMIN` | Asignar especialidad a un técnico. |
| `DELETE` | `/api/tecnicos/{id}/especialidades/{idEspecialidad}` | `ADMIN` | Desasignar especialidad de un técnico. |

### 4. Módulo de Incidencias (`/api/incidencias`)
*Aislamiento: Los clientes únicamente pueden ver y gestionar sus propias incidencias.*
| Método | Endpoint | Rol Requerido | Descripción |
|---|---|---|---|
| `GET` | `/api/incidencias` | Autenticado | Listar incidencias (clientes solo las reportadas por ellos). |
| `GET` | `/api/incidencias/{id}` | Autenticado | Obtener incidencia por ID (valida propiedad si es cliente). |
| `POST` | `/api/incidencias` | Autenticado | Reportar nueva incidencia. Si es cliente, se auto-asigna a su ID de usuario. |
| `PUT` | `/api/incidencias/{id}` | Autenticado | Actualizar incidencia. Registra cambios de estado en el historial. |
| `PATCH` | `/api/incidencias/{id}/estado/{idEstado}` | `ADMIN`, `TECNICO` | Transición de estado con registro de auditoría en historial. |
| `GET` | `/api/incidencias/{id}/chats` | Autenticado | Listar mensajes de chat de la incidencia (valida propiedad). |
| `POST` | `/api/incidencias/{id}/chats` | Autenticado | Enviar mensaje en el chat de la incidencia. |
| `GET` | `/api/incidencias/{id}/historial` | Autenticado | Consultar historial cronológico de cambios de estado de la incidencia. |
| `GET` | `/api/incidencias/{id}/imagenes` | Autenticado | Listar imágenes adjuntas a la incidencia. |
| `POST` | `/api/incidencias/{id}/imagenes` | Autenticado | Adjuntar imagen a la incidencia. |
| `GET` | `/api/incidencias/{id}/costos` | `ADMIN`, `TECNICO` | Consultar costos de resolución de la incidencia (restringido a personal interno). |
| `POST` | `/api/incidencias/{id}/costos` | `ADMIN`, `TECNICO` | Registrar costo asociado a la incidencia. |
| `GET` | `/api/incidencias/{id}/encuesta` | Autenticado | Consultar encuesta de satisfacción de la incidencia. |
| `POST` | `/api/incidencias/{id}/encuesta` | Autenticado | Registrar calificación y encuesta de satisfacción (solo propietario de la incidencia). |

### 5. Módulo de Solicitudes de Servicio (`/api/solicitudes-servicio`)
*Aislamiento: Los clientes únicamente pueden ver y gestionar sus propias solicitudes.*
| Método | Endpoint | Rol Requerido | Descripción |
|---|---|---|---|
| `GET` | `/api/solicitudes-servicio` | Autenticado | Listar solicitudes (clientes solo sus solicitudes). |
| `GET` | `/api/solicitudes-servicio/{id}` | Autenticado | Obtener solicitud por ID (valida propiedad si es cliente). |
| `POST` | `/api/solicitudes-servicio` | Autenticado | Crear solicitud con correlativo y código autonumérico anual (`SOL-YYYY-NNNN`). |
| `PUT` | `/api/solicitudes-servicio/{id}` | Autenticado | Actualizar solicitud de servicio. |
| `PATCH` | `/api/solicitudes-servicio/{id}/estado/{idEstado}` | `ADMIN`, `TECNICO` | Cambiar estado de la solicitud con registro en el historial. |
| `GET` | `/api/solicitudes-servicio/{id}/historial` | Autenticado | Consultar historial de cambios de la solicitud. |
| `GET` | `/api/solicitudes-servicio/{id}/costos` | `ADMIN`, `TECNICO` | Listar costos asociados a la solicitud de servicio. |
| `POST` | `/api/solicitudes-servicio/{id}/costos` | `ADMIN`, `TECNICO` | Registrar costo de la solicitud. |
| `GET` | `/api/solicitudes-servicio/{id}/cotizaciones` | Autenticado | Consultar versiones históricas de cotización. |
| `POST` | `/api/solicitudes-servicio/{id}/cotizaciones` | `ADMIN`, `TECNICO` | Generar nueva versión de cotización. |
| `GET` | `/api/solicitudes-servicio/{id}/imagenes` | Autenticado | Listar documentos o imágenes adjuntas a la solicitud. |
| `POST` | `/api/solicitudes-servicio/{id}/imagenes` | Autenticado | Adjuntar documento o imagen a la solicitud. |

### 6. Inventario, Mantenimiento y Auditoría (`/api/equipos`, `/api/informes-mantenimiento`, `/api/auditoria-informes`)
| Método | Endpoint | Rol Requerido | Descripción |
|---|---|---|---|
| `CRUD` | `/api/equipos` | `ADMIN`, `TECNICO` | Gestión completa del inventario de equipos (operativo, en mantenimiento, de baja, etc.). |
| `GET` | `/api/equipos/{id}/historial-mantenimiento` | Autenticado | Consultar hoja de vida e historial de eventos de mantenimiento del equipo. |
| `POST` | `/api/equipos/{id}/historial-mantenimiento` | `ADMIN`, `TECNICO` | Registrar evento en la hoja de vida del equipo. |
| `GET` | `/api/informes-mantenimiento` | `ADMIN`, `TECNICO` | Listar informes técnicos de mantenimiento. |
| `GET` | `/api/informes-mantenimiento/{id}` | `ADMIN`, `TECNICO` | Obtener detalle de informe técnico. |
| `POST` | `/api/informes-mantenimiento` | `ADMIN`, `TECNICO` | Crear informe técnico con auditoría automática en `auditoria_informes`. |
| `PUT` | `/api/informes-mantenimiento/{id}` | `ADMIN`, `TECNICO` | Actualizar informe técnico registrando datos anteriores y nuevos en auditoría. |
| `GET` | `/api/plantillas-checklist` | Autenticado | Listar plantillas dinámicas de checklist de inspección. |
| `POST` | `/api/plantillas-checklist` | `ADMIN` | Crear nueva plantilla de checklist. |
| `GET` | `/api/auditoria-informes` | `ADMIN` | Pistas de auditoría de modificaciones a informes (acceso exclusivo para administradores). |
| `GET` | `/api/auditoria-informes/informe/{id}` | `ADMIN` | Consultar historial de cambios y responsable de un informe específico. |

### 7. Catálogos, Base de Conocimiento y Configuración
| Método | Endpoint | Rol Requerido | Descripción |
|---|---|---|---|
| `GET` | `/api/articulos-kb/publicados` | Autenticado | Consultar base de conocimiento (artículos publicados accesibles por clientes). |
| `GET/POST/PUT` | `/api/articulos-kb` | `ADMIN`, `TECNICO` | Gestión y aprobación de artículos de la base de conocimiento. |
| `CRUD` | `/api/productos` | Autenticado / `ADMIN` | Catálogo de productos y repuestos. |
| `CRUD` | `/api/proveedores` | `ADMIN`, `TECNICO` | Directorio de proveedores homologados. |
| `GET/POST/DEL`| `/api/configuracion-sla` | `ADMIN` | Tiempos máximos de respuesta y resolución por severidad y cliente. |
| `CRUD` | `/api/suscripciones` | Autenticado / `ADMIN` | Suscripciones de clientes a módulos del sistema. |
| `GET` | `/api/modulos` | Autenticado | Catálogo de módulos funcionales (`INCIDENCIAS`, `SOLICITUDES`, etc.). |
| `GET` | `/api/roles` | Autenticado | Catálogo de roles de usuario (`ADMIN`, `TECNICO`, `CLIENTE`). |
| `GET` | `/api/niveles-admin` | `ADMIN` | Catálogo de niveles jerárquicos de administración. |
| `GET` | `/api/categorias` | Autenticado | Catálogo de categorías de incidencias (`Hardware`, `Software`, `Redes`). |
| `GET` | `/api/severidades` | Autenticado | Catálogo de severidades (`Baja`, `Media`, `Alta`). |
| `GET` | `/api/estados-incidencia` | Autenticado | Estados de tickets de incidencias (`Abierta`, `En atención`, `Resuelta`). |
| `GET` | `/api/estados-solicitud` | Autenticado | Estados de solicitudes de servicio (`Pendiente`, `En proceso`, `Finalizada`). |
| `GET` | `/api/estados-tecnico` | Autenticado | Disponibilidad de técnicos (`Disponible`, `Ocupado`, `No disponible`). |
| `GET` | `/api/modos-atencion` | Autenticado | Modalidades de soporte (`Remoto`, `Presencial`, `Telefónico`). |
| `GET` | `/api/tipos-servicio` | Autenticado | Tipos de servicio (`Mantenimiento`, `Instalación`, `Soporte`). |
| `GET` | `/api/tipos-equipo` | Autenticado | Tipos de equipos (`Laptop`, `Impresora`, `Router`). |
| `GET` | `/api/tipos-costo` | Autenticado | Tipos de costos aplicables (`Mano de obra`, `Repuestos`, `Traslado`). |
| `GET` | `/api/tipos-especialidad` | Autenticado | Especialidades técnicas (`Redes`, `Hardware`, `Software`). |
| `CRUD` | `/api/empresas`, `/api/sedes`, `/api/areas` | `ADMIN`, `TECNICO` | Estructura organizacional y sedes de clientes. |
| `GET/POST/DEL`| `/api/usuario-sede-area` | `ADMIN` | Asignación de usuarios a sedes y áreas físicas. |

---

## 🔒 Formato de Errores HTTP (`ApiError`)

Todas las excepciones del sistema se capturan de forma centralizada y devuelven una estructura uniforme:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Error de validacion en la solicitud",
  "timestamp": "2026-11-15T20:04:30.387",
  "validaciones": {
    "email": "El email debe tener un formato valido",
    "password": "La contrasena debe tener al menos 6 caracteres"
  }
}
```

Para errores de restricción de integridad (`DataIntegrityViolationException`), se retorna código `409 CONFLICT`:
```json
{
  "error": "No se pudo guardar o eliminar el registro.",
  "detalle": "Comprueba las claves foraneas, los valores unicos y si el registro esta en uso."
}
```
