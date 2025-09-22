# LatStream - Configuración de Desarrollo

## Estructura del Proyecto Actualizada

El proyecto ha sido renombrado de **StreamSpace** a **LatStream** y reestructurado con el nuevo paquete base:

### Cambios Realizados

1. **Paquete Base**: `com.akshathsaipittala.streamspace` → `com.robertfenyiner.latstream`
2. **Nombre de la Aplicación**: `StreamSpace` → `LatStream`
3. **Clase Principal**: `StreamSpaceApplication` → `LatStreamApplication`
4. **Artefacto Maven**: `streamspace-0.0.1` → `latstream-1.0.0`

### Configuración Actualizada

#### application.properties
```properties
spring.application.name=LatStream
unit3d.api.base-url=https://lat-team.com
unit3d.api.key=jmH9ybPlod9zXaJv80CwEVM8PCay3yDx3H3b279WrjWDv5udhOMto6ZioKyXEN3iPEUWJQ0tBH5BlPV2FTUHdm6DI8EOfiUbqihV
unit3d.api.enabled=true
```

#### User-Agent
- Actualizado a: `LatStream/1.0`

### API Key Configurada
- **API Key**: `jmH9ybPlod9zXaJv80CwEVM8PCay3yDx3H3b279WrjWDv5udhOMto6ZioKyXEN3iPEUWJQ0tBH5BlPV2FTUHdm6DI8EOfiUbqihV`
- **Tracker**: LAT-Team (https://lat-team.com)
- **Estado**: Habilitado

### Funcionalidades Disponibles

#### Endpoints Principales
- `/` - Página principal de LatStream
- `/search/torrents?term=...` - Búsqueda en LAT-Team
- `/unit3d/browse` - Navegador de torrents
- `/unit3d/movies` - Solo películas
- `/unit3d/tv` - Solo series de TV
- `/unit3d/freeleech` - Torrents freeleech
- `/unit3d/top-seeded` - Más seeded

#### Características
- ✅ Integración completa con LAT-Team API
- ✅ Búsqueda en tiempo real
- ✅ Navegación por categorías
- ✅ Información detallada de torrents
- ✅ Soporte para freeleech
- ✅ Estadísticas de seeders/leechers
- ✅ Interfaz web responsive
- ✅ Tema oscuro/claro

### Compilación en Ubuntu 22.04

```bash
# Instalar dependencias
sudo apt update
sudo apt install openjdk-21-jdk maven git -y

# Clonar y compilar
git clone https://github.com/robertfenyiner/latstream.git
cd latstream
mvn clean install

# Ejecutar
java -jar target/latstream-1.0.0.jar
```

### Estructura de Archivos
```
latstream/
├── src/main/java/com/robertfenyiner/latstream/
│   ├── LatStreamApplication.java
│   ├── config/
│   │   ├── UNIT3DConfig.java
│   │   ├── APIClientsBuilder.java
│   │   └── ...
│   ├── www/
│   │   ├── UNIT3DAPIClient.java
│   │   ├── UNIT3DController.java
│   │   ├── UnifiedSearchController.java
│   │   └── ...
│   └── ...
├── src/main/resources/
│   ├── application.properties
│   └── templates/
└── pom.xml
```

### Próximos Pasos para Ubuntu

1. **Instalar Java 21** - OpenJDK
2. **Instalar Maven** - Para compilación
3. **Clonar repositorio** - Git clone
4. **Configurar propiedades** - Verificar API key
5. **Compilar proyecto** - mvn clean install
6. **Ejecutar aplicación** - java -jar
7. **Configurar servicio** - systemd (opcional)
8. **Configurar proxy** - nginx (opcional)

### Seguridad

- API key configurada y funcionando
- User-Agent personalizado
- Autenticación Bearer token
- Headers apropiados para LAT-Team API

### Monitoreo

- Logs disponibles en consola y archivo
- Métricas de rendimiento habilitadas
- Conexión a base de datos H2 persistente