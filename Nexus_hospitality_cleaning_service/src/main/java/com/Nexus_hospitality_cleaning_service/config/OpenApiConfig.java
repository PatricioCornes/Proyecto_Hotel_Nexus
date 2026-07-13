package com.Nexus_hospitality_cleaning_service.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration public class OpenApiConfig {
    @Bean OpenAPI cleaningApi() { return new OpenAPI().info(new Info().title("Nexus Cleaning API").version("1.0.0").description("Tareas de limpieza y asignación de personal")); }
}
