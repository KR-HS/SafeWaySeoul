package com.project.userapp.kinder.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.userapp.command.KinderVO;
import com.project.userapp.command.LocationVO;
import com.project.userapp.kinder.mapper.KinderMapper;
import com.project.userapp.location.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class KinderServiceImpl implements KinderService {

    @Autowired
    private KinderMapper kinderMapper;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private RestTemplate restTemplate;

    private final String apiKey = "61746e657467687333367350594a73";
    private final int pageSize= 1000;
    @Override
    public List<KinderVO> getKinderList() {
        return kinderMapper.getKinderList();
    }

    // ✅ 서버 실행 직후 1회 실행
    @PostConstruct
    public void initOnStartup() {
        System.out.println("🚀 서버 실행 후 Kinder 데이터 수집 시작");
        registKinderAPI();
    }

    // ✅ 매일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 * * *")
    public void scheduledUpdate() {
        System.out.println("⏰ 주기적 Kinder 데이터 수집 시작");
        registKinderAPI();
    }

    @Override
    public void registKinderAPI() {
        int pageSize = 1000;
        int startIndex = 1;

        // 1. 총 개수 확인
        String countUrl = String.format(
                "http://openapi.seoul.go.kr:8088/%s/json/ChildCareInfo/1/1/",
                apiKey
        );
        JsonNode countRoot = restTemplate.getForObject(countUrl, JsonNode.class);
        int totalCount = countRoot.path("ChildCareInfo").path("list_total_count").asInt();
        System.out.println("총 어린이집 수: " + totalCount);

        // 2. 페이징 돌면서 가져오기
        while (startIndex <= totalCount) {
            int endIndex = Math.min(startIndex + pageSize - 1, totalCount);

            String url = String.format(
                    "http://openapi.seoul.go.kr:8088/%s/json/ChildCareInfo/%d/%d/",
                    apiKey, startIndex, endIndex
            );

            System.out.println("요청: " + url);

            JsonNode root = restTemplate.getForObject(url, JsonNode.class);
            JsonNode rows = root.path("ChildCareInfo").path("row");
            System.out.println("row 수: " + rows.size());

            for (JsonNode node : rows) {
                KinderVO vo = KinderVO.builder()
                        .kinderName(node.path("CRNAME").asText())
                        .kinderPhone(node.path("CRTELNO").asText())
                        .kinderPostcode(node.path("ZIPCODE").asText())
                        .kinderAddress(node.path("CRADDR").asText())
                        .kinderCapacity(node.path("CRCHCNT").asInt())
                        .kinderWeekendOpen(node.path("CRSPEC").asText().contains("휴일") ? "Y" : "N")
                        .kinderNightOpen(node.path("CRSPEC").asText().contains("야간") ? "Y" : "N")
                        .build();

                String abolished = node.path("CRABLDT").asText();
                if (abolished != null && !abolished.trim().isEmpty()) continue;

                if (!kinderMapper.existsByNameAndPhone(vo.getKinderName(), vo.getKinderPhone())) {
                    kinderMapper.insertKinder(vo);
                    LocationVO locationVO = LocationVO.builder().
                            latitude(node.path("LA").asText()).
                            longitude(node.path("LO").asText()).
                            kinderKey(vo.getKinderKey())
                            .build();
                    locationMapper.registLocation(locationVO);
                }
            }

            startIndex += pageSize;
        }
    }
}
