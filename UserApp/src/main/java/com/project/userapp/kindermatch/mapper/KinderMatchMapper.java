package com.project.userapp.kindermatch.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KinderMatchMapper {

    int deleteByChildKey(Integer childKey);

}
