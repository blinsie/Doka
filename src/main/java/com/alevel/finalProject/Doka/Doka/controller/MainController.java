package com.alevel.finalProject.Doka.Doka.controller;



import com.alevel.finalProject.Doka.Doka.domain.Message;
import com.alevel.finalProject.Doka.Doka.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessageRepository messageRepository;


    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main() {

        return "main";
    }

   /* @PostMapping("/main")
    public String add(@RequestParam String name, @RequestParam String email, Map<String, Object> model) {
        User user = new User(name, email);
        userRepository.save(user);
        Iterable<User> users = userRepository.findAll();
        model.put("users", users);
        return "main";
    }*/

    @PostMapping("/main")
    public String addMsg(@RequestParam String msg, Map<String, Object> model) {
        Message message = new Message(msg);
        messageRepository.save(message);


        System.out.println("Message found with findAll():");
        System.out.println("-------------------------------");
        for (Message m : messageRepository.findAll()) {
            System.out.println(m);
        }
        System.out.println();
        return "main";
    }
}
