#!/bin/bash

# Script de despliegue para LatStream
# ===================================

echo "🚀 Iniciando despliegue de LatStream..."

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Función para imprimir mensajes con colores
print_status() {
    echo -e "${GREEN}✓${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}⚠${NC} $1"
}

print_error() {
    echo -e "${RED}✗${NC} $1"
}

# 1. Verificar que estamos en el directorio correcto
if [ ! -f "pom.xml" ]; then
    print_error "No se encontró pom.xml. Ejecuta este script desde el directorio raíz del proyecto."
    exit 1
fi

print_status "Directorio del proyecto verificado"

# 2. Limpiar procesos Java previos que puedan estar bloqueando la BD
print_warning "Limpiando procesos Java previos..."
pkill -f "LatStreamApplication" 2>/dev/null || true
pkill -f "mvn.*spring-boot:run" 2>/dev/null || true

# 3. Limpiar archivos de base de datos bloqueados
print_warning "Limpiando archivos de base de datos..."
rm -f ~/.h2.mv.db ~/.h2.trace.db data/latstream.mv.db data/latstream.trace.db 2>/dev/null || true

# 4. Crear directorio para la base de datos
mkdir -p data
print_status "Directorio de datos creado"

# 5. Limpiar y compilar
print_status "Ejecutando mvn clean install..."
mvn clean install

if [ $? -ne 0 ]; then
    print_error "Error en la compilación. Revisa los errores anteriores."
    exit 1
fi

print_status "Compilación exitosa"

# 6. Verificar que las pruebas pasan
print_status "Ejecutando pruebas..."
mvn test

if [ $? -ne 0 ]; then
    print_error "Las pruebas fallaron. Revisa los errores anteriores."
    exit 1
fi

print_status "Todas las pruebas pasaron"

# 7. Mostrar información de la aplicación
echo ""
echo "🎉 ¡Despliegue completado exitosamente!"
echo ""
echo "📋 Información de la aplicación:"
echo "   • Puerto: 8080"
echo "   • URL: http://localhost:8080"
echo "   • Consola H2: http://localhost:8080/h2-console"
echo "   • Base de datos: jdbc:h2:file:./data/latstream"
echo "   • Usuario DB: sa"
echo "   • Password DB: password"
echo ""
echo "🚀 Para ejecutar la aplicación:"
echo "   mvn spring-boot:run"
echo ""
echo "🛑 Para detener la aplicación:"
echo "   Ctrl+C o pkill -f LatStreamApplication"
echo ""