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
