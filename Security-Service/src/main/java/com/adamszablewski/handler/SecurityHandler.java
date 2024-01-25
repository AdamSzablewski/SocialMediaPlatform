//package com.adamszablewski.handler;
//
//import com.adamszablewski.dto.LoginDto;
//import com.adamszablewski.exception.InvalidCredentialsException;
//import com.adamszablewski.feign.BookingServiceClient;
//import com.adamszablewski.feign.UserServiceClient;
//import com.adamszablewski.util.JwtUtil;
//import com.adamszablewski.util.TokenGenerator;
//import com.adamszablewski.util.UserValidator;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Service
//@AllArgsConstructor
//public class SecurityHandler {
//
//    private final PasswordEncoder passwordEncoder;
//    private final BookingServiceClient bookingServiceClient;
//    private final UserServiceClient userServiceClient;
//    private final JwtUtil jwtUtil;
//    private final UserValidator userValidator;
//    private final TokenGenerator tokenGenerator;
//    public Mono<ServerResponse> login(ServerRequest serverRequest) {
//
//        return serverRequest.bodyToMono(LoginDto.class)
//                .flatMap(loginDto ->
//                        userServiceClient.getHashedPassword(loginDto.getEmail())
//                                .flatMap(response -> {
//                                    if (!passwordEncoder.matches(loginDto.getPassword(), response)) {
//                                        return Mono.error(InvalidCredentialsException::new);
//                                    }
//                                    return generateToken(loginDto.getEmail())
//                                            .flatMap(token -> ServerResponse.ok().bodyValue(token));
//                                })
//                )
//
//                .onErrorResume(InvalidCredentialsException.class, ex ->
//                        ServerResponse.status(HttpStatus.UNAUTHORIZED).build()
//                );
//    }
////    public Mono<ServerResponse> validateToken(ServerRequest serverRequest){
//////        String token = serverRequest.queryParam("token").orElse(null);
//////        return jwtUtil.validateToken()
////        return serverRequest.queryParam("token")
////                .flatMap(token ->
////                    jwtUtil.validateToken(token)
////                            .flatMap(isValid -> {
////                                if (isValid) {
////                                    return ServerResponse.ok().build();
////                                } else {
////                                    return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
////                                }
////
////                }))
////                .switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).build());
////
////    }
////    public Mono<ServerResponse> findCustomer (ServerRequest request){
////        int customerId= Integer. valueOf( request.pathVariable ( name: "input")) ;
////// dao.getCustomerList().filter(c-›c.getId()==customerId).take(1).single();
////        Mono‹Customer > customerMono = dao.getCustomerList().filter(c -› c.getId() == customerId).next);
////        return ServerResponse.ok ().body (customerMono, Customer.class) ;
////    }
//
//
//    private Mono<String> generateToken(String email) {
//        return tokenGenerator.generateToken(userServiceClient.getUserIdFromUsername(email));
//    }
//
//}
