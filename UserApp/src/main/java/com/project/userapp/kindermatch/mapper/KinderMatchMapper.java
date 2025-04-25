package com.project.userapp.kindermatch.mapper;

import com.project.userapp.command.KinderMatchVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KinderMatchMapper {

    int deleteByChildKey(Integer childKey);
    int insertMatch(KinderMatchVO vo);
    int updatePickup(KinderMatchVO vo);



}
