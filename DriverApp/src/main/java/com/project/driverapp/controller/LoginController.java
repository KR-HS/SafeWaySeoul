package com.project.driverapp.controller;

import com.project.driverapp.command.DriverVO;
import com.project.driverapp.driver.mapper.DriverMapper;
import com.project.driverapp.driver.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private DriverService driverService;

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @PostMapping("/loginOk")
    public String loginForm(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam("email") String id
            , @RequestParam("password") String pw, RedirectAttributes ra) {
        if(id.isBlank() || pw.isBlank()){
            ra.addFlashAttribute("msg","로그인 정보를 입력해주세요");
            return "redirect:/user/login";
        }

        DriverVO vo = DriverVO.builder().userId(id).userPw(pw).build();
        DriverVO userVO = driverService.findInfo(vo);

        if(userVO==null){
            ra.addFlashAttribute("msg","회원정보를 다시 확인해주세요.");
            return "redirect:/user/login";
        }

        HttpSession session = request.getSession();

        session.setAttribute("driverInfo",userVO);



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
    public String logout(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession();
        if(session!=null) session.invalidate();

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                System.out.println("쿠키 이름: " + c.getName() + ", 만료: " + c.getMaxAge());
            }
        }
        
        Cookie cookie = new Cookie("loginToken", null); // "loginToken"은 실제 쿠키 이름으로 교체
        cookie.setPath("/"); // 설정했던 path와 동일하게
        cookie.setMaxAge(0); // 유효기간 0초 → 즉시 삭제
        cookie.setHttpOnly(true); // 필요 시 보안 설정도 유지
        response.addCookie(cookie);


        return "redirect:/user/login";
    }

    @GetMapping("/join")
    public String join() {
        return "login/join";
    }

    @PostMapping("/joinForm")
    public String joinForm(DriverVO vo, RedirectAttributes ra) {
        // 회원가입 기능 추가 필요 -----
        if(false){
            // 로그인폼 제약조건 검사
            ra.addFlashAttribute("msg","올바르지 않은 회원가입 형태입니다");
            return "redirect:/user/join";
        }

        // 제약 조건
        System.out.println(vo.toString());
        int result = driverService.register(vo);
        System.out.println("등록결과"+result);
        ra.addFlashAttribute("msg","회원으로 등록되셨습니다.");

        return "redirect:/user/login";
    }

    //로그인 성공창
    @GetMapping("/FindUserId")
    public String findUserIdPage() {
        return "FindUserId"; // templates/FindUserId.html
    }
    //비밀번호 재설정창
    @GetMapping("/updatePw")
    public String updatePw() {
        return "updatePw"; //
    }
}
