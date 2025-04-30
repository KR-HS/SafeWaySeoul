package com.project.userapp.community.mapper;

import com.project.userapp.command.CommentVO;
import com.project.userapp.command.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityMapper {

    int write(PostVO vo); // 글 등록

    List<PostVO> getPostList(String searchStr);
    List<PostVO> getMyPostList(Integer userKey, String searchStr);
    PostVO getPostById(int postKey);

    void writeComment(CommentVO commentVO);
    List<CommentVO> getAllComment(int postKey);
    Integer getCommentCountByPostKey(int postKey);

    int update(@Param("postKey") int postKey,
               @Param("postTitle") String postTitle,
               @Param("postContent") String postContent); // 글 수정

    int postDelete(int postKey); // 글 삭제
}


