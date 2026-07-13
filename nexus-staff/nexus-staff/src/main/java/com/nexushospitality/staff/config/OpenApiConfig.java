package com.nexushospitality.staff.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration public class OpenApiConfig {
    @Bean OpenAPI staffApi() { return new OpenAPI().info(new Info().title("Nexus Staff API").version("1.0.0").description("Personal, disponibilidad y asignaciones")); }
}
