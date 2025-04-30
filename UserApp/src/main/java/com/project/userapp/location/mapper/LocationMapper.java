package com.project.userapp.location.mapper;

import com.project.userapp.command.ChildrenVO;
import com.project.userapp.command.LocationVO;
import com.project.userapp.command.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.List;

@Mapper
public interface LocationMapper {
    void registLocation(LocationVO vo);


    //내 아이의 집오는 경로를 탐색하기
    List<ChildrenVO> mychildRoutebyrecordKey(int childKey);


    List<LocationVO> selectByRecordKey(@Param("recordKey") int recordKey);

    //한아이의 배차의 상태 조회하기
    ChildrenVO recordStateFromChild(@Param("childKey") int childKey,@Param("recordKey") int recordKey);

}
