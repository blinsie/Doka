package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.Role;
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

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
class RegistrationControllerTest {
    @Autowired
    private RegistrationController registrationController;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        log.info("Before each registration controller test ");
        User user = new User();
        user.setId(0);
        user.setUsername("user");
        user.setPassword("123");
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        Mockito.when(userRepository.findByUsername("user")).thenReturn(user);
        log.info("Created mock userRepository");
    }

    @AfterEach
    void afterEach(){
        log.info("Test complete");
    }


    @Test
    void addUser() {
        log.info("Start addUser() test");
        User user = new User();
        user.setUsername("user1");
        String result = registrationController.addUser(user, new HashMap<>());
        String expected = "redirect:/login";
        assertEquals(expected, result);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername("user1");
    }

    @Test
    void addExistUser() {
        User user = new User();
        user.setUsername("user");
        String result = registrationController.addUser(user, new HashMap<>());
        String expected = "reg";
        assertEquals(expected, result);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername("user");
    }
}