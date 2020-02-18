package com.alevel.finalProject.Doka.Doka.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    @PostMapping("/hello-world")
    public HelloWorldResponse helloWorld(@RequestBody HelloWorldRequest request) {
        System.out.println(request.text);
        return new HelloWorldResponse();
    }

}