package com.project.userapp.controller;

import com.project.userapp.children.service.ChildrenService;
import com.project.userapp.command.ChildrenVO;
import com.project.userapp.command.KinderVO;
import com.project.userapp.command.UserVO;
import com.project.userapp.kinder.service.KinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ChildrenService childrenService;

    @Autowired
    private KinderService kinderService;


    @GetMapping("/*")
    public String loading() {
        return "loading";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        UserVO vo = (UserVO) session.getAttribute("userInfo");
        System.out.println(vo.toString());
        List<ChildrenVO> list = childrenService.myChildren(vo.getUserKey());

        //전체 유치원에 관한 정보 받아오기
        List<KinderVO> kinderList = kinderService.getKinderList();

        model.addAttribute("children",list);
        model.addAttribute("kinderInfo",kinderList);
        return "home";
    }

    @GetMapping("/child")
    public String child(Model model, HttpSession session) {
        UserVO vo = (UserVO) session.getAttribute("userInfo");
        System.out.println(vo.toString());
        List<ChildrenVO> list = childrenService.myChildren(vo.getUserKey());

        model.addAttribute("children",list);
        return "/user/mypage";
    }

    @GetMapping("/regChild")
    public String regChild(){ return "/modal/regChild";}

    @GetMapping("/tracing")
    public String tracing(){
        return "/user/tracing";
    }

    @GetMapping("/idFind")
    public String idFind(){
        return "/login/IdFind";
    }

    //로그인 성공창
    @GetMapping("/FindUserId")
    public String findUserIdPage() {
        return "FindUserId"; //
    }
    //비밀번호 재설정창
    @GetMapping("/updatePw")
    public String updatePw() {
        return "updatePw"; //
    }


//    @GetMapping("/mypage")
//    public String mypage(HttpSession session, Model model) {
//
//        // 테스트용 세션
//        UserVO testUser = (UserVO) session.getAttribute("userInfo");
//
//        if (testUser == null) {
//            testUser = UserVO.builder()
//                    .userKey(1)
//                    .userId("testuser01")
//                    .userName("대종수")
//                    .userPhone("010-1234-5678")
//                    .userType("PARENT")
//                    .userAddress("서울시 도봉구 시루봉로6길")
//                    .build();
//            session.setAttribute("userInfo", testUser);
//        }
//
//        // 테스트용 자녀 리스트 생성
//        List<ChildrenVO> children = new ArrayList<>();
//
//        ChildrenVO child1 = ChildrenVO.builder()
//                .childName("강유진")
//                .childBirth("2020-06-01")
//                .childGender("F")
//                .parentKey(testUser.getUserKey())
//                .build();
//
//        ChildrenVO child2 = ChildrenVO.builder()
//                .childName("소종수")
//                .childBirth("2024-12-25")
//                .childGender("M")
//                .parentKey(testUser.getUserKey())
//                .build();
//
//        children.add(child1);
//        children.add(child2);
//
//        model.addAttribute("children", children);
//
//        return "/user/mypage";
//    }


}
