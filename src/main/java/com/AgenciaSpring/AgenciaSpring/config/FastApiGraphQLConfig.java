package com.AgenciaSpring.AgenciaSpring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FastApiGraphQLConfig {

    @Bean
    public HttpGraphQlClient fastApiGraphQlClient() {
        // Configuramos la URL del microservicio de Python (FastAPI)
        // Puedes cambiar esta URL si FastAPI corre en otro puerto u host
        WebClient webClient = WebClient.builder()
                .baseUrl("https://fastapi-production-f563.up.railway.app/graphql")
                .build();

        return HttpGraphQlClient.builder(webClient).build();
    }
}
