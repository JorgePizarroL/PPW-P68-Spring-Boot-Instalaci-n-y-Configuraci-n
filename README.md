# Práctica 1 - Spring Boot: Instalación, Configuración Inicial y Primer Endpoint

---

## Configuración del proyecto

| Campo | Valor |
|---|---|
| Build Tool | Gradle – Kotlin DSL |
| Language | Java |
| Spring Boot | 4.1.0 |
| Packaging | Jar |
| Java | 17 |
| Group | ec.edu.ups.icc |
| Artifact | fundamentos01 |
| Package | ec.edu.ups.icc.fundamentos01 |

## Dependencias
- Spring Web
- Spring Boot DevTools

---

## Evidencias 

### 1. Verificación de Java
![java version](assets/01_java_version.png)

### 2. Servidor Spring Boot ejecutándose
![tomcat running](assets/02_tomcat_running.png)

### 3. Endpoint /api/status funcionando
![endpoint status](assets/03_endpoint_status.png)

### 4. Archivo StatusController.java
![ls controllers](assets/04_ls_controllers.png)

---

## Explicación

### Funcionamiento del endpoint
El endpoint `/api/status` cuando lo abres en el navegador te devuelve 
un JSON con el nombre del servicio, si está corriendo y la hora exacta. 
Lo que entendí es que `@RestController` es lo que hace que el método 
devuelva ese JSON automáticamente.

### Spring Boot y el servidor
No hay que instalar ningún servidor por separado, 
Spring Boot ya trae Tomcat incluido y lo inicia solo cuando se ejecuta
`./gradlew bootRun`. Con agregar la dependencia web ya 
funciona todo en el puerto 8080.

---

# Práctica 2 - Spring Boot: Estructura del Proyecto y Arquitectura Modular

## Estructura modular del proyecto

```
src/main/java/ec/edu/ups/icc/fundamentos01/
    ├── auth/
    ├── config/
    ├── core/
    │    ├── dtos/
    │    └── entities/
    ├── products/
    │    ├── controllers/
    │    ├── dtos/
    │    ├── entities/
    │    ├── mappers/
    │    ├── models/
    │    ├── repositories/
    │    └── services/
    ├── students/
    │    ├── controllers/
    │    └── models/
    ├── users/
    │    ├── controllers/
    │    ├── dtos/
    │    ├── entities/
    │    ├── mappers/
    │    ├── models/
    │    ├── repositories/
    │    └── services/
    ├── utils/
    └── Fundamentos01Application.java
```

## Evidencias

### 1. Estructura modular en el IDE
![estructura modular](assets/05_estructura_modular.png)

### 2. Archivo Fundamentos01Application.java
![aplicacion principal](assets/06_fundamentos_application.png)

---

## Explicación: ¿Por qué usar módulos separados?

Separar el proyecto por dominios (products, users, auth) permite que
cada funcionalidad sea independiente: sus controladores, servicios,
repositorios y entidades están agrupados juntos. Esto facilita encontrar
y modificar código sin afectar otras partes del sistema. Además,
Spring Boot detecta todos los componentes automáticamente gracias al
@ComponentScan que activa @SpringBootApplication, siempre que estén
dentro del package raíz ec.edu.ups.icc.fundamentos01.

---

# Práctica 3 - Spring Boot: API REST con Controladores, DTOs, Modelos y Mappers

## Evidencias

### 1. GET /api/products - Lista de productos
![get all products](assets/07_get_all_products.png)

### 2. GET /api/products/1 - Producto por ID
![get product by id](assets/08_get_product_by_id.png)

### 3. DELETE /api/products/1 - Eliminar producto existente
![delete product existing](assets/09_delete_product_existing.png)

### 4. DELETE /api/products/99 - Eliminar producto inexistente
![delete product not found](assets/10_delete_product_not_found.png)

---

## Explicación

Se construyó un CRUD REST completo para el recurso products usando
controladores, DTOs, modelos y mappers. El DTO de entrada recibe solo
name, price y stock, mientras que el DTO de respuesta devuelve esos
mismos campos más el id generado automáticamente. El mapper convierte
entre DTO, modelo y entidad sin exponer datos internos. Los endpoints
siguen el estándar REST: GET retorna 200, DELETE retorna 204 No Content
cuando el producto existe, y 500 con mensaje "Product not found" cuando
no existe.

---

# Práctica 4 - Spring Boot: Controladores + Servicios + Lógica de Negocio

## Evidencias

### 1. ProductServiceImpl.java
![product service impl](assets/11_product_service_impl.png)

### 2. ProductsController.java
![products controller](assets/12_products_controller.png)

---

## Explicación: ¿Cómo se inyecta el servicio en el controlador?

El controlador no crea manualmente el servicio. En su lugar declara
una dependencia final y la recibe por constructor:

    private final ProductService service;

    public ProductsController(ProductService service) {
        this.service = service;
    }

Spring Boot detecta que el controlador necesita un ProductService,
busca una clase que implemente esa interfaz, encuentra ProductServiceImpl
porque tiene la anotación @Service, crea una instancia automáticamente
y la inyecta en el controlador. Esto se llama inyección de dependencias
por constructor y permite que el controlador no tenga lógica de negocio,
solo delega cada operación al servicio.

---

# Práctica 5 - Spring Boot: Persistencia con JPA, Entidades y Repositorios

## Evidencias

### 1. Productos en PostgreSQL
![products postgresql](assets/13_products_postgresql.png)

---

## Explicación: Flujo de datos desde la API REST hasta PostgreSQL

Cuando llega una petición HTTP al controlador, este delega al servicio.
El servicio convierte el DTO de entrada en un ProductModel usando el
mapper. Luego convierte el modelo en una ProductEntity y la guarda en
PostgreSQL mediante el ProductRepository. Al leer datos, el repositorio
devuelve entidades que el mapper convierte a modelos y luego a DTOs de
respuesta que se envían al cliente.

BaseEntity centraliza los campos comunes de todas las entidades: id
generado automáticamente por PostgreSQL, createdAt asignado con
@PrePersist, updatedAt actualizado con @PreUpdate, y deleted para
eliminación lógica. Esto evita repetir esos campos en cada entidad.

---

# Práctica 6 - Spring Boot: Validación de DTOs y Control de Datos de Entrada

## Evidencias

### 1. POST inválido → 400 Bad Request
Se envió `name` vacío, `price` negativo y `stock` negativo. Spring Boot rechaza la petición antes de llegar al servicio.

![post invalido](assets/14_post_invalido.png)

### 2. POST válido → producto creado correctamente
Se creó el producto "Camara" con precio y stock válidos.

![post valido](assets/15_post_valido.png)

### 3. PUT sobre producto eliminado → error
Se eliminó el producto con id 6 y luego se intentó actualizarlo. El servicio lanza `"Cannot update a deleted product"`.

![put producto eliminado](assets/16_put_producto_eliminado.png)

### 4. GET findAll → producto eliminado no aparece
Después de eliminar el producto con id 6, el listado solo devuelve los productos activos. El id 6 no aparece en la respuesta.

![get findall sin eliminados](assets/17_get_findall_filtrado.png)

---

# Práctica 7 - Spring Boot: Manejo Global de Errores y Excepciones

## Evidencias

### 1. GET producto inexistente → 404 Not Found
Se buscó un producto con id 999 que no existe. El handler devuelve el formato estándar de error.

![producto no encontrado](assets/18_producto_no_encontrado.png)

### 2. POST producto duplicado → 409 Conflict
Se intentó crear un producto con el nombre "Teclado" que ya existe en la base de datos.

![producto duplicado](assets/19_producto_duplicado.png)

### 3. POST inválido → 400 Bad Request con details
Se enviaron datos inválidos. El handler devuelve el campo `details` con el error específico de cada campo.

![validacion details](assets/20_validacion_details.png)

---

# Práctica 8 - Spring Boot: Relaciones ManyToOne, Foreign Keys y Consultas Relacionales

## Relaciones implementadas

En esta práctica los productos dejaron de existir de forma aislada. Ahora cada producto se relaciona con:

- Un **usuario** (`UserEntity`) que lo registra
- Una **categoría** (`CategoryEntity`) a la que pertenece

Ambas relaciones son `@ManyToOne` desde `ProductEntity`:

```java
@ManyToOne(optional = false, fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private UserEntity owner;

@ManyToOne(optional = false, fetch = FetchType.LAZY)
@JoinColumn(name = "category_id", nullable = false)
private CategoryEntity category;
```

Esto crea dos columnas de clave foránea en la tabla `products`: `user_id` y `category_id`, ambas `NOT NULL` porque un producto no puede existir sin usuario ni categoría (`optional = false`).

## Nuevo módulo: categories

Se agregó el módulo `categories/` completo (entidad, DTOs, repositorio, servicio y controlador), siguiendo el mismo patrón de capas que ya usaba `products/`.

## Evidencias

### 1. Estructura de la tabla `products` en PostgreSQL
Se verificó con `\d products` que las columnas `user_id` y `category_id` se crearon como claves foráneas hacia `users` y `categories`.

![estructura tabla products](assets/21_products_table_structure.png)

### 2. Creación de producto con relaciones anidadas
Al crear un producto enviando `userId` y `categoryId`, la respuesta devuelve los objetos `owner` y `category` completos, no solo sus IDs.

![creación producto con relaciones](assets/22_create_product_relations.png)

### 3. Consulta de productos por categoría
`GET /api/products/category/{categoryId}` devuelve solo los productos activos de esa categoría.

![productos por categoría](assets/23_products_by_category.png)

---

## Explicación: ¿Cómo se relaciona ProductEntity con UserEntity y CategoryEntity?

Usé `@ManyToOne` porque la relación es "muchos productos pertenecen a un mismo usuario/categoría" — es la entidad "muchos" (`Product`) la que apunta hacia la entidad "uno" (`User` o `Category`). `@JoinColumn` define el nombre exacto de la columna de clave foránea que Hibernate crea en la tabla `products` (`user_id` y `category_id`), apuntando al `id` de la tabla relacionada.

Usé `fetch = FetchType.LAZY` en vez de `EAGER` porque así el usuario y la categoría solo se cargan de la base de datos cuando realmente se accede a ellos (`entity.getOwner()`), en vez de traerlos siempre aunque no se necesiten. Esto es importante en listados grandes, donde cargar todas las relaciones de golpe sería un desperdicio de consultas.

Antes de guardar un producto, el servicio valida que tanto el usuario como la categoría existan y no estén eliminados lógicamente (`deleted = false`); si alguno no existe, se lanza `NotFoundException` y la API responde `404 Not Found`.