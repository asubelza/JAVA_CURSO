package com.coderhouse.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("MangaStore API - Sistema de Gestión de Ventas")
                .version("1.0.0")
                .description("""
                    Esta API REST fue desarrollada como parte del proyecto final del curso de Java para CoderHouse. 
                    
                    El objetivo de esta aplicación es permitir la gestión integral de una tienda de mangas, 
                    incluyendo el manejo de clientes, productos (mangas), categorías, y el registro de compras. 
                    
                    A través de esta API es posible:
                    - Registrar y consultar clientes.
                    - Administrar mangas disponibles, incluyendo su categoría y precio.
                    - Realizar compras asociadas a un cliente, guardando el historial de adquisiciones.
                    - Consultar las relaciones entre clientes y mangas adquiridos.
                    
                    La API está diseñada en el IDE Eclipse utiliza Spring Boot, 
                    JPA/Hibernate y MySQL como base tecnológica.
                    
                    Documentación generada con Swagger.
                    """)
                .contact(new Contact()
                    .name("Alfonso Waldemar Subelza")
                    .email("subelzaalfonso@gmail.com")
                    .url("https://github.com/tuusuario"))  // Reemplazá si tenés un GitHub real
                .license(new License()
                    .name("Nombre de la licencia si hubiera")
                    .url("https://url de la licencia si hubiera")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:8081")
                    .description("Servidor Local de Desarrollo")
            ));
    }
}
