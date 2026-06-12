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