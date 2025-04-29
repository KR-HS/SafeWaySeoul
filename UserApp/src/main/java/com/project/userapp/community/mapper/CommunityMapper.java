package com.project.userapp.community.mapper;

import com.project.userapp.command.PostVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {

    List<PostVO> getPostList();
    PostVO getPostById(int postKey);
    int write(PostVO vo);
}
