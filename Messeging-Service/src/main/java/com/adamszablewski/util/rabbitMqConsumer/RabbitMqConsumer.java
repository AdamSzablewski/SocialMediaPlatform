package com.adamszablewski.util.rabbitMqConsumer;


import com.adamszablewski.dto.MessageDto;
import com.adamszablewski.model.Message;
import com.adamszablewski.model.MessageDTO;
import com.adamszablewski.service.ConversationService;
import com.adamszablewski.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.adamszablewski.util.rabbitMqConsumer.RabbitMqConfig.USER_DELETED_QUEUE;

@Service
@AllArgsConstructor
public class RabbitMqConsumer {

    MessageService messageService;
    ConversationService conversationService;



//    @RabbitListener(queues = RabbitMqConfig.SALES_ORDER_CONFIRMATION_QUEUE)
//    public void consume(SalesOrderConfirmation salesOrderConfirmation){
//        System.out.println("||| Message recieved |||||| "+ salesOrderConfirmation.toString());
//    }
    @RabbitListener(queues = RabbitMqConfig.MESSAGE_MEASSAGE_QUEUE)
    public void consume(MessageDto message){

        System.out.println("||| Message Object recieved |||||| "+ message.toString());
        messageService.addMessageToConversationFromMessageQueue(message);
    }
    @RabbitListener(queues = USER_DELETED_QUEUE)
    public void consume(long userId){

        System.out.println("||| Delete user : |||||| "+ userId);
        conversationService.deleteConversation(userId);
    }
}
