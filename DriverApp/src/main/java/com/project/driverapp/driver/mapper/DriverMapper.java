package com.project.driverapp.driver.mapper;

import com.project.driverapp.command.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DriverMapper {
    int idCheck(String userId);//아이디 중복체크
    int register(DriverVO userVO); // 회원가입
    int registerInfo(DriveInfoVO driveInfoVO);
    DriverVO findInfo(DriverVO vo);// 회원정보 찾기(아이디찾기, 비밀번호찾기, 로그인)
    int modify(DriverVO userVO);// 회원정보 변경


    List<ChildrenVO> manageOfChildren(int recordKey);
    List<KinderVO> findKinderForDriver(int driverKey); //기사의 유치원 정보
    int updateDropState(
            @Param("recordKey") int recordKey,
            @Param("childKey") int childKey,
            @Param("dropState") String dropState);

    List<Integer> getDriveInfoKey(String time);
    int registRecordDailyAM(Integer key);
    int registRecordDailyPM(Integer key);
    List<RecordMatchVO> getRecordMatachInfo();
    int registRecordMatchDaily(RecordMatchVO vo);



}


