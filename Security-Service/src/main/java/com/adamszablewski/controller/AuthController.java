package com.adamszablewski.controller;


import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.service.SecurityService;
import com.adamszablewski.util.TokenGenerator;
import com.adamszablewski.dto.LoginDto;
import com.adamszablewski.dto.RegisterDto;
import com.adamszablewski.util.UserValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
@Service
@AllArgsConstructor
public class AuthController {


    private PasswordEncoder passwordEncoder;

    private TokenGenerator tokenGenerator;
    private SecurityService securityService;
    private final UserValidator userValidator;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto user){

        securityService.validateUser(user);
        String token = tokenGenerator.generateToken(user.getEmail());
        return ResponseEntity.ok(token);
    }
    @GetMapping("/hash")
    public ResponseEntity<String> hashPassword(@RequestParam(name = "password") String password){

        return ResponseEntity.ok(securityService.hashPassword(password));
    }

    @GetMapping("/validate/owner/facilityID/{facilityId}/user/{userMail}")
    public ResponseEntity<RestResponseDTO<Boolean>> validateOwner(@PathVariable Long facilityId,@PathVariable String userMail){
        RestResponseDTO<Boolean> response = RestResponseDTO.<Boolean>builder()
                .value(securityService.validateOwner(facilityId, userMail))
                .build();

        return ResponseEntity.ok(response);

    }
    @GetMapping("/validate/employee/facilityID/{facilityId}/user/{userMail}")
    public ResponseEntity<RestResponseDTO<Boolean>> validateEmployee(@PathVariable Long facilityId,@PathVariable String userMail){
        RestResponseDTO<Boolean> response = RestResponseDTO.<Boolean>builder()
                .value(securityService.validateEmployee(facilityId, userMail))
                .build();

        return ResponseEntity.ok(response);

    }
    @GetMapping("/validate/user/{userId}/{userMail}")
    public ResponseEntity<RestResponseDTO<Boolean>> validateUser(@PathVariable Long userId,@PathVariable String userMail){
        RestResponseDTO<Boolean> response = RestResponseDTO.<Boolean>builder()
                .value(securityService.validateUserEmail(userId, userMail))
                .build();

        return ResponseEntity.ok(response);

    }
    
    @GetMapping("/validate/token")
    public ResponseEntity<RestResponseDTO<Boolean>> validateToken(@RequestParam("token") String token){

        String errorMessage = "";
        boolean validated = false;
        try {
            validated = securityService.validateToken(token);
        }catch (Exception e){
            errorMessage = e.getMessage();
        }
        RestResponseDTO<Boolean> response = RestResponseDTO.<Boolean>builder()
                .value(validated)
                .error(errorMessage)
                .build();

        return ResponseEntity.ok(response);
    }


}
