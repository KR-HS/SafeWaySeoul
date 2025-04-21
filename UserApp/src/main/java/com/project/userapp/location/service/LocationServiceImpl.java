package com.project.userapp.location.service;

import com.project.userapp.command.LocationVO;
import com.project.userapp.location.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public void registLocation(LocationVO vo) {
        locationMapper.registLocation(vo);
    }
}
