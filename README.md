# Nexus Hospitality

Sistema académico de gestión hotelera compuesto por siete microservicios Spring Boot, un servidor de descubrimiento Eureka, RabbitMQ y MySQL.

## Inicio rápido

Requisitos: Docker Desktop en ejecución y PowerShell 7 o Windows PowerShell 5.1.

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\build-all.ps1
docker compose up -d --build
docker compose ps
```

- Eureka: <http://localhost:8761>
- RabbitMQ: <http://localhost:15672> (`guest` / `guest`)
- Swagger: `http://localhost:PUERTO/swagger-ui.html`
- Salud: `http://localhost:PUERTO/actuator/health`

| Servicio | Puerto | Responsabilidad |
|---|---:|---|
| Staff | 8080 | Personal y asignación de empleados |
| Rooms | 8081 | Habitaciones y estados |
| Restaurant | 8082 | Restaurantes y consumos |
| Reservation | 8083 | Ciclo de vida de reservas |
| Payments | 8084 | Pagos, reembolsos y factura final |
| Garage | 8085 | Uso y costo de estacionamiento |
| Cleaning | 8086 | Tareas de limpieza |
| Eureka Server | 8761 | Registro y descubrimiento |

La arquitectura y contratos están explicados en [docs/arquitectura.md](docs/arquitectura.md), [docs/eventos.md](docs/eventos.md), [docs/despliegue.md](docs/despliegue.md) y [docs/openapi.md](docs/openapi.md). La bitácora completa del trabajo se mantiene en `leeme.txt`.
