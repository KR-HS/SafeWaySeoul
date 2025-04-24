package com.project.driverapp.kinder.mapper;

import com.project.driverapp.command.KinderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KinderMapper {

    //주소 유효성 검사////////////////////
    //2. KinderVo -> 매퍼에서 주소로 DB 조회
    int checkAddress(@Param("kinderAddress") String kinderAddress,
                     @Param("kinderPostcode") String kinderPostcode,
                     @Param("kinderName") String kinderName);
    /// ///////////////////////////////

    List<KinderVO> getKinderList(String name);

}
