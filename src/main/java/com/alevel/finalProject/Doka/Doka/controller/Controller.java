package com.alevel.finalProject.Doka.Doka.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class Controller {

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }


}