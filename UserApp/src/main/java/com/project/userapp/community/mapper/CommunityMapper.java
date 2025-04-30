package com.project.userapp.community.mapper;

import com.project.userapp.command.CommentVO;
import com.project.userapp.command.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;
import java.util.List;

@Mapper
public interface CommunityMapper {

    List<PostVO> getPostList(String searchStr);
    PostVO getPostById(int postKey);
    int write(PostVO vo);//글쓰기
    void writeComment(CommentVO commentVO);
    List<CommentVO> getAllComment(int postKey);
    Integer getCommentCountByPostKey(int postKey);
    int update(@Param("postKey") int postKey, @Param("postTitle") String postTitle, @Param("postContent") String postContent); //글 수정
    int postDelete(int postKey); //글 삭제
}
