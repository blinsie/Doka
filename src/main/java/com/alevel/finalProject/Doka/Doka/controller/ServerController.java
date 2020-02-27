package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.Message;
import com.alevel.finalProject.Doka.Doka.db.entity.User;
import com.alevel.finalProject.Doka.Doka.db.repos.MessageRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("server")
@Controller
public class ServerController {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    public ServerController(UserRepository userRepository, MessageRepository messageRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("/")
    public String serverProfile() {
        return "server";
    }

    @GetMapping("text-channel")
    public String textChannel(Model model, Principal principal) {
        model.asMap().put("username", principal.getName());
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
            if (!m.getTo().equals("all_users_in_group")) {
                forRemove.add(m);
            }
            if (m.getAuthor().equals(principal.getName())) {
                m.setAuthor("Me");
            }
        }
        messages.removeAll(forRemove);
        model.asMap().put("messages", messages);
        return "text-channel";
    }


    @MessageMapping("/groupMsg")
    public void sendPersonalMessage(Message msg, Principal principal) {
        msg.setAuthor(principal.getName());
        msg.setTo("all_users_in_group");
        System.out.println(msg.toString());
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
        //simpMessagingTemplate.convertAndSendToUser(receiver, "/queue/group", msg);
    }
}
