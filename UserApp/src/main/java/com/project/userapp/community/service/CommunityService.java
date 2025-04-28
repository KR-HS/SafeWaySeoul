package com.project.userapp.community.service;

import com.project.userapp.command.PostVO;

import java.util.List;

public interface CommunityService {

    List<PostVO> getPostList();
    PostVO getPostById(int postKey);
}
