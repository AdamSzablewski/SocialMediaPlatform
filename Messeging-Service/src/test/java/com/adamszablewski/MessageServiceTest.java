package com.adamszablewski;

import com.adamszablewski.dto.MessageDto;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.feign.ImageServiceClient;
import com.adamszablewski.feign.SecurityServiceClient;
import com.adamszablewski.feign.UserServiceClient;
import com.adamszablewski.model.Conversation;
import com.adamszablewski.model.Message;
import com.adamszablewski.repository.ConversationRepository;
import com.adamszablewski.repository.MessageRepository;
import com.adamszablewski.service.MessageService;
import com.adamszablewski.util.ConversationCreator;
import com.adamszablewski.util.UniqueIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest(properties = "spring.config.name=application-test")
public class MessageServiceTest {

    private MessageService messageService;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private ConversationRepository conversationRepository;
    @Mock
    private ConversationCreator conversationCreator;
    @Mock
    UserServiceClient userServiceClient;
    @Mock
    SecurityServiceClient securityServiceClient;
    @Mock
    UniqueIdGenerator uniqueIdGenerator;
    @Mock
    ImageServiceClient imageServiceClient;

    @BeforeEach
    void setup(){
        messageService = new MessageService(messageRepository, conversationRepository, conversationCreator, userServiceClient,
                securityServiceClient, uniqueIdGenerator, imageServiceClient);
    }

    @Test
    void addMessageToConversationFromMessageQueueTest_should_add_to_Existing_conversation_for_1_User(){


        MessageDto message = MessageDto.builder()
                .id(1L)
                .message("Test message")
                .dateSent(LocalDateTime.now())
                .recipient(0L)
                .recipient(1L)
                .sender(1L)
                .build();
        Conversation conversation = Conversation.builder()
                .id(1L)
                .ownerId(1L)
                .messages(new ArrayList<>())
                .isSystemConversation(true)
                .build();

        when(conversationRepository.findByOwnerIdAndIsSystemConversation(message.getRecipient(), true)).thenReturn(Optional.of(conversation));


        messageService.addMessageToConversationFromMessageQueue(message);

        assertThat(conversation.getMessages().size()).isEqualTo(1);
    }
    @Test
    void addMessageToConversationFromMessageQueueTest_should_create_conversation_for_1_User(){

        MessageDto message = MessageDto.builder()
                .id(1L)
                .message("Test message")
                .dateSent(LocalDateTime.now())
                .sender(0L)
                .recipient(1L)
                .build();
        Conversation conversation = Conversation.builder()
                .id(1L)
                .ownerId(1L)
                .messages(new ArrayList<>())
                .isSystemConversation(true)
                .build();

        when(conversationRepository.findByOwnerIdAndIsSystemConversation(message.getRecipient(), true)).thenReturn(Optional.empty());
        when(conversationCreator.createConversation(message.getRecipient(), true)).thenReturn(conversation);

        messageService.addMessageToConversationFromMessageQueue(message);

        verify(conversationCreator).createConversation(message.getRecipient(), true);
        assertThat(conversation.getMessages().size()).isEqualTo(1);
    }
    @Test
    void sendMessageToUserByIdTest_shouldAddMessageForEachUsersConversations(){
        long senderId =1L;
        long recipientId =2L;
        String instanceId = "unique";
        String userEmail = "test@mail.com";

        Conversation conversation1 = Conversation.builder()
                .id(1L)
                .ownerId(1L)
                .messages(new ArrayList<>())
                .isSystemConversation(false)
                .build();
        Conversation conversation2 = Conversation.builder()
                .id(2L)
                .ownerId(2L)
                .messages(new ArrayList<>())
                .isSystemConversation(false)
                .build();
        Message originalMessage = Message.builder()
               // .id(1L)
                .message("Test message")
                .dateSent(LocalDateTime.now())
                .sender(0L)
                .recipient(1L)
                .build();

        when(conversationRepository.findByOwnerIdAndRecipientId(senderId, recipientId)).thenReturn(Optional.of(conversation1));
        when(conversationRepository.findByOwnerIdAndRecipientId(recipientId, senderId)).thenReturn(Optional.of(conversation2));
        when(uniqueIdGenerator.generateUniqueId()).thenReturn("unique");

        messageService.sendMessageToUserById(recipientId, senderId, originalMessage);

        assertThat(conversation1.getMessages().get(0).getInstanceId()).isEqualTo(instanceId);
        assertThat(conversation2.getMessages().get(0).getInstanceId()).isEqualTo(instanceId);


    }
    @Test
    void sendMessageToUserByIdTest_shouldAddMessageForEachUsersConversations_andCreateForBoth(){
        long senderId =1L;
        long recipientId =2L;
        String instanceId = "unique";
        String userEmail = "test@mail.com";

        Conversation conversation1 = Conversation.builder()
                .id(1L)
                .ownerId(1L)
                .messages(new ArrayList<>())
                .isSystemConversation(false)
                .build();
        Conversation conversation2 = Conversation.builder()
                .ownerId(2L)
                .messages(new ArrayList<>())
                .isSystemConversation(false)
                .build();
        Message originalMessage = Message.builder()
                .message("Test message")
                .dateSent(LocalDateTime.now())
                .sender(0L)
                .recipient(1L)
                .build();

        when(conversationRepository.findByOwnerIdAndRecipientId(senderId, recipientId)).thenReturn(Optional.empty());
        when(conversationRepository.findByOwnerIdAndRecipientId(recipientId, senderId)).thenReturn(Optional.empty());
        when(conversationCreator.createConversation(senderId, recipientId)).thenReturn(conversation1);
        when(conversationCreator.createConversation(recipientId, senderId)).thenReturn(conversation2);

        when(uniqueIdGenerator.generateUniqueId()).thenReturn("unique");

        messageService.sendMessageToUserById(recipientId, senderId, originalMessage);

        assertThat(conversation1.getMessages().get(0).getInstanceId()).isEqualTo(instanceId);
        assertThat(conversation2.getMessages().get(0).getInstanceId()).isEqualTo(instanceId);
    }
    @Test
    void sendImageTOUserByIdTest_shouldAddImaggeMessageToBothConversations(){
        long senderId =1L;
        long recipientId =2L;
        String instanceId = "unique";
        String userEmail = "test@mail.com";

        Conversation conversation1 = Conversation.builder()
                .id(1L)
                .ownerId(1L)
                .messages(new ArrayList<>())
                .isSystemConversation(false)
                .build();
        Conversation conversation2 = Conversation.builder()
                .ownerId(2L)
                .messages(new ArrayList<>())
                .isSystemConversation(false)
                .build();
        Message originalMessage = Message.builder()
                .message("Test message")
                .dateSent(LocalDateTime.now())
                .sender(0L)
                .recipient(1L)
                .build();

        when(conversationRepository.findByOwnerIdAndRecipientId(senderId, recipientId)).thenReturn(Optional.empty());
        when(conversationRepository.findByOwnerIdAndRecipientId(recipientId, senderId)).thenReturn(Optional.empty());
        when(conversationCreator.createConversation(senderId, recipientId)).thenReturn(conversation1);
        when(conversationCreator.createConversation(recipientId, senderId)).thenReturn(conversation2);

        when(uniqueIdGenerator.generateUniqueId()).thenReturn("unique");

        messageService.sendMessageToUserById(recipientId, senderId, originalMessage);

    }
}
