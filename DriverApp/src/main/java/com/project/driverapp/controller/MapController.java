package com.project.driverapp.controller;

import com.project.driverapp.command.RouteRequest;
import com.project.driverapp.mapservice.KakaoMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MapController {

    private final KakaoMapService kakaoMapService;

    @PostMapping("/route")
    public ResponseEntity<List<Double>> getRouteWithWaypoints(
            @RequestBody RouteRequest request
    ) {
        List<Double> coordinates = kakaoMapService.getRoute(
                request.getStartX(), request.getStartY(),
                request.getEndX(), request.getEndY(),
                request.getWaypoints()
        );
        return ResponseEntity.ok(coordinates);
    }
}