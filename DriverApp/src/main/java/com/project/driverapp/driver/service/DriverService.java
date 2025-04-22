package com.project.driverapp.driver.service;


import com.project.driverapp.command.ChildrenVO;
import com.project.driverapp.command.DriverVO;
import com.project.driverapp.command.KinderVO;

import java.util.List;

public interface DriverService {

    int idCheck(String userId);//아이디 중복체크
    int register(DriverVO userVO); // 회원가입
    DriverVO findInfo(DriverVO vo);// 회원정보 찾기
    int modify(DriverVO userVO);// 회원정보 변경

    List<ChildrenVO> manageOfChildren(int recordKey);
    KinderVO findKinderForDriver(int driverKey); //기사의 유치원 정보
}
