package com.project.userapp.children.service;

import com.project.userapp.children.mapper.ChildrenMapper;
import com.project.userapp.command.ChildrenVO;
import com.project.userapp.command.FileVO;
import com.project.userapp.command.RecordVO;
import com.project.userapp.files.mapper.FilesMapper;
import com.project.userapp.kindermatch.mapper.KinderMatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
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
        childrenMapper.registKinderRelation(vo.getChildKey(), kinderKey);

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

        System.out.println("*맵:"+groupedByChildKey.toString());
        // 2. 각 그룹에서 null인 recordVO를 제외하고, 매칭된 recordVO만 남기기
        list = groupedByChildKey.values().stream()
                .flatMap(childGroup -> {
                    // 그룹 내에서 recordVO가 존재하는 것 중 하나를 찾음
                    RecordVO representativeRecord = childGroup.stream()
                            .map(ChildrenVO::getRecordVO) // ChildrenVO에서 recordVO꺼내기
                            .filter(Objects::nonNull)// recordVO에서 null이 아닌값 찾기
                            .findFirst()// null이 아닌 가장 처음값 선택
                            .orElse(null); // 없으면 null로 설정

                    // 각 ChildrenVO에 대해 대표 recordVO를 세팅 (필요하면 새로 복사하거나)
                    return childGroup.stream()
                            .map(child -> {
                                if (representativeRecord != null) {
                                    child.setRecordVO(representativeRecord);
                                }
                                return child;
                            });
                })
                .collect(Collectors.toList());

        System.out.println("사이즈"+list.size());


        for (ChildrenVO vo : list) {
            FileVO fileVO = fileMapper.selectProfileByChild(vo.getChildKey());
            if (fileVO != null) {
                String imageUrl = "/upload/" + fileVO.getFilePath() + "/" + fileVO.getFileUuid() + "_" + fileVO.getFileName();
                vo.setProfileImageUrl(imageUrl);
            }
        }
        System.out.println("********"+ list.toString());
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
