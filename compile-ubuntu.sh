#!/bin/bash

echo "=== LatStream Compilation Script ==="
echo "Executing from Ubuntu terminal..."

# Verificar si Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "Maven no está instalado. Instalando Maven..."
    sudo apt update
    sudo apt install -y maven
fi

# Verificar si Java está instalado
if ! command -v java &> /dev/null; then
    echo "Java no está instalado. Instalando OpenJDK 21..."
    sudo apt update
    sudo apt install -y openjdk-21-jdk
fi

echo "Versions instaladas:"
mvn --version
java --version

echo "Cambiando al directorio del proyecto..."
WINDOWS_PATH="/mnt/d/Onedrive Robert Personal/OneDrive/Documents/GitHub/stream"
cd "$WINDOWS_PATH"

echo "Limpiando proyecto..."
mvn clean

echo "Compilando proyecto..."
mvn compile

echo "Si la compilación fue exitosa, ejecutar:"
echo "mvn spring-boot:run"