package com.project.driverapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/driver")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }
    @GetMapping("/join")
    public String join(){
        return "login/join";
    }
    @PostMapping("/joinForm")
    public String joinForm() {
        return "redirect:/home";
    }

}
