package com.adamszablewski.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.security.Key;

@Component
@AllArgsConstructor
public class JwtUtil {
    private final WebClient webClient;

    public static final String JWT_SECRET = "462D4A614E635266556A586E3272357538782F413F4428472B4B6250655368566B5970337336763979244226452948404D635166546A576E5A7134743777217A";
    @Autowired
    public JwtUtil(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("lb://SECURITY").build();
    }


    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    public Mono<Boolean> validateToken(String token) {


        return webClient
                .get()
                .uri("/validateToken?token=" + token)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorReturn(false);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
