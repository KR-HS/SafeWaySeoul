package com.project.userapp.files.service;


import com.project.userapp.command.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FilesService {
    void uploadProfileImageToS3(UserVO userVO, MultipartFile profile) throws IOException;
    void uploadContentImageToS3(Integer postKey, List<MultipartFile> list);
    void deleteFileByPostKey(Integer postKey);
}
