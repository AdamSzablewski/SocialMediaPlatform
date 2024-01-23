package com.adamszablewski;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class GatewayConfig {



    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()

                .route("user-service", r -> r
                        .path("/user/**")
                        .uri("lb://USER-SERVICE"))
                .route("security-service", r -> r
                        .path("/security/**")
                        .uri("lb://SECURITY-SERVICE"))
                .route("video-service", r -> r
                        .path("/video/**")
                        .uri("lb://VIDEO-SERVICE"))
                .route("image-service", r -> r
                        .path("/images/**")
                        .uri("lb://IMAGE-SERVICE"))
                .route("messaging-service", r -> r
                        .path("/conversations/**")
                        .or()
                        .path("/messages/**")
                        .uri("lb://MESSAGING"))
                .route("friend-service", r -> r
                        .path("/friends/**")
                        .uri("lb://FRIEND-SERVICE"))
                .route("post-service-command", r -> r
                        .path("/posts/write/**")
                        .uri("lb://POST-SERVICE-COMMAND"))
                .route("post-service-query", r -> r
                        .path("/posts/read/**")
                        .uri("lb://POST-SERVICE-QUERY"))
                .route("uniqueID-service", r -> r
                        .path("/uniqueID/**")
                        .uri("lb://UNIQUEID-SERVICE"))
                .route("eureka-status", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }

}
