package com.alevel.finalProject.Doka.Doka.controller;


import com.alevel.finalProject.Doka.Doka.domain.Message;
import com.alevel.finalProject.Doka.Doka.domain.User;
import com.alevel.finalProject.Doka.Doka.repos.MessageRepository;
import com.alevel.finalProject.Doka.Doka.repos.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TextChannelController {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public TextChannelController(MessageRepository messageRepository, SimpMessagingTemplate simpMessagingTemplate, UserRepository userRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @MessageMapping("/personalMsg")
    public void sendMessage(Message msg, Principal principal) {
        msg.setAutor(principal.getName());
        System.out.println(msg.toString());
        String sender = msg.getAutor();
        String content = msg.getText();
        String receiver = msg.getTo();
        System.out.println(sender + " " + content + " " + receiver);
        messageRepository.save(msg);
        simpMessagingTemplate.convertAndSendToUser(receiver, "/queue/reply", msg);
    }

    @GetMapping("/chat")
    public String chat(Map<String, Object> model, Principal principal) {
        model.put("username", principal.getName());
        List<User> users = userRepository.findAll();
        model.put("userlist", users);
        List<Message> messages = messageRepository.findAll();
        List<Message> forRemove = new ArrayList<>();
        for (Message m : messages) {
            if (m.getAutor() == null) {
                m.setAutor("<empty_author>");
            }
            if (m.getTo() == null) {
                m.setTo("<empty_receiver>");
            }
            if (m.getText() == null) {
                m.setText("<empty_text>");
            }
            if (!m.getAutor().equals(principal.getName())) {
                if (!m.getTo().equals(principal.getName())) {
                    forRemove.add(m);
                }
            }
        }
        messages.removeAll(forRemove);
        model.put("messages", messages);
        return "chat";
    }


}