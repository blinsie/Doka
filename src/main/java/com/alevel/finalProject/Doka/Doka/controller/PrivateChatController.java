package com.alevel.finalProject.Doka.Doka.controller;


import com.alevel.finalProject.Doka.Doka.db.entity.Message;
import com.alevel.finalProject.Doka.Doka.db.entity.User;
import com.alevel.finalProject.Doka.Doka.db.repos.MessageRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class PrivateChatController {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public PrivateChatController(MessageRepository messageRepository, SimpMessagingTemplate simpMessagingTemplate, UserRepository userRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @MessageMapping("/personalMsg")
    public void sendPersonalMessage(Message msg, Principal principal) {
        msg.setAuthor(principal.getName());
        String sender = msg.getAuthor();
        String content = msg.getText();
        String receiver = msg.getTo();
        log.info(sender + " " + content + " " + receiver);
        messageRepository.save(msg);
        simpMessagingTemplate.convertAndSendToUser(receiver, "/queue/reply", msg);
    }


    @GetMapping("/private-chat")
    public String chat(Map<String, Object> model, Principal principal) {
        model.put("username", principal.getName());
        List<User> users = userRepository.findAll();
        model.put("userlist", users);
        List<Message> messages = messageRepository.findAll();
        List<Message> forRemove = new ArrayList<>();
        for (Message m : messages) {
            if (m.getAuthor() == null) {
                m.setAuthor("<empty_author>");
            }
            if (m.getTo() == null) {
                m.setTo("<empty_receiver>");
            }
            if (m.getText() == null) {
                m.setText("<empty_text>");
            }
            if (!m.getAuthor().equals(principal.getName())) {
                if (!m.getTo().equals(principal.getName())) {
                    forRemove.add(m);
                }
            }
            if(m.getAuthor().equals(principal.getName())){
                m.setAuthor("Me");
            }
            if(m.getTo().equals(principal.getName())){
                m.setTo("Me");
            }
        }
        messages.removeAll(forRemove);
        model.put("messages", messages);
        return "private-chat";
    }


}