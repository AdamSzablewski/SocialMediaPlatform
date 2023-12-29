package com.adamszablewski.controller;


import com.adamszablewski.annotations.SecureContentResource;
import com.adamszablewski.annotations.SecureUserIdResource;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.model.Message;
import com.adamszablewski.model.MessageDTO;
import com.adamszablewski.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/user/{recipientId}}")
    @SecureUserIdResource
    public ResponseEntity<RestResponseDTO<MessageDTO>> sendMessageToUserById(@PathVariable long recipientId,
                                                                             @RequestParam("userId") long userId,
                                                                             @RequestBody Message message,
                                                                             HttpServletRequest httpServletRequest){
        messageService.sendMessageToUserById(recipientId, userId, message);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @PostMapping("/user/{recipientId}/from/{senderId}/image")
    @SecureUserIdResource
    public ResponseEntity<RestResponseDTO<MessageDTO>> sendImageToUserById(@PathVariable long recipientId,
                                                                           @RequestParam("userId") long userId,
                                                                           @RequestParam MultipartFile image,
                                                                           HttpServletRequest httpServletRequest) throws IOException {
        messageService.sendImageToUserById(recipientId, userId, image);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @DeleteMapping("/{conversationId}/message/{messageId}/user/{ownerId}")
    @SecureContentResource
    public ResponseEntity<String> deleteMessageFromConversationForUser(@RequestParam("conversationId") long conversationId,
                                                                                            @RequestParam("messageId") long messageId,
                                                                                            @RequestParam("userId") long ownerId,
                                                                                            HttpServletRequest httpServletRequest{
        messageService.deleteMessageFromConversationForUser(conversationId, messageId, ownerId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/message/instance")
    @SecureContentResource
    public ResponseEntity<String> deleteMessageFromConversationForAll(@RequestParam("instanceId") String instanceId,
                                                                                           @RequestParam("userId") long ownerId,
                                                                                           HttpServletRequest httpServletRequest){
        messageService.deleteMessageFromConversationForAll(instanceId, ownerId);
        return ResponseEntity.ok().build();
    }
}
