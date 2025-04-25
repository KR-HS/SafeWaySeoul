package com.project.driverapp.driveInfo.service;

import com.project.driverapp.command.DriveInfoVO;

import java.util.List;

public interface DriveInfoService {
    List<DriveInfoVO> getDriveInfo(Integer userKey);
}
