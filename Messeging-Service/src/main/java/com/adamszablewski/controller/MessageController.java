package com.adamszablewski.controller;


import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.model.Message;
import com.adamszablewski.model.MessageDTO;
import com.adamszablewski.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    MessageService messageService;

    @PostMapping("/user/{recipientId}/from/{senderId}")
    public ResponseEntity<RestResponseDTO<MessageDTO>> sendMessageToUserById(@PathVariable long recipientId,
                                                                             @PathVariable long senderId,
                                                                             @RequestHeader("userEmail") String userEmail,
                                                                             @RequestBody Message message){
        messageService.sendMessageToUserById(recipientId, senderId, userEmail, message);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @PostMapping("/user/{recipientId}/from/{senderId}/image")
    public ResponseEntity<RestResponseDTO<MessageDTO>> sendImageToUserById(@PathVariable long recipientId,
                                                                             @PathVariable long senderId,
                                                                             @RequestHeader("userEmail") String userEmail,
                                                                             @RequestParam MultipartFile image) throws IOException {
        messageService.sendImageToUserById(recipientId, senderId, userEmail, image);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @DeleteMapping("/{conversationId}/message/{messageId}/user/{ownerId}")
    public ResponseEntity<RestResponseDTO<MessageDTO>> deleteMessageFromConversationForUser(@PathVariable long conversationId,
                                                                                            @PathVariable long messageId,
                                                                                            @PathVariable long ownerId,
                                                                             @RequestHeader("userEmail") String userEmail){
        messageService.deleteMessageFromConversationForUser(conversationId, messageId, userEmail, ownerId);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @DeleteMapping("/message/instance/{instanceId}/user/{ownerId}")
    public ResponseEntity<RestResponseDTO<MessageDTO>> deleteMessageFromConversationForAll(@PathVariable String instanceId,
                                                                                            @PathVariable long ownerId,
                                                                                            @RequestHeader("userEmail") String userEmail){
        messageService.deleteMessageFromConversationForAll(instanceId, userEmail, ownerId);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
}
