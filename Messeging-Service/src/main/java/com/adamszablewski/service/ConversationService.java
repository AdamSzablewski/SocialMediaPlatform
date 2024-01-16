package com.adamszablewski.service;

import com.adamszablewski.exceptions.NoSuchConversationFoundException;
import com.adamszablewski.exceptions.NoSuchUserFoundException;
import com.adamszablewski.exceptions.NotAuthorizedException;
import com.adamszablewski.model.Conversation;
import com.adamszablewski.repository.ConversationRepository;
import com.adamszablewski.util.Mapper;
import com.adamszablewski.util.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

//import static com.adamszablewski.util.Mapper.mapConversationToDTO;

@AllArgsConstructor
@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final UserValidator userValidator;
    private final Mapper mapper;
    private final MessageService messageService;


    public List<Conversation> getCoversationsForUser(long userId) {
        return conversationRepository.findAllByOwnerId(userId);

    }

    public void deleteConversation(long id) {
        conversationRepository.deleteById(id);
    }

    public void deleteConversationForUser(Long userId) {
        List<Conversation> conversations = conversationRepository.findAllByOwnerId(userId);
        conversations.forEach(conversation -> {
            conversation.getMessages().forEach(
                    message -> messageService.deleteMessageFromConversationForUser(conversation, message));
        });
    }


//    public ResponseEntity<String> createConversation(String user) {
//        conversationCreator.createConversation(user);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//
//    }
//    public Conversation createConversation(String user) {
//        Optional<Conversation> optionalConversation =  conversationRepository.findByParticipantsContains(user);
//        if (optionalConversation.isPresent()){
//            return optionalConversation.get();
//        }
//        Conversation conversation = Conversation.builder()
//                .participants(List.of(user, "support"))
//                .messages(new ArrayList<>())
//                .build();
//        conversationRepository.save(conversation);
//
//        return conversation;
//    }


}
