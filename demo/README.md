# Corruna (demo)

Este documento contiene las instrucciones de instalación, ejecución, credenciales de prueba y una colección Postman para probar la API (tema: heladería).

**Resumen rápido**
- Swagger UI: http://localhost:8081/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8081/v3/api-docs
- Endpoint notas:
  - Crear usuario: `POST /api/usuarios`
  - Login: `POST /auth/login`
  - Listar usuarios: `GET /api/usuarios`


## Requisitos previos
- Java JDK 17+ (el `pom.xml` indica Java 21; se recomienda JDK 21). Asegúrate que `JAVA_HOME` apunte al JDK, no a un JRE.
- Git (opcional)
- MySQL (servidor) y cliente `mysql` en tu PATH.
- Maven wrapper incluido (`./mvnw.cmd` en Windows)
- (Opcional) Node.js si quieres generar hashes bcrypt con `bcryptjs` localmente


## Configuración de la base de datos
1. Crea la base y las tablas usando el script SQL provisto (`create_populate_heladeria.sql`) o manualmente. Si no tienes el script, puedes ejecutarlo con:

```powershell
mysql -u root -p < path\to\create_populate_heladeria.sql
```

2. Verifica `src/main/resources/application.properties` y ajusta `spring.datasource.url`, `spring.datasource.username` y la contraseña si tu MySQL no usa `root` sin contraseña.


## Construir y ejecutar
Desde la carpeta `demo` (PowerShell):

```powershell
cd 'c:\Users\dilan\Downloads\corruna back\corruna\demo'
# Ejecutar la app (usa el wrapper Maven incluido)
.\mvnw.cmd spring-boot:run

# o compilar empaquetar (sin ejecutar tests):
.\mvnw.cmd -DskipTests package
```

Nota: si Maven falla por "No compiler is provided..." instala/configura un JDK y exporta `JAVA_HOME` antes de ejecutar.


## Swagger / Documentación
- Swagger UI (web): `http://localhost:8081/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8081/v3/api-docs`

Abre el navegador con:

```powershell
Start-Process "http://localhost:8081/swagger-ui/index.html"
```


## Credenciales de prueba (crear vía API)
La API permite crear usuarios por `POST /api/usuarios` y el servicio hashará la contraseña automáticamente. Usa los siguientes JSONs en `POST /api/usuarios` si aún no tienes usuarios en la base:

Admin (rol ADMIN = id 1):
```json
{
  "nombre": "Admin Heladeria",
  "email": "admin@heladeria.local",
  "contra": "AdminPass!2025",
  "estado": true,
  "fecha_creacion": 20251124,
  "rol": { "id": 1 }
}
```

Cliente (rol CLIENTE = id 2):
```json
{
  "nombre": "Cliente Demo",
  "email": "cliente@heladeria.local",
  "contra": "Cliente123!",
  "estado": true,
  "fecha_creacion": 20251124,
  "rol": { "id": 2 }
}
```

Después de crear el usuario (o si ya está en la DB), prueba login:

POST `http://localhost:8081/auth/login` body JSON:
```json
{ "email": "admin@heladeria.local", "contra": "AdminPass!2025" }
```
Respuesta esperada: un JSON con `message` confirmando autenticación (la aplicación actual no usa JWT).


## Ejemplos con cURL y PowerShell
Crear usuario (PowerShell):
```powershell
$body = @{ nombre='Admin Heladeria'; email='admin@heladeria.local'; contra='AdminPass!2025'; estado=$true; fecha_creacion=20251124; rol = @{ id = 1 } } | ConvertTo-Json
Invoke-RestMethod -Method Post -Uri http://localhost:8081/api/usuarios -Body $body -ContentType 'application/json'
```

Login (cURL):
```bash
curl -X POST http://localhost:8081/auth/login -H "Content-Type: application/json" -d '{"email":"admin@heladeria.local","contra":"AdminPass!2025"}'
```


## Colección Postman
Importa `postman_collection_heladeria.json` (archivo incluido) en Postman. Contiene requests para:
- Crear Admin
- Crear Cliente
- Login
- Listar usuarios
- Listar productos


## Notas finales
- Si quieres restablecer autenticación basada en tokens (JWT) o sessions, puedo volver a implementarla. Por ahora la app responde con confirmación de autenticación.
- Si quieres, genero automáticamente usuarios en la DB con contraseñas hasheadas y actualizo el script SQL para insertar hashes.


---
Generado automáticamente: README con pasos clave para instalar, ejecutar y probar la API (tema: heladería). Si quieres que adapte algo (por ejemplo, puerto distinto o endpoints más concretos), dímelo y lo ajusto.
