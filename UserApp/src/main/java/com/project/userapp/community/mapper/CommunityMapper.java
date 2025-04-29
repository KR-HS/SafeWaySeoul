package com.project.userapp.community.mapper;

import com.project.userapp.command.CommentVO;
import com.project.userapp.command.PostVO;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpSession;
import java.util.List;

@Mapper
public interface CommunityMapper {

    List<PostVO> getPostList();
    PostVO getPostById(int postKey);
    int write(PostVO vo);
    void writeComment(CommentVO commentVO);
    List<CommentVO> getAllComment(int postKey);

}
