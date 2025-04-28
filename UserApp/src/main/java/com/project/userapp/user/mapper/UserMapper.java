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
    boolean registerCheck(UserVO userVO);
    void updateUser(UserVO vo);

    // 기존 메서드들은 유지하면서 추가

    // KinderMatch 삭제
    int deleteKinderMatchByParent(int parentKey);
    // Files 삭제 (자녀 기준)
    int deleteFilesByChildKey(int parentKey);
    // Files 삭제 (부모 기준)
    int deleteFilesByParent(int parentKey);
    // Children 삭제
    int deleteChildrenByParent(int parentKey);
    // User 삭제
    int deleteUser(int userKey);



}
