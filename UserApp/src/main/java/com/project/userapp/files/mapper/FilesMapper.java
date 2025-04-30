package com.project.userapp.files.mapper;

import com.project.userapp.command.FileVO;
import org.apache.ibatis.annotations.Mapper;


import java.util.HashMap;
import java.util.Map;
import java.util.List;


@Mapper
public interface FilesMapper {
    int registFile(FileVO fileVO);
    int updateFile(FileVO fileVO);
    FileVO selectProfileByChild(Integer childKey);
    int deleteByChildKey(Integer childKey);
    FileVO selectProfileByUser(Integer userKey);


    int isExistFile(FileVO vo);

    void insertFile(FileVO fileVO);

    List<FileVO> getFilesByPostKey(Integer postKey);

    void deleteFileByPostKey(Integer postKey);

}
