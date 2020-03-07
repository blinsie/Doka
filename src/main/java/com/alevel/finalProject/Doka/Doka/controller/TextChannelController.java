package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.Message;
import com.alevel.finalProject.Doka.Doka.db.entity.User;
import com.alevel.finalProject.Doka.Doka.db.repos.MessageRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

/**
 * Class that control sending message to text channels
 */
@Slf4j
@Controller
public class TextChannelController {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    public TextChannelController(UserRepository userRepository, MessageRepository messageRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/groupMsg")
    public void sendPersonalMessage(Message msg, Principal principal) {
        msg.setAuthor(principal.getName());
        String sender = msg.getAuthor();
        String content = msg.getText();
        String receiver = msg.getTo();
        messageRepository.save(msg);
        log.info(sender + " " + content + " " + receiver);
        log.info("Sender: " + sender + " Msg: " + content + " " + "Text-channels receivers: " + receiver);
        List<User> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            msg.setTo(users.get(i).getUsername());
            if (!users.get(i).getUsername().equals(principal.getName())) {
                simpMessagingTemplate.convertAndSendToUser(users.get(i).getUsername(), "/queue/group", msg);
            }
        }
    }
}
