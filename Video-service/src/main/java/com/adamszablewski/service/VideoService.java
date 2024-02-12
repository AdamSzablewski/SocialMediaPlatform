package com.adamszablewski.service;

import com.adamszablewski.Model.Video;
import com.adamszablewski.repository.VideoRepository;
import com.adamszablewski.s3.S3service;
import com.adamszablewski.util.UniqueIdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@AllArgsConstructor
public class VideoService {


    private VideoRepository videoRepository;

    private UniqueIdGenerator uniqueIdGenerator;
    private final S3service s3service;




    public void deleteUserData(Long userId) {
        videoRepository.deleteAllByUserId(userId);
    }

    public void deleteVideo(String multimediaId) {
        videoRepository.deleteByMultimediaId(multimediaId);
    }
    @Transactional
    public void createVideoEntity(MultipartFile file, String multimediaId, long userId) {
        Video video = Video.builder()
                .multimediaId(multimediaId)
                .contentType(file.getContentType())
                .userId(userId)
                .build();
        videoRepository.save(video);
        s3service.upploadVideoToS3(file, multimediaId);
    }
}
