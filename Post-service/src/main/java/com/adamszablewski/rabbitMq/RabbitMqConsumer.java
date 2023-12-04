package com.adamszablewski.rabbitMq;//package com.adamszablewski.rabbitMq;
//
//
//
//import com.adamszablewski.service.UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class RabbitMqConsumer {
//
//    private UserService userService;
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
//        System.out.println("||| Message Object recieved |||||| "+ userId);
//        messageService.addMessageToConversation(message);
//    }
//}
