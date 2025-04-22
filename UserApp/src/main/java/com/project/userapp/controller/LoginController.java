package com.project.userapp.controller;

import com.project.userapp.command.UserVO;
import com.project.userapp.user.service.UserService;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @PostMapping("/loginOk")
    public String loginForm(HttpServletRequest request, @RequestParam("email") String id
            , @RequestParam("password") String pw, RedirectAttributes ra) {

        UserVO vo = UserVO.builder().userId(id).userPw(pw).build();
        UserVO userVO = userService.findInfo(vo);

        if(userVO==null || id.isEmpty() || pw.isEmpty()){
            ra.addFlashAttribute("msg","회원정보를 다시 확인해주세요.");
            return "redirect:/user/login";
        }


        HttpSession session = request.getSession();

        session.setAttribute("userInfo",userVO);
        System.out.println("로그인성공!"+session.getAttribute("userInfo"));

        return "redirect:/home";
        

    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userInfo");
        return "redirect:/user/login";
    }

    @GetMapping("/join")
    public String join() {
        return "login/join";
    }

    @PostMapping("/joinForm")
    public String joinForm(UserVO vo, RedirectAttributes ra) {
        // 회원가입 기능 추가 필요 -----
        if(false){
            // 로그인폼 제약조건 검사
            ra.addFlashAttribute("msg","올바르지 않은 회원가입 형태입니다");
            return "redirect:/user/join";
        }

        // 제약 조건
        System.out.println(vo.toString());
        int result = userService.register(vo);
        System.out.println("등록결과"+result);
        ra.addFlashAttribute("msg","회원으로 등록되셨습니다.");

        return "redirect:/user/login";
    }
    



}
