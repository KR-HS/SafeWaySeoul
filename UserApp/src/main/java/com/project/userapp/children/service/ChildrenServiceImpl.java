package com.project.userapp.children.service;

import com.project.userapp.children.mapper.ChildrenMapper;
import com.project.userapp.command.ChildrenVO;
import com.project.userapp.command.FileVO;
import com.project.userapp.file.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ChildrenServiceImpl implements ChildrenService{

    @Autowired
    private ChildrenMapper childrenMapper;

    @Autowired
    private FileMapper fileMapper;


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
    public int registChild(ChildrenVO vo, MultipartFile file) {
        int result = childrenMapper.registChild(vo);
        childrenMapper.registKinderRelation(vo.getChildKey());

        // 자녀 프로필사진 등록
        String originName = file.getOriginalFilename();
        String filename = originName.substring(originName.lastIndexOf("/") + 1);

        UUID uuid = UUID.randomUUID(); // 16진수형태의 랜덤문자열을 반환
        String filepath = makeFolder();
        String path = uploadPath + "/" + filepath + "/" + uuid + "_" + filename; // 업로드패스
        try {

            File saveFile = new File(path);
            file.transferTo(saveFile); // 파일업로드를 처리
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        result = fileMapper.registFile(FileVO.builder().
                fileUuid(uuid.toString()).
                filePath(filepath).
                fileName(filename).
                childKey(vo.getChildKey()).
                build());

        return result;
    }

    @Override
    public int deleteChild(Integer childKey) {
        return childrenMapper.deleteChild(childKey);
    }

    @Override
    public List<ChildrenVO> myChildren(Integer parentKey) {
        return childrenMapper.myChildren(parentKey);
    }
}
