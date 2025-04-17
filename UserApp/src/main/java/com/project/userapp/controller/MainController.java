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

    @GetMapping("/regChild")
    public String regChild(){ return "/modal/regChild";}

    @GetMapping("/tracing")
    public String tracing(){
        return "/user/tracing";
    }

    @GetMapping("/idFind")
    public String idFind(){
        return "/login/IdFind";
    }

    //로그인 성공창
    @GetMapping("/FindUserId")
    public String findUserIdPage() {
        return "FindUserId"; //
    }
    //비밀번호 재설정창
    @GetMapping("/updatePw")
    public String updatePw() {
        return "updatePw"; //
    }

}
