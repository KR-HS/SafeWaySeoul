package com.project.userapp.file.mapper;

import com.project.userapp.command.FileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    int registFile(FileVO fileVO);
}
