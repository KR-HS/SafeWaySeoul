package com.project.userapp.command;

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
    private String longitude;
    private Timestamp locationRegtime;
    private Integer recKey;
}
