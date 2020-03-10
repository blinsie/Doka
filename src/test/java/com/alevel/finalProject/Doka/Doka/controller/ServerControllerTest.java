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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ServerControllerTest {

    @Autowired
    private ServerController serverController;

    @MockBean
    private ServerRepository serverRepository;

    @MockBean
    private TextChannelRepository textChannelRepository;

    @MockBean
    private VoiceRoomRepository voiceRoomRepository;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private Model model;

    @MockBean
    private Principal principal;

    @BeforeEach
    void beforeEach(){
        log.info("Before each server controller test");
        List<TextChannel> textChannels = new ArrayList<>();
        TextChannel channel1 = new TextChannel(1, "first", new Server());
        TextChannel channel2 = new TextChannel(2, "second", new Server());
        textChannels.add(channel1);
        textChannels.add(channel2);

        List<VoiceRoom> rooms = new ArrayList<>();
        VoiceRoom room1 = new VoiceRoom(1, "room1", new Server());
        VoiceRoom room2 = new VoiceRoom(2, "room2", new Server());
        rooms.add(room1);
        rooms.add(room2);

        List<Server> servers = new ArrayList<>();
        Server server = new Server(1,"server", textChannels, rooms);
        textChannels.get(0).setServer(server);
        textChannels.get(1).setServer(server);
        rooms.get(0).setServer(server);
        rooms.get(1).setServer(server);
        servers.add(server);


        Mockito.when(textChannelRepository.findAll()).thenReturn(textChannels);
        log.info("Created mock textChannel");
        Mockito.when(voiceRoomRepository.findAll()).thenReturn(rooms);
        log.info("Created mock voiceRoom");
        Mockito.when(serverRepository.findAll()).thenReturn(servers);
        log.info("Created mock server");
    }

    @AfterEach
    void afterEach(){
        log.info("Test complete");
    }

    @Test
    void setUpNewServerWithServerName() {
        log.info("Start setUpNewServerWithServerName() test");
        Server server = new Server();
        server.setServer_name("testServerName");
        boolean result = serverController.setUpNewServerWithServerName(server);
        Assertions.assertTrue(result);
        Mockito.verify(serverRepository, Mockito.times(1)).findAll();
        Mockito.verify(serverRepository, Mockito.times(1)).save(server);
    }

    @Test
    void findServerChannelsByServerId() {
        log.info("Start findServerChannelsByServerId() test");
        List<TextChannel> channels = serverController.findServerChannelsByServerId(1);
        List<TextChannel> expected = new ArrayList<>();
        TextChannel channel1 = new TextChannel(1, "first", new Server());
        TextChannel channel2 = new TextChannel(2, "second", new Server());
        expected.add(channel1);
        expected.add(channel2);

        List<VoiceRoom> rooms = new ArrayList<>();
        VoiceRoom room1 = new VoiceRoom(1, "room1", new Server());
        VoiceRoom room2 = new VoiceRoom(2, "room2", new Server());
        rooms.add(room1);
        rooms.add(room2);

        Server server = new Server(1,"server", expected, rooms);
        expected.get(0).setServer(server);
        expected.get(1).setServer(server);
        assertTrue(expected.equals(channels));
        Mockito.verify(textChannelRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findServerRoomsByServerId() {
        log.info("Start findServerRoomsByServerId() test");
        List<VoiceRoom> rooms = serverController.findServerRoomsByServerId(1);

        List<TextChannel> textChannels = new ArrayList<>();
        TextChannel channel1 = new TextChannel(1, "first", new Server());
        TextChannel channel2 = new TextChannel(2, "second", new Server());
        textChannels.add(channel1);
        textChannels.add(channel2);

        List<VoiceRoom> expected = new ArrayList<>();
        VoiceRoom room1 = new VoiceRoom(1, "room1", new Server());
        VoiceRoom room2 = new VoiceRoom(2, "room2", new Server());
        expected.add(room1);
        expected.add(room2);

        Server server = new Server(1,"server", textChannels, rooms);
        textChannels.get(0).setServer(server);
        textChannels.get(1).setServer(server);
        expected.get(0).setServer(server);
        expected.get(1).setServer(server);

        assertTrue(expected.equals(rooms));
        Mockito.verify(voiceRoomRepository, Mockito.times(1)).findAll();
    }

    @Test
    void createNewTextChannel() {
        log.info("Start createNewTextChannel() test");
        String path = serverController.createNewTextChannel("server", "textChannel");
        assertEquals("redirect:/text-channel/swap/server/textChannel", path);
        Mockito.verify(serverRepository, Mockito.atLeast(1)).findAll();
        Mockito.verify(textChannelRepository, Mockito.atLeast(1)).findAll();
    }

    @Test
    void swapTextChannel() {
        log.info("Start swapTextChannel() test");
        String path = serverController.swapTextChannel("server", "first", model, principal);
        assertEquals("text-channel", path);
        Mockito.verify(serverRepository, Mockito.atLeast(1)).findAll();
    }

    @Test
    void editMessagesThatHaveEmptyContent() {
        log.info("Start editMessagesThatHaveEmptyContent() test");
        List<Message> messages = new ArrayList<>();
        messages.add(new Message());
        messages.add(new Message());
        messages.add(new Message());
        serverController.editMessagesThatHaveEmptyContent(messages);
        boolean res = true;
        for (Message m : messages) {
            if (m.getAuthor() == null) {
                res = false;
            }
            if (m.getTo() == null) {
                res = false;
            }
            if (m.getText() == null) {
                res = false;
            }
        }
        assertTrue(res);
    }

    @Test
    void getServerByName() {
        log.info("Start getServerByName() test");
        Server server = serverController.getServerByName("server");

        List<TextChannel> textChannels = new ArrayList<>();
        TextChannel channel1 = new TextChannel(1, "first", new Server());
        TextChannel channel2 = new TextChannel(2, "second", new Server());
        textChannels.add(channel1);
        textChannels.add(channel2);

        List<VoiceRoom> rooms = new ArrayList<>();
        VoiceRoom room1 = new VoiceRoom(1, "room1", new Server());
        VoiceRoom room2 = new VoiceRoom(2, "room2", new Server());
        rooms.add(room1);
        rooms.add(room2);

        Server expected = new Server(1,"server", textChannels, rooms);
        textChannels.get(0).setServer(server);
        textChannels.get(1).setServer(server);
        rooms.get(0).setServer(server);
        rooms.get(1).setServer(server);

        assertTrue(expected.equals(server));
        Mockito.verify(serverRepository, Mockito.times(1)).findAll();
    }


}