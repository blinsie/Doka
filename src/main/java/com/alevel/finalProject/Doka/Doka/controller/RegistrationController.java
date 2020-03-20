package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.User;
import com.alevel.finalProject.Doka.Doka.db.repos.UserRepository;
import com.alevel.finalProject.Doka.Doka.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * This class control user registration view
 */
@Slf4j
@Controller
public class RegistrationController {
    private final UserRepository userRepository;
    private final UserService userService;

    public RegistrationController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/reg")
    public String reg(Model model) {
        model.addAttribute("message", "");
        return ("/reg");
    }

    @PostMapping("/reg")
    public String addUser(User user, Map<String, Object> model) {
        boolean isAddCompleate = userService.addUser(user);
        if (!isAddCompleate) {
            model.put("message", "User exists!");
            log.warn("Someone try to add exist user: {}", user.getUsername());
            return "reg";
        }
        return "redirect:/login";
    }

}
