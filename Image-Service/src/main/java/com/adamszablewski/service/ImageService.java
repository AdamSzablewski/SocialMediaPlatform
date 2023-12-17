package com.adamszablewski.service;


import com.adamszablewski.exceptions.FileNotFoundException;
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
    private final FacilityPhotoRepository facilityPhotoRepository;
    private final MessagePhotoRepository messagePhotoRepository;
    private final UniqueIdGenerator uniqueIdGenerator;
    private final UserValidator userValidator;
    private final BookingClient bookingClient;
    private final PortfolioRepository portfolioRepository;

    private ImageData uploadImage(MultipartFile file) throws IOException {
        return imageRepository.save(ImageData.builder()
                .type(file.getContentType())
                .name(file.getOriginalFilename())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());

    }
    private ImageData uploadImage(byte[] imageData) throws IOException {
        return imageRepository.save(ImageData.builder()
                .type("image/jpeg")
                .imageData(ImageUtils.compressImage(imageData))
                .build());

    }

    @Transactional
    public void addFacilityImage(long id, MultipartFile file, String userEmail, long userId) throws IOException {

        if (!userValidator.isOwner(id, userEmail)){
            throw new NotAuthorizedException();
        }
        facilityPhotoRepository.deleteAllByFacilityId(id);
        ImageData image = uploadImage(file);
        FacilityPhoto facilityPhoto = FacilityPhoto.builder()
                .facilityId(id)
                .userId(userId)
                .image(image)
                .build();
        facilityPhotoRepository.save(facilityPhoto);
    }
    @Transactional
    public byte[] getImageForFacility(long id) {
         FacilityPhoto facilityPhoto = facilityPhotoRepository.findByFacilityId(id)
                 .orElseThrow(FileNotFoundException::new);
         byte[] imageData = facilityPhoto.getImage().getImageData();
         return ImageUtils.decompressImage(imageData);
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
        ImageData image = uploadImage(file);
        ProfilePhoto profilePhoto = ProfilePhoto.builder()
                .userId(id)
                .image(image)
                .build();
        profilePhotoRepository.save(profilePhoto);
    }
    @Transactional
    public void deleteImagesForUser(long userId) {
        facilityPhotoRepository.deleteAllByUserId(userId);
        portfolioRepository.deleteAllByUserId(userId);
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
    public void deleteFacilityImage(long facilityID, String userEmail) {
        if (!userValidator.isOwner(facilityID, userEmail)){
            throw new NotAuthorizedException();
        }
        facilityPhotoRepository.deleteByFacilityId(facilityID);
    }
    @Transactional
    public void addMessageImage(byte[] file, String imageId, Set<Long> recipients) throws IOException {

        ImageData image = uploadImage(file);
        MessagePhoto messagePhoto = MessagePhoto.builder()
                .users(recipients)
                .imageId(imageId)
                .image(image)
                .build();
        messagePhotoRepository.save(messagePhoto);
    }
    @Transactional
    public byte[] getImageForMessage(String imageId, long userId) {
        MessagePhoto messagePhoto = messagePhotoRepository.findByImageId(imageId)
                .orElseThrow(FileNotFoundException::new);
        if (messagePhoto.getUsers().stream().noneMatch(id -> messagePhoto.getUsers().contains(userId))){
            throw new NotAuthorizedException();
        }
        return ImageUtils.decompressImage(messagePhoto.getImage());
    }
    @Transactional
    public void addPortfolioImage(MultipartFile file, long facilityId, String userEmail, long userId) throws IOException {
        if(!userValidator.isOwner(facilityId, userEmail)){
            throw new NotAuthorizedException();
        }
        ImageData image = uploadImage(file);
        String imageId = uniqueIdGenerator.generateUniqueImageId();
        PortfolioPhoto portfolioPhoto = PortfolioPhoto.builder()
                .userId(userId)
                .facility(facilityId)
                .imageId(imageId)
                .imageData(image)
                .build();
        portfolioRepository.save(portfolioPhoto);
        bookingClient.addPortfolioImage(imageId, facilityId);
    }
}
