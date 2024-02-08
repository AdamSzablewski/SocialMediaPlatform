//package com.adamszablewski.rabbitMq;
//
//
//
//import com.adamszablewski.service.ImageService;
//import lombok.AllArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class RabbitMqConsumer {
//
//    private ImageService imageService;
//
//
//
////    @RabbitListener(queues = RabbitMqConfig.SALES_ORDER_CONFIRMATION_QUEUE)
////    public void consume(SalesOrderConfirmation salesOrderConfirmation){
////        System.out.println("||| Message recieved |||||| "+ salesOrderConfirmation.toString());
////    }
//    @RabbitListener(queues = RabbitMqConfig.USER_DELETED_QUEUE)
//    public void consume(long userId){
//
//        System.out.println("||| Delete user : |||||| "+ userId);
//        imageService.deleteImagesForUser(userId);
//    }
//    @RabbitListener(queues = RabbitMqConfig.IMAGE_DELETED_QUEUE)
//    public void consume(String multimediaId){
//
//        System.out.println("||| Delete image : |||||| "+ multimediaId);
//        imageService.delteImageWithMultimediaId(multimediaId);
//
//    }
//}
