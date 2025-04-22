package com.project.userapp.files.mapper;

import com.project.userapp.command.FileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FilesMapper {
    int registFile(FileVO fileVO);
    FileVO selectProfileByChild(Integer childKey);
    int deleteByChildKey(Integer childKey);
}
