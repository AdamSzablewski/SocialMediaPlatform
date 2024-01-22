package com.adamszablewski.controller;

import com.adamszablewski.service.UniqueIDService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/uniqueID")
public class UniqueIDController {

    private final UniqueIDService uniqueIDService;


    @GetMapping("/video")
    public ResponseEntity<String> getUniqueVideoID(){
        return ResponseEntity.ok(uniqueIDService.getUniqueVideoID());
    }
    @GetMapping("/image")
    public ResponseEntity<String> getUniqueImageID(){
        return ResponseEntity.ok(uniqueIDService.getUniqueImageID());
    }
}
