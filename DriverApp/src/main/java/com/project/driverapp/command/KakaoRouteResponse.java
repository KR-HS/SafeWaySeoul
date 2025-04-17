package com.project.driverapp.command;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class KakaoRouteResponse {
    private List<Route> routes;

    @Getter @Setter
    public static class Route {
        private List<Section> sections;
    }
    @Getter @Setter
    public static class Section {
        private List<Road> roads;
    }
    @Getter @Setter
    public static class Road {
        private List<Double> vertexes; // [경도, 위도, 경도, 위도, ...]
    }
}