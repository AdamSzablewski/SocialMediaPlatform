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
                        .path("/appointments/**")
                        .or()
                        .path("/facilities/**")
                        .or()
                        .path("/services/**")
                        .or()
                        .path("/timeslots/**")
                        .or()
                        .uri("lb://USER-SERVICE"))
                .route("user-service", r -> r
                        .path("/users/**")
                        .uri("lb://USER-SERVICE"))
                .route("security-service", r -> r
                        .path("/security/**")
                        .uri("lb://SECURITY-SERVICE"))
                .route("image-service", r -> r
                        .path("/images/**")
                        .uri("lb://IMAGE-SERVICE"))
                .route("messaging-service", r -> r
                        .path("/conversations/**")
                        .or()
                        .path("/messages/**")
                        .uri("lb://MESSAGING"))

                .route("eureka-status", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }

}
