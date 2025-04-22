package com.project.driverapp.command;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequest {
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private List<Waypoint> waypoints;
}
