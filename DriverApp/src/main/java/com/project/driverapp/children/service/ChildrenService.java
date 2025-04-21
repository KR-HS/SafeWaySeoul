package com.project.driverapp.children.service;

import com.project.driverapp.command.ChildrenVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChildrenService {

    List<ChildrenVO> findByKinder(String postCode, String address);
}
