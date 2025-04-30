package com.project.userapp.community.service;

import com.project.userapp.command.CommentVO;
import com.project.userapp.command.PostVO;
import com.project.userapp.community.mapper.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public List<PostVO> getPostList(String searchStr) {
        return communityMapper.getPostList(searchStr);
    }

    @Override
    public List<PostVO> getMyPostList(Integer userKey, String searchStr) {
        return communityMapper.getMyPostList(userKey, searchStr);
    }

    @Override
    public PostVO getPostById(int postKey) { return communityMapper.getPostById(postKey);}

    @Override
    public int write(PostVO vo) {
        return communityMapper.write(vo);
    }
  
    @Override
    public void writeComment(CommentVO commentVO) {
        communityMapper.writeComment(commentVO);
    }

    @Override
    public List<CommentVO> getAllComment(int postKey) {
        return communityMapper.getAllComment(postKey);

    }

    @Override
    public Integer getCommentCountByPostKey(int postKey) {
        return communityMapper.getCommentCountByPostKey(postKey);
    }
}
