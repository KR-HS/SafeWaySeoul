package com.project.driverapp.record.mapper;

import com.project.driverapp.command.RecordVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {
    List<RecordVO> getRecordList(Integer driverKey);
}
