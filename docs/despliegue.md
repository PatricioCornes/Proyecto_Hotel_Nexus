# Compilación y despliegue

## Desarrollo local con Docker

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\build-all.ps1
docker compose config
docker compose up -d --build
docker compose ps
```

El script empaqueta Eureka y los siete microservicios con sus respectivos Maven Wrapper. Compose conserva los datos en el volumen `nexus_mysql_data`, espera a que MySQL, RabbitMQ y Eureka estén saludables y luego inicia servicios en orden.

## Operación

```powershell
docker compose logs -f reservation payments rooms cleaning
docker compose restart reservation
docker compose down
```

`docker compose down` no elimina la información. Para una evaluación reproducible, compruebe que los nueve servicios aparezcan `healthy` y que Eureka muestre siete aplicaciones `UP`.

## Configuración

Las propiedades sensibles o dependientes del ambiente se inyectan mediante variables: `SPRING_DATASOURCE_URL`, `SPRING_RABBITMQ_HOST` y `EUREKA_DEFAULT_ZONE`. Las imágenes ejecutan Java con un usuario `nexus` sin privilegios.
