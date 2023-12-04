package com.adamszablewski;

import com.adamszablewski.dto.LoginDto;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.feign.UserServiceClient;
import com.adamszablewski.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Configuration
public class AuthenticationFilter {

    private final JwtUtil jwtUtil;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter customGlobalFilter(AuthenticationFilter authenticationFilter) {
        return (exchange, chain) -> {
            final ServerHttpRequest originalRequest = exchange.getRequest();
            final ServerHttpRequest modifiedRequest; // Declare it here

            if (isRegisterDtoRequest(originalRequest) || isLoginRequest(originalRequest)) {
                return chain.filter(exchange);
            }
            if (!originalRequest.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("No token");
            }

            String tokenCandidate = originalRequest.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if (tokenCandidate != null && tokenCandidate.startsWith("Bearer ")) {
                tokenCandidate = tokenCandidate.substring(7);
            }
            final String token = tokenCandidate;

            modifiedRequest = originalRequest.mutate()
                    .header("userEmail", jwtUtil.getUsernameFromJWT(token))
                    .build();

            return jwtUtil.validateToken(token)
                    .flatMap(isValidated -> {
                        if (!isValidated) {
                            return Mono.error(new RuntimeException("Not Authorized"));
                        }
                        return chain.filter(exchange.mutate().request(modifiedRequest).build());
                    })
                    .onErrorResume(error -> {
                        return chain.filter(exchange);
                    });
        };
    }


    private boolean isLoginRequest(ServerHttpRequest request) {
        String path = request.getPath().toString();
        return path.contains("security/login");
    }

    private boolean isRegisterDtoRequest(ServerHttpRequest request) {
        String path = request.getPath().toString();
        return path.contains("/register");
    }
}
