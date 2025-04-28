package com.project.userapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community")
public class CommunityController {


    @GetMapping("/postList")
    public String postList() {
        return "community/postList";
    }

    @GetMapping("/postDetail")
    public String postDetail() {return "community/postDetail";}
}
