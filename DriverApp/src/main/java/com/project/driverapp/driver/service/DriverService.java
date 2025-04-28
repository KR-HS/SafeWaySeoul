package com.project.driverapp.driver.service;


import com.project.driverapp.command.*;

import java.util.List;

public interface DriverService {

    int idCheck(String userId);//아이디 중복체크
    int register(DriverVO userVO); // 회원가입
    int registerInfo(DriveInfoVO driveInfoVO);
    DriverVO findInfo(DriverVO vo);// 회원정보 찾기
    int modify(DriverVO userVO);// 회원정보 변경

    List<ChildrenVO> manageOfChildren(int recordKey);
    List<KinderVO> findKinderForDriver(int driverKey); //기사의 유치원 정보
    int updateDropState(int recordKey, int childKey, String dropState);

    List<Integer> getDriveInfoKey(String time);
    int registRecordDailyAM(List<Integer> list);
    int registRecordDailyPM(List<Integer> list);
    List<RecordMatchVO> getRecordMatachInfo();
    int registRecordMatchDaily(List<RecordMatchVO> vo);


}
