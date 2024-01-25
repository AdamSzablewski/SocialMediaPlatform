//package com.adamszablewski.router;
//
//import com.adamszablewski.handler.SecurityHandler;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//@Configuration
//@AllArgsConstructor
//public class RouterConfig {
//    private final SecurityHandler handler;
//
//    @Bean
//    public RouterFunction<ServerResponse> routerFunction(){
//        return RouterFunctions.route()
//                .GET("/security/hash", handler::login)
//                .GET("/security/extract", handler::login)
//                .POST("/security/login", handler::login)
//                .build();
//
//    }
//}
