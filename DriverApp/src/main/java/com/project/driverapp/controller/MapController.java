package com.project.driverapp.controller;

import com.project.driverapp.mapservice.KakaoMapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/map")
public class MapController {

    private final KakaoMapService kakaoMapService;

    public MapController(KakaoMapService kakaoMapService) {
        this.kakaoMapService = kakaoMapService;
    }

    @GetMapping("/route")
    public ResponseEntity<List<Double>> getRoute(
            @RequestParam double startX,
            @RequestParam double startY,
            @RequestParam double endX,
            @RequestParam double endY
    ) {
        List<Double> coordinates = kakaoMapService.getRoute(startX, startY, endX, endY);
        return ResponseEntity.ok(coordinates);
    }
}
