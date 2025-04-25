package com.project.driverapp.controller;

import com.project.driverapp.command.DriveInfoVO;
import com.project.driverapp.command.DriverVO;
import com.project.driverapp.command.KinderVO;
import com.project.driverapp.driver.mapper.DriverMapper;
import com.project.driverapp.driver.service.DriverService;
import com.project.driverapp.kinder.service.KinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private DriverService driverService;
    @Autowired
    private KinderService kinderService;

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

        System.out.println("로그인성공!"+session.getAttribute("driverInfo"));

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
    public String joinForm(@RequestParam("driverInfoName") String driverName,
                           @RequestParam("driverCarName") String driverCarName, DriverVO vo, RedirectAttributes ra) {
        // 회원가입 기능 추가 필요 -----
        if(false){
            // 로그인폼 제약조건 검사
            ra.addFlashAttribute("msg","올바르지 않은 회원가입 형태입니다");
            return "redirect:/user/join";
        }
/// //////////////////////////////////
        // 1. 컨트롤러에서 유치원명, 우편번호로 KinderVO에서 해당값이 있는지 조회 후
        //    있으면 회원가입(로그인창) / 없으면 회원가입 불가(다시 회원가입창으로 돌려보내기)

//        System.out.println("으아ㅏㅏㅏㅏㅏㅏㅏㅏㅇ");
//        System.out.println(vo.getUserAddressDetail());
//        System.out.println(vo.getUserAddress());
//        System.out.println(vo.getUserPostcode());
//
//        boolean checkAddress = kinderService.isValidAddress(vo.getUserAddressDetail(), vo.getUserAddress(), vo.getUserPostcode());
//        System.out.println(checkAddress);
//        if (!checkAddress) {
//            System.out.println("끄아ㅏㅏㅏㅏㅏㅏㅏㅏㅇ");
//            ra.addFlashAttribute("msg", "입력하신 유치원 주소가 존재하지 않습니다.");
//            return "redirect:/user/join";
//        }



        // 제약 조건
        System.out.println(driverName);
        System.out.println(driverCarName);

        int result = driverService.register(vo);

        System.out.println(vo.toString());
        int driverKey = vo.getUserKey();
        DriveInfoVO driveInfo1 = DriveInfoVO.builder().driveInfoName("오전" + driverName).driveCarName(driverCarName).userKey(driverKey).build();
        DriveInfoVO driveInfo2 = DriveInfoVO.builder().driveInfoName("오후" + driverName).driveCarName(driverCarName).userKey(driverKey).build();
        System.out.println("********"+driveInfo1.toString());
        result = driverService.registerInfo(driveInfo1);
        result = driverService.registerInfo(driveInfo2);


        ra.addFlashAttribute("msg","회원으로 등록되셨습니다.");

        return "redirect:/user/login";
    }

    /**
     * 유치원조회
     * @return
     */
    @GetMapping("/find")
    @ResponseBody
    public List<KinderVO> find(@RequestParam("name") String name) {
        System.out.println(name);
        List<KinderVO> kindervo = kinderService.getKinderList(name);

        return kindervo;
    }
    
    
    
    
    @GetMapping("/IdFind")
    public String IdFind() {return "login/IdFind";}

    @PostMapping("/IdFindForm")
    public String IdFindForm(@RequestParam("name") String userName, @RequestParam("phone") String userPhone, RedirectAttributes ra, Model model) {

            if(userName.trim().isBlank() || userPhone.trim().isBlank()){
            ra.addFlashAttribute("msg","정보를 입력해주세요");
            return "redirect:/user/IdFind";
        }

        DriverVO vo = DriverVO.builder().userName(userName).userPhone(userPhone).build();
        DriverVO DriverVO = driverService.findInfo(vo);

        if(DriverVO==null){
            ra.addFlashAttribute("msg","등록된 회원정보를 다시 확인해주세요.");
            return "redirect:/user/IdFind";
        }

        model.addAttribute("driverInfo",DriverVO);

        return "/FindUserId";

    }

    @GetMapping("/FindUserId")
    public String FindUserId() {return "login/FindUserId";}



    @GetMapping("/pswFind")
    public String pswFind() {return "login/pswFind";}

    @PostMapping("pswFindForm")
    public String pswFindForm(@RequestParam("id") String userId, @RequestParam("phone") String userPhone, RedirectAttributes ra, Model model) {

        if(userId.trim().isBlank() || userPhone.trim().isBlank()){
            ra.addFlashAttribute("msg","정보를 입력해주세요");
            return "redirect:/user/pswFind";
        }

        DriverVO vo = DriverVO.builder().userId(userId).userPhone(userPhone).build();
        DriverVO DriverVO = driverService.findInfo(vo);
        model.addAttribute("userId", userId);

        if(DriverVO==null){
            ra.addFlashAttribute("msg","등록된 회원정보를 다시 확인해주세요.");
            return "redirect:/user/pswFind";
        }

        model.addAttribute("driverInfo",DriverVO);

        return "/updatePw";
    }

    @PostMapping("/updatePw")
    public String updatePw(@RequestParam("id") String userId, @RequestParam("password") String userPw, RedirectAttributes ra, Model model) {


        DriverVO vo = DriverVO.builder().userId(userId).userPw(userPw).build();
        int result = driverService.modify(vo);

        if(result == 1){
            ra.addFlashAttribute("msg","변경되었습니다.");
        } else {
            ra.addFlashAttribute("msg", "작업에 실패하였습니다");
        }

        return "redirect:/user/login";
    }



}
