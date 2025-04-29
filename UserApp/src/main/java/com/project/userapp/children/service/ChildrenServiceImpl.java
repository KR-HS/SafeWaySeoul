package com.project.userapp.children.service;

import com.project.userapp.children.mapper.ChildrenMapper;
import com.project.userapp.command.*;
import com.project.userapp.files.mapper.FilesMapper;
import com.project.userapp.kindermatch.mapper.KinderMatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChildrenServiceImpl implements ChildrenService{

    @Autowired
    private ChildrenMapper childrenMapper;

    @Autowired
    private FilesMapper fileMapper;

    @Autowired
    private KinderMatchMapper kinderMatchMapper;


    @Value("${com.project.userapp.upload.path}")
    private String uploadPath;

    // 폴더생성함수
    private String makeFolder(){
        String filepath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        File file = new File(uploadPath +"/"+ filepath);
        if(file.exists()==false){ // 해당위치에 파일 or 폴더가 존재하지 않으면 if문 작동
            file.mkdirs();
        }
        return filepath;
    }

    @Override
    @Transactional
    public int registChild(ChildrenVO vo, MultipartFile file, Integer kinderKey) {
        int result = childrenMapper.registChild(vo);

        // 자녀 프로필사진 등록
        if (!file.isEmpty()) {
            try {
                String originName = file.getOriginalFilename();
                String filename = originName.substring(originName.lastIndexOf("/") + 1);

                UUID uuid = UUID.randomUUID();
                String filepath = makeFolder();
                String path = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;

                File saveFile = new File(path);
                file.transferTo(saveFile);

                fileMapper.registFile(FileVO.builder()
                        .fileUuid(uuid.toString())
                        .filePath(filepath)
                        .fileName(filename)
                        .childKey(vo.getChildKey())
                        .userKey(vo.getParentKey())  // 유저키도 추가
                        .build());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Transactional
    @Override
    public int deleteChild(Integer childKey) {

        // 1. KINDERMATCH 테이블에서도 삭제
        kinderMatchMapper.deleteByChildKey(childKey);

        // 2. FILE 테이블에서도 삭제
        fileMapper.deleteByChildKey(childKey);

        // 3. CHILDREN 테이블에서도 삭제
        return childrenMapper.deleteChild(childKey);
    }

    @Override
    public List<ChildrenVO> myChildren(Integer parentKey) {

        List<ChildrenVO> list = childrenMapper.myChildren(parentKey);

        // 1. child_key별로 데이터를 그룹화
        Map<Integer, List<ChildrenVO>> groupedByChildKey = list.stream()
                .collect(Collectors.groupingBy(ChildrenVO::getChildKey));

        list = groupedByChildKey.values().stream()
                .flatMap(childGroup -> {
                    // 그룹 내에서 recordVO가 존재하는 것 중 하나를 찾음
                    RecordVO representativeRecord = childGroup.stream()
                            .map(ChildrenVO::getRecordVO) // ChildrenVO에서 recordVO 꺼내기
                            .filter(Objects::nonNull) // recordVO에서 null이 아닌 값 찾기
                            .filter(record -> {
                                // 지금 시간 가져오기
                                LocalDateTime now = LocalDateTime.now();

                                // 오늘 날짜의 00시 00분 00초
                                LocalDateTime todayMidnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
                                System.out.println("오늘 0시 0분 0초 :" + todayMidnight);

                                // 오늘 날짜의 12시 00분 00초
                                LocalDateTime todayNoon = LocalDateTime.of(LocalDate.now(), LocalTime.NOON);
                                System.out.println("오늘 12시 00분 00초: " + todayNoon);

                                // 내일 날짜의 00시 00분 00초
                                LocalDateTime tomorrowMidnight = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT);

                                // `record.getRecordStartTime()`이 `Timestamp` 타입일 경우
                                Timestamp recordStartTime = record.getRecordStartTime();
                                LocalDateTime recordDateTime = recordStartTime.toLocalDateTime(); // Timestamp -> LocalDateTime
                                System.out.println("Record Start Time: " + recordStartTime);
                                System.out.println("Record DateTime: " + recordDateTime);

                                // 조건: 현재 시간이 오전인지 오후인지 판단하여, 해당 구간의 RecordStartTime을 확인
                                if (now.isBefore(todayNoon)) { // 오전 구간: 00시~12시
                                    // 오전 구간에 해당하는지 확인
                                    boolean isInMorning = recordDateTime.isAfter(todayMidnight) && recordDateTime.isBefore(todayNoon);
                                    System.out.println(now + " 오전, " + isInMorning);
                                    return isInMorning;
                                } else { // 오후 구간: 12시~24시
                                    // 오후 구간에 해당하는지 확인
                                    boolean isInAfternoon = recordDateTime.isAfter(todayNoon) && recordDateTime.isBefore(tomorrowMidnight);
                                    System.out.println(now + " 오후, " + isInAfternoon);
                                    return isInAfternoon;
                                }
                            })
                            .findFirst() // 조건을 만족하는 첫 번째 값을 찾기
                            .orElse(null); // 없으면 null이 아닌 다른 기본값을 반환할 수도 있지만, null로 유지 가능

                    // 각 ChildrenVO에 대해 대표 recordVO를 세팅
                    return childGroup.stream()
                            .filter(child -> {
                                // child.getRecordVO()가 representativeRecord와 일치하는지 확인
                                if (representativeRecord == null) {
                                    return child.getRecordVO() == null; // 대표 레코드가 null이면 child의 recordVO도 null이어야만 일치
                                }
                                return representativeRecord.equals(child.getRecordVO()); // 대표 레코드와 child의 recordVO가 일치하는지 확인
                            })
                            .map(child -> {
                                // 일치하는 경우만 대표 recordVO 설정
                                child.setRecordVO(representativeRecord);
                                return child;
                            });
                })
                .collect(Collectors.toList());




        for (ChildrenVO vo : list) {
            FileVO fileVO = fileMapper.selectProfileByChild(vo.getChildKey());
            if (fileVO != null) {
                String imageUrl = "/upload/" + fileVO.getFilePath() + "/" + fileVO.getFileUuid() + "_" + fileVO.getFileName();
                vo.setProfileImageUrl(imageUrl);
            }
        }
        System.out.println("********"+ list.toString());
        System.out.println(list.size());
        return list;
    }

    @Override
    public ChildrenVO getChildDetail(Integer childKey) {
        ChildrenVO vo = childrenMapper.getChildDetail(childKey);
        FileVO fileVO = fileMapper.selectProfileByChild(childKey);
        if (fileVO != null) {
            String imageUrl = "/upload/" + fileVO.getFilePath() + "/" + fileVO.getFileUuid() + "_" + fileVO.getFileName();
            vo.setProfileImageUrl(imageUrl);
        }
        return vo;
    }

    @Override
    @Transactional
    public int updateChild(ChildrenVO vo, MultipartFile file) {
        int result = childrenMapper.updateChild(vo);

        if (!file.isEmpty()) {
            try {
                String originName = file.getOriginalFilename();
                String filename = originName.substring(originName.lastIndexOf("/") + 1);

                UUID uuid = UUID.randomUUID();
                String filepath = makeFolder();
                String path = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;

                File saveFile = new File(path);
                file.transferTo(saveFile);

                fileMapper.registFile(FileVO.builder()
                        .fileUuid(uuid.toString())
                        .filePath(filepath)
                        .fileName(filename)
                        .childKey(vo.getChildKey())
                        .userKey(vo.getParentKey())
                        .build());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }





}
