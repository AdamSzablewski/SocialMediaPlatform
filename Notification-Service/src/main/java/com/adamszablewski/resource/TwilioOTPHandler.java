package com.adamszablewski.resource;

import com.adamszablewski.dto.PasswordResetRequestDto;
import com.adamszablewski.service.TwilioOTPService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class TwilioOTPHandler {

    private TwilioOTPService twilioOTPService;

    public Mono<ServerResponse> sendOTP(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(PasswordResetRequestDto.class)
                .flatMap(dto -> twilioOTPService.sendOTPForPasswordReset(dto))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK)
                        .body(BodyInserters.fromValue(dto)));
    }
    public Mono<ServerResponse> validateOTP(ServerRequest serverRequest){
        return serverRequest.bodyToMono(PasswordResetRequestDto.class)
                .flatMap(dto -> twilioOTPService.validateOTP(dto.getOneTimePassword(), dto.getUsername()))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK)
                        .body(BodyInserters.fromValue(dto)));
    }
}
