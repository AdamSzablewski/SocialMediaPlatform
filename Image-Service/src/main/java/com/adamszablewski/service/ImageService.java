package com.adamszablewski.service;


import com.adamszablewski.exceptions.FileNotFoundException;
import com.adamszablewski.exceptions.NoSuchImageException;
import com.adamszablewski.exceptions.NotAuthorizedException;
import com.adamszablewski.feign.BookingClient;
import com.adamszablewski.model.*;
import com.adamszablewski.repository.*;
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

    private final ImageRepository imageRepository;
    private final ProfilePhotoRepository profilePhotoRepository;
    private final MessagePhotoRepository messagePhotoRepository;
    private final UniqueIdGenerator uniqueIdGenerator;
    private final UserValidator userValidator;
    private final BookingClient bookingClient;
    private final PostPhotoRepository postPhotoRepository;

    private ImageData uploadImage(MultipartFile file, String imageId) throws IOException {
        return imageRepository.save(ImageData.builder()
                .type(file.getContentType())
                .name(file.getOriginalFilename())
                .multimediaId(imageId)
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());

    }
    private ImageData uploadImage(byte[] imageData,String imageId) throws IOException {
        return imageRepository.save(ImageData.builder()
                .type("image/jpeg")
                .multimediaId(imageId)
                .imageData(ImageUtils.compressImage(imageData))
                .build());

    }
    @Transactional
    public byte[] getImageForUser(long id) {
        ProfilePhoto profilePhoto = profilePhotoRepository.findByUserId(id)
                .orElseThrow(FileNotFoundException::new);
        byte[] imageData = profilePhoto.getImage().getImageData();
        return ImageUtils.decompressImage(imageData);
    }
    @Transactional
    public void addUserImage(long id, MultipartFile file, String userEmail) throws IOException {

        if (!userValidator.isUser(id, userEmail)){
            throw new NotAuthorizedException();
        }
        profilePhotoRepository.deleteByUserId(id);
        String imageId = uniqueIdGenerator.generateUniqueImageId();
        ImageData image = uploadImage(file, imageId);
        ProfilePhoto profilePhoto = ProfilePhoto.builder()
                .userId(id)
                .image(image)
                .build();
        profilePhotoRepository.save(profilePhoto);
    }
    @Transactional
    public void deleteImagesForUser(long userId) {
        postPhotoRepository.deleteAllByUserId(userId);
        profilePhotoRepository.deleteByUserId(userId);
    }
    @Transactional
    public void deleteUserImage(long id, String userEmail) {
        if (!userValidator.isUser(id, userEmail)){
            throw new NotAuthorizedException();
        }
        profilePhotoRepository.deleteByUserId(id);

    }
    @Transactional
    public String addMessageImage(byte[] file, Set<Long> recipients) throws IOException {


        String imageId = uniqueIdGenerator.generateUniqueImageId();
        ImageData image = uploadImage(file, imageId);
        MessagePhoto messagePhoto = MessagePhoto.builder()
                .users(recipients)
                .multimediaId(imageId)
                .image(image)
                .build();
        messagePhotoRepository.save(messagePhoto);
        return imageId;
    }
    @Transactional
    public byte[] getImageForMessage(String imageId, long userId) {
        MessagePhoto messagePhoto = messagePhotoRepository.findByMultimediaId(imageId)
                .orElseThrow(FileNotFoundException::new);
        if (messagePhoto.getUsers().stream().noneMatch(id -> messagePhoto.getUsers().contains(userId))){
            throw new NotAuthorizedException();
        }
        return ImageUtils.decompressImage(messagePhoto.getImage());
    }

    @Transactional
    public String addPostImage(byte[] imageArray, long userId) throws IOException {
//        if(!userValidator.isOwner(facilityId, userEmail)){
//            throw new NotAuthorizedException();
//        }

        String imageId = uniqueIdGenerator.generateUniqueImageId();
        ImageData image = uploadImage(imageArray, imageId);
        PostPhoto postPhoto = PostPhoto.builder()
                .userId(userId)
                .image(image)
                .multimediaId(imageId)
                .build();

        postPhotoRepository.save(postPhoto);
        return imageId;
    }

    public void removePostImage(long postId) {
        postPhotoRepository.deleteByPostId(postId);
    }

    public byte[] getImageByImageId(String mltimediaId) {
        ImageData image = imageRepository.findByMultimediaId(mltimediaId)
                .orElseThrow(NoSuchImageException::new);
        return ImageUtils.decompressImage(image.getImageData());
    }

    public void delteImageWithMultimediaId(String multimediaId) {
        imageRepository.deleteByMultimediaId(multimediaId);
        messagePhotoRepository.deleteByMultimediaId(multimediaId);
        postPhotoRepository.deleteByMultimediaId(multimediaId);
        profilePhotoRepository.deleteByMultimediaId(multimediaId);

    }

    public long getOwnerForMultimediaId(String multimediaId) {
        ImageData imageData = imageRepository.findByMultimediaId(multimediaId)
                .orElseThrow(NoSuchImageException::new);
        return imageData.getUserId();
    }
}
