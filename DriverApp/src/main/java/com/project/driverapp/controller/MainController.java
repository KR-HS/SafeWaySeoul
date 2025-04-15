package com.project.driverapp.controller;

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
    @GetMapping("/manage")
    public String manage() {
        return "driver/manage";
    }
    @GetMapping("/startManage")
    public String startManage() {return "modal/startManage";}

}