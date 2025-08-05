package com.ltp.contacts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI openApi() {
        OpenAPI openAPI = new OpenAPI();

        Info apiInfo = new Info();
        apiInfo.setTitle("Contact API");
        apiInfo.setVersion("v1.0");
        apiInfo.setDescription("An API that can manage contacts.");

        openAPI.info(apiInfo);

        return openAPI;
    }
}
