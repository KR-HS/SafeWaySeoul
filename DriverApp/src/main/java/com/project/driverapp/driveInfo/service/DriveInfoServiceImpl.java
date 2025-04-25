package com.project.driverapp.driveInfo.service;

import com.project.driverapp.command.DriveInfoVO;
import com.project.driverapp.driveInfo.mapper.DriveInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriveInfoServiceImpl implements DriveInfoService {

    @Autowired
    private DriveInfoMapper driveInfoMapper;

    @Override
    public List<DriveInfoVO> getDriveInfo(Integer userKey) {
        return driveInfoMapper.getDriveInfo(userKey);
    }
}
