package com.project.userapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }
    @GetMapping("/join")
    public String join() {
        return "login/join";
    }
    @PostMapping("/joinForm")
    public String joinForm() {
        // 회원가입 기능 추가 필요 -----

        return "home";
    }

}
