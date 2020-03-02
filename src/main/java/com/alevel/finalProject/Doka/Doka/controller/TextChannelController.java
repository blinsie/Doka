package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.Message;
import com.alevel.finalProject.Doka.Doka.db.entity.TextChannel;
import com.alevel.finalProject.Doka.Doka.db.entity.User;
import com.alevel.finalProject.Doka.Doka.db.repos.MessageRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.TextChannelRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
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

    @GetMapping("/")
    public String serverProfile() {
        return "server";
    }

    @GetMapping("text-channel/swap/default")
    public String textChannel(Model model, Principal principal) {
        model.asMap().put("username", principal.getName());
        List<Message> messages = messageRepository.findAll();
        List<Message> forRemove = new ArrayList<>();
        List<TextChannel> channels = textChannelRepository.findAll();
        if(channels.size() == 0){
            TextChannel defaultTextChannel = new TextChannel();
            defaultTextChannel.setText_channel_name("default");
            textChannelRepository.save(defaultTextChannel);
            channels = textChannelRepository.findAll();
        }
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
        model.asMap().put("channels", channels);
        return "text-channel";
    }



    @GetMapping("text-channel/{param}")
    public String add(@PathVariable String param, Model model) {
        TextChannel newTextChannel = new TextChannel();
        newTextChannel.setText_channel_name(param);
        textChannelRepository.save(newTextChannel);
        List<TextChannel> channels = textChannelRepository.findAll();
        model.asMap().put("channels", channels);
        return "redirect:/server/text-channel";
    }

    @GetMapping("text-channel/swap/{param}")
    public String swapTextChannel(@PathVariable String param, Model model, Principal principal) {
        List<TextChannel> channels = textChannelRepository.findAll();
        TextChannel byName = new TextChannel();
        for (int i = 0; i < channels.size(); i++) {
            if(channels.get(i).getText_channel_name().equals(param)){
                byName = channels.get(i);
            }
        }
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
            if (m.getAuthor().equals(principal.getName())) {
                m.setAuthor("Me");
            }
            if(!m.getTo().equals(byName.getText_channel_name())){
                forRemove.add(m);
            }
        }
        messages.removeAll(forRemove);
        System.out.println(messages);
        model.asMap().put("messages", messages);
        model.asMap().put("channels", channels);
        model.asMap().put("username", principal.getName());
        return "text-channel";
    }


    @MessageMapping("/groupMsg")
    public void sendPersonalMessage(Message msg, Principal principal) {
        msg.setAuthor(principal.getName());
        //msg.setTo("all_users_in_group");

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
