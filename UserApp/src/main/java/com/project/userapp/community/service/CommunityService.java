package com.project.userapp.community.service;

import com.project.userapp.command.CommentVO;
import com.project.userapp.command.PostVO;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CommunityService {

    List<PostVO> getPostList(String searchStr);
    List<PostVO> getMyPostList(Integer userKey, String searchStr);
    PostVO getPostById(int postKey);
    int write(PostVO vo); //글쓰기
    void writeComment(CommentVO commentVO);
    List<CommentVO> getAllComment(int postKey);
    Integer getCommentCountByPostKey(int postKey);
    int update(int postKey, String postTitle, String postContent); //글 수정
    int postDelete(int postKey); //글삭제
}
