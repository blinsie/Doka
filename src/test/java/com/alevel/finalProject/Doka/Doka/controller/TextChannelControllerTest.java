package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.Message;
import com.alevel.finalProject.Doka.Doka.db.entity.User;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TextChannelControllerTest {
    @Autowired
    private TextChannelController textChannelController;

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
        log.info("Before each text channel controller test ");
        Mockito.when(principal.getName()).thenReturn("user1");
        log.info("Created mock principal");
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setUsername("user1");
        user2.setUsername("user2");
        user3.setUsername("user3");
        userList.add(user1); userList.add(user2); userList.add(user3);
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        log.info("Created mock userRepository");
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
        message.setTo("text_channel");
        message.setMessage_id("0");
        textChannelController.sendPersonalMessage(message, principal);

        Mockito.verify(messageRepository, Mockito.times(1)).save(message);
        Mockito.verify(userRepository, Mockito.times(1)).findAll();

        Mockito.verify(simpMessagingTemplate, Mockito.times(1))
                .convertAndSendToUser("user2", "/queue/group", message);
        Mockito.verify(simpMessagingTemplate, Mockito.times(1))
                .convertAndSendToUser("user3", "/queue/group", message);
    }
}