//package com.adamszablewski.rabbitMq;
//
//import lombok.AllArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class RabbitMqConsumer {
//
//
//
//
//
////    @RabbitListener(queues = RabbitMqConfig.SALES_ORDER_CONFIRMATION_QUEUE)
////    public void consume(SalesOrderConfirmation salesOrderConfirmation){
////        System.out.println("||| Message recieved |||||| "+ salesOrderConfirmation.toString());
////    }
////    @RabbitListener(queues = RabbitMqConfig.MESSAGE_MEASSAGE_QUEUE)
////    public void consume(MessageDto message){
////
////        System.out.println("||| Message Object recieved |||||| "+ message.toString());
////        messageService.addMessageToConversationFromMessageQueue(message);
////    }
////    @RabbitListener(queues = RabbitMqConfig.FRIEND_REQUEST_QUEUE)
////    public void consume(FriendRequest friendRequest){
////
////        System.out.println("||| Friend request recieved |||||| "+ friendRequest.toString());
////        messageService.addFriendRequestMessage(friendRequest);
////
////    }
////    @RabbitListener(queues = RabbitMqConfig.USER_DELETED_QUEUE)
////    public void consume(long userId){
////
////        System.out.println("||| Delete user : |||||| "+ userId);
////        conversationService.deleteConversation(userId);
////    }
//}
