## Servicio de Precios para E-commerce

## Definición del Proyecto

Microservicio en Spring Boot que expone precios de productos vía REST y publica eventos en Kafka. 
Implementa arquitectura hexagonal y CQRS, gestionando la persistencia en H2 (memoria) y validando los datos con Avro.

- **Base de datos**: H2 (en memoria)
- **Mensajería**: Apache Kafka
- **API**: REST (Spring Web)
- **Arquitectura**: Hexagonal (Ports & Adapters)

## Operaciones Principales
- Consultar precios de productos según marca, producto y fecha de aplicación.
- Soporte para operaciones de comando y consulta (CQRS).
- Publicar eventos de dominio en Kafka para procesamiento asincrónico.

## Características
- Exposición de precios mediante REST.
- Publicación de eventos en Kafka.
- Arquitectura Hexagonal + CQRS.
- Persistencia en H2 y mapeo con JPA/Hibernate.
- Validación de mensajes Avro.
- Logging centralizado.

## Documentación de la API
- Documentación y pruebas interactivas vía Swagger UI (springdoc-openapi).
- Endpoints accesibles típicamente en: http://localhost:8080/swagger-ui.html o http://localhost:8080/swagger-ui/index.html.

## Stack Tecnológico
- Java 21
- Maven
- Base de datos H2 (en memoria)
- Spring Boot
- Spring Data JPA (H2)
- Spring Kafka
- Spring Validation & Actuator
- Avro para serialización de mensajes
- Apache Kafka para mensajería
- JUnit & Spring Boot Test
- OpenAPI / Swagger UI
- i18n

## Arquitectura y Filosofía del Microservicio

- Arquitectura Hexagonal

Organiza el código en Ports (interfaces de entrada y salida) y Adapters (implementaciones concretas).
Permite que la lógica de negocio sea independiente de frameworks, bases de datos o mensajería.
Facilita pruebas unitarias y reemplazo de infraestructuras sin afectar el dominio.

- DDD (Domain-Driven Design)

La lógica de negocio está centrada el dominio.
Separación del dominio (lógica) de infraestructura (persistencia, mensajería, API).

- CQRS

Separación de comandos y consultas para mayor claridad y escalabilidad.

- Eventos de Dominio

Publicación de cambios de estado para sistemas reactivos.

- Pruebas y calidad

Testing unitario y de integración, logging centralizado, validación de datos mediante Avro.


## Running the project
- Tener Java 21 y Maven instalados.
- Clona el repositorio y navega al directorio del proyecto.
- Compila el proyecto y genera las clases Avro:
```bash
  mvn clean compile
```
- Ejecuta el microservicio:
```bash
  mvn spring-boot:run
```

## Pruebas de la Aplicación

### Tests de la Prueba Técnica

Se han desarrollado tests específicos para validar el comportamiento solicitado en la prueba técnica, 
centrados en el endpoint de consulta de precios aplicables:

- Endpoint: `GET /api/v1/products/price`
- Parámetros:
    - `applicationDate`: Fecha y hora de aplicación del precio (ISO 8601)
    - `productId`: Identificador del producto
    - `brandId`: Identificador de la cadena
- Datos de ejemplo inicializados en la base de datos H2:

| BRAND_ID | PRODUCT_ID | START_DATE           | END_DATE             | PRICE_LIST | PRICE |
|----------|------------|--------------------|--------------------|------------|-------|
| 1        | 35455      | 2020-06-14 00:00:00 | 2020-12-31 23:59:59 | 1          | 35.50 |
| 1        | 35455      | 2020-06-14 15:00:00 | 2020-06-14 18:30:00 | 2          | 25.45 |
| 1        | 35455      | 2020-06-15 00:00:00 | 2020-06-15 11:00:00 | 3          | 30.50 |
| 1        | 35455      | 2020-06-15 16:00:00 | 2020-12-31 23:59:59 | 4          | 38.95 |

#### Escenarios validados

1. **10:00 del 14/06/2020** → Precio esperado: 35.50 (Tarifa 1)
2. **16:00 del 14/06/2020** → Precio esperado: 25.45 (Tarifa 2)
3. **21:00 del 14/06/2020** → Precio esperado: 35.50 (Tarifa 1)
4. **10:00 del 15/06/2020** → Precio esperado: 30.50 (Tarifa 3)
5. **21:00 del 16/06/2020** → Precio esperado: 38.95 (Tarifa 4)

#### Ubicación de los tests

Los tests se encuentran en el paquete: **src/test/java/com/example/ecommerce/validation/technical/test**

Archivo principal: `EndpointTechnicalTest.java`

Estos tests se pueden ejecutar de manera directa lanzando el Run de la clase o con Maven:

```
  mvn test
```

### Repositorios

- **Confluent Maven Repo:** `https://packages.confluent.io/maven/`  
  Necesario para librerías de Kafka Avro.

  **Avro Plugin config:**
- `sourceDirectory`: `src/main/avro`
- `outputDirectory`: `target/generated-sources/avro`
- Fase de ejecución: `generate-sources`

---
### Cómo usarlo
1. Coloca tus archivos Avro (`.avsc`) en `src/main/avro/`.
2. Genera las clases Java con:
```bash
   mvn clean compile
```
3. Ejecuta el microservicio:
```bash
    mvn spring-boot:run
```

## Pruebas de Eventos Asincrónicos con Kafka

Para poder probar la publicación y consumo de eventos en Kafka, se puede levantar un entorno local mediante Docker Compose. 
Esto permite trabajar con producers y listeners de forma rápida sin necesidad de un cluster externo.

### Docker Compose para Kafka y Schema Registry
```yaml
version: '3.8'

services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - "2181:2181"

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
````

### Instrucciones para levantar el entorno

1. Asegúrate de tener Docker y Docker Compose instalados.
2. Lanza el Docker en tu equipo.
3. Comprueba que está el fichero yml del docker-compose, en caso contrario, guarda el archivo YAML anterior como `docker-compose.yml`.
4. En la terminal, navega al directorio donde guardaste el archivo y ejecuta:
```bash
   docker-compose up -d
```
5. Verifica que los servicios estén corriendo:
```bash
   docker-compose ps
```
6. Configura tu microservicio para que apunte a `localhost:9092` para Kafka.
7. Ahora puedes probar la publicación y consumo de eventos en Kafka desde tu microservicio.

> Nota: Si en futuras pruebas se requiere usar Avro/Protobuf, se puede añadir un servicio de
> `schema-registry` en el `docker-compose.yml` para gestionar esquemas.
> En este caso, el endpoint quedaría disponible en `http://localhost:8081`.

## Configuración Maven del Microservicio

Este proyecto es un **microservicio Spring Boot** con integración **Kafka** y **Avro**.

---

### Dependencias

| Tipo          | Dependencia                                      | Propósito                                            |
|---------------|--------------------------------------------------|------------------------------------------------------|
| Web           | `spring-boot-starter-web`                        | Exponer APIs REST                                    |
| Datos         | `spring-boot-starter-data-jpa`                   | Persistencia con JPA/Hibernate                       |
| Validación    | `spring-boot-starter-validation`                 | Validaciones de datos                                |
| Base de datos | `h2`                                             | DB embebida para pruebas                             |
| Kafka         | `spring-kafka`                                   | Integración con Apache Kafka                         |
| Kafka Avro    | `kafka-avro-serializer`                          | Serialización con Avro y Schema Registry             |
| Lombok        | `lombok`                                         | Generación automática de código (getters, setters…)  |
| DevTools      | `spring-boot-devtools`                           | Recarga en caliente en desarrollo                    |
| Testing       | `spring-boot-starter-test` / `spring-kafka-test` | Frameworks de testing                                |
| OpenAPI       | `springdoc-openapi-starter-webmvc-ui`            | Documentación y pruebas interactivas con Swagger UI  |

---

### Plugins

| Plugin                    | Versión     | Función                                                                |
|---------------------------|-------------|------------------------------------------------------------------------|
| Maven Compiler Plugin     | 3.11.0      | Compila con **Java 21**, soporta Lombok como *annotation processor*    |
| Spring Boot Maven Plugin  | -           | Empaquetado ejecutable del microservicio                               |
| Avro Maven Plugin         | 1.11.4      | Genera clases Java a partir de archivos `.avsc`                        |


## Configuración del `application.yml`

| Sección                                                       | Propiedad           | Valor                                                            | Descripción                                                                                                                                                 |
|---------------------------------------------------------------|---------------------|------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `spring.application`                                          | `name`              | ecommerce                                                        | Nombre de la aplicación. Útil para logs y métricas.                                                                                                         |
| `spring.server`                                               | `port`              | 8080                                                             | Puerto donde se levanta la aplicación.                                                                                                                      |
| `spring.datasource`                                           | `url`               | `jdbc:h2:mem:ecommerce;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE` | Base de datos H2 en memoria. `DB_CLOSE_DELAY=-1` mantiene la BD viva mientras la app esté corriendo; `DB_CLOSE_ON_EXIT=FALSE` evita que se cierre al salir. |
|                                                               | `driver-class-name` | org.h2.Driver                                                    | Driver JDBC para H2.                                                                                                                                        |
|                                                               | `username`          | devUser                                                          | Usuario para la base de datos.                                                                                                                              |
|                                                               | `password`          |                                                                  | Contraseña para la base de datos (vacía en desarrollo).                                                                                                     |
| `spring.jpa.hibernate.ddl-auto`                               | `update`            | update                                                           | Hibernate actualiza automáticamente el esquema de la base de datos según las entidades. Útil en desarrollo, **no recomendado en producción**.               |
| `spring.jpa.show-sql`                                         | true                | true                                                             | Muestra las consultas SQL en los logs.                                                                                                                      |
| `spring.jpa.properties.hibernate.format_sql`                  | true                | true                                                             | Formatea las consultas SQL para que sean más legibles en logs.                                                                                              |
| `spring.kafka.bootstrap-servers`                              |                     | localhost:9092                                                   | Dirección del broker Kafka.                                                                                                                                 |
| `spring.kafka.producer.key-serializer`                        |                     | StringSerializer                                                 | Serializador de la clave del mensaje Kafka.                                                                                                                 |
| `spring.kafka.producer.value-serializer`                      |                     | KafkaAvroSerializer                                              | Serializador de Avro para el valor del mensaje Kafka.                                                                                                       |
| `spring.kafka.producer.properties.schema.registry.url`        |                     | http://localhost:8081                                            | URL del Schema Registry para Avro.                                                                                                                          |
| `spring.kafka.consumer.group-id`                              |                     | ecommerce-group                                                  | Grupo de consumidores Kafka para coordinar offsets.                                                                                                         |
| `spring.kafka.consumer.key-deserializer`                      |                     | StringDeserializer                                               | Deserializador de la clave del mensaje Kafka.                                                                                                               |
| `spring.kafka.consumer.value-deserializer`                    |                     | KafkaAvroDeserializer                                            | Deserializador Avro para el valor del mensaje Kafka.                                                                                                        |
| `spring.kafka.consumer.properties.specific.avro.reader`       |                     | true                                                             | Permite que Kafka lea objetos Avro específicos generados por el compilador Avro.                                                                            |
| `spring.kafka.consumer.properties.auto-offset-reset`          |                     | earliest                                                         | Si no hay offset previo, empieza a leer desde el inicio del topic.                                                                                          |
| `spring.h2.console.enabled`                                   |                     | true                                                             | Habilita la consola web de H2.                                                                                                                              |
| `spring.h2.console.path`                                      |                     | /h2-console                                                      | Ruta de acceso a la consola H2.                                                                                                                             |
| `logging.level.root`                                          |                     | INFO                                                             | Nivel de logging global.                                                                                                                                    |
| `logging.level.com.example`                                   |                     | DEBUG                                                            | Nivel de logging específico para la aplicación.                                                                                                             |
| `logging.level.org.hibernate.SQL`                             |                     | DEBUG                                                            | Muestra las consultas SQL ejecutadas por Hibernate.                                                                                                         |
| `logging.level.org.hibernate.type.descriptor.sql.BasicBinder` |                     | TRACE                                                            | Muestra los valores que se enlazan a los parámetros SQL.                                                                                                    |

---

# COMANDOS

## COMANDOS DE DOCKER

1️⃣ Gestión de contenedores

| Comando                                                | Descripción                                                   |
|--------------------------------------------------------|---------------------------------------------------------------|
| docker ps                                              | Lista los contenedores en ejecución.                          |
| docker ps                                              | Lista contenedores en ejecución.                              |
| docker ps -a                                           | Lista todos los contenedores, incluso los detenidos.          |
| docker start <nombre:container>	                       | Inicia un contenedor detenido.                                |
| docker stop <nombre:container>	                        | Detiene un contenedor en ejecución.                           |
| docker restart <nombre:container>                      | Reinicia un contenedor.                                       |
| docker rm <nombre:container>	                          | Elimina un contenedor detenido.                               |
| docker rm -f <nombre:container>	                       | Elimina un contenedor forzando su detención.                  |
| docker logs <nombre:container>	                        | Muestra los logs de un contenedor.                            |
| docker logs -f <nombre:container>	                     | Sigue los logs en tiempo real (follow).                       |
| docker logs --tail <numero:lineas> <nombre:container>	 | Muestra solo las últimas n líneas de log.                     |
| docker exec -it <nombre:container> /bin/bash           | Abre un shell interactivo dentro de un contenedor.            |
| docker inspect <nombre:container>	                     | Muestra información detallada de un contenedor.               |
| docker top <nombre:container>                          | Muestra los procesos en ejecución dentro de un contenedor.    |
| docker stats <nombre:container>	                       | Muestra estadísticas de uso en tiempo real de un contenedor.  |

2️⃣ Gestión de imágenes

| Comando                         | Descripción                                                   |
|---------------------------------|---------------------------------------------------------------|
| docker images	                  |Lista todas las imágenes locales.|
| docker rmi <nombre:imagen>	     |Elimina una imagen local.|
| docker build -t <nombre:tag> .	 |Construye una imagen desde un Dockerfile.|
| docker pull <nombre:imagen>	           |Descarga una imagen del repositorio.|
| docker push <nombre:imagen>	           |Sube una imagen al repositorio.|

3️⃣ Docker Compose

| Comando                               | Descripción                                                   |
|---------------------------------------|---------------------------------------------------------------|
| docker-compose up	                    |Levanta todos los servicios del docker-compose.yml.|
| docker-compose up -d	                 |Levanta los servicios en segundo plano (detached).|
| docker-compose down	                  |Detiene y elimina los servicios creados.|
| docker-compose logs	                  |Muestra logs de todos los servicios.|
| docker-compose logs -f	               |Sigue logs en tiempo real.|
| docker-compose logs <nombre:service>	 |Muestra logs de un servicio específico.|
| docker-compose build	                 |Construye las imágenes definidas en docker-compose.yml.|
| docker-compose stop	                  |Detiene los servicios sin eliminarlos.|
| docker-compose restart	               |Reinicia los servicios.|
| docker-compose ps	                    |Lista los servicios y su estado.|

4️⃣ Redes y volúmenes


| Comando                                 | Descripción                                                   |
|-----------------------------------------|---------------------------------------------------------------|
| docker network ls	                      | Lista todas las redes de Docker.|
| docker network inspect <nombre:network> |Información detallada de una red.|
| docker volume ls	                       |Lista todos los volúmenes.|
| docker volume inspect <nombre:volumen>	 |Información detallada de un volumen.|
| docker volume rm <nombre:volumen>	      |Elimina un volumen.|

5️⃣ Limpieza y mantenimiento

| Comando                 | Descripción                                                  |
|-------------------------|--------------------------------------------------------------|
| docker system prune	    | Elimina contenedores detenidos, redes y volúmenes no usados. |
| docker system prune -a	 | Además de lo anterior, elimina imágenes no usadas.           |
| docker image prune	     | Elimina imágenes no referenciadas.                           |
| docker container prune	 | Elimina contenedores detenidos.                              |
| docker volume prune	    | Elimina volúmenes no referenciados.                          |
| docker network prune	   | Elimina redes no usadas.                                     |

6️⃣ Información del sistema

| Comando         | Descripción                                                 |
|-----------------|-------------------------------------------------------------|
| docker info	    | Información general del entorno Docker.                     |
| docker version	 | Muestra la versión de Docker y Docker Compose.              |
| docker stats	   | Muestra estadísticas de uso de contenedores en tiempo real. |