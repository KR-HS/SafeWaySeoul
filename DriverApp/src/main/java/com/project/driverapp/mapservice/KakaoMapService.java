package com.project.driverapp.mapservice;
import com.project.driverapp.command.KakaoRouteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class KakaoMapService {

    private final RestTemplate restTemplate;

    @Value("${kakao.restapi.key}")
    private String restApiKey;

    // RestTemplate을 Bean으로 주입받는 방식 추천!
    public KakaoMapService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Double> getRoute(double startX, double startY, double endX, double endY) {
        String url = String.format(
                "https://apis-navi.kakaomobility.com/v1/directions?origin=%f,%f&destination=%f,%f",
                startX, startY, endX, endY
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + restApiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoRouteResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    KakaoRouteResponse.class
            );

            KakaoRouteResponse body = response.getBody();
            if (body == null || body.getRoutes() == null || body.getRoutes().isEmpty()) {
                throw new IllegalStateException("카카오모빌리티 API에서 경로 데이터가 오지 않았습니다.");
            }

            // 여러 section/road가 있을 수 있으니, 모든 vertexes를 합쳐서 반환 (실제 경로를 위해)
            List<KakaoRouteResponse.Route> routes = body.getRoutes();
            List<Double> allVertexes = new java.util.ArrayList<>();
            for (KakaoRouteResponse.Section section : routes.get(0).getSections()) {
                for (KakaoRouteResponse.Road road : section.getRoads()) {
                    allVertexes.addAll(road.getVertexes());
                }
            }
            return allVertexes;

        } catch (RestClientException e) {
            // 로그 남기기
            System.err.println("카카오모빌리티 API 호출 실패: " + e.getMessage());
            throw new RuntimeException("카카오모빌리티 API 호출 실패", e);
        }
    }
}