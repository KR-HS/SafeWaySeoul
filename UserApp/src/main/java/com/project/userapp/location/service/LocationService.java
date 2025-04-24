package com.project.userapp.location.service;


import com.project.userapp.command.ChildrenVO;
import com.project.userapp.command.LocationVO;
import com.project.userapp.command.UserVO;

import java.util.List;

public interface LocationService {
    void registLocation(LocationVO vo);


    List<ChildrenVO> mychildRoutebyrecordKey(int childKey);


}
