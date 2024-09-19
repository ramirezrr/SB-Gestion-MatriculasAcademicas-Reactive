# Control de registros de matrícula de los estudiantes de una academia API con Webflux 

## Descripción del Proyecto

Este proyecto es una API RESTful desarrollada con Spring Boot, diseñada para gestionar la información de estudiantes, matrículas y cursos. Proporciona un conjunto completo de operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para gestionar entidades relacionadas con el proceso educativo.
- Control de registros de matrícula de los estudiantes de una academia,
donde se almacena los datos de los estudiantes, de los cursos y los
cursos que los estudiantes están matriculados.

### Funcionalidades

- **Gestión de Estudiantes**: Permite registrar, actualizar, eliminar y consultar información de estudiantes. _Las consultas lo ordena de manera ascendente y descendente._
- **Gestión de Matrículas**: Facilita la gestión de matrículas de estudiantes en cursos específicos.
- **Gestión de Cursos**: Permite crear, actualizar y eliminar cursos.
- **Seguridad**: Implementa control de acceso basado en roles para gestionar usuarios y sus permisos, asegurando que solo los usuarios autorizados puedan acceder a ciertas funcionalidades.
- **Validaciones globales** manejadas a través de un GlobalExceptionHandler, que intercepta errores y envía un bean con detalles del error.
- **Configuración de Logback** para una gestión eficiente de logs.
- **Uso de filtros** para agregar un X-Request-ID en cada registro de log y en el header de respuesta, mejorando la trazabilidad de las operaciones.
- **Paginación** de resultados para facilitar la visualización de grandes conjuntos de datos.

### Tecnologías Utilizadas
- **Spring Boot**: 3.3.3
- **Java**: 21
- **Spring WebFlux**: Soporte para programación reactiva.
- **Spring Data MongoDB Reactive**: Interacción con MongoDB de manera reactiva.
- **ModelMapper**: 3.0.0 para la conversión entre objetos y DTOs.
- **Lombok**: Reduce el boilerplate (opcional).
- **Spring Boot Starter Validation**: Validación de datos de entrada.
- **Spring Boot Starter Security**: Funcionalidades de seguridad (autenticación y autorización).
- **JWT (JSON Web Tokens)**:
   - **jjwt-api**: 0.12.5
   - **jjwt-impl**: 0.12.5 (runtime)
   - **jjwt-jackson**: 0.12.5 (runtime)

### Others:
**SHA512 Online:** Se utiliza para generar hashes seguros utilizando el algoritmo SHA-512, lo que ayuda a proteger contraseñas y datos sensibles. https://sha512.online/

**Bcrypt Generator:** Se utiliza para generar hashes de contraseñas con el algoritmo Bcrypt, proporcionando una forma segura de almacenar contraseñas mediante el uso de sal y un costo de trabajo ajustable. https://bcrypt-generator.com/?



---

## Ejecución y Pruebas

Se agregó la implementación de CommandLineRunner para inicializar los roles y usuarios en la aplicación. Se eliminan todos los roles y usuarios existentes y se crean nuevos con permisos ADMIN y USER. Se utiliza BCryptPasswordEncoder para encriptar las contraseñas

### Login, Seguridad
Para realizar una solicitud de login y generar el **TOKEN**, puedes usar la siguiente petición **POST**:

```
POST http://localhost:8080/login
Content-Type: application/json

{
  "username": "mitocode",
  "password": "123"
}
```

### Guardar Estudiantes
Para agregar un estudiante, usa la siguiente solicitud **PUT**:

```
### GUARDAR ESTUDIANTES
PUT http://localhost:8080/estudiantes
Content-Type: application/json
Authorization: Bearer <tu_token_JWT>

{
  "nombre": "Carlos",
  "apellido": "Carrasco",
  "dni": 12345678,
  "edad": 32
}

```

### Guardar Cursos
Para agregar un curso, utiliza el siguiente ejemplo de solicitud **PUT** con un token JWT para autenticación:

```
PUT http://localhost:8080/cursos
Content-Type: application/json
Authorization: Bearer <tu_token_JWT>

{
  "nombre": "Matematica",
  "siglas": "Mat",
  "estado": true
}
```

### Guardar Matrículas
Para agregar una matrícula con un estudiante y varios cursos, usa la siguiente solicitud **PUT**:

```
PUT http://localhost:8080/matriculas
Content-Type: application/json
Authorization: Bearer <tu_token_JWT>

{
  "fecha": "12/12/2024",
  "estudiante": {
    "id": "66e257841bdf0a536d54d7e8"
  },
  "cursos": [
    {
      "id": "66e258466005a86c3451a815",
      "nombre": "sasf"
    },
    {
      "id": "66e258466005a86c3451a444",
      "nombre": "ggg"
    }
  ],
  "estado": false
}
```

Recuerda que para todas las peticiones que requieran autenticación JWT, debes reemplazar `<tu_token_JWT>` con el token correspondiente que obtuviste durante el proceso de autenticación.

## Ejecutar:
```shell
spring-boot:run
```
   