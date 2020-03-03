package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.Message;
import com.alevel.finalProject.Doka.Doka.db.entity.Server;
import com.alevel.finalProject.Doka.Doka.db.entity.TextChannel;
import com.alevel.finalProject.Doka.Doka.db.repos.MessageRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.ServerRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.TextChannelRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ServerController {

    private final ServerRepository serverRepository;
    private final TextChannelRepository textChannelRepository;
    private final MessageRepository messageRepository;

    public ServerController(ServerRepository serverRepository, TextChannelRepository textChannelRepository, MessageRepository messageRepository) {
        this.serverRepository = serverRepository;
        this.textChannelRepository = textChannelRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        List<Server> serverList = serverRepository.findAll();
        model.put("servers", serverList);
        return "main";
    }

    @GetMapping("/server")
    public String server() {
        return "redirect:/main";
    }

    @GetMapping("server/new/{param}")
    public String createNewServer(@PathVariable String param) {
        List<Server> serverList = serverRepository.findAll();
        for (Server s : serverList) {
            if (s.getServer_name().equals(param)) {
                return "redirect:/";
            }
        }
        Server server = new Server();
        TextChannel defaultTextChanel = new TextChannel();
        defaultTextChanel.setText_channel_name("default");
        defaultTextChanel.setServer(server);
        List<TextChannel> textChannels = new ArrayList<>();
        textChannels.add(defaultTextChanel);
        server.setServer_name(param);
        server.setText_channels(textChannels);
        serverRepository.save(server);


        return "redirect:/server/" + param;
    }

    @GetMapping("server/{param}")
    public String goToServer(@PathVariable String param, Model model) {
        List<Server> serverList = serverRepository.findAll();
        Server server = new Server();
        boolean isPresent = false;
        for (Server serv : serverList) {
            if (serv.getServer_name().equals(param)) {
                server = serv;
                isPresent = true;
            }
        }
        if (!isPresent) {
            return "redirect:/";
        }
        List<TextChannel> channels = textChannelRepository.findAll();
        List<TextChannel> forRemove = new ArrayList<>();
        for (TextChannel t : channels) {
            if (t.getServer().getServer_id() != server.getServer_id()) {
                forRemove.add(t);
            }
        }
        channels.removeAll(forRemove);
        model.asMap().put("channels", channels);

        return "/server";
    }

    @GetMapping("/text-channel/new/{serv}/{text}")
    public String createNewTextChannel(@PathVariable String serv, @PathVariable String text, Model model) {
        List<TextChannel> channels = textChannelRepository.findAll();
        for (TextChannel t : channels) {
            if (t.getText_channel_name().equals(text)) {
                return "redirect:/";
            }
        }
        TextChannel newTextChannel = new TextChannel();
        newTextChannel.setText_channel_name(text);
        List<Server> serverList = serverRepository.findAll();
        Server server = new Server();
        boolean isPresent = false;
        for (Server s : serverList) {
            if (s.getServer_name().equals(serv)) {
                server = s;
                isPresent = true;
            }
        }
        if (!isPresent) {
            return "redirect:/";
        }
        newTextChannel.setServer(server);
        textChannelRepository.save(newTextChannel);
        List<TextChannel> forRemove = new ArrayList<>();
        for (TextChannel t : channels) {
            if (t.getServer().getServer_id() != server.getServer_id()) {
                forRemove.add(t);
            }
        }
        channels.removeAll(forRemove);
        model.asMap().put("channels", channels);
        return "redirect:/text-channel/swap/" + serv + "/" + text;
    }

    @GetMapping("text-channel/swap/{serv}/{text}")
    public String swapTextChannel(@PathVariable String serv, @PathVariable String text, Model model, Principal principal) {
        List<Server> serverList = serverRepository.findAll();
        Server server = new Server();
        boolean isPresent = false;
        for (Server s : serverList) {
            if (s.getServer_name().equals(serv)) {
                server = s;
                isPresent = true;
            }
        }
        if (!isPresent) {
            return "redirect:/";
        }
        List<TextChannel> channels = textChannelRepository.findAll();
        TextChannel byName = new TextChannel();
        List<TextChannel> textChannelsForRemove = new ArrayList<>();
        for (TextChannel t : channels) {
            if (t.getText_channel_name().equals(text)) {
                if (t.getServer().getServer_id() == server.getServer_id()) {
                    byName = t;
                }
            }
            if (t.getServer().getServer_id() != server.getServer_id()) {
                textChannelsForRemove.add(t);
            }
        }
        channels.removeAll(textChannelsForRemove);
        model.asMap().put("channels", channels);
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
            if (!m.getTo().equals(byName.getText_channel_name())) {
                forRemove.add(m);
            }
            if (m.getText_channel_id() != byName.getText_channel_id()) {
                forRemove.add(m);
            }
        }
        messages.removeAll(forRemove);
        System.out.println(messages);
        model.asMap().put("messages", messages);
        model.asMap().put("channels", channels);
        model.asMap().put("username", principal.getName());
        model.asMap().put("text_name", byName.getText_channel_name());
        model.asMap().put("text_id", byName.getText_channel_id());

        return "text-channel";
    }


    @PostMapping("/main")
    public String addMsg(@RequestParam String msg, Map<String, Object> model) {

        return "main";
    }
}
