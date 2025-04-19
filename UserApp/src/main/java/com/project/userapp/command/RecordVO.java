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
public class RecordVO {
    private Integer recordKey;
    private Timestamp recordStartTime;
    private Timestamp recordEndTime;
    private String recordState;
    private Integer driverKey;
}
