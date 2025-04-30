package com.project.userapp.files.service;

// import com.amazonaws.services.s3.AmazonS3;
// import com.amazonaws.services.s3.model.CannedAccessControlList;
// import com.amazonaws.services.s3.model.ObjectMetadata;
// import com.amazonaws.services.s3.model.PutObjectRequest;

import com.project.userapp.command.FileVO;
import com.project.userapp.command.UserVO;
import com.project.userapp.files.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Transactional
public class FilesServiceImpl implements FilesService {

    @Autowired
    private FilesMapper filesMapper;

    // AWS S3 주석처리
    // @Autowired
    // private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket:dummy-bucket}") // 기본값 설정
    private String bucketName;

    @Override
    public void uploadProfileImageToS3(UserVO userVO, MultipartFile profile) throws IOException {

        // 오늘 날짜 폴더명 생성
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 저장 파일명 만들기
        String fileName = profile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String saveName = uuid + "_" + fileName;

        // S3 파일 경로 대신 로컬 경로 흉내
        String s3FilePath = today + "/" + saveName;

        // AWS 업로드 코드 주석처리
        /*
        try (InputStream inputStream = profile.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(profile.getSize());

            amazonS3.putObject(
                    new PutObjectRequest(bucketName, s3FilePath, inputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
            System.out.println("\u2705 S3 업로드 성공: " + s3FilePath);
        } catch (Exception e) {
            System.err.println("\u274C S3 업로드 실패: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        */

        // S3 URL 대신 임시 저장 경로 사용 (나중에 교체)
        String fileUrl = "/upload/" + s3FilePath;

        FileVO fileVO = FileVO.builder()
                .fileName(fileName)
                .filePath(fileUrl)
                .fileUuid(uuid)
                .userKey(userVO.getUserKey())
                .build();

        System.out.println("********" + fileVO.toString());

        if (filesMapper.isExistFileByUserKey(userVO.getUserKey()) > 0) {
            filesMapper.updateFileByUser(fileVO);
            return;
        }
        filesMapper.registFile(fileVO);
    }
}