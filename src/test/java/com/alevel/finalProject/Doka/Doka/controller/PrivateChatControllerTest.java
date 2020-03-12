package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.Message;
import com.alevel.finalProject.Doka.Doka.db.repos.MessageRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
class PrivateChatControllerTest {
    @Autowired
    private  PrivateChatController privateChatController;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @MockBean
    private Principal principal;

    @BeforeEach
    void setUp() {
        log.info("Before each private chat controller test ");
        Mockito.when(principal.getName()).thenReturn("user1");
        log.info("Created mock principal");
    }

    @AfterEach
    void tearDown() {
        log.info("Test complete");
    }

    @Test
    void sendPersonalMessage() {
        log.info("Start sendPersonalMessage() test");
        Message message = new Message();
        message.setAuthor("user1");
        message.setText_channel_id(0);
        message.setText("text");
        message.setTo("user2");
        message.setMessage_id("0");
        privateChatController.sendPersonalMessage(message, principal);

        Mockito.verify(messageRepository, Mockito.times(1)).save(message);
        Mockito.verify(simpMessagingTemplate, Mockito.times(1))
                .convertAndSendToUser("user2", "/queue/reply", message);
    }

    @Test
    void chat() {
        log.info("Start chat() test");
        String result = privateChatController.chat(new HashMap<>(), principal);
        assertEquals("private-chat", result);
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verify(messageRepository, Mockito.times(1)).findAll();
    }
}