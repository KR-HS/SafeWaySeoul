package com.example.websocketserver.controller;

import com.example.websocketserver.dto.LocationDTO;
import com.example.websocketserver.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
public class LocationController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private LocationMapper locationMapper;

    // DriverApp이 실시간 좌표를 보내는 엔드포인트
    @PostMapping("/location/update")
    public void receiveLocationFromDriver(@RequestBody LocationDTO location) {

        // 1. DB에 저장
        locationMapper.insertLocation(location);

        // 2. recordKey 채널에 실시간 브로드캐스트 (UserApp이 구독)
        messagingTemplate.convertAndSend(
                "/topic/location/update/" + location.getRecordKey(),
                location
        );

        System.out.println("받은 위치 데이터: " + location);
        System.out.println("브로드캐스트 채널: /topic/location/update/" + location.getRecordKey());
    }
}