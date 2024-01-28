package com.adamszablewski.controller;


import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.service.SecurityService;
import com.adamszablewski.util.TokenGenerator;
import com.adamszablewski.dto.LoginDto;
import com.adamszablewski.util.UserValidator;
import lombok.AllArgsConstructor;
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
        String token = securityService.generateTokenFromEmail(user.getEmail());
        return ResponseEntity.ok(token);
    }
    @GetMapping("/reset-password")
    public ResponseEntity<String> getOTP(@RequestParam("phoneNumber") String phoneNumber,
                                         @RequestParam("userId") long userId){
        securityService.sendOTP(phoneNumber, userId);
        return ResponseEntity.ok("One time password sent");
    }
    @GetMapping("/hash")
    public ResponseEntity<String> hashPassword(@RequestParam(name = "password") String password){

        return ResponseEntity.ok(securityService.hashPassword(password));
    }
    @GetMapping("/extract")
    public ResponseEntity<Long> extractUserIdFromToken(@RequestParam(name = "token") String token){

        return ResponseEntity.ok(securityService.extractUserIdFromToken(token));
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
