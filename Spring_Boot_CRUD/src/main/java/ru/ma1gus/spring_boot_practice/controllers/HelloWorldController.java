package ru.ma1gus.spring_boot_practice.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {

    @Value("${hello}")
    private String helloWorld;

    @GetMapping("/hello_world")
    public String hello() {
        System.out.println(this.helloWorld);
        return "hello_world";
    }
}
