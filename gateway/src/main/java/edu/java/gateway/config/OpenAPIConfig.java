package edu.java.gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI myOpenApi() {
        Info info = new Info()
                .title("Hashtest service API")
                .version("1.0")
                .description("API сервиса для тестирования хэш-функций.");

        return new OpenAPI().info(info);
    }
}
