# Documentación HTTP

Cada microservicio publica OpenAPI 3 generado por springdoc:

- JSON: `http://localhost:PUERTO/v3/api-docs`
- Swagger UI: `http://localhost:PUERTO/swagger-ui.html`

Use los puertos 8080 a 8086 según la tabla del README. Swagger expone modelos, restricciones de validación y operaciones disponibles. Los endpoints bajo `/interno` existen para comunicación entre servicios y no deberían exponerse directamente fuera de una red de desarrollo.

La colección `postman/Nexus-Hospitality.postman_collection.json` contiene un recorrido básico. Importe también `postman/Nexus-Local.postman_environment.json` y seleccione el entorno **Nexus Local**.
