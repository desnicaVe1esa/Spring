package ru.ma1gus.Spring_REST_practice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloRESTController {

    @GetMapping("/hello")
    public String hello(){
        return "Чисти говно блядь";
    }
}
