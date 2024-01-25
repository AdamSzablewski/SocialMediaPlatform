//package com.adamszablewski.config;
//
//import com.adamszablewski.handler.FeedHandler;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//@AllArgsConstructor
//@Configuration
//public class RouterConfig {
//    private final FeedHandler handler;
//
//    @Bean
//    public RouterFunction<ServerResponse> routerFunction(){
//        return RouterFunctions.route()
//                .GET("/posts/read/feed", handler::login)
//                .GET("/security/extract", handler::login)
//                .POST("/security/login", handler::login)
//                .build();
//
//    }
//}
