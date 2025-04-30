package com.project.userapp.community.mapper;

import com.project.userapp.command.CommentVO;
import com.project.userapp.command.PostVO;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpSession;
import java.util.List;

@Mapper
public interface CommunityMapper {

    int insertPost(PostVO postVO);
    List<PostVO> getPostList(String searchStr);
    List<PostVO> getMyPostList(Integer userKey, String searchStr);
    PostVO getPostById(int postKey);
    void writeComment(CommentVO commentVO);
    List<CommentVO> getAllComment(int postKey);
    Integer getCommentCountByPostKey(int postKey);
    Integer getLastPostKey(Integer userKey);

}

