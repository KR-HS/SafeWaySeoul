package com.project.driverapp.kinder.service;

import com.project.driverapp.command.KinderVO;

import java.util.List;

public interface KinderService {

    //주소 유효성 검사////////
    boolean isValidAddress(String kinderAddress, String kinderPostcode, String kinderName);
    /// ///////////////////

    public List<KinderVO> getKinderList(String name);
}
