package com.nexus.hospitality_rooms;

import com.nexus.hospitality_rooms.entidad.Habitacion;
import com.nexus.hospitality_rooms.repositorio.HabitacionRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class HabitacionDataSeeder implements CommandLineRunner {

    private final HabitacionRepositorio habitacionRepositorio;

    public HabitacionDataSeeder(HabitacionRepositorio habitacionRepositorio) {
        this.habitacionRepositorio = habitacionRepositorio;
    }

    @Override
    public void run(String... args) {
        habitacionRepositorio.findByNumero("101").ifPresentOrElse(
                r -> {},
                () -> {
                    Habitacion habitacion = Habitacion.builder()
                            .numero("101")
                            .tipo("Doble")
                            .precioPorNoche(new java.math.BigDecimal("120.00"))
                            .estado("Disponible")
                            .build();
                    habitacionRepositorio.save(habitacion);
                }
        );
    }
}

