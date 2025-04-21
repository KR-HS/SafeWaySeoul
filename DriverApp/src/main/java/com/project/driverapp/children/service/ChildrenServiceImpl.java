package com.project.driverapp.children.service;

import com.project.driverapp.children.mapper.ChildrenMapper;
import com.project.driverapp.command.ChildrenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ChildrenServiceImpl implements ChildrenService {

    @Autowired
    private ChildrenMapper childrenMapper;

    @Override
    public List<ChildrenVO> findByKinder(String postCode, String address) {
        return childrenMapper.findByKinder(postCode, address);
    }
}
