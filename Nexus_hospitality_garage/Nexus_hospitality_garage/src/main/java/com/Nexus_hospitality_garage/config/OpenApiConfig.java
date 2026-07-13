package com.Nexus_hospitality_garage.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration public class OpenApiConfig {
    @Bean OpenAPI garageApi() { return new OpenAPI().info(new Info().title("Nexus Garage API").version("1.0.0").description("Plazas y usos facturables de estacionamiento")); }
}
