package com.project.userapp.location.service;

import com.project.userapp.command.KinderVO;
import com.project.userapp.command.LocationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocationService {
    void registLocation(LocationVO vo);
    List<LocationVO> selectByRecordKey(int recordKey);
}
