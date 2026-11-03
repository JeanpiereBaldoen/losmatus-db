# LOSMATUS - Backend

Backend del sistema LOSMATUS desarrollado con Java 21,
Spring Boot 3.3.4, Maven y MySQL 8.

## Tecnologías

- Java 21
- Spring Boot 3.3.4
- Spring Data JPA
- Hibernate
- Maven
- MySQL 8

## Estructura del proyecto

- backend/: Código fuente del backend.
- database/: Archivo SQL original.
- entity/: Entidades JPA.
- repository/: Acceso a datos.
- service/: Lógica del negocio.
- controller/: API REST.

## Base de datos

Nombre: tecnolp0_losmatusv4

La estructura contiene 47 tablas y 66 claves foráneas.

Para utilizarla, importar manualmente el archivo:

database/BD_LOSMATUS.sql

No se utiliza Flyway.

## Configuración

El archivo application.properties contiene la conexión
a MySQL.

La contraseña se configura mediante la variable
de entorno DB_PASSWORD.

## Ejecución

Abrir una terminal en la carpeta backend.

Compilar:

mvn clean compile

Ejecutar:

mvn spring-boot:run

## Endpoints implementados

GET /api/incidencias

GET /api/incidencias/{id}

## Estado del proyecto

Primera versión del backend.

Entidades implementadas:
- Rol
- Usuario
- Incidencia

Se ha verificado la compilación, la conexión con MySQL
y las consultas de incidencias.

La API todavía no dispone de autenticación, por lo
que debe utilizarse únicamente en un entorno local
de desarrollo.

Los demás módulos están pendientes de implementación.