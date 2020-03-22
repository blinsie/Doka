package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.Message;
import com.alevel.finalProject.Doka.Doka.db.entity.Server;
import com.alevel.finalProject.Doka.Doka.db.entity.TextChannel;
import com.alevel.finalProject.Doka.Doka.db.entity.VoiceRoom;
import com.alevel.finalProject.Doka.Doka.db.repos.MessageRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.ServerRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.TextChannelRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.VoiceRoomRepository;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * This class control server parts that cross with view templates
 */
@Slf4j
@Controller
public class ServerController {

    private final ServerRepository serverRepository;
    private final TextChannelRepository textChannelRepository;
    private final VoiceRoomRepository voiceRoomRepository;
    private final MessageRepository messageRepository;

    public ServerController(ServerRepository serverRepository, TextChannelRepository textChannelRepository, VoiceRoomRepository voiceRoomRepository, MessageRepository messageRepository) {
        this.serverRepository = serverRepository;
        this.textChannelRepository = textChannelRepository;
        this.voiceRoomRepository = voiceRoomRepository;
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

    @PostMapping("server/new")
    public String createNewServer(@RequestParam String server_name) {
        Server server = new Server();
        server.setServer_name(server_name);
        boolean isCreated = setUpNewServerWithServerName(server);
        if(!isCreated){
            return "redirect:/";
        }
        return "redirect:/server/" + server_name;
    }

    public boolean setUpNewServerWithServerName(Server server){
        List<Server> serverList = serverRepository.findAll();
        for (Server s : serverList) {
            if (s.getServer_name().equals(server.getServer_name())) {
                log.warn("Server - {} is already exist", server.getServer_name());
                return false;
            }
        }
        TextChannel defaultTextChanel = new TextChannel();
        defaultTextChanel.setText_channel_name("default");
        defaultTextChanel.setServer(server);
        List<TextChannel> textChannels = new ArrayList<>();
        textChannels.add(defaultTextChanel);
        server.setText_channels(textChannels);
        serverRepository.save(server);
        log.info("Creating new server - {}", server.getServer_name());
        return true;
    }

    @GetMapping("server/{serv}")
    public String goToServer(@PathVariable String serv, Model model) {
        Server server = getServerByName(serv);
        if (server.getServer_name() == null) {
            return "redirect:/";
        }
        List<TextChannel> channels = findServerChannelsByServerId(server.getServer_id());
        List<VoiceRoom> rooms = findServerRoomsByServerId(server.getServer_id());
        model.asMap().put("channels", channels);
        model.asMap().put("rooms", rooms);
        return "/server";
    }

    public List<TextChannel> findServerChannelsByServerId(int id){
        List<TextChannel> channels = textChannelRepository.findAll();
        channels = channels.stream()
                .filter(f -> f.getServer().getServer_id().equals(id))
                .collect(Collectors.toList());
        return channels;
    }

    public List<VoiceRoom> findServerRoomsByServerId(int id){
        List<VoiceRoom> rooms = voiceRoomRepository.findAll();
        rooms = rooms.stream()
                .filter(r -> r.getServer().getServer_id().equals(id))
                .collect(Collectors.toList());
        return rooms;
    }

    @PostMapping("/text-channel/new")
    public String createNewTextChannel(@RequestParam String server_name, @RequestParam String text_name) {
        List<TextChannel> channels = textChannelRepository.findAll();
        for (TextChannel t : channels) {
            if (t.getText_channel_name().equals(text_name)) {
                log.warn("Text-channel - {} is already exist!", text_name);
                return "redirect:/";
            }
        }
        TextChannel newTextChannel = new TextChannel();
        newTextChannel.setText_channel_name(text_name);
        Server server = getServerByName(server_name);
        if (server.getServer_name() == null) {
            return "redirect:/";
        }
        newTextChannel.setServer(server);
        textChannelRepository.save(newTextChannel);
        log.info("Creating text-channel - {}", text_name);
        return "redirect:/text-channel/swap/" + server_name + "/" + text_name;
    }

    @GetMapping("text-channel/swap/{serv}/{text}")
    public String swapTextChannel(@PathVariable String serv, @PathVariable String text, Model model, Principal principal) {
        Server server = getServerByName(serv);
        if (server.getServer_name() == null) {
            return "redirect:/";
        }
        List<TextChannel> channels = textChannelRepository.findAll();
        TextChannel byName = new TextChannel();
        channels = channels.stream()
                .filter(t -> t.getServer().getServer_id().equals(server.getServer_id()))
                .collect(Collectors.toList());

        for (TextChannel t : channels) {
            if (t.getText_channel_name().equals(text)) {
                if (t.getServer().getServer_id().equals(server.getServer_id())) {
                    byName = t;
                }
            }
        }
        if (byName.getText_channel_name() == null) {
            log.warn("Text-channel - {} doesn't exist!", text);
            return "redirect:/";
        }

        log.info("Find text-channel - {}", byName.getText_channel_name());

        List<Message> messages = messageRepository.findAll();
        editMessagesThatHaveEmptyContent(messages);
        List<Message> forRemove = new ArrayList<>();
        for (Message m : messages) {
            if (m.getText_channel_id() == null) {
                m.setText_channel_id(-1);
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
        log.info("Messages: {}", messages);
        model.asMap().put("messages", messages);
        model.asMap().put("channels", channels);
        model.asMap().put("username", principal.getName());
        model.asMap().put("text_name", byName.getText_channel_name());
        model.asMap().put("text_id", byName.getText_channel_id());

        return "text-channel";
    }

    public void editMessagesThatHaveEmptyContent(List<Message> messages) {
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

    @PostMapping("voice-room/new")
    public String createNewVoiceRoom(@RequestParam String server_name, @RequestParam String room_name, Model model) {
        Server server = getServerByName(server_name);
        if (server.getServer_name() == null) {
            return "redirect:/";
        }
        List<VoiceRoom> rooms = voiceRoomRepository.findAll();
        for (VoiceRoom r : rooms) {
            if (r.getVoice_room_name().equals(room_name)) {
                log.warn("Voice room - {} is already exist!", room_name);
                return "redirect:/";
            }
        }
        VoiceRoom newVoiceRoom = new VoiceRoom();
        newVoiceRoom.setVoice_room_name(room_name);
        newVoiceRoom.setServer(server);
        voiceRoomRepository.save(newVoiceRoom);
        log.info("Creating new voice room - {}", room_name);
        return "redirect:/voice-room/swap/" + server_name + "/" + room_name;
    }

    @GetMapping("voice-room/swap/{serv}/{room}")
    public String swapVoiceRoom(@PathVariable String serv, @PathVariable String room, Model model, Principal principal) {
        Server server = getServerByName(serv);
        if (server.getServer_name() == null) {
            return "redirect:/";
        }
        List<VoiceRoom> rooms = voiceRoomRepository.findAll();
        Optional<VoiceRoom> first = rooms.stream()
                .filter(r -> r.getVoice_room_name().equals(room))
                .findFirst();
        if (!first.isPresent()) {
            log.warn("Voice room - {} doesn't exist!", room);
            return "redirect:/";
        }
        VoiceRoom presentVoiceRoom = first.get();
        log.info("User - {} swap voice room to - {}", principal.getName(), room);
        model.asMap().put("voice_room_name", presentVoiceRoom.getVoice_room_name());
        model.asMap().put("voice_room_id", presentVoiceRoom.getVoice_room_id());
        model.asMap().put("user_name", principal.getName());
        return "voice-room";
    }

    protected Server getServerByName(String serverName) {
        List<Server> serverList = serverRepository.findAll();
        Server server = new Server();
        boolean isPresent = false;
        for (Server s : serverList) {
            if (s.getServer_name().equals(serverName)) {
                server = s;
                isPresent = true;
            }
        }
        if (!isPresent) {
            log.warn("Server - {} doesn't exist", serverName);
            return new Server();
        }
        return server;
    }
}
