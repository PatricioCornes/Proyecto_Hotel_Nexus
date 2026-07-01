package com.Nexus_hospitality_payments;

import com.Nexus_hospitality_payments.entidad.Pago;
import com.Nexus_hospitality_payments.repositorio.PagoRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class PagoDataSeeder implements CommandLineRunner {

    private final PagoRepositorio pagoRepositorio;

    public PagoDataSeeder(PagoRepositorio pagoRepositorio) {
        this.pagoRepositorio = pagoRepositorio;
    }

    @Override
    public void run(String... args) {
        pagoRepositorio.findByReferencia("PAGO-101-001").ifPresentOrElse(
                r -> {},
                () -> {
                    Pago pago = Pago.builder()
                            .referencia("PAGO-101-001")
                            .monto(new BigDecimal("120.00"))
                            .fecha(LocalDate.now())
                            .metodo("Tarjeta")
                            .estado("Pagado")
                            .build();
                    pagoRepositorio.save(pago);
                }
        );
    }
}

