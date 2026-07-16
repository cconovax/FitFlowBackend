# SexBody

Proyecto base multi-módulo.

Este repositorio incluye el módulo `auth` (API de Autenticación con JWT, Roles y Permisos).

# Auth: API de Autenticación con JWT, Roles y Permisos

API REST de autenticación implementada con Spring Boot 4, JWT, PostgreSQL y arquitectura limpia por capas.

## 🏗️ Arquitectura

El proyecto sigue una arquitectura limpia dividida en capas:

### Capas de la Aplicación

```
├── domain/                     # Capa de Dominio
│   ├── entities/              # Entidades del negocio
│   ├── repositories/          # Interfaces de repositorios
│   └── exceptions/            # Excepciones del dominio
│
├── application/               # Capa de Aplicación
│   ├── dto/                   # Data Transfer Objects
│   │   ├── request/          # DTOs de entrada
│   │   └── response/         # DTOs de salida
│   └── services/             # Lógica de negocio
│
├── infrastructure/            # Capa de Infraestructura
│   ├── config/               # Configuraciones
│   ├── security/             # Seguridad y JWT
│   │   ├── jwt/             # Utilidades JWT
│   │   ├── annotations/     # Anotaciones personalizadas
│   │   └── aspect/          # AOP para validación de permisos
│   └── exception/           # Manejo global de excepciones
│
└── presentation/              # Capa de Presentación
    └── controllers/          # Controladores REST
```

## 🚀 Características

- ✅ **Autenticación JWT**: Sistema de autenticación basado en tokens
- ✅ **Roles y Permisos**: Usuarios con múltiples roles, roles con múltiples permisos
- ✅ **Validación de Permisos**: Protección de endpoints mediante anotaciones
- ✅ **Arquitectura Limpia**: Separación clara de responsabilidades
- ✅ **PostgreSQL**: Base de datos relacional para persistencia
- ✅ **OpenAPI/Swagger**: Documentación interactiva de la API
- ✅ **Manejo de Errores**: Sistema robusto de manejo de excepciones
- ✅ **Validación**: Validación automática de DTOs con Bean Validation

## 📋 Requisitos Previos

- Java 17 o superior
- PostgreSQL 12 o superior
- Gradle 8.x

## ⚙️ Configuración

### 1. Base de Datos

Crea una base de datos PostgreSQL:

```sql
CREATE DATABASE sexbody_db;
```

### 2. Configuración de la Aplicación

Edita el archivo `src/main/resources/application.properties`:

```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/sexbody_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

# JWT Configuration (¡Cambia esto en producción!)
jwt.secret=5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437
jwt.expiration=86400000
```

### 3. Compilar y Ejecutar

```bash
# Windows
gradlew.bat :auth:bootRun

# Linux/Mac
./gradlew :auth:bootRun
```

La aplicación estará disponible en: `http://localhost:8080`

## 📚 Documentación de la API

### Swagger UI

Accede a la documentación interactiva en:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

### Usuarios de Prueba

La aplicación crea automáticamente usuarios de prueba:

| Usuario | Contraseña | Roles | Permisos |
|---------|-----------|-------|----------|
| admin | admin123 | ADMIN | Todos los permisos |
| user | user123 | USER | user:read |

## 🔐 Autenticación

### 1. Registro de Usuario

**POST** `/api/auth/register`

```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "roleIds": [1]
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 3,
  "username": "john_doe",
  "email": "john@example.com",
  "roles": ["USER"],
  "permissions": ["user:read"]
}
```

### 2. Iniciar Sesión

**POST** `/api/auth/login`

```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "admin",
  "email": "admin@example.com",
  "roles": ["ADMIN"],
  "permissions": [
    "user:read",
    "user:create",
    "user:update",
    "user:delete",
    "role:read",
    "role:create",
    "role:update",
    "role:delete",
    "permission:read",
    "permission:create",
    "permission:update",
    "permission:delete"
  ]
}
```

### 3. Obtener Usuario Actual

**GET** `/api/auth/me`

**Headers:**
```
Authorization: Bearer <token>
```

## 🛡️ Endpoints Protegidos

### Usuarios

#### Listar Todos los Usuarios
**GET** `/api/users`
- **Permiso requerido**: `user:read`
- **Headers**: `Authorization: Bearer <token>`

#### Obtener Usuario por ID
**GET** `/api/users/{id}`
- **Permiso requerido**: `user:read`
- **Headers**: `Authorization: Bearer <token>`

#### Eliminar Usuario
**DELETE** `/api/users/{id}`
- **Permiso requerido**: `user:delete`
- **Headers**: `Authorization: Bearer <token>`

## 🎯 Sistema de Permisos

### Permisos Predefinidos

#### Usuarios
- `user:read` - Leer usuarios
- `user:create` - Crear usuarios
- `user:update` - Actualizar usuarios
- `user:delete` - Eliminar usuarios

#### Roles
- `role:read` - Leer roles
- `role:create` - Crear roles
- `role:update` - Actualizar roles
- `role:delete` - Eliminar roles

#### Permisos
- `permission:read` - Leer permisos
- `permission:create` - Crear permisos
- `permission:update` - Actualizar permisos
- `permission:delete` - Eliminar permisos

### Roles Predefinidos

#### ADMIN
- Todos los permisos del sistema

#### MODERATOR
- `user:read`
- `user:update`
- `role:read`

#### USER
- `user:read`

## 🔧 Uso de la Anotación @RequirePermission

Para proteger un endpoint con un permiso específico:

```java
@GetMapping("/protected")
@RequirePermission("user:read")
public ResponseEntity<?> protectedEndpoint() {
    // Este método solo se ejecuta si el usuario tiene el permiso "user:read"
    return ResponseEntity.ok("Acceso permitido");
}
```

## 🗄️ Modelo de Base de Datos

```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│   users     │         │  user_roles  │         │    roles    │
├─────────────┤         ├──────────────┤         ├─────────────┤
│ id (PK)     │────────>│ user_id (FK) │<────────│ id (PK)     │
│ username    │         │ role_id (FK) │         │ name        │
│ email       │         └──────────────┘         │ description │
│ password    │                                  │ created_at  │
│ first_name  │                                  └──────┬──────┘
│ last_name   │                                         │
│ enabled     │         ┌──────────────────┐           │
│ created_at  │         │ role_permissions │           │
│ updated_at  │         ├──────────────────┤           │
└─────────────┘         │ role_id (FK)     │<──────────┘
                        │ permission_id    │
                        └────────┬─────────┘
                                 │
                        ┌────────▼──────┐
                        │  permissions  │
                        ├───────────────┤
                        │ id (PK)       │
                        │ name          │
                        │ description   │
                        │ created_at    │
                        └───────────────┘
```

## 🧪 Pruebas con cURL

### Registro
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "test123",
    "firstName": "Test",
    "lastName": "User"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### Listar Usuarios (con token)
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer <tu_token_aqui>"
```

## 🐛 Manejo de Errores

La API devuelve errores en un formato consistente:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Usuario no encontrado con ID: 999",
  "path": "/api/users/999",
  "timestamp": "2026-01-20T10:30:00"
}
```

### Códigos de Estado HTTP

- `200` - OK
- `201` - Created
- `204` - No Content
- `400` - Bad Request
- `401` - Unauthorized
- `403` - Forbidden
- `404` - Not Found
- `409` - Conflict
- `500` - Internal Server Error

## 📦 Dependencias Principales

- Spring Boot 4.0.1
- Spring Security 6.x
- Spring Data JPA
- PostgreSQL Driver
- JJWT 0.12.3
- Lombok
- SpringDoc OpenAPI 2.3.0
- Bean Validation

## 🔒 Seguridad

### Mejores Prácticas Implementadas

1. **Contraseñas encriptadas** con BCrypt
2. **Tokens JWT** con firma HMAC-SHA256
3. **Validación de entrada** en todos los endpoints
4. **Manejo seguro de excepciones** sin exponer información sensible
5. **CORS configurado** según necesidades
6. **Sesiones stateless** con JWT

### Recomendaciones para Producción

1. **Cambiar la clave secreta JWT** en `application.properties`
2. **Usar variables de entorno** para credenciales sensibles
3. **Habilitar HTTPS**
4. **Configurar CORS** apropiadamente
5. **Implementar rate limiting**
6. **Agregar logs de auditoría**
7. **Configurar expiración de tokens** según necesidades

## 🚀 Extensiones Futuras

- [ ] Refresh tokens
- [ ] Verificación de email
- [ ] Recuperación de contraseña
- [ ] Autenticación de dos factores (2FA)
- [ ] API de gestión de roles y permisos
- [ ] Auditoría de acciones de usuario
- [ ] Paginación en listados
- [ ] Filtros y búsqueda avanzada
- [ ] Rate limiting por usuario

## 📝 Licencia

Este proyecto está bajo la Licencia Apache 2.0.

## 👥 Autor

Desarrollado con ❤️ usando Spring Boot y arquitectura limpia.
