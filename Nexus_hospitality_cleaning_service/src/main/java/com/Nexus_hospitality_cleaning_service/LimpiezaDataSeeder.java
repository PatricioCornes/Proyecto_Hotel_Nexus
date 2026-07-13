package com.Nexus_hospitality_cleaning_service;

import com.Nexus_hospitality_cleaning_service.entidad.Limpieza;
import com.Nexus_hospitality_cleaning_service.repositorio.LimpiezaRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LimpiezaDataSeeder implements CommandLineRunner {

    private final LimpiezaRepositorio limpiezaRepositorio;

    public LimpiezaDataSeeder(LimpiezaRepositorio limpiezaRepositorio) {
        this.limpiezaRepositorio = limpiezaRepositorio;
    }

    @Override
    public void run(String... args) {
        limpiezaRepositorio.findByReferencia("LIMP-101-001").ifPresentOrElse(
                r -> {},
                () -> {
                    Limpieza limpieza = Limpieza.builder()
                            .referencia("LIMP-101-001")
                            .habitacionId(obtenerHabitacionIdSiExiste(101L))
                            .fecha(LocalDate.now())
                            .tipo("General")
                            .estado("Limpia")
                            .build();
                    limpiezaRepositorio.save(limpieza);
                }
        );
    }

    private Long obtenerHabitacionIdSiExiste(Long habitacionNumero) {
        return habitacionNumero;
    }
}

