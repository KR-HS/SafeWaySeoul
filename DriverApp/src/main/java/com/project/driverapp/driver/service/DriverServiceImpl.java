package com.project.driverapp.driver.service;


import com.project.driverapp.command.ChildrenVO;
import com.project.driverapp.command.DriverVO;
import com.project.driverapp.driver.mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverMapper driverMapper;


    @Override
    public int idCheck(String userId) {
        // false면 아이디 중복, true면 아이디 없음(회원가입 가능)
        return driverMapper.idCheck(userId);
    }

    @Override
    public int register(DriverVO userVO) {
        return driverMapper.register(userVO);
    }

    @Override
    public DriverVO findInfo(DriverVO vo) {
        return driverMapper.findInfo(vo);
    }


    @Override
    public int modify(DriverVO driverVO) {
        return driverMapper.modify(driverVO);
    }

    @Override
    public List<ChildrenVO> manageOfChildren(int recordKey) {
        return driverMapper.manageOfChildren(recordKey);
    }
}
