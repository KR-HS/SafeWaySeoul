package com.project.userapp.user.service;


import com.project.userapp.command.UserVO;

public interface UserService {

    int idCheck(String userId);//아이디 중복체크
    int register(UserVO userVO); // 회원가입
    UserVO findInfo(UserVO vo);// 회원정보 찾기
    int modify(UserVO userVO);// 회원정보 변경
    boolean registerCheck(UserVO userVO); // 중복 회원가입 불가
    void updateUser(UserVO vo);
}
