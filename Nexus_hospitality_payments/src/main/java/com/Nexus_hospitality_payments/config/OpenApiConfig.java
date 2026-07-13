package com.Nexus_hospitality_payments.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration public class OpenApiConfig {
    @Bean OpenAPI paymentsApi() { return new OpenAPI().info(new Info().title("Nexus Payments API").version("1.0.0").description("Pagos, reembolsos, cargos y facturación final")); }
}
