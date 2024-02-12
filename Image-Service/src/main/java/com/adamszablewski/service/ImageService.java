package com.adamszablewski.service;


import com.adamszablewski.feign.UniqueIDServiceClient;
import com.adamszablewski.model.*;

import com.adamszablewski.repository.PhotoRepository;
import com.adamszablewski.s3.S3buckets;
import com.adamszablewski.s3.S3service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ImageService {

    private final S3service s3service;
    private final S3buckets s3buckets;
    private final UniqueIDServiceClient uniqueIDServiceClient;
    private final PhotoRepository photoRepository;

    public String upploadImageToS3(MultipartFile file, String multimediaId) {

        if(multimediaId.length() == 0){
            throw new RuntimeException("Wrong imageID");
        }
        try {
            s3service.putObject(s3buckets.getCustomer(),
                    multimediaId,
                    file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return multimediaId;
    }

    @Transactional
    public void deleteUserData(Long userId) {
        //todo implement this
    }

    public String createPhotoResource(MultipartFile file, long userId) {
        Photo photo = createPhoto(userId);
        upploadImageToS3(file, photo.getMultimediaId());
        photo.setMultimediaId(photo.getMultimediaId());
        photoRepository.save(photo);
        return photo.getMultimediaId();
    }
    public String createPhotoResource(MultipartFile file, long userId, String multimediaId) {
        Photo photo = createPhoto(userId, multimediaId);
        upploadImageToS3(file, multimediaId);
        photo.setMultimediaId(multimediaId);
        photoRepository.save(photo);
        return multimediaId;
    }

    public Photo createPhoto(long userId){
        String multimediaId = uniqueIDServiceClient.getUniqueImageId();
        return Photo.builder()
                .userId(userId)
                .multimediaId(multimediaId)
                .localDateTime(LocalDateTime.now())
                .build();
    }
    public Photo createPhoto(long userId, String multimediaId){
        return Photo.builder()
                .userId(userId)
                .multimediaId(multimediaId)
                .localDateTime(LocalDateTime.now())
                .build();
    }



}
