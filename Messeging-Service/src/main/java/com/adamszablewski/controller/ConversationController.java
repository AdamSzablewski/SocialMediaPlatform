package com.adamszablewski.controller;



import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.model.Conversation;
import com.adamszablewski.repository.ConversationRepository;
import com.adamszablewski.service.ConversationService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationService conversationService;
    private final ConversationRepository conversationRepository;

    @GetMapping("/user/{id}")
    @CircuitBreaker(name = "messagingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "messagingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Conversation>> getCoversationsForUser(@PathVariable long id,
                                                                           @RequestHeader("userEmail") String userEmail){
        RestResponseDTO<Conversation> responseDTO = RestResponseDTO.<Conversation>builder()
                .values(conversationService.getCoversationsForUser(id, userEmail))
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/user/{id}")
    @CircuitBreaker(name = "messagingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "messagingServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<Conversation>> deleteConversation(@PathVariable long id){

        conversationService.deleteConversation(id);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    public  ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
   
}
