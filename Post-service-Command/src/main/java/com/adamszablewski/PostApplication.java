package com.adamszablewski;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableAsync
public class PostApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
    }
}