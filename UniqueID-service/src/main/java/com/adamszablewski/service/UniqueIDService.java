package com.adamszablewski.service;

import com.adamszablewski.util.UniqueIdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UniqueIDService {

    private final UniqueIdGenerator uniqueIdGenerator;

    public String getUniqueVideoID() {
        return uniqueIdGenerator.generateUniqueVideoId();
    }
    public String getUniqueImageID() {
        return uniqueIdGenerator.generateUniqueImageId();
    }
}
