package com.project.userapp.kinder.mapper;

import com.project.userapp.command.KinderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KinderMapper {
    List<KinderVO> getKinderList();
}
