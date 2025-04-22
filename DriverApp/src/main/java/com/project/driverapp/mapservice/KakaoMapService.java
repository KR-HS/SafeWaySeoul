package com.project.driverapp.mapservice;

import com.project.driverapp.command.KakaoRouteResponse;
import com.project.driverapp.command.Waypoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KakaoMapService {

    private final RestTemplate restTemplate;

    @Value("${kakao.restapi.key}")
    private String restApiKey;

    public List<Double> getRoute(double startX, double startY,
                                 double endX, double endY,
                                 List<Waypoint> waypoints) {
        String url = "https://apis-navi.kakaomobility.com/v1/waypoints/directions";

        // 요청 바디 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("origin", Map.of("x", startX, "y", startY));
        requestBody.put("destination", Map.of("x", endX, "y", endY));

        if (waypoints != null && !waypoints.isEmpty()) {
            List<Map<String, Double>> waypointList = waypoints.stream()
                    .map(wp -> Map.of("x", wp.getX(), "y", wp.getY()))
                    .collect(Collectors.toList());
            requestBody.put("waypoints", waypointList);
        }

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + restApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // API 호출
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<KakaoRouteResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, KakaoRouteResponse.class);

        // 응답 처리
        KakaoRouteResponse body = response.getBody();
        return extractCoordinates(body);
    }

    private List<Double> extractCoordinates(KakaoRouteResponse response) {
        List<Double> coordinates = new ArrayList<>();
        response.getRoutes().forEach(route ->
                route.getSections().forEach(section ->
                        section.getRoads().forEach(road ->
                                road.getVertexes().forEach(coordinates::add)
                        )
                )
        );
        return coordinates;
    }
}
