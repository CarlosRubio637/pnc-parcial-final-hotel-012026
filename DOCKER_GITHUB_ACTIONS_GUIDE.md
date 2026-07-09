gi## 🚀 Guía de Uso: Docker y GitHub Actions

### Descripción General
Este proyecto incluye containerización con Docker y automatización con GitHub Actions para CI/CD.

---

## 🐳 Docker - Ejecución Local

### Prerrequisitos
- Docker y Docker Compose instalados

### Pasos para ejecutar:

1. **Compilar el proyecto:**
   ```bash
   ./gradlew clean build -x test
   ```

2. **Levantar los servicios:**
   ```bash
   docker-compose up --build
   ```

   Esto levantará:
   - ✅ **Backend (web)**: http://localhost:8080
   - ✅ **PostgreSQL (db)**: localhost:5433

3. **Detener los servicios:**
   ```bash
   docker-compose down
   ```

4. **Ver logs del backend:**
   ```bash
   docker-compose logs -f web
   ```

5. **Ver logs de la base de datos:**
   ```bash
   docker-compose logs -f db
   ```

---

## 🔧 Configuración de Variables de Entorno

En `docker-compose.yml`, puedes modificar:

```yaml
environment:
  SPRING_PROFILES_ACTIVE: prod
  SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/hotel_db
  SPRING_DATASOURCE_USERNAME: postgres
  SPRING_DATASOURCE_PASSWORD: postgres
  APP_JWT_SECRET: <tu_secreto_base64>
  APP_JWT_ACCESS_TOKEN_EXPIRATION_MS: 86400000
```

---

## 🔐 GitHub Actions - CI/CD

### Configuración Inicial

1. **Agregar secretos en GitHub:**
   - Ve a tu repositorio → Settings → Secrets and variables → Actions
   - Agrega:
     - `DOCKER_USERNAME`: tu username de Docker Hub
     - `DOCKER_PASSWORD`: tu token de acceso de Docker Hub

2. **El workflow se dispara con:**
   - Push a rama `main`
   - Push a rama `develop`

### Pasos del Workflow:

1. **Build**: Compila el proyecto con Gradle
2. **Upload Artifacts**: Guarda el .jar generado
3. **Docker Build**: Construye la imagen Docker y la sube a Docker Hub

### Monitoreo:
- Ve a tu repositorio → Actions para ver el estado de los workflows

---

## 📄 Estructura de Archivos

```
.github/workflows/
  └── build.yml               # Workflow de GitHub Actions

Dockerfile                    # Imagen Docker del backend

docker-compose.yml            # Orquestación de servicios

build/libs/
  └── *.jar                   # Archivo compilado (se crea después de compilar)
```

---

## 🛠️ Endpoints Disponibles

### Autenticación (sin protección)
- `POST /api/auth/login` - Login con username/password

### Habitaciones (protegidas por JWT)
- `GET /api/rooms` - Listar todas (ADMIN, MANAGER, USER)
- `GET /api/rooms/{id}` - Obtener por ID (ADMIN, MANAGER, USER)
- `GET /api/rooms/filter/status?status=AVAILABLE` - Filtrar por estado
- `GET /api/rooms/filter/type?roomType=SINGLE` - Filtrar por tipo
- `POST /api/rooms` - Crear (ADMIN, MANAGER)
- `PUT /api/rooms/{id}` - Actualizar (ADMIN, MANAGER)
- `DELETE /api/rooms/{id}` - Eliminar (ADMIN)

---

## 🔑 Ejemplo de Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

Respuesta esperada:
```json
{
  "accessToken": "eyJhbGc...",
  "tokenType": "Bearer"
}
```

---

## 📦 Crear Habitaciones

```bash
curl -X POST http://localhost:8080/api/rooms \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <tu_token>" \
  -d '{
    "roomNumber": "101",
    "roomType": "SINGLE",
    "capacity": 1,
    "pricePerNight": 50.00,
    "status": "AVAILABLE"
  }'
```

---

## ✅ Verificación

- Todos los servicios están corriendo: `docker-compose ps`
- Base de datos está lista: Espera el healthcheck (10-30 segundos)
- Flyway ejecuta migraciones automáticamente

---

## 📝 Notas Importantes

- El .jar debe existir en `build/libs/` antes de construir la imagen Docker
- Las migraciones SQL (`V*__*.sql`) se ejecutan automáticamente con Flyway
- Los Secrets de GitHub son confidenciales y no se muestran en los logs
- El workflow solo sube a Docker Hub en push a rama `main`


