package com.project.userapp.community.service;

import com.project.userapp.command.PostVO;
import com.project.userapp.community.mapper.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public List<PostVO> getPostList() {
        return communityMapper.getPostList();
    }
}
