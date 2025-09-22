# Configuración UNIT3D para StreamSpace

## Descripción

Esta adaptación permite que StreamSpace se conecte a tu tracker privado UNIT3D en lugar de usar APIs públicas como YTS. Esto te da acceso a tu contenido privado con mejor calidad y confiabilidad.

## Configuración

### 1. Obtener API Key de tu Tracker UNIT3D

1. Ingresa a tu tracker UNIT3D
2. Ve a tu perfil/configuración
3. Busca la sección "API Keys" o "Developer"
4. Genera una nueva API key
5. Copia la API key generada

### 2. Configurar StreamSpace

Edita el archivo `src/main/resources/application.properties`:

```properties
# UNIT3D Private Tracker Configuration
unit3d.api.base-url=https://tu-tracker.com
unit3d.api.key=tu-api-key-aqui
unit3d.api.enabled=true
```

Reemplaza:
- `https://tu-tracker.com` con la URL de tu tracker
- `tu-api-key-aqui` con tu API key obtenida

### 3. Endpoints Disponibles

Una vez configurado, tendrás acceso a:

#### Búsqueda General
- `/search/torrents?term=pelicula` - Busca en tu tracker privado

#### Navegación Específica UNIT3D
- `/unit3d/browse` - Navegar todos los torrents
- `/unit3d/movies` - Solo películas  
- `/unit3d/tv` - Solo series de TV
- `/unit3d/freeleech` - Torrents freeleech
- `/unit3d/top-seeded` - Más seeded
- `/unit3d/torrents/{id}` - Detalles de torrent específico

#### Búsqueda Avanzada
```
/unit3d/browse?search=movie&categoryId=1&typeId=1&resolutionId=3&page=1
```

### 4. Estructura de Datos

Los datos devueltos incluyen:

```json
{
  "data": {
    "data": [
      {
        "id": 123,
        "name": "Movie Name 2023",
        "info_hash": "abc123...",
        "size": 1073741824,
        "seeders": 15,
        "leechers": 2,
        "free": true,
        "category": {"id": 1, "name": "Movies"},
        "resolution": {"id": 3, "name": "1080p"},
        "download_link": "https://tracker.com/download/123",
        "imdb": "tt1234567",
        "rating": 8.5
      }
    ]
  },
  "meta": {
    "current_page": 1,
    "last_page": 10,
    "total": 250
  }
}
```

### 5. Funcionalidades

#### Ventajas sobre YTS público:
- ✅ Mejor calidad de torrents
- ✅ Más variedad de contenido  
- ✅ Velocidades de descarga más rápidas
- ✅ Contenido más confiable
- ✅ Torrents freeleech
- ✅ Estadísticas detalladas
- ✅ Sin límites de API

#### Funcionalidades específicas:
- Búsqueda por categorías (Movies, TV, Music, etc.)
- Filtros por resolución (720p, 1080p, 4K)
- Filtros por tipo de codificación
- Información detallada de MediaInfo
- Enlaces de descarga directos
- Estadísticas de seeders/leechers en tiempo real

### 6. Compatibilidad

- ✅ Mantiene compatibilidad con YTS (fallback automático)
- ✅ Todas las funciones de streaming siguen funcionando
- ✅ Descarga de torrents funciona igual
- ✅ Interfaz web sin cambios

### 7. Seguridad

- La API key se envía como Bearer token
- Todas las conexiones usan HTTPS
- Headers de autenticación seguros
- Sin exposición de credenciales en logs

### 8. Troubleshooting

#### Error: "UNIT3D API is not enabled"
- Asegúrate que `unit3d.api.enabled=true`
- Verifica que tu API key sea válida
- Confirma que la URL base sea correcta

#### Error: "Unauthorized" 
- Revisa que tu API key esté correcta
- Confirma que tengas permisos de API en tu tracker
- Verifica que la API key no haya expirado

#### Sin resultados en búsquedas
- Confirma que tu tracker tenga contenido
- Prueba términos de búsqueda más generales
- Verifica que tu cuenta tenga permisos de búsqueda

## Ejemplo de Uso

```bash
# Compilar y ejecutar
mvn clean install
java -jar target/StreamSpace-0.0.1-SNAPSHOT.jar

# Abrir en navegador
http://localhost:8080

# Buscar en tu tracker privado
http://localhost:8080/search/torrents?term=avengers

# Ver contenido freeleech
http://localhost:8080/unit3d/freeleech
```

## Notas Importantes

1. **Privacidad**: Asegúrate de usar VPN si tu tracker lo requiere
2. **Rate Limiting**: La API privada generalmente no tiene límites, pero respeta los términos de tu tracker
3. **Contenido**: Solo tendrás acceso al contenido disponible en tu tracker privado
4. **Backup**: Mantén la funcionalidad YTS como respaldo en caso de problemas con el tracker privado