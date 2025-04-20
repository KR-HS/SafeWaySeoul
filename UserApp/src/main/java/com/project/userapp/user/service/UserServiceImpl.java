package com.project.userapp.user.service;


import com.project.userapp.command.UserVO;
import com.project.userapp.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean idCheck(String userId) {
        // false면 아이디 중복, true면 아이디 없음(회원가입 가능)
        return userMapper.idCheck(userId)==0;
    }

    @Override
    public int register(UserVO userVO) {
        return userMapper.register(userVO);
    }

    @Override
    public UserVO findInfo(UserVO vo) {
        return userMapper.findInfo(vo);
    }


    @Override
    public int modify(UserVO userVO) {
        return userMapper.modify(userVO);
    }
}
