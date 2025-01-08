package com.kibernumacademy.apigateway.beans;

import java.util.Set;

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
            .filters(filter -> {
                filter.circuitBreaker(config -> config
                    .setName("gateway-circuit-breaker")
                    .setStatusCodes(Set.of("500", "400", "408"))
                    .setFallbackUri("forward:/company-fallback/*"));
                return filter;
            })
            .uri("lb://company-service"))
        .route("report-msv", route -> route
            .path("/report/**")
            .uri("lb://report-msv"))
        .route("company-fallback", route -> route
            .path("/company-fallback/**")
            .uri("lb://company-fallback"))
        .build();
  
        /**
         * El código de estado HTTP 408, también conocido como "Request Timeout" o "Tiempo de espera agotado en la solicitud", indica que el servidor web no recibió una solicitud completa del cliente dentro del tiempo esperado
         */
  }


  
  @Bean
  @Profile(value="oauth2")
  public RouteLocator routeLocatorEurekaOAuth2CircuitBreaker(RouteLocatorBuilder builder) {
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
            .filters(filter -> {
                filter.circuitBreaker(config -> config
                    .setName("gateway-circuit-breaker")
                    .setStatusCodes(Set.of("500", "400", "408"))
                    .setFallbackUri("forward:/company-fallback/*"));
                return filter;
            })
            .uri("lb://company-service"))
        .route("report-msv", route -> route
            .path("/report/**")
            .uri("lb://report-msv"))
        .route("company-fallback", route -> route
            .path("/company-fallback/**")
            .uri("lb://company-fallback"))
        .route("auth-server", route -> route
            .path("/auth/**")
            .uri("lb://auth-server"))
        .build();
  
        /**
         * El código de estado HTTP 408, también conocido como "Request Timeout" o "Tiempo de espera agotado en la solicitud", indica que el servidor web no recibió una solicitud completa del cliente dentro del tiempo esperado
         */
  }
}
