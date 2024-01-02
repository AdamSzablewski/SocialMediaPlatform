//package com.adamszablewski.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class NativeLibraryConfig {
//
//    @Bean
//    public NativeLibraryLoader nativeLibraryLoader() {
//        return new NativeLibraryLoader();
//    }
//
//    public static class NativeLibraryLoader {
//
//        public NativeLibraryLoader() {
//            loadNativeLibraries();
//        }
//
//        private void loadNativeLibraries() {
//            try {
//                System.loadLibrary("opencv_java"); // Load OpenCV library
//                System.loadLibrary("ffmpeg");      // Load FFmpeg library
//            } catch (UnsatisfiedLinkError e) {
//                // Handle the exception or log the error
//                throw new RuntimeException("Failed to load native libraries", e);
//            }
//        }
//    }
//}
