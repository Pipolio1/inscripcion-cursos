# Sistema de Inscripción de Cursos

Proyecto desarrollado con **Spring Boot** para la asignatura Desarrollo Cloud Native. Permite gestionar cursos educativos y realizar inscripciones de estudiantes, con despliegue automático mediante CI/CD en AWS EC2.

## Tecnologías

- Java 21
- Spring Boot 4.0.6
- Spring Data JPA
- Oracle Cloud Database
- Docker & Docker Hub
- GitHub Actions (CI/CD)
- AWS EC2

## Endpoints principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/cursos` | Lista todos los cursos disponibles |
| POST | `/api/cursos` | Agrega un nuevo curso a la oferta educativa |
| POST | `/api/inscripciones` | Inscribe un estudiante en uno o más cursos |

## Estructura del proyecto

```
├── .github/workflows/ci-cd.yml   # Pipeline de CI/CD
├── src/main/java/.../
│   ├── controller/               # Controladores REST
│   ├── service/                  # Lógica de negocio
│   ├── repository/               # Repositorios JPA
│   ├── entity/                   # Entidades JPA
│   └── dto/                      # Objetos de transferencia de datos
├── Dockerfile                    # Imagen Docker multi-stage
├── docker-compose.yml            # Orquestación local
└── pom.xml
```

## Configuración de Oracle Cloud (IMPORTANTE)

1. Ingresa a tu consola de Oracle Cloud
2. Ve a tu instancia de Autonomous Database
3. En **Database Connection**, copia la cadena de conexión JDBC
4. Actualiza las siguientes variables en `src/main/resources/application.properties` o usa variables de entorno:

```properties
spring.datasource.url=jdbc:oracle:thin:@tu_host:1521/tu_servicio
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
```

### Opción recomendada: Variables de entorno

Para no exponer credenciales en el código, usa variables de entorno (ya configurado en el proyecto):

```bash
export ORACLE_DB_URL=jdbc:oracle:thin:@tu_host:1521/tu_servicio
export ORACLE_DB_USERNAME=tu_usuario
export ORACLE_DB_PASSWORD=tu_password
```

## Ejecución local

### Opción 1: Maven
```bash
./mvnw spring-boot:run
```

### Opción 2: Docker Compose
```bash
docker-compose up --build
```

### Opción 3: JAR
```bash
./mvnw clean package -DskipTests
java -jar target/inscripcion-cursos-0.0.1-SNAPSHOT.jar
```

## CI/CD - GitHub Actions

El pipeline se ejecuta automáticamente al hacer push a la rama `main` y consta de 3 etapas:

1. **Build & Test**: Compila el proyecto y ejecuta tests con Maven
2. **Docker Build & Push**: Construye la imagen Docker y la publica en Docker Hub
3. **Deploy to EC2**: Conecta por SSH a tu instancia EC2, descarga la imagen y ejecuta el contenedor

### Configuración de Secretos en GitHub

Ve a **Settings > Secrets and variables > Actions** y agrega:

| Secreto | Descripción |
|---------|-------------|
| `DOCKERHUB_USERNAME` | Tu usuario de Docker Hub |
| `DOCKERHUB_TOKEN` | Token de acceso personal de Docker Hub (no la contraseña) |
| `EC2_HOST` | IP pública o DNS de tu instancia EC2 |
| `EC2_USER` | Usuario SSH (ej: `ec2-user` o `ubuntu`) |
| `EC2_SSH_KEY` | Clave privada PEM para conectarte por SSH |
| `ORACLE_DB_URL` | URL JDBC de tu base Oracle Cloud |
| `ORACLE_DB_USERNAME` | Usuario de Oracle |
| `ORACLE_DB_PASSWORD` | Contraseña de Oracle |

### Obtener Docker Hub Token

1. Ingresa a [hub.docker.com](https://hub.docker.com)
2. Ve a **Account Settings > Security > New Access Token**
3. Genera un token con permisos `read`, `write`, `delete`
4. Copia el token y guárdalo en el secreto `DOCKERHUB_TOKEN`

## Ejemplos de uso

### Crear un curso
```bash
curl -X POST http://localhost:8080/api/cursos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Desarrollo Cloud Native",
    "instructor": "Prof. García",
    "duracion": 40,
    "costo": 150000
  }'
```

### Listar cursos
```bash
curl http://localhost:8080/api/cursos
```

### Inscribir estudiante
```bash
curl -X POST http://localhost:8080/api/inscripciones \
  -H "Content-Type: application/json" \
  -d '{
    "nombreEstudiante": "Ana López",
    "emailEstudiante": "ana.lopez@email.com",
    "cursoIds": [1, 2]
  }'
```

## Notas importantes

- Asegúrate de que tu instancia EC2 tenga Docker instalado
- El puerto 8080 debe estar abierto en el Security Group de EC2
- Si usas Oracle Autonomous Database con Wallet, deberás montar el wallet como volumen en Docker
- La aplicación usa `ddl-auto=update` para crear las tablas automáticamente

## Autores

Desarrollado para la asignatura **Desarrollo Cloud Native (CDY2204)**.
