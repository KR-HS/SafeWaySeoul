package com.project.userapp.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
