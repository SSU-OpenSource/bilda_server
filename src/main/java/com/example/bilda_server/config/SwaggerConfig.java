package com.example.bilda_server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "authorization";

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
            .components(new Components()
	.addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
	    .type(SecurityScheme.Type.HTTP)
	    .name(SECURITY_SCHEME_NAME)
	    .scheme("bearer")
	    .bearerFormat("JWT")))
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))

            .info(new Info()
	.title("빌다 프로젝트")
	.description("스프링, 안드로이드 기반의 프로젝트 입니다.")
	.version("1.0.0"));
    }
}
