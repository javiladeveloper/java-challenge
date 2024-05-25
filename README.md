# User Registration API

Este proyecto es una API RESTful para la creación y gestión de usuarios. La API permite registrar usuarios, consultar usuarios por correo electrónico y listar todos los usuarios registrados. La API está construida con Spring Boot y utiliza una base de datos en memoria H2.

## Requisitos

- Java 11+
- Maven 3.6.0+
- Postman (opcional, para pruebas de la API)

## Instalación

1. Clona el repositorio:

```sh
git clone https://github.com/tu-usuario/user-registration-api.git
cd user-registration-api
```

## Compilar y Ejecutar

### Usar los Scripts
### Compilar la Aplicación
Ejecuta el siguiente comando para compilar y empaquetar la aplicación:

```sh
./scripts/build.sh
```

### Ejecutar la Aplicación
Ejecuta el siguiente comando para iniciar la aplicación:

```sh
./scripts/run.sh
```

La aplicación se ejecutará en http://localhost:8080.

## Endpoints
### Registrar Usuario
- URL: /api/usuarios/registro
- Método: POST
- Headers: Content-Type: application/json
- Body:
```sh
{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "hunter2",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "countrycode": "57"
        }
    ]
}
```
- Respuestas:
    - 201 Created: Usuario registrado exitosamente.
    - 400 Bad Request: El correo ya está registrado.

### Consultar Usuario por Email
- URL: /api/usuarios/{email}
- Método: GET
- Headers: Content-Type: application/json
- Respuestas:
    - 200 OK: Usuario encontrado.
    - 404 Not Found: Usuario no encontrado.

### Listar Todos los Usuarios
- URL: /api/usuarios
- Método: GET
- Headers: Content-Type: application/json
- Respuestas:
  - 200 OK: Listado de usuarios. 

### Swagger-UI
- URL: /swagger-ui/
- Método: GET
- Respuestas:
  - 200 ok: Api Documentation

### Api-docs
- URL: /v2/api-docs
- Método: GET
- Respuestas:
  - 200 ok: Api Docs

### Base de Datos
- URL: /h2-console/
- Método: GET
- Respuestas:
  - 200 ok: Login BD

## Pruebas
Las pruebas unitarias están escritas utilizando JUnit y Mockito. Para ejecutar las pruebas, usa el siguiente comando:

```sh
mvn test
```

### Diagrama de la Solución

![MyImages](./images/DiagramSolution.png)

#### Diagrama de Clases UML

```plaintext
+------------------------+
| UserRegistrationApiApp |
+------------------------+
            |
            v
+-------------------+     +-----------------------+
| UsuarioController |<--->|    UsuarioService     |
+-------------------+     +-----------------------+
            |                        |
            v                        v
+-------------------+     +-----------------------+
|   Usuario         |     |    UsuarioRepository  |
+-------------------+     +-----------------------+
| - id              |                ^
| - name            |                |
| - email           |                |
| - password        |                |
| - created         |                |
| - modified        |                |
| - lastLogin       |                |
| - token           |                |
| - isActive        |                |
| - phones          |                |
+-------------------+                |
            |                        |
            v                        |
+-------------------+                |
|     Phone         |                |
+-------------------+                |
| - id              |                |
| - number          |                |
| - citycode        |                |
| - countrycode     |                |
+-------------------+                |
                                     |
                      +---------------------------+
                      |        JpaRepository       |
                      +---------------------------+
```

## Autor
Jonathan Avila Huamolle

## Licencia
Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.