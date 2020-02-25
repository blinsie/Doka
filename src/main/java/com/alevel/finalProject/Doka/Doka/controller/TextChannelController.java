package com.alevel.finalProject.Doka.Doka.controller;


import com.alevel.finalProject.Doka.Doka.domain.Message;
import com.alevel.finalProject.Doka.Doka.repos.MessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Map;

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

    @GetMapping("/chat")
    public String chat(Map<String, Object> model, Principal principal) {
        model.put("username", principal.getName());
        Iterable<Message> messages = messageRepository.findAll();

        messages.forEach(message -> {
            if(message.getText_channel_id() == null){
                message.setText_channel_id(-1);
            }
            if(message.getAutor() == null){
                message.setAutor("-1");
            }
            if(message.getMessage_id() == null){
                message.setMessage_id("-1");
            }
            if(message.getTo() == null){
                message.setTo("<empty_receiver>");
            }
            if(message.getText() == null){
                message.setText("<empty_text>");
            }
        });
        model.put("messages", messages);
        return "chat";
    }


}