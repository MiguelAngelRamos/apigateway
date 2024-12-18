package com.kibernumacademy.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

	// @Bean
	// public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
	// 	return builder.routes()
	// 		.route("user-service", r -> r.path("/users/**")
	// 		.uri("lb://user-service"))
	// 		.route("order-service", r -> r.path("/orders/**")
	// 		.uri("lb://order-service"))
	// 		.build();
	// }

}
