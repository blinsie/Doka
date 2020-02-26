package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.domain.Message;
import com.alevel.finalProject.Doka.Doka.repos.MessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    private final MessageRepository messageRepository;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @GetMapping("/")
    public String greeting(Model model) {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
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
        return "main";
    }

    @PostMapping("/main")
    public String addMsg(@RequestParam String msg, Map<String, Object> model) {
        Message message = new Message();
        message.setText(msg);
        if(message.getText_channel_id() == null){
            message.setText_channel_id(-1);
        }
        if(message.getAutor() == null){
            message.setAutor("<empty_author>");
        }
        if(message.getMessage_id() == null){
            message.setMessage_id("<empty_message_id>");
        }
        if(message.getTo() == null){
            message.setTo("<empty_receiver>");
        }
        if(message.getText() == null){
            message.setText("<empty_text>");
        }
        messageRepository.save(message);
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);

        return "main";
    }
}
