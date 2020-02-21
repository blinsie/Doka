package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.domain.Role;
import com.alevel.finalProject.Doka.Doka.domain.User;
import com.alevel.finalProject.Doka.Doka.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/reg")
    public String reg(Model model) {
        model.addAttribute("message", "");
        User user = new User("1", "1");
        userRepository.save(user);
        return ("/reg");
    }

    @PostMapping("/reg")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            model.put("message", "User exists!");
            return "reg";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        System.out.println(user.toString());
        userRepository.save(user);

        return "redirect:/login";
    }

}
