package ru.ma1gus.practice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/hello")
    public String hello(HttpServletRequest httpServletRequest) {
        String name = httpServletRequest.getParameter("name");
        String surname = httpServletRequest.getParameter("surname");
        System.out.println("Yo " + name + " " + surname);
        return "home/hello";
    }

    @GetMapping("/bye")
    public String bye(@RequestParam(value = "name", required = false) String name,
                      @RequestParam(value = "surname", required = false) String surname,
                      Model model) {
        model.addAttribute("message", "See ya " + name + " " + surname);
        System.out.println("See ya " + name + " " + surname);
        return "home/bye";
    }

    @GetMapping("/calculator")
    public String calculator(@RequestParam("a") double a,
                             @RequestParam("b") double b,
                             @RequestParam("action") String action,
                             Model model) {
        double result;
        String warning;
        switch (action) {
            case "addition":
                result = a + b;
                model.addAttribute("result", result);
                break;
            case "subtraction":
                result = a - b;
                model.addAttribute("result", result);
                break;
            case "multiplication":
                result = a * b;
                model.addAttribute("result", result);
                break;
            case "division":
                result = a / b;
                model.addAttribute("result", result);
                break;
            default:
                warning = "Ты быканул или мне показалось?";
                model.addAttribute("warning", warning);
                break;
        }
        return "home/calculator";
    }
}
