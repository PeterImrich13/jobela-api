package com.jobela.jobela_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI jobelaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Jobela API")
                        .description("Backend API for the Jobela job platform")
                        .version("v1"));
    }
}