package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.User;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
class FriendPropertiesControllerTest {

    @Autowired
    private FriendPropertiesController friendPropertiesController;

    @MockBean
    private Model model;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private Principal principal;

    @BeforeEach
    void setUp() {
        log.info("Before each friend properties controller test ");
        Mockito.when(principal.getName()).thenReturn("user1");
        log.info("Created mock principal");
        User user1 = new User();
        user1.setUsername("user1");
        user1.setId(1);
        User user2 = new User();
        user2.setUsername("user2");
        user2.setId(2);
        List<Integer> friendListId = new ArrayList<>();
        friendListId.add(2);
        user1.setFriend_list_id(friendListId);
        Mockito.when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepository.findById(2)).thenReturn(java.util.Optional.of(user2));
        Mockito.when(userRepository.findByUsername("user1")).thenReturn(user1);
        Mockito.when(userRepository.findByUsername("user2")).thenReturn(user2);
        log.info("Created mock userRepository");
    }

    @AfterEach
    void tearDown() {
        log.info("Test complete");
    }

    @Test
    void friendListPage() {
        log.info("Start friendListPage() test");
        String res = friendPropertiesController.friendListPage(model, principal);
        assertEquals("friends", res);
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(principal.getName());
        Mockito.verify(userRepository, Mockito.times(1)).findById(2);
    }

    @Test
    void addFriendByName() {
        log.info("Start addFriendByName() test");
        String res = friendPropertiesController.addFriendByName(principal, "user2");
        assertEquals("redirect:/friends", res);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername("user2");
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername("user1");
    }

    @Test
    void deleteFriendByName() {
        log.info("Start deleteFriendByName() test");
        String res = friendPropertiesController.deleteFriendByName("user2", principal);
        assertEquals("redirect:/friends", res);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername("user2");
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername("user1");
        User user1 = new User();
        user1.setUsername("user1");
        user1.setId(1);
        User user2 = new User();
        user2.setUsername("user2");
        user2.setId(2);
        List<Integer> friendListId = new ArrayList<>();
        friendListId.add(2);
        user1.setFriend_list_id(friendListId);
        user1.getFriend_list_id().remove(user2.getId());
        Mockito.verify(userRepository, Mockito.times(1)).save(user1);
    }
}