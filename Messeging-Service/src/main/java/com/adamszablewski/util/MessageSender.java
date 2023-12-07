package com.adamszablewski.util;


import com.adamszablewski.model.Conversation;
import com.adamszablewski.model.Message;
import com.adamszablewski.repository.ConversationRepository;
import com.adamszablewski.repository.MessageRepository;
import com.adamszablewski.service.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Component
public class MessageSender {

    private final ConversationRepository conversationRepository;
    private final ConversationService conversationService;
    private final MessageRepository messageRepository;
    private final ConversationCreator conversationCreator;




//    public void addMessageToConversation(Message message, Conversation conversation){
//        List<Message> messages = conversation.getMessages();
//        messages.add(message);
//        messageRepository.save(message);
//    }
//    @Transactional
//    public void addMessageToConversation(Message message){
//        messageRepository.save(message);
//        message.getReceivers().forEach(reciever -> {
//            Optional<Conversation> optionalConversation = conversationRepository.findByUserId(reciever);
//            Conversation conversation;
//            if (optionalConversation.isEmpty()){
//                conversation = conversationCreator.createConversation(reciever);
//            }else {
//                conversation = optionalConversation.get();
//            }
//
//            conversation.getMessages().add(message);
//            conversationRepository.save(conversation);
//        });
//
//        System.out.println("printing from addmessagetoconv "+ message);
//
//
//    }


}
