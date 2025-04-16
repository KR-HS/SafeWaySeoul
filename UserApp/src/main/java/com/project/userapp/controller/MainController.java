package com.project.userapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {



    @GetMapping("/*")
    public String loading() {
        return "loading";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/child")
    public String child(){
        return "/user/mypage";
    }
    @GetMapping("/tracing")
    public String tracing(){
        return "tracing";
    }
}
