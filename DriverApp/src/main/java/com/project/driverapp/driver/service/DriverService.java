package com.project.driverapp.driver.service;


import com.project.driverapp.command.DriverVO;

public interface DriverService {

    boolean idCheck(String userId);//아이디 중복체크
    int register(DriverVO userVO); // 회원가입
    DriverVO findInfo(DriverVO vo);// 회원정보 찾기
    int modify(DriverVO userVO);// 회원정보 변경
}
