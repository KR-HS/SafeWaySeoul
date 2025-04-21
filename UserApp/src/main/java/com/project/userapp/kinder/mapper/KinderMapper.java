package com.project.userapp.kinder.mapper;

import com.project.userapp.command.KinderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KinderMapper {
    List<KinderVO> getKinderList();

    boolean existsByNameAndPhone(@Param("kinderName") String kinderName,@Param("kinderPhone") String kinderPhone);
    void insertKinder(KinderVO vo);
}
