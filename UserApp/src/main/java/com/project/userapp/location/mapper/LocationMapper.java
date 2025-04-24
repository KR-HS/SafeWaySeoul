package com.project.userapp.location.mapper;

import com.project.userapp.command.LocationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LocationMapper {
    void registLocation(LocationVO vo);

    List<LocationVO> selectByRecordKey(@Param("recordKey") int recordKey);

}
