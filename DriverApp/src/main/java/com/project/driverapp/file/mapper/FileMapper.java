package com.project.driverapp.file.mapper;

import com.project.driverapp.command.FileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    int registFile(FileVO fileVO);
}
