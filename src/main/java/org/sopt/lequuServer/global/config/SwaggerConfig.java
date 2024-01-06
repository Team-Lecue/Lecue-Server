package org.sopt.lequuServer.global.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecuritySchemes({
        @SecurityScheme(
                name = "Refresh",
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER
        ),
        @SecurityScheme(
                name = "JWT Auth",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                scheme = "bearer"
        )
})
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Le(cue) Project")
                .description("Le(cue) Project API Document")
                .version("1.0.0");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
