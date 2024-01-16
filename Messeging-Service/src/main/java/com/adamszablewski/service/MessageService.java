package com.adamszablewski.service;

import com.adamszablewski.dto.FriendRequest;
import com.adamszablewski.dto.MessageDto;
import com.adamszablewski.exceptions.NoSuchConversationFoundException;
import com.adamszablewski.exceptions.NoSuchMessageException;
import com.adamszablewski.feign.ImageServiceClient;
import com.adamszablewski.feign.SecurityServiceClient;
import com.adamszablewski.feign.UserServiceClient;
import com.adamszablewski.model.Conversation;
import com.adamszablewski.model.Message;
import com.adamszablewski.repository.ConversationRepository;
import com.adamszablewski.repository.MessageRepository;
import com.adamszablewski.util.ConversationCreator;
import com.adamszablewski.util.UniqueIdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class MessageService {
   private final MessageRepository messageRepository;
   private final ConversationRepository conversationRepository;
   private final ConversationCreator conversationCreator;
   private final UserServiceClient userServiceClient;
   private final SecurityServiceClient securityServiceClient;
   private final UniqueIdGenerator uniqueIdGenerator;
   private final ImageServiceClient imageServiceClient;

    public void addMessageToConversationFromMessageQueue(MessageDto message) {
        Message newMessage = Message.builder()
                .message(message.getMessage())
                .sender(message.getSender())
                .owner(message.getRecipient())
                .dateSent(message.getDateSent())
                .build();
        messageRepository.save(newMessage);

        Conversation conversation = conversationRepository.findByOwnerIdAndIsSystemConversation(message.getRecipient(), true)
                .orElseGet(() -> conversationCreator.createConversation(message.getRecipient(), true));

        conversation.getMessages().add(newMessage);
        conversationRepository.save(conversation);

    }



    public void sendMessageToUserById(long recipientId, long senderId, Message message) {
        Conversation ownerConversation = conversationRepository.findByOwnerIdAndRecipientId(senderId, recipientId)
                .orElseGet(()-> conversationCreator.createConversation(senderId, recipientId));
        Conversation recipientConversation = conversationRepository.findByOwnerIdAndRecipientId(recipientId, senderId)
                .orElseGet(()-> conversationCreator.createConversation(recipientId, senderId));

        String instanceID = uniqueIdGenerator.generateUniqueId();

        Message m1 = Message.builder()
                .message(message.getMessage())
                .dateSent(LocalDateTime.now())
                .sender(senderId)
                .recipient(recipientId)
                .owner(senderId)
                .conversation(ownerConversation)
                .instanceId(instanceID)
                .build();
        Message m2 = Message.builder()
                .message(message.getMessage())
                .sender(senderId)
                .recipient(recipientId)
                .owner(recipientId)
                .conversation(recipientConversation)
                .dateSent(LocalDateTime.now())
                .instanceId(instanceID)
                .build();
        messageRepository.saveAll(List.of(m1, m2));
        ownerConversation.getMessages().add(m1);
        recipientConversation.getMessages().add(m2);
        conversationRepository.saveAll(Set.of(ownerConversation, recipientConversation));

    }
    public void sendImageToUserById(long recipientId, long senderId, MultipartFile image) throws IOException {

        Conversation ownerConversation = conversationRepository.findByOwnerIdAndRecipientId(senderId, recipientId)
                .orElseGet(()-> conversationCreator.createConversation(senderId, recipientId));
        Conversation recipientConversation = conversationRepository.findByOwnerIdAndRecipientId(recipientId, senderId)
                .orElseGet(()-> conversationCreator.createConversation(recipientId, senderId));

        String instanceID = uniqueIdGenerator.generateUniqueId();
        String imageId = uniqueIdGenerator.generateUniqueImageId();

        Message m1 = Message.builder()
                .dateSent(LocalDateTime.now())
                .sender(senderId)
                .imageId(imageId)
                .recipient(recipientId)
                .owner(senderId)
                .conversation(ownerConversation)
                .instanceId(instanceID)
                .build();
        Message m2 = Message.builder()
                .sender(senderId)
                .imageId(imageId)
                .recipient(recipientId)
                .owner(recipientId)
                .conversation(recipientConversation)
                .dateSent(LocalDateTime.now())
                .instanceId(instanceID)
                .build();
        byte[] imageData = image.getBytes();

        messageRepository.saveAll(Set.of(m1, m2));
        ownerConversation.getMessages().add(m1);
        recipientConversation.getMessages().add(m2);
        conversationRepository.saveAll(Set.of(ownerConversation, recipientConversation));

        imageServiceClient.sendImageToImageService(imageData, imageId, Set.of(senderId, recipientId));

    }


    public void deleteMessageFromConversationForUser(long conversationId, long messageId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(NoSuchConversationFoundException::new);
        Message message = conversation.getMessages().stream()
                .filter(m -> m.getId() == messageId)
                .findFirst()
                .orElseThrow(NoSuchMessageException::new);
        conversation.getMessages().remove(message);
        messageRepository.delete(message);
    }
    public void deleteMessageFromConversationForUser(Conversation conversation, Message message) {
        conversation.getMessages().remove(message);
        messageRepository.delete(message);
    }
    @Transactional
    public void deleteMessageFromConversationForAll(String instanceId, long ownerId) {

        Message message = messageRepository.findByInstanceIdAndOwner(instanceId, ownerId)
                .orElseThrow(NoSuchMessageException::new);
        Message message2 = messageRepository.findByInstanceIdAndOwner(instanceId, message.getRecipient())
                .orElseThrow(NoSuchMessageException::new);
        message.getConversation().getMessages().remove(message);
        message2.getConversation().getMessages().remove(message2);

        messageRepository.deleteAllByInstanceId(instanceId);

    }

    public void addFriendRequestMessage(FriendRequest friendRequest) {
        String friendRequestMessageText = "You have a friend request from "+friendRequest.getSenderId();
        MessageDto messageDto = MessageDto.builder()
                .message(friendRequestMessageText)
                .sender(friendRequest.getSenderId())
                .recipient(friendRequest.getReceiverId())
                .dateSent(friendRequest.getDateTime())
                .build();
        addMessageToConversationFromMessageQueue(messageDto);
    }
}
