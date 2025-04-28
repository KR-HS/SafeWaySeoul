package com.project.driverapp.record.service;

import com.project.driverapp.command.RecordVO;

import java.util.List;

public interface RecordService {
    List<RecordVO> getRecordList(Integer driverKey);
    int getRemainPassengerCount(Integer recordKey);
    int updateDriveStateFinish(Integer recordKey);
    int updateDriveStateStart(Integer recordKey);
}
