package com.project.driverapp.location;

import com.project.driverapp.command.LocationVO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

@Component
public class LocationSender {

    private final RestTemplate restTemplate = new RestTemplate();
    private boolean isDriving = false; // 상태 플래그
    private int recordKey;

//    @Scheduled(fixedRate = 1000)
//    public void sendLocation() {
//        if (!isDriving) return; // 운행 중이 아니면 전송 X
//
//        double lat = 37.123 + Math.random() * 0.01;
//        double lng = 126.456 + Math.random() * 0.01;
//
//        LocationVO location = LocationVO.builder()
//                .recordKey(recordKey)
//                .latitude(String.valueOf(lat))
//                .longitude(String.valueOf(lng))
//                .build();
//
//        System.out.println("운행 중, 좌표 전송: " + lat + ", " + lng);
//
//        restTemplate.postForObject("http://localhost:8082/location/update", location, Void.class);
//    }

    // 컨트롤러에서 호출
    public void startDriving(int recordKey) {
        this.recordKey = recordKey;
        this.isDriving = true;
    }

    public void stopDriving() {
        this.isDriving = false;
    }

    public void sendLocation(LocationVO location) {
        if (!isDriving || location.getRecordKey() != this.recordKey) return;

        System.out.println("운행 중(클라이언트 기반), 좌표 전송: " + location.getLatitude() + ", " + location.getLongitude());

        restTemplate.postForObject("http://localhost:8082/location/update", location, Void.class);
//        restTemplate.postForObject("https://1166-218-153-162-9.ngrok-free.app/location/update", location, Void.class);
    }
}
