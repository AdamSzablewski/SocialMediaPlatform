//package com.adamszablewski.kafka;
//
//import lombok.AllArgsConstructor;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//import static com.adamszablewski.kafka.KafkaConfig.USER_DELETED;
//
//@Component
//@AllArgsConstructor
//public class KafkaConsumer {
//
//    private Dao dao;
//    @KafkaListener(topics = USER_DELETED, groupId = "post-group")
//    public void consumeUserDeleted(Long userId){
//        dao.deleteUserData(userId);
//    }
//}
