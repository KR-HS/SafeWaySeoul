package com.project.driverapp.driveInfo.mapper;

import com.project.driverapp.command.DriveInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DriveInfoMapper {
    List<DriveInfoVO> getDriveInfo(Integer userKey);
}
