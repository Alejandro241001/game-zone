package org.iesalixar.daw2.Alejandroangulomendez.game_zone.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("🎮 Game Zone API")
                        .version("1.0.0")
                        .description("Documentación de la API REST de Game Zone")
                        .contact(new Contact()
                                .name("Alejandro Ángulo")
                                .email("soporte@gamezone.com")));
    }
}
