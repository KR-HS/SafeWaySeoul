package com.project.driverapp.driver.service;


import com.project.driverapp.command.*;
import com.project.driverapp.driver.mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverMapper driverMapper;


    // ✅ 매일 새벽 0시에 실행
    @Scheduled(cron = "0 0 12 * * *")
    @Transactional
    public void scheduledUpdate() {
        int result = 0;
        // 오전, 오후에 맞는 driveInfoKey받기
        List<Integer> am = getDriveInfoKey("오전");
        List<Integer> pm = getDriveInfoKey("오후");

        // record테이블에 추가
        registRecordDailyAM(am);
        registRecordDailyPM(pm);

        // recordMatch테이블에 필요한 데이터 가져오기(km_key, record_key)
        List<RecordMatchVO> list = getRecordMatachInfo();

        // recordMatch에도 추가
        registRecordMatchDaily(list);

        System.out.println("기사 운행정보 추가");
    }

    // 테스트용, 서버실행시 한번 실행

    // kindermatch먼저 넣고 실행 -> recordMatch 들어감
//    @PostConstruct
//    @Transactional
//    public void scheduledUpdateTest() {
//        // 오전, 오후에 맞는 driveInfoKey받기
//        List<Integer> am = getDriveInfoKey("오전");
//        List<Integer> pm = getDriveInfoKey("오후");
//
//        // record테이블에 추가
//        registRecordDailyAM(am);
//        registRecordDailyPM(pm);
//
//        // recordMatch테이블에 필요한 데이터 가져오기(km_key, record_key)
//        List<RecordMatchVO> list = getRecordMatachInfo();
//
//        // recordMatch에도 추가
//        registRecordMatchDaily(list);
//
//
//        System.out.println("기사 운행정보 추가<테스트>");
//    }



    @Override
    public int idCheck(String userId) {
        // false면 아이디 중복, true면 아이디 없음(회원가입 가능)
        return driverMapper.idCheck(userId);
    }

    @Override
    public int register(DriverVO userVO) {
        return driverMapper.register(userVO);
    }

    @Override
    public int registerInfo(DriveInfoVO driveInfoVO) {
        return driverMapper.registerInfo(driveInfoVO);
    }


    @Override
    public DriverVO findInfo(DriverVO vo) {
        return driverMapper.findInfo(vo);
    }


    @Override
    public int modify(DriverVO driverVO) {
        return driverMapper.modify(driverVO);
    }

    @Override
    public List<ChildrenVO> manageOfChildren(int recordKey) {
        return driverMapper.manageOfChildren(recordKey);
    }

    @Override
    public List<KinderVO> findKinderForDriver(int driverKey) {
        return driverMapper.findKinderForDriver(driverKey);
    }

    @Override
    public int updateDropState(int recordKey, int childKey, String dropState) {
        return driverMapper.updateDropState(recordKey, childKey, dropState);
    }





    // 자동적으로 기사 운행예정 리스트 매일 넣어주는 부분
    // 0. 오전, 오후에 맞는 driveInfo키 받아오기
    @Override
    public List<Integer> getDriveInfoKey(String time) {
        return driverMapper.getDriveInfoKey(time);
    }
    // 1. record테이블에 기록추가
    @Override
    public int registRecordDailyAM(List<Integer> list) {
        int result = 0;
        for(Integer i : list) {
            result = driverMapper.registRecordDailyAM(i);
        }
        return result;
    }

    @Override
    public int registRecordDailyPM(List<Integer> list) {
        int result = 0;
        for(Integer i : list) {
            result = driverMapper.registRecordDailyPM(i);
        }
        return result;
    }

        // 2. recordMatch테이블에 넣을 recordKey와 km_key받아오기(아이의 픽업여부가 Y이고 오늘의 레코드에 대해서만)
    @Override
    public List<RecordMatchVO> getRecordMatachInfo() {
        return driverMapper.getRecordMatachInfo();
    }

        // 3. recordMatch테이블에 기록추가
    @Override
    public int registRecordMatchDaily(List<RecordMatchVO> list) {
        int result = 0;
        for(RecordMatchVO RMvo : list){
            result = driverMapper.registRecordMatchDaily(RMvo);
        }
        return result;
    }
}
