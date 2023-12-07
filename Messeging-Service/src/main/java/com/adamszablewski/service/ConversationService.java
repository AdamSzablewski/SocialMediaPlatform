package com.adamszablewski.service;

import com.adamszablewski.exceptions.NoSuchUserFoundException;
import com.adamszablewski.exceptions.NotAuthorizedException;
import com.adamszablewski.model.Conversation;
import com.adamszablewski.repository.ConversationRepository;
import com.adamszablewski.util.Mapper;
import com.adamszablewski.util.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

//import static com.adamszablewski.util.Mapper.mapConversationToDTO;

@AllArgsConstructor
@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final UserValidator userValidator;
    private final Mapper mapper;


    public Set<Conversation> getCoversationsForUser(long user, String userEmail) {
        if(!userValidator.isUser(user, userEmail)){
            throw new NotAuthorizedException();
        }
        return conversationRepository.findAllByOwnerId(user);

    }

    public void deleteConversation(long id) {
        conversationRepository.deleteById(id);
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
