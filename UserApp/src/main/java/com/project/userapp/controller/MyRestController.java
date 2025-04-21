package com.project.userapp.controller;

import com.project.userapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class MyRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/checkId")
    public int checkId(@RequestBody Map<String, String> map) {

        return userService.idCheck(map.get("userId")); // 예: 1이상이면 중복, 0이면 사용가능
    }
}
