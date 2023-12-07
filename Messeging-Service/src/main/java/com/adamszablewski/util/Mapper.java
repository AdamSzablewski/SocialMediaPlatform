package com.adamszablewski.util;


import com.adamszablewski.dto.ConversationDTO;

import com.adamszablewski.model.Conversation;
import com.adamszablewski.model.Message;
import com.adamszablewski.model.Identifiable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class Mapper {

    public static  <T extends Identifiable> List<Long> convertObjectListToIdList(List<T> list){
        return list.stream()
                .map(Identifiable::getId)
                .collect(Collectors.toList());
    }
    public static <T extends Identifiable> Long convertObjectToId(T entity){
        return entity.getId();
    }

//    public static MessageDTO mapMessageToDTO(Message message){
//        return MessageDTO.builder()
//                .id(message.getId())
//                .sender(message.getSender())
//                .message(message.getMessage())
//                .dateSent(message.getDateSent())
//                .build();
//    }
//    public static List<MessageDTO> mapMessageToDTO(List<Message> messages){
//        List<MessageDTO> messageDTOS = new ArrayList<>();
//        messages.forEach(message -> {
//            messageDTOS.add(MessageDTO.builder()
//                    .id(message.getId())
//                    .sender(message.getSender())
//                    .message(message.getMessage())
//                    .dateSent(message.getDateSent())
//                    .build());
//        });
//        return messageDTOS;
//    }
//
//    public static ConversationDTO mapConversationToDTO(Conversation conversation){
//        return ConversationDTO.builder()
//                .id(conversation.getId())
//                .user(conversation.getOwnerId())
//                .
//                .messages(mapMessageToDTO(conversation.getMessages()))
//                .build();
//    }

//    public static List<ConversationDTO> mapConversationToDTO(List<Conversation> conversations){
//        List<ConversationDTO> conversationDTOS = new ArrayList<>();
//        conversations.forEach(conversation -> {
//
//            conversationDTOS.add(ConversationDTO.builder()
//                    .id(conversation.getId())
//                    .user(conversation.getUser())
//                    .messages(mapMessageToDTO(conversation.getMessages()))
//                    .build());
//        });
//
//        return conversationDTOS;
//    }

}
