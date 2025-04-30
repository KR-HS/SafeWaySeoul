package com.project.userapp.files.mapper;

import com.project.userapp.command.FileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FilesMapper {
    int registFile(FileVO fileVO);
    int updateFileByUser(FileVO fileVO);
    FileVO selectProfileByChild(Integer childKey);
    int deleteByChildKey(Integer childKey);
    FileVO selectProfileByUser(Integer userKey);

    int isExistFileByUserKey(Integer userKey);
}
