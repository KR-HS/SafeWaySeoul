package com.project.userapp.location.service;


import com.project.userapp.command.ChildrenVO;
import com.project.userapp.command.LocationVO;
import com.project.userapp.command.UserVO;

import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface LocationService {
    void registLocation(LocationVO vo);



    List<ChildrenVO> mychildRoutebyrecordKey(int childKey);

    List<LocationVO> selectByRecordKey(int recordKey);

}
