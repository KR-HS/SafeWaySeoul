package com.project.driverapp.driver.mapper;

import com.project.driverapp.command.DriverVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DriverMapper {
    int idCheck(String userId);//아이디 중복체크
    int register(DriverVO userVO); // 회원가입
    DriverVO findInfo(DriverVO vo);// 회원정보 찾기(아이디찾기, 비밀번호찾기, 로그인)
    int modify(DriverVO userVO);// 회원정보 변경
}
