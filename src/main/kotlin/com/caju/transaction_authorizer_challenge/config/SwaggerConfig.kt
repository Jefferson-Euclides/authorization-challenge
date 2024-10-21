package com.caju.transaction_authorizer_challenge.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .servers(listOf(Server().url("localhost:8080/api")))
            .info(
                Info()
                    .title("Caju Transaction Authorizer")
                    .version("1.0")
                    .description("API documentation")
            )
    }
}