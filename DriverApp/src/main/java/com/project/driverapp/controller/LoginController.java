package com.project.driverapp.controller;

import com.project.driverapp.command.DriverVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }
    @PostMapping("/loginOk")
    public String loginForm(HttpServletRequest request, @RequestParam("email") String id
            , @RequestParam("password") String pw, RedirectAttributes ra) {
    // db연결후 id, pw같은지 확인
        String userId="aaa123";
        String userPw="aaa123";
        // userName은 DB에서 가져오기
        String userName="aaa123";
        HttpSession session = request.getSession();

        if(id.equals(userId) && pw.equals(userPw)) {
            Map<String,String> userMap = new HashMap<String, String>();
            userMap.put("id", userId);
            userMap.put("pw", userPw);
            userMap.put("name",userName);
            session.setAttribute("userMap",userMap);

            System.out.println("로그인성공!"+session.getAttribute("userMap"));
            return "redirect:/home";
        }

        ra.addFlashAttribute("msg","회원정보를 다시 확인해주세요.");
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
