package com.example.websocketserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private Integer locationId;
    private String latitude;
    private String longitude;
    private Timestamp locationRegtime;
    private Integer recordKey;
    private Integer userKey;
    private Integer kinderKey;
}
