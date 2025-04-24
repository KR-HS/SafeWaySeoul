package com.project.driverapp.kinder.service;

import com.project.driverapp.command.KinderVO;
import com.project.driverapp.kinder.mapper.KinderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KinderServiceImpl implements KinderService {

    //주소 유효성 검사////////////////////////
    @Autowired
    private KinderMapper kinderMapper;

    //결과가 1이상이면 존재하는 것으로 판단
    @Override
    public boolean isValidAddress(String kinderAddress, String kinderPostcode, String kinderName) {
        int count = kinderMapper.checkAddress(kinderAddress, kinderPostcode, kinderName);
        return count > 0;
    }

    @Override
    public List<KinderVO> getKinderList(String name) {
        return kinderMapper.getKinderList(name);
    }

    // ////////////////////////////////////

}
