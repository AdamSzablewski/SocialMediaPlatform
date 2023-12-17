package com.adamszablewski.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UniqueIdGenerator {
    public String generateUniqueId() {

        UUID uuid = UUID.randomUUID();
        long timestamp = System.currentTimeMillis();
        return uuid.toString() + "-" + timestamp;
    }


    public String generateUniqueImageId() {
        UUID uuid = UUID.randomUUID();
        long timestamp = System.currentTimeMillis();
        return "IMAGE"+uuid.toString() + "-" + timestamp;
    }
}