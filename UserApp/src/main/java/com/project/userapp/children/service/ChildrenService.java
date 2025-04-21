package com.project.userapp.children.service;

import com.project.userapp.command.ChildrenVO;
import com.project.userapp.command.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChildrenService {
    int registChild(ChildrenVO vo, MultipartFile file);
    int deleteChild(Integer childKey);
    List<ChildrenVO> myChildren(Integer parentKey);



}
