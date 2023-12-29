package com.adamszablewski.controller;



import com.adamszablewski.annotations.SecureContentResource;
import com.adamszablewski.annotations.SecureUserIdResource;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.model.Conversation;
import com.adamszablewski.repository.ConversationRepository;
import com.adamszablewski.service.ConversationService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationService conversationService;
    private final ConversationRepository conversationRepository;

    @GetMapping()
    @SecureUserIdResource
    @CircuitBreaker(name = "messagingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "messagingServiceRateLimiter")
    public ResponseEntity<List<Conversation>> getCoversationsForUser(@RequestParam("userId") long userId,
                                                                    HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(conversationService.getCoversationsForUser(userId));
    }

    @DeleteMapping()
    @SecureContentResource
    @CircuitBreaker(name = "messagingServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "messagingServiceRateLimiter")
    public ResponseEntity<String> deleteConversation(@RequestParam("conversationId") long conversationId,
                                                     HttpServletRequest httpServletRequest){

        conversationService.deleteConversation(conversationId);
        return ResponseEntity.ok().build();
    }
    public  ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
   
}
