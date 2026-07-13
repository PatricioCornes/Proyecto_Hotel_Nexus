# Arquitectura

## Componentes

Cada microservicio conserva su propia base lógica en MySQL. Eureka mantiene el registro de instancias y permite que Reservation, Payments y Cleaning resuelvan destinos por nombre, sin direcciones fijas. RabbitMQ transporta los eventos del ciclo hotelero.

```text
Reservation --Feign/Eureka--> Rooms
Reservation --Feign/Eureka--> Payments (reembolsos)
Payments --RestClient/Eureka--> Reservation + Restaurant + Garage
Cleaning --RestClient/Eureka--> Staff

Reservation --eventos--> RabbitMQ --> Payments / Rooms / Cleaning
Cleaning    --eventos--> RabbitMQ --> Rooms
```

## Decisiones

- Spring Boot 4.0.6 y Spring Cloud 2025.1.2.
- Eureka Server independiente; los siete servicios son clientes.
- OpenFeign para los contratos síncronos de Reservation y `@LoadBalanced RestClient` para Payments y Cleaning.
- RabbitMQ para evitar acoplar el flujo principal a llamadas síncronas largas.
- Bean Validation en la frontera HTTP y respuesta uniforme de errores.
- Actuator ofrece `/actuator/health`; Docker usa este contrato para determinar disponibilidad.

## Formato de error

Los errores HTTP contienen `timestamp`, `status`, `error`, `codigo`, `mensaje`, `path` y `detalles`. Las entradas inválidas responden 400, recursos inexistentes 404, conflictos 409, dependencias remotas no disponibles 503 y fallos no previstos 500.
