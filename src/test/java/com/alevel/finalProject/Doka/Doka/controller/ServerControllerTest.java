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
import org.junit.jupiter.api.Assertions;
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

    @Test
    void setUpNewServerWithServerName() {
        Server server = new Server();
        server.setServer_name("testServerName");
        boolean result = serverController.setUpNewServerWithServerName(server);
        Assertions.assertTrue(result);
        Mockito.verify(serverRepository, Mockito.times(1)).findAll();
        Mockito.verify(serverRepository, Mockito.times(1)).save(server);
    }

    @Test
    void findServerChannelsByServerId() {
        List<TextChannel> channels = serverController.findServerChannelsByServerId(0);
        Mockito.verify(textChannelRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findServerRoomsByServerId() {
        List<VoiceRoom> rooms = serverController.findServerRoomsByServerId(0);
        Mockito.verify(voiceRoomRepository, Mockito.times(1)).findAll();
    }

    @Test
    void createNewTextChannel() {
        String path = serverController.createNewTextChannel("server", "textChannel");
        assertEquals("redirect:/", path);
        Mockito.verify(serverRepository, Mockito.atLeast(1)).findAll();
        Mockito.verify(textChannelRepository, Mockito.atLeast(1)).findAll();
    }

    @Test
    void swapTextChannel() {
        String path = serverController.swapTextChannel("server", "textChannel", model, principal);
        assertEquals("redirect:/", path);
        Mockito.verify(serverRepository, Mockito.atLeast(1)).findAll();
    }

    @Test
    void editMessagesThatHaveEmptyContent() {
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
        Server server = serverController.getServerByName("server");
        Mockito.verify(serverRepository, Mockito.times(1)).findAll();
    }


}