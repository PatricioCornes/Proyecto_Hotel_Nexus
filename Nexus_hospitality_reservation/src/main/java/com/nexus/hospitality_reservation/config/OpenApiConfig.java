package com.nexus.hospitality_reservation.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration public class OpenApiConfig {
    @Bean OpenAPI reservationApi() { return new OpenAPI().info(new Info().title("Nexus Reservation API").version("1.0.0").description("Reservas, confirmación, check-in, check-out y cancelación")); }
}
