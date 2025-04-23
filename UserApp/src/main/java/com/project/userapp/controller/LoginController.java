package com.project.userapp.controller;

import com.project.userapp.command.UserVO;
import com.project.userapp.user.service.UserService;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String loginForm(HttpServletRequest request, HttpServletResponse response, @RequestParam("email") String id
            , @RequestParam("password") String pw, RedirectAttributes ra) {

        if(id.isBlank() || pw.isBlank()){
            ra.addFlashAttribute("msg","로그인 정보를 입력해주세요");
            return "redirect:/user/login";
        }

        UserVO vo = UserVO.builder().userId(id).userPw(pw).build();
        UserVO userVO = userService.findInfo(vo);

        if(userVO==null){
            ra.addFlashAttribute("msg","회원정보를 다시 확인해주세요.");
            return "redirect:/user/login";
        }

        HttpSession session = request.getSession();

        session.setAttribute("userInfo",userVO);

        // 쿠키 생성
        Cookie loginCookie = new Cookie("loginToken", userVO.getUserId()); // JWT나 유저 ID 등 고유값
        loginCookie.setHttpOnly(true);           // JS에서 접근 못하도록
        loginCookie.setSecure(true);             // HTTPS에서만 전송
        loginCookie.setPath("/");                // 전체 경로에서 유효
        loginCookie.setMaxAge(60 * 60 * 24 * 7); // 7일간 유지

        response.addCookie(loginCookie);

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
            // 회원가입 제약조건 검사
            ra.addFlashAttribute("msg","올바르지 않은 회원가입 형태입니다");
            return "redirect:/user/join";
        }

        if(userService.registerCheck(vo)){
            ra.addFlashAttribute("msg","회원님의 아이디가 이미 존재합니다.");
            return "redirect:/user/login";
        }
        // 제약 조건
        System.out.println(vo.toString());
        int result = userService.register(vo);
        System.out.println("등록결과"+result);
        ra.addFlashAttribute("msg","회원으로 등록되셨습니다.");

        return "redirect:/user/login";
    }

    @GetMapping("/IdFind")
    public String IdFind() {return "login/IdFind";}

    @PostMapping("/IdFindForm")
    public String IdFindForm(@RequestParam("name") String userName, @RequestParam("phone") String userPhone, RedirectAttributes ra, Model model) {

        if(userName.trim()=="" || userPhone.trim()==""){
            ra.addFlashAttribute("msg","정보를 입력해주세요");
            return "redirect:/user/IdFind";
        }

        UserVO vo = UserVO.builder().userName(userName).userPhone(userPhone).build();
        UserVO userVO = userService.findInfo(vo);

        if(userVO==null){
            ra.addFlashAttribute("msg","등록된 회원정보를 다시 확인해주세요.");
            return "redirect:/user/IdFind";
        }

        model.addAttribute("userInfo",userVO);

        return "/FindUserId";
    }

    @GetMapping("/FindUserId")
    public String FindUserId() {return "login/FindUserId";}


    @GetMapping("/pswFind")
    public String pswFind() {return "login/pswFind";}

    @PostMapping("pswFindForm")
    public String pswFindForm(@RequestParam("id") String userId, @RequestParam("phone") String userPhone, RedirectAttributes ra, Model model) {

        if(userId.trim()=="" || userPhone.trim()==""){
            ra.addFlashAttribute("msg","정보를 입력해주세요");
            return "redirect:/user/pswFind";
        }

        UserVO vo = UserVO.builder().userId(userId).userPhone(userPhone).build();
        UserVO userVO = userService.findInfo(vo);
        model.addAttribute("userId", userId);

        if(userVO==null){
            ra.addFlashAttribute("msg","등록된 회원정보를 다시 확인해주세요.");
            return "redirect:/user/pswFind";
        }

        model.addAttribute("userInfo",userVO);

        return "/updatePw";
    }

    @PostMapping("/updatePw")
    public String updatePw(@RequestParam("id") String userId, @RequestParam("password") String userPw, RedirectAttributes ra, Model model) {


        UserVO vo = UserVO.builder().userId(userId).userPw(userPw).build();
        int result = userService.modify(vo);

        if(result == 1){
            ra.addFlashAttribute("msg","변경되었습니다.");
        } else {
            ra.addFlashAttribute("msg", "작업에 실패하였습니다");
        }

        return "redirect:/user/login";
    }

}
