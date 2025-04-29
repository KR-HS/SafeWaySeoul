package com.project.userapp.files.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.project.userapp.command.FileVO;
import com.project.userapp.command.UserVO;
import com.project.userapp.files.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Transactional  // ← 이거 꼭 붙어 있어야 합니다!
public class FilesServiceImpl implements FilesService {
    @Autowired
    private FilesMapper filesMapper;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public void uploadProfileImageToS3(UserVO userVO, MultipartFile profile) throws IOException {
        // [수정1] 오늘 날짜 폴더명 생성
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // [수정2] 저장 파일명 만들기
        String fileName = profile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String saveName = uuid + "_" + fileName;

        // [수정3] S3 파일 경로 (날짜 폴더 안에 저장)
        String s3FilePath = today + "/" + saveName;

        // [수정4] S3로 파일 업로드
        try (InputStream inputStream = profile.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(profile.getSize());

            amazonS3.putObject(
                    new PutObjectRequest(bucketName, s3FilePath, inputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)  // ← 퍼블릭 읽기 권한 추가
            );
            System.out.println("✅ S3 업로드 성공: " + s3FilePath);
        } catch (Exception e) {
            System.err.println("❌ S3 업로드 실패: " + e.getMessage());
            e.printStackTrace();
            throw e; // 여기서 끊기면 DB insert도 안 됨
        }

        // [수정5] DB에 저장할 파일 경로 (웹 경로)
        String fileUrl = "https://s3." + amazonS3.getRegionName() + ".amazonaws.com/" + bucketName + "/" + s3FilePath;

        // DB에 파일 경로 저장
        FileVO fileVO = FileVO.builder()
                .fileName(fileName)
                .filePath(fileUrl)
                .fileUuid(uuid)
                .userKey(userVO.getUserKey())
                .build();

        System.out.println("********"+fileVO.toString());

        if(filesMapper.isExistFileByUserKey(userVO.getUserKey())>0){
            filesMapper.updateFileByUser(fileVO);
            return;
        }
        filesMapper.registFile(fileVO);  // DB insert
    }
}
