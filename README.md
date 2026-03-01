# AsturianCinema 🎬

Plataforma web profesional para descubrir, compartir y disfrutar del cine hecho en Asturias y basado en la cultura regional.

## Tecnologías

### Backend
- **Spring Boot 3.4** - Framework principal
- **Java 17** - Lenguaje
- **Spring Security 6** - Seguridad con JWT
- **Spring Data JPA** - Acceso a datos
- **PostgreSQL** - Base de datos
- **Flyway** - Migraciones
- **WebSocket** - Tiempo real

### Frontend
- **Angular 18** - Framework SPA
- **TypeScript** - Lenguaje
- **TailwindCSS** - Estilos
- **Angular Material** - Componentes UI

## Estructura del Proyecto

```
asturian-cinema/
├── backend/                    # Spring Boot API
│   ├── src/main/java/
│   │   └── com/asturiancinema/
│   │       ├── config/         # Configuraciones
│   │       ├── controller/      # Controladores REST
│   │       ├── dto/            # Data Transfer Objects
│   │       ├── entity/         # Entidades JPA
│   │       ├── exception/      # Excepciones
│   │       ├── repository/      # Repositorios
│   │       ├── security/       # Seguridad JWT
│   │       └── service/        # Servicios
│   └── src/main/resources/
│       ├── application.properties
│       └── db/migration/       # Flyway migrations
│
├── frontend/                   # Angular SPA
│   └── asturian-cinema/
│       └── src/app/
│           ├── core/           # Servicios, guards, interceptors
│           ├── features/      # Componentes de página
│           └── shared/        # Componentes compartidos
│
└── README.md
```

## Características

### Autenticación
- Registro y login con JWT
- Refresh tokens
- Roles: USER, ADMIN, MODERATOR

### Películas
- Catálogo con paginación
- Búsqueda y filtros
- Películas destacadas y-topadas
- Sistema de valoración y reseñas
- Favoritos y listas personalizadas

### Comunidad
- Perfiles de usuario
- Sistema de follows
- Notificaciones en tiempo real (WebSocket)
- Blog con artículos

### Administración
- Dashboard con estadísticas
- Gestión de películas
- Gestión de usuarios
- Moderación de contenido

### UI/UX
- Diseño responsive
- Modo oscuro/claro
- Animaciones profesionales
- Tipografía premium

## Primeros Pasos

### Requisitos
- Java 17+
- Node.js 18+
- PostgreSQL 15+

### Configuración

1. **Base de datos:**
```bash
# Crear base de datos PostgreSQL
createdb asturian_cinema
```

2. **Backend:**
```bash
cd backend
./mvnw spring-boot:run
```

3. **Frontend:**
```bash
cd frontend/asturian-cinema
npm install
ng serve
```

### Usuario Admin por defecto
- Usuario: `admin`
- Contraseña: `admin123`

## API Endpoints

### Autenticación
- `POST /api/auth/register` - Registro
- `POST /api/auth/login` - Login
- `POST /api/auth/refresh` - Refresh token
- `GET /api/auth/me` - Usuario actual

### Películas
- `GET /api/movies` - Listar
- `GET /api/movies/{id}` - Detalle
- `GET /api/movies/asturianas` - Películas asturianas
- `GET /api/movies/search?q=` - Buscar
- `POST /api/movies` - Crear (Admin)
- `PUT /api/movies/{id}` - Actualizar (Admin)
- `DELETE /api/movies/{id}` - Eliminar (Admin)

### Reseñas
- `GET /api/movies/{id}/reviews` - Listar reseñas
- `POST /api/movies/{id}/reviews` - Crear reseña

### Usuarios
- `GET /api/users/profile` - Perfil
- `GET /api/users/favorites` - Favoritos
- `POST /api/users/favorites/{id}` - Añadir favorito
- `POST /api/users/follow/{id}` - Seguir usuario

### Admin
- `GET /api/admin/stats` - Estadísticas
- `GET /api/admin/users` - Gestionar usuarios
- `PUT /api/admin/users/{id}/role` - Cambiar rol

## Contribución

1. Fork del repositorio
2. Crear branch (`git checkout -b feature/nueva-caracteristica`)
3. Commit (`git commit -m 'Añadir nueva característica'`)
4. Push (`git push origin feature/nueva-caracteristica`)
5. Crear Pull Request

## Licencia

MIT License - Ver LICENSE para más detalles.
