package com.project.driverapp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationVO {
    private Integer locationId;
    private String lattitude;
    private Timestamp locationRegtime;
    private Integer recKey;
}
