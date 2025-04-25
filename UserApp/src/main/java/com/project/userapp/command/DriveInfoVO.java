package com.project.userapp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriveInfoVO {
    private Integer driveInfoKey;
    private String driveInfoName;
    private String driveCarName;
    private Integer userKey;
}
