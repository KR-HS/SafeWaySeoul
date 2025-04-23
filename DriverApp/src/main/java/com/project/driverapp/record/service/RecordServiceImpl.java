package com.project.driverapp.record.service;

import com.project.driverapp.command.RecordVO;
import com.project.driverapp.record.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<RecordVO> getRecordList(Integer driverKey) {
        return recordMapper.getRecordList(driverKey);
    }

    @Override
    public int getRemainPassengerCount(Integer recordKey) {
        return recordMapper.getRemainPassengerCount(recordKey);
    }
}
