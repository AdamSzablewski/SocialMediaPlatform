package com.adamszablewski.controller;

import com.adamszablewski.classes.Person;
import com.adamszablewski.dtos.PersonDto;
import com.adamszablewski.service.PersonService;
import com.adamszablewski.utils.ExceptionHandler;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class PersonController {
    private final PersonService personService;
    private final ExceptionHandler exceptionHandler;

    @GetMapping()
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<PersonDto> getPersonById(@RequestParam(name = "userId")long userId){
        return ResponseEntity.ok(personService.getPerson(userId));
    }

    @GetMapping("/email")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<PersonDto> getPersonByEmail(@RequestParam(name = "email")String email){
        return ResponseEntity.ok(personService.getPerson(email));
    }
    @GetMapping("/convert")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<Long> getUserIdForUsername(@RequestParam(name = "email")String email){
        return ResponseEntity.ok(personService.getUserIdForUsername(email));
    }
    @GetMapping("/username")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<String> getUsernameFromId(@RequestParam(name = "userId")long userId){
        return ResponseEntity.ok(personService.getUsernameFromId(userId));
    }
    @GetMapping("/password/hashed")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<String> getHashedPassword(@RequestParam("userEmail") String userEmail){
        return ResponseEntity.ok(personService.getHashedPassword(userEmail));
    }
    @PostMapping()
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<String> createUser(@RequestBody Person person){
        personService.createUser(person);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "userServiceRateLimiter")
    public ResponseEntity<String> deleteUser(@RequestHeader("userEmail") String userEmail){
        personService.deleteUser(userEmail);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> fallBackMethod(Throwable throwable){
        return exceptionHandler.handleException(throwable);
    }
}
