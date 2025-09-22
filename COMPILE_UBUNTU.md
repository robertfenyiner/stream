# LatStream - Compilación y Ejecución en Ubuntu

## Pasos para compilar y ejecutar desde Ubuntu:

### 1. Abrir terminal de Ubuntu y navegar al proyecto:
```bash
cd "/mnt/d/Onedrive Robert Personal/OneDrive/Documents/GitHub/stream"
```

### 2. Instalar dependencias (si no están instaladas):
```bash
# Instalar Maven
sudo apt update
sudo apt install -y maven

# Instalar OpenJDK 21
sudo apt install -y openjdk-21-jdk

# Verificar instalaciones
mvn --version
java --version
```

### 3. Compilar el proyecto:
```bash
# Limpiar proyecto
mvn clean

# Compilar
mvn compile

# Si todo funciona, empaqueta la aplicación
mvn package -DskipTests
```

### 4. Ejecutar la aplicación:
```bash
# Opción 1: Con Maven
mvn spring-boot:run

# Opción 2: Con JAR generado
java -jar target/latstream-1.0.0.jar
```

### 5. Acceder a la aplicación:
- URL: http://localhost:8080
- LAT-Team search: http://localhost:8080/search/latteam?term=movie

## Configuración actual:
- **Nombre**: LatStream
- **Puerto**: 8080
- **API**: LAT-Team (https://lat-team.com)
- **API Key**: Configurada en application.properties
- **Base de datos**: H2 (archivo local)

## Archivos principales:
- `LatStreamApplication.java` - Clase principal
- `application.properties` - Configuración
- `pom.xml` - Dependencias Maven
- `INSTALACION_UBUNTU.md` - Guía completa de instalación

## Solución de problemas:
1. Si hay errores de compilación, ejecutar: `mvn clean compile -X`
2. Para ver logs detallados: `mvn spring-boot:run -X`
3. Verificar que el puerto 8080 esté libre: `lsof -i :8080`