package com.project.userapp.user.mapper;

import com.project.userapp.command.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int idCheck(String userId);//아이디 중복체크
    int register(UserVO userVO); // 회원가입
    UserVO findInfo(UserVO vo);// 회원정보 찾기(아이디찾기, 비밀번호찾기, 로그인)
    int modify(UserVO userVO);// 회원정보 변경
}
