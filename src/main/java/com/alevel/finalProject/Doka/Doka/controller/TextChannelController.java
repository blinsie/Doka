package com.alevel.finalProject.Doka.Doka.controller;


import com.alevel.finalProject.Doka.Doka.domain.Message;
import com.alevel.finalProject.Doka.Doka.repos.MessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TextChannelController {
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    public TextChannelController(MessageRepository messageRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
    }

/*    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message sendMessage(Message message, Principal principal) throws Exception {
        String name = principal.getName();//get logged in username
        Message resultMessage = new Message(name + ":\t" + HtmlUtils.htmlEscape(message.getText()), name);
        messageRepository.save(resultMessage);
        return resultMessage;
    }*/

    @MessageMapping("/personalMsg")
    public void greeting(Message msg) {
        //msg.setAutor(principal.getName());
        String sender = msg.getAutor();
        String content = msg.getText();
        String receiver = msg.getTo();
        System.out.println(sender + " " + content + " " + receiver);
        simpMessagingTemplate.convertAndSendToUser(receiver, "/queue/reply", msg);
    }
}