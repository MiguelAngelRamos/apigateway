package com.kibernumacademy.apigateway.beans;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class GatewayBeans {

  @Bean
  @Profile(value = "eureka-on")
  public RouteLocator routeLocatorEurekaOn(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("user-service", route -> route
            .path("/users/**") // Ruta para el servicio de usuarios
            .uri("lb://user-service")) // URI con balanceador de carga
        .route("order-service", route -> route
            .path("/orders/**") // Ruta para el servicio de pedidos
            .uri("lb://order-service")) // URI con balanceador de carga
        .route("product-service", route -> route
            .path("/products/**") // Ruta para el servicio de productos
            .uri("lb://product-service")) // URI con balanceador de carga
        .route("pricing-service", route -> route
            .path("/prices/**") // Ruta para el servicio de precios
            .uri("lb://pricing-service")) // URI con balanceador de carga
        .route("company-service", route -> route
            .path("/company/**")
            .uri("lb://company-service"))
        .build();
  }

  @Bean
  @Profile(value="eureka-on-circuit-breaker")
  public RouteLocator routeLocatorEurekaOnCircuitBreaker(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("user-service", route -> route
            .path("/users/**") // Ruta para el servicio de usuarios
            .uri("lb://user-service")) // URI con balanceador de carga
        .route("order-service", route -> route
            .path("/orders/**") // Ruta para el servicio de pedidos
            .uri("lb://order-service")) // URI con balanceador de carga
        .route("product-service", route -> route
            .path("/products/**") // Ruta para el servicio de productos
            .uri("lb://product-service")) // URI con balanceador de carga
        .route("pricing-service", route -> route
            .path("/prices/**") // Ruta para el servicio de precios
            .uri("lb://pricing-service")) // URI con balanceador de carga
        .route("company-service", route -> route
            .path("/company/**")
            .uri("lb://company-service"))
            .route("report-msv", route -> route
            .path("/report/**")
            .uri("lb://report-msv"))
        .route("company-fallback", route -> route
            .path("/company-fallback/**")
            .uri("lb://company-fallback"))
        .build();
  
  }
}
