# Contratos de eventos

Los eventos se publican en el exchange RabbitMQ definido por la configuración compartida de cada servicio. El campo `eventId` permite rastreo y el consumidor aplica operaciones idempotentes mediante referencias de negocio.

| Evento | Productor | Consumidores | Efecto principal |
|---|---|---|---|
| `ReservationCreated` | Reservation | Payments | Crea pago pendiente |
| `PaymentCompleted` | Payments | Reservation, Rooms | Confirma reserva y asigna/reserva habitación |
| `CheckInCompleted` | Reservation | Rooms | Habitación pasa a `OCUPADA` |
| `CheckOutCompleted` | Reservation | Rooms, Cleaning | Habitación pasa a `SUCIA` y crea limpieza |
| `CleaningRequested` | Cleaning | Staff mediante llamada descubierta | Asigna empleado disponible |
| `CleaningFinished` | Cleaning | Rooms | Habitación vuelve a `DISPONIBLE` y empleado se libera |

## Estados

- Habitación: `DISPONIBLE`, `RESERVADA`, `OCUPADA`, `SUCIA`, `EN_MANTENIMIENTO`.
- Limpieza: `PENDIENTE`, `EN_PROGRESO`, `FINALIZADA`.
- Pago: `PENDIENTE`, `APROBADO`, `RECHAZADO`, `REEMBOLSADO`.

RabbitMQ conserva mensajes durables. Si un consumidor se reinicia, continúa desde su cola. Los productores incluyen identificadores de reserva/habitación para correlación.
