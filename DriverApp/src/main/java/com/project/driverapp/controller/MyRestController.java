package com.project.driverapp.controller;

import com.project.driverapp.driver.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MyRestController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/user/checkId")
    public int checkId(@RequestBody Map<String, String> map) {
        return driverService.idCheck(map.get("userId"));
    }
}
