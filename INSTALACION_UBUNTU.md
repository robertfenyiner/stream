# LatStream - Guía de Instalación para Ubuntu 22.04

## Descripción
LatStream es una aplicación de streaming privada que se integra con el tracker LAT-Team utilizando la API de UNIT3D. Esta guía te ayudará a instalar y configurar la aplicación en Ubuntu 22.04.

## Requisitos del Sistema

### Hardware Mínimo
- CPU: 2 núcleos
- RAM: 4GB (recomendado 8GB)
- Almacenamiento: 20GB libres
- Red: Conexión estable a internet

### Software Requerido
- Ubuntu 22.04 LTS
- Java 21 (OpenJDK)
- Maven 3.8+
- Git

## Instalación Paso a Paso

### 1. Actualizar el Sistema
```bash
sudo apt update && sudo apt upgrade -y
```

### 2. Instalar Java 21
```bash
# Instalar OpenJDK 21
sudo apt install openjdk-21-jdk -y

# Verificar la instalación
java -version
javac -version
```

### 3. Instalar Maven
```bash
# Instalar Maven
sudo apt install maven -y

# Verificar la instalación
mvn -version
```

### 4. Instalar Git
```bash
sudo apt install git -y
```

### 5. Crear Usuario del Sistema (Opcional pero Recomendado)
```bash
# Crear usuario dedicado para LatStream
sudo useradd -m -s /bin/bash latstream
sudo usermod -aG sudo latstream

# Cambiar al usuario latstream
sudo su - latstream
```

### 6. Clonar el Repositorio
```bash
# Clonar el proyecto
git clone https://github.com/robertfenyiner/latstream.git
cd latstream
```

### 7. Configurar la Aplicación

#### Editar application.properties
```bash
nano src/main/resources/application.properties
```

Configuración básica para producción:
```properties
server.port=8080
server.http2.enabled=true
server.error.include-message=always

spring.application.name=LatStream
spring.threads.virtual.enabled=true

# Base de datos H2 (archivo persistente)
spring.datasource.url=jdbc:h2:file:/home/latstream/data/latstream
spring.datasource.username=sa
spring.datasource.password=tu_password_seguro_aqui
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false

# UNIT3D Private Tracker Configuration (LAT-Team)
unit3d.api.base-url=https://lat-team.com
unit3d.api.key=jmH9ybPlod9zXaJv80CwEVM8PCay3yDx3H3b279WrjWDv5udhOMto6ZioKyXEN3iPEUWJQ0tBH5BlPV2FTUHdm6DI8EOfiUbqihV
unit3d.api.enabled=true

# Logging
logging.level.com.robertfenyiner.latstream=INFO
logging.level.root=WARN
```

### 8. Crear Directorio de Datos
```bash
mkdir -p /home/latstream/data
```

### 9. Compilar la Aplicación
```bash
# Compilar el proyecto
mvn clean install -DskipTests

# Verificar que se creó el JAR
ls -la target/
```

### 10. Crear Script de Inicio
```bash
# Crear script de inicio
cat > start-latstream.sh << 'EOF'
#!/bin/bash

# Variables
JAR_FILE="/home/latstream/latstream/target/latstream-1.0.0.jar"
PID_FILE="/home/latstream/latstream.pid"
LOG_FILE="/home/latstream/logs/latstream.log"

# Crear directorio de logs
mkdir -p /home/latstream/logs

# Función para iniciar
start() {
    if [ -f $PID_FILE ]; then
        echo "LatStream ya está ejecutándose (PID: $(cat $PID_FILE))"
        return 1
    fi
    
    echo "Iniciando LatStream..."
    nohup java -jar $JAR_FILE > $LOG_FILE 2>&1 &
    echo $! > $PID_FILE
    echo "LatStream iniciado (PID: $(cat $PID_FILE))"
}

# Función para detener
stop() {
    if [ ! -f $PID_FILE ]; then
        echo "LatStream no está ejecutándose"
        return 1
    fi
    
    PID=$(cat $PID_FILE)
    echo "Deteniendo LatStream (PID: $PID)..."
    kill $PID
    rm -f $PID_FILE
    echo "LatStream detenido"
}

# Función para reiniciar
restart() {
    stop
    sleep 3
    start
}

# Función para ver estado
status() {
    if [ -f $PID_FILE ]; then
        PID=$(cat $PID_FILE)
        if ps -p $PID > /dev/null; then
            echo "LatStream está ejecutándose (PID: $PID)"
        else
            echo "LatStream no está ejecutándose (archivo PID obsoleto)"
            rm -f $PID_FILE
        fi
    else
        echo "LatStream no está ejecutándose"
    fi
}

case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        restart
        ;;
    status)
        status
        ;;
    *)
        echo "Uso: $0 {start|stop|restart|status}"
        exit 1
        ;;
esac
EOF

# Hacer ejecutable el script
chmod +x start-latstream.sh
```

### 11. Crear Servicio Systemd (Opcional)
```bash
# Crear archivo de servicio
sudo tee /etc/systemd/system/latstream.service > /dev/null << 'EOF'
[Unit]
Description=LatStream Application
After=network.target

[Service]
Type=simple
User=latstream
Group=latstream
WorkingDirectory=/home/latstream/latstream
ExecStart=/usr/bin/java -jar /home/latstream/latstream/target/latstream-1.0.0.jar
Restart=always
RestartSec=10
StandardOutput=syslog
StandardError=syslog
SyslogIdentifier=latstream

[Install]
WantedBy=multi-user.target
EOF

# Recargar systemd y habilitar el servicio
sudo systemctl daemon-reload
sudo systemctl enable latstream.service
```

## Uso

### Iniciar la Aplicación

#### Usando el script:
```bash
./start-latstream.sh start
```

#### Usando systemd:
```bash
sudo systemctl start latstream
```

#### Manualmente:
```bash
java -jar target/latstream-1.0.0.jar
```

### Acceder a la Aplicación
- Abrir navegador web
- Ir a: `http://localhost:8080`
- O desde otra máquina: `http://IP_DEL_SERVIDOR:8080`

### Verificar Estado
```bash
# Con el script
./start-latstream.sh status

# Con systemd
sudo systemctl status latstream

# Ver logs
tail -f /home/latstream/logs/latstream.log
```

## Configuración del Firewall

### UFW (Ubuntu Firewall)
```bash
# Habilitar UFW
sudo ufw enable

# Permitir el puerto de LatStream
sudo ufw allow 8080/tcp

# Verificar reglas
sudo ufw status
```

## Actualizaciones

### Actualizar la Aplicación
```bash
# Detener la aplicación
./start-latstream.sh stop

# Actualizar código
git pull origin main

# Recompilar
mvn clean install -DskipTests

# Reiniciar
./start-latstream.sh start
```

## Troubleshooting

### Problemas Comunes

#### Error de Java
```bash
# Verificar versión de Java
java -version

# Si no es Java 21, establecer JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
```

#### Error de Conexión a LAT-Team
- Verificar que la API key sea correcta
- Verificar conectividad: `curl -I https://lat-team.com`
- Revisar logs: `tail -f /home/latstream/logs/latstream.log`

#### Puerto ya en uso
```bash
# Verificar qué proceso usa el puerto 8080
sudo netstat -tlnp | grep :8080

# Cambiar puerto en application.properties
server.port=8081
```

#### Error de permisos
```bash
# Dar permisos al usuario latstream
sudo chown -R latstream:latstream /home/latstream/
```

## Logs y Monitoreo

### Ubicación de Logs
- Aplicación: `/home/latstream/logs/latstream.log`
- Sistema: `sudo journalctl -u latstream -f`

### Monitoreo de Recursos
```bash
# Ver uso de CPU y memoria
htop

# Ver uso de disco
df -h

# Ver conexiones de red
sudo netstat -an | grep :8080
```

## Seguridad

### Recomendaciones
1. Cambiar la contraseña de la base de datos H2
2. Usar HTTPS en producción (con nginx reverse proxy)
3. Configurar fail2ban para proteger contra ataques
4. Mantener el sistema actualizado
5. Usar firewall restrictivo

### Backup
```bash
# Backup de la base de datos
cp /home/latstream/data/latstream.mv.db /backup/

# Backup de configuración
cp src/main/resources/application.properties /backup/
```

## Soporte

Para soporte y problemas:
1. Revisar logs primero
2. Verificar conectividad a LAT-Team
3. Consultar documentación de UNIT3D API
4. Verificar versiones de Java y Maven

---

**Nota**: Esta aplicación está diseñada específicamente para usar con el tracker privado LAT-Team. Asegúrate de tener permisos y credenciales válidas antes de la instalación.