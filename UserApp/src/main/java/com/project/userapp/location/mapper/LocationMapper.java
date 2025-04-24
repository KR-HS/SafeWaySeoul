package com.project.userapp.location.mapper;

import com.project.userapp.command.ChildrenVO;
import com.project.userapp.command.LocationVO;
import com.project.userapp.command.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LocationMapper {
    void registLocation(LocationVO vo);

    List<ChildrenVO> mychildRoutebyrecordKey(int childKey);
}
