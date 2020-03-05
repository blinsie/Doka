package com.alevel.finalProject.Doka.Doka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Slf4j
@Controller
public class VoiceRoomController {
    @GetMapping("/voice-room")
    public String view(Model model, Principal principal) {
        model.asMap().put("user_name", principal.getName());
        return "/voice-room";
    }
}
