package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.Role;
import com.alevel.finalProject.Doka.Doka.db.entity.User;
import com.alevel.finalProject.Doka.Doka.db.repos.ServerRepository;
import com.alevel.finalProject.Doka.Doka.db.repos.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    private final UserRepository userRepository;
    private final ServerRepository serverRepository;

    public RegistrationController(UserRepository userRepository, ServerRepository serverRepository) {
        this.userRepository = userRepository;
        this.serverRepository = serverRepository;
    }

    @GetMapping("/reg")
    public String reg(Model model) {
        model.addAttribute("message", "");
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


/*
        Server defaultServer = new Server();
        serverRepository.save(defaultServer);
        List<Server> userServerList = new ArrayList<>();
        user.setServerList(userServerList);
        user.addServer(defaultServer);
        defaultServer.setServer_name("defaultServer");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        defaultServer.setUsers(userList);
*/
        userRepository.save(user);

        return "redirect:/login";
    }

}
