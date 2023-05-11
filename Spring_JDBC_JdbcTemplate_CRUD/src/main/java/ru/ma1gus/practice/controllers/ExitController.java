package ru.ma1gus.practice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/out")
public class ExitController {

    @GetMapping("/exit")
    public String exit() {
        return "out/exit";
    }
}
