package com.project.userapp.kindermatch.service;

import com.project.userapp.command.KinderMatchVO;
import com.project.userapp.kindermatch.mapper.KinderMatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KinderMatchServiceImpl implements KinderMatchService {

    @Autowired
    private KinderMatchMapper kinderMatchMapper;

    @Override
    public int insertMatch(KinderMatchVO vo) {
        return kinderMatchMapper.insertMatch(vo);
    }

    @Override
    public int updatePickup(KinderMatchVO vo) {
        return kinderMatchMapper.updatePickup(vo);
    }


}
