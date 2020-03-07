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

/**
 * This class control private chat view and sending message by WebSockets
 */
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
        messageRepository.save(msg);
        log.info("Sender: " + sender + " Msg: " + content + " " + "Receiver: " + receiver);
        simpMessagingTemplate.convertAndSendToUser(receiver, "/queue/reply", msg); // send to user(receiver) that subscribe to "/queue/reply"
    }


    @GetMapping("/private-chat")
    public String chat(Map<String, Object> model, Principal principal) {
        List<User> users = userRepository.findAll();
        List<Message> messages = messageRepository.findAll();
        editMessagesThatHaveEmptyContent(messages);
        List<Message> forRemove = new ArrayList<>();
        for (Message m : messages) {
            if (!m.getAuthor().equals(principal.getName())) {
                if (!m.getTo().equals(principal.getName())) {
                    forRemove.add(m);
                }
            }
            if (m.getAuthor().equals(principal.getName())) {
                m.setAuthor("Me");
            }
            if (m.getTo().equals(principal.getName())) {
                m.setTo("Me");
            }
        }
        messages.removeAll(forRemove);
        model.put("username", principal.getName());
        model.put("userlist", users);
        model.put("messages", messages);
        return "private-chat";
    }

    private void editMessagesThatHaveEmptyContent(List<Message> messages) {
        messages.stream()
                .forEach(m -> {
                    if (m.getAuthor() == null) {
                        m.setAuthor("<empty_author>");
                    }
                    if (m.getTo() == null) {
                        m.setTo("<empty_receiver>");
                    }
                    if (m.getText() == null) {
                        m.setText("<empty_text>");
                    }
                });
    }


}