#!/bin/bash

# Script de verificación para LatStream
echo "=== LatStream - Verificación de Configuración ==="
echo

# Verificar Java
echo "1. Verificando Java..."
if java -version 2>&1 | grep -q "21"; then
    echo "   ✅ Java 21 detectado"
    java -version 2>&1 | head -1
else
    echo "   ❌ Java 21 no encontrado"
    echo "   Instalar con: sudo apt install openjdk-21-jdk"
fi
echo

# Verificar Maven
echo "2. Verificando Maven..."
if command -v mvn >/dev/null 2>&1; then
    echo "   ✅ Maven detectado"
    mvn -version | head -1
else
    echo "   ❌ Maven no encontrado"
    echo "   Instalar con: sudo apt install maven"
fi
echo

# Verificar conectividad a LAT-Team
echo "3. Verificando conectividad a LAT-Team..."
if curl -s --head https://lat-team.com | head -1 | grep -q "200 OK"; then
    echo "   ✅ LAT-Team accesible"
else
    echo "   ❌ LAT-Team no accesible"
    echo "   Verificar conexión a internet y VPN si es necesario"
fi
echo

# Verificar estructura del proyecto
echo "4. Verificando estructura del proyecto..."
if [ -f "pom.xml" ]; then
    echo "   ✅ pom.xml encontrado"
else
    echo "   ❌ pom.xml no encontrado"
fi

if [ -f "src/main/java/com/robertfenyiner/latstream/LatStreamApplication.java" ]; then
    echo "   ✅ Clase principal encontrada"
else
    echo "   ❌ Clase principal no encontrada"
fi

if [ -f "src/main/resources/application.properties" ]; then
    echo "   ✅ Archivo de configuración encontrado"
else
    echo "   ❌ Archivo de configuración no encontrado"
fi
echo

# Verificar configuración de API
echo "5. Verificando configuración de API..."
if grep -q "unit3d.api.enabled=true" src/main/resources/application.properties 2>/dev/null; then
    echo "   ✅ API UNIT3D habilitada"
else
    echo "   ❌ API UNIT3D no habilitada"
fi

if grep -q "unit3d.api.key=" src/main/resources/application.properties 2>/dev/null; then
    echo "   ✅ API Key configurada"
else
    echo "   ❌ API Key no configurada"
fi
echo

# Verificar puertos disponibles
echo "6. Verificando disponibilidad del puerto 8080..."
if netstat -tuln 2>/dev/null | grep -q ":8080 "; then
    echo "   ⚠️  Puerto 8080 en uso"
    echo "   Proceso usando el puerto:"
    netstat -tulnp 2>/dev/null | grep ":8080 "
else
    echo "   ✅ Puerto 8080 disponible"
fi
echo

echo "=== Resumen ==="
echo "Si todos los elementos tienen ✅, el sistema está listo para ejecutar LatStream."
echo
echo "Comandos de compilación y ejecución:"
echo "  mvn clean install"
echo "  java -jar target/latstream-1.0.0.jar"
echo
echo "Acceso web: http://localhost:8080"
echo