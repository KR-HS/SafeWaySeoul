package com.project.userapp.location.mapper;

import com.project.userapp.command.LocationVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocationMapper {
    void registLocation(LocationVO vo);
}
