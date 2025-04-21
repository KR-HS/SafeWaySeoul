package com.project.driverapp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KinderVO {
    private Integer kinderKey;
    private String kinderName;
    private String kinderPhone;
    private String kinderPostcode;
    private String kinderAddress;
    private String kinderOpenTime;
    private String kinderCloseTime;
    private String kinderWeekendOpen;
    private String kinderNightOpen;
    private Integer kinderCapacity;
}
