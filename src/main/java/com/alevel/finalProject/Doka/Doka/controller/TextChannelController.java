package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.Message;
import com.alevel.finalProject.Doka.Doka.db.entity.User;
import com.alevel.finalProject.Doka.Doka.db.repos.MessageRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.TextChannelRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequestMapping("server")
@Controller
public class TextChannelController {
    private final UserRepository userRepository;
    private final TextChannelRepository textChannelRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    public TextChannelController(UserRepository userRepository, TextChannelRepository textChannelRepository, MessageRepository messageRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.userRepository = userRepository;
        this.textChannelRepository = textChannelRepository;
        this.messageRepository = messageRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/groupMsg")
    public void sendPersonalMessage(Message msg, Principal principal) {
        msg.setAuthor(principal.getName());
        String sender = msg.getAuthor();
        String content = msg.getText();
        String receiver = msg.getTo();

        log.info(sender + " " + content + " " + receiver);
        messageRepository.save(msg);
        List<User> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            msg.setTo(users.get(i).getUsername());
            if (!users.get(i).getUsername().equals(principal.getName())) {
                simpMessagingTemplate.convertAndSendToUser(users.get(i).getUsername(), "/queue/group", msg);
            }
        }
    }
}
