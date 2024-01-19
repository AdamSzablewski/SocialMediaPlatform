package com.adamszablewski.service;

import com.adamszablewski.Model.Video;
import com.adamszablewski.repository.VideoRepository;
import com.adamszablewski.util.UniqueIdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@AllArgsConstructor
public class VideoService {


    private VideoRepository videoRepository;

    private UniqueIdGenerator uniqueIdGenerator;


    public void uploadVideo(MultipartFile file, String name, String contentType) throws IOException {

        Video video = new Video();
        video.setName(name);
        video.setContentType(contentType);
        //video.setVideoData(compressVideo(file.getBytes()));
        video.setVideoData(file.getBytes());

        saveVideo(video);

    }
    public String uploadVideo(MultipartFile file, long userId) throws IOException {
        String multimediaId = uniqueIdGenerator.generateUniqueId();
        Video video = new Video();
        video.setContentType(file.getContentType());
        video.setMultimediaId(multimediaId);
        //video.setVideoData(compressVideo(file.getBytes()));
        video.setVideoData(file.getBytes());

        saveVideo(video);
        return multimediaId;

    }
    public String uploadVideo(byte[] videoData, String contentType, long userId) {
        String multimediaId = uniqueIdGenerator.generateUniqueId();
        Video video = Video.builder()
                .videoData(videoData)
                .contentType(contentType)
                .userId(userId)
                .multimediaId(multimediaId)
                .build();
        saveVideo(video);

        return multimediaId;
    }

//    private byte[] compressVideo(byte[] originalVideoData) throws FrameGrabber.Exception, FrameRecorder.Exception {
//        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(new ByteArrayInputStream(originalVideoData));
//        grabber.start();
//
//        ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();
//        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(compressedStream, grabber.getImageWidth(), grabber.getImageHeight());
//        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
//        recorder.start();
//
//        Frame frame;
//        while ((frame = grabber.grab()) != null) {
//            recorder.record(frame);
//        }
//
//        grabber.stop();
//        recorder.stop();
//
//        return compressedStream.toByteArray();
//    }

    public Video saveVideo(Video video) {
        return videoRepository.save(video);
    }

    public Video getVideoById(Long videoId) throws IOException {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(EntityNotFoundException::new);
        //video.setVideoData(decompressVideo(video.getVideoData()));
        return video;
    }

    public static byte[] decompressVideo(byte[] compressedVideoData) {
        // Create a ByteArrayInputStream from the compressed video data
        try (InputStream inputStream = new ByteArrayInputStream(compressedVideoData)) {
            // Create an FFmpegFrameGrabber to read the compressed video
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
            grabber.start();

            // Create a ByteArrayOutputStream to store the decompressed video data
            ByteArrayOutputStream decompressedStream = new ByteArrayOutputStream();

            // Create an FFmpegFrameRecorder to write the decompressed video
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(decompressedStream, grabber.getImageWidth(), grabber.getImageHeight());
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_RAWVIDEO); // Set video codec to raw video
            recorder.start();

            // Read and decompress frames
            Frame frame;
            while ((frame = grabber.grab()) != null) {
                recorder.record(frame);
            }

            grabber.stop();
            recorder.stop();

            // Return the decompressed video data as a byte array
            return decompressedStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    public Mono<Resource> retrieveContent(Long videoId) {
        return Mono.defer(() -> {
            try {
                Video videoEntity = getVideoById(videoId);
                InputStream videoInputStream = new ByteArrayInputStream(videoEntity.getVideoData());
                ByteArrayResource resource = new ByteArrayResource(videoInputStream.readAllBytes());
                return Mono.just(resource);
            } catch (IOException e) {
                return Mono.error(new RuntimeException(e));
            }
        });
    }

    public void deleteUserData(Long userId) {
        videoRepository.deleteAllByUserId(userId);
    }
}
