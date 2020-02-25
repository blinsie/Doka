package com.alevel.finalProject.Doka.Doka.controller;


import com.alevel.finalProject.Doka.Doka.domain.Message;
import com.alevel.finalProject.Doka.Doka.repos.MessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class TextChannelController {
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    public TextChannelController(MessageRepository messageRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
    }

    @MessageMapping("/personalMsg")
    public void greeting(Message msg, Principal principal) {
        msg.setAutor(principal.getName());
        System.out.println(msg.toString());
        String sender = msg.getAutor();
        String content = msg.getText();
        String receiver = msg.getTo();
        System.out.println(sender + " " + content + " " + receiver);
        messageRepository.save(msg);
        simpMessagingTemplate.convertAndSendToUser(receiver, "/queue/reply", msg);
    }
}