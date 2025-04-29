package com.project.userapp.community.service;

import com.project.userapp.command.CommentVO;
import com.project.userapp.command.PostVO;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CommunityService {

    List<PostVO> getPostList();
    PostVO getPostById(int postKey);
    int write(PostVO vo);
    void writeComment(CommentVO commentVO);
    List<CommentVO> getAllComment(int postKey);
    Integer getCommentCountByPostKey(int postKey);

}
