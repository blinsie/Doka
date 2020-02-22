package com.alevel.finalProject.Doka.Doka.controller;


import com.alevel.finalProject.Doka.Doka.domain.Message;
import com.alevel.finalProject.Doka.Doka.repos.MessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class TextChannelController {
    private final MessageRepository messageRepository;

    public TextChannelController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message sendMessage(Message message, Principal principal) throws Exception {
        String name = principal.getName();//get logged in username
        Message resultMessage = new Message(name + ":\t" + HtmlUtils.htmlEscape(message.getText()), name);
        messageRepository.save(resultMessage);
        return resultMessage;
    }
}