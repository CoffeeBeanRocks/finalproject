package com.example.c322.finalproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class GreetingController {

    //Get https//:localhost:8080/test
    @GetMapping("/test")
    public String greetings() {
        return "Banking System!";
    }
}
