package com.kibernumacademy.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.kibernumacademy.apigateway.dto.TokenDto;

import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GatewayFilter {

  // Validar el token
  private static final String AUTH_VALIDATE_URI = "http://localhost:4060/auth/jwt";
  // Nombre del encabezado que se enviara en la solicitud
  private static final String ACCESS_TOKEN_HEADER_NAME = "accessToken";

  private final WebClient webclient;


  public AuthFilter() {
    this.webclient = WebClient.builder().build();
  }
  
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    // Verificar si el encabezado de autorizacion esta presente en el solicitud
    if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
      return onError(exchange);
    }

    // Obtener la lista de valores del encabezado de autorización
    final var authValues = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
    // Verificar si la lista es nula o esta vacia 
    if(authValues == null || authValues.isEmpty()) {
      return onError(exchange);
    }

    //*

    /* List<String> values 
    * values.add("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb2ZpYSIsImlhdCI6MTczNjM0NDgyNywiZXhwIjoxNzM2NDMxMjI3fQ.z1W1xGCoXnRJ32y1ABUYwoUQwHz-e_CJ6CuYA7Ixhvw") 
    
    
    
    */
    // Obtener el primer valor del encabezado de autorizacion
    final var tokenHeader = authValues.get(0);
    final var chunks = tokenHeader.split(" ");
    // * chunks = ["Bearer","eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb2ZpYSIsImlhdCI6MTczNjM0NDgyNywiZXhwIjoxNzM2NDMxMjI3fQ.z1W1xGCoXnRJ32y1ABUYwoUQwHz-e_CJ6CuYA7Ixhvw"]
    if(chunks.length !=2 || !chunks[0].equals("Bearer")) {
      return onError(exchange);
    }

    // Obtener el token
    final var token = chunks[1];

    // Realizar la solicitud POST para validar el token 
    return this.webclient.post()
          .uri(AUTH_VALIDATE_URI)
          .header(ACCESS_TOKEN_HEADER_NAME, token)
          .retrieve()
          .bodyToMono(TokenDto.class)
          .map(response -> exchange)
          .flatMap(exchangeObj -> chain.filter(exchangeObj));
          //.flatMap(chain::filter);

       
  }


  // Método auxiliar para manejar los errores de autenticación
  private Mono<Void> onError(ServerWebExchange exchange) {
    final var response = exchange.getResponse();
    response.setStatusCode(HttpStatus.BAD_REQUEST);
    return response.setComplete();
  }



}
