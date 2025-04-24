package com.project.driverapp.controller;

import com.project.driverapp.command.LocationVO;
import com.project.driverapp.location.LocationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver/location")
public class LocationController {

    @Autowired
    private LocationSender locationSender;

    @PostMapping("/start")
    public void start(@RequestParam int recordKey){
        locationSender.startDriving(recordKey);
    }

    @PostMapping("/stop")
    public void stop(){
        locationSender.stopDriving();
    }

    @PostMapping("/send")
    public void sendLocation(@RequestBody LocationVO location) {
        locationSender.sendLocation(location); // 운행 중일 때만 DB 저장 + WebSocket 전송
    }
}
