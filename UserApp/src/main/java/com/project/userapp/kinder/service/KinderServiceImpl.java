package com.project.userapp.kinder.service;

import com.project.userapp.command.KinderVO;
import com.project.userapp.kinder.mapper.KinderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KinderServiceImpl implements KinderService {

    @Autowired
    private KinderMapper kinderMapper;

    @Override
    public List<KinderVO> getKinderList() {
        return kinderMapper.getKinderList();
    }
}
