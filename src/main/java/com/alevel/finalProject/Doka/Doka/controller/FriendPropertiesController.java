package com.alevel.finalProject.Doka.Doka.controller;

import com.alevel.finalProject.Doka.Doka.db.entity.User;
import com.alevel.finalProject.Doka.Doka.db.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * This class control friends view. It can add/delete friend from user friend list
 */
@Slf4j
@Controller
public class FriendPropertiesController {
    private final UserRepository userRepository;

    public FriendPropertiesController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/friends")
    public String friendListPage(Model model, Principal principal) {
        List<User> users = userRepository.findAll();
        User userFromDB = userRepository.findByUsername(principal.getName());
        users.remove(userFromDB);
        List<String> usersName = users.stream().map(User::getUsername).collect(Collectors.toList());
        List<Integer> friendsId = userFromDB.getFriend_list_id();
        List<String> friendsName = new ArrayList<>();
        for (Integer i : friendsId) {
            Optional<User> get = userRepository.findById(i);
            get.ifPresent(user -> friendsName.add(user.getUsername()));
        }
        log.info("Load friends list for {}", principal.getName());
        model.asMap().put("usersName", usersName);
        model.asMap().put("friendList", friendsName);
        return "friends";
    }

    @GetMapping("/friends/add/{friend}")
    public String addFriendByName(Principal principal,
                                 @PathVariable String friend) {
        log.info("Trying to add {} to {} friend list",friend ,principal.getName());
        User forAddToFriend = userRepository.findByUsername(friend);
        User presentUser = userRepository.findByUsername(principal.getName());
        List<Integer> friendsId = presentUser.getFriend_list_id();
        if (friendsId == null) {
            friendsId = new ArrayList<>();
        }
        if (!friendsId.contains(forAddToFriend.getId()) && !friend.equals(principal.getName())) {
            friendsId.add(forAddToFriend.getId());
            presentUser.setFriend_list_id(friendsId);
            userRepository.save(presentUser);
            log.info("Save {} with new friend {}", principal.getName(), friend);
        } else {
            log.info("Error! {} is already friends with {} or NoSuchFriendExeprion", friend, principal.getName());
        }
        return "redirect:/friends";
    }

    @GetMapping("/friends/delete/{friend}")
    public String deleteFriendByName(@PathVariable String friend, Principal principal) {
        log.info("Trying to delete {} from {} friend list",friend ,principal.getName());
        User forDeleteFromFriend = userRepository.findByUsername(friend);
        User presentUser = userRepository.findByUsername(principal.getName());
        presentUser.getFriend_list_id().remove(forDeleteFromFriend.getId());
        userRepository.save(presentUser);
        log.info("Save {} without friend {}", principal.getName(), friend);
        return "redirect:/friends";
    }


}
