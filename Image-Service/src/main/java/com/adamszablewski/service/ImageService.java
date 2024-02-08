package com.adamszablewski.service;


import com.adamszablewski.exceptions.FileNotFoundException;
import com.adamszablewski.exceptions.NoSuchImageException;
import com.adamszablewski.exceptions.NotAuthorizedException;
import com.adamszablewski.feign.BookingClient;
import com.adamszablewski.feign.UniqueIDServiceClient;
import com.adamszablewski.model.*;

import com.adamszablewski.s3.S3buckets;
import com.adamszablewski.s3.S3service;
import com.adamszablewski.util.ImageUtils;
import com.adamszablewski.util.UniqueIdGenerator;
import com.adamszablewski.util.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Service
@AllArgsConstructor
public class ImageService {

    private final S3service s3service;
    private final S3buckets s3buckets;
    private final UniqueIDServiceClient uniqueIDServiceClient;

    public String upploadImageToS3(MultipartFile file) {

        String imageId = uniqueIDServiceClient.getUniqueImageId();
        if(imageId.length() == 0){
            throw new RuntimeException("Wrong imageID");
        }
        try {
            s3service.putObject(s3buckets.getCustomer(),
                    imageId,
                    file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageId;
    }
    public byte[] getImageByImageIdS3(String imageId) {
        return s3service.getObject(s3buckets.getCustomer(), imageId);
    }

    @Transactional
    public void deleteUserData(Long userId) {
        //todo implement this
    }

}
