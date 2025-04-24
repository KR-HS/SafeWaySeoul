package com.project.userapp.controller;


import com.project.userapp.command.RouteRequest;
import com.project.userapp.mapservice.KakaoMapService;
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
