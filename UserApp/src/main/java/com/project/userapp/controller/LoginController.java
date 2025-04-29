package com.project.userapp.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.project.userapp.command.FileVO;
import com.project.userapp.command.UserVO;
import com.project.userapp.files.mapper.FilesMapper;
import com.project.userapp.files.service.FilesService;
import com.project.userapp.user.service.UserService;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class LoginController {



    @Value("${com.project.userapp.upload.path}")
    private String uploadPath;


    @Autowired
    private UserService userService;

    @Autowired
    private FilesService fileService;
    @Autowired
    private FilesMapper filesMapper;

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
    public String logout(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession();
        if(session!=null) session.invalidate();

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

        if(userName.trim().isBlank() || userPhone.trim().isBlank()){
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

    @PostMapping("/delete")
    @ResponseBody
    public String deleteUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            UserVO user = (UserVO) session.getAttribute("userInfo");

            if (user != null) {
                int userKey = user.getUserKey();
                userService.deleteUser(userKey);

                // 세션 및 쿠키 삭제
                session.invalidate();
                Cookie cookie = new Cookie("loginToken", null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
        }
        return "success"; // Ajax 호출이니까 "success" 문자열로 응답
    }

    @GetMapping("/userInfoModi")
    public String userInfoModifyPage(Model model, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("userInfo");
        model.addAttribute("user", user);

        FileVO profile = filesMapper.selectProfileByUser(user.getUserKey());
        model.addAttribute("profile", profile);

        return "user/userInfoModi";
    }


    @PostMapping("/update")
    public String updateUser(UserVO vo, @RequestParam("profile") MultipartFile profile, HttpSession session, RedirectAttributes ra) {

        UserVO sessionUser = (UserVO) session.getAttribute("userInfo");
        System.out.println(sessionUser.getUserPw());
        if(vo.getUserPw()==null) vo.setUserPw(sessionUser.getUserPw());

        vo.setUserKey(sessionUser.getUserKey());

        // 1. DB 정보 수정
        userService.updateUser(vo);

        // 2. 프로필 이미지 업로드
//        if (!profile.isEmpty()) {
//
//            // [수정1] 오늘 날짜 폴더명 생성
//            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//
//            // [수정2] 오늘 날짜 폴더 객체 생성
//            File uploadFolder = new File(uploadPath, today);
//            if (!uploadFolder.exists()) {
//                uploadFolder.mkdirs(); // 폴더 없으면 생성
//            }
//
//            // [수정3] 저장 파일명 만들기
//            String fileName = profile.getOriginalFilename();
//            String uuid = UUID.randomUUID().toString();
//            String saveName = uuid + "_" + fileName;
//
//            // [수정4] 최종 저장할 파일 경로 (날짜 폴더 안에)
//            File saveFile = new File(uploadFolder, saveName);
//
//            try {
//                profile.transferTo(saveFile);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            // [수정5] DB에 저장할 파일 경로 (웹 경로)
//            String dbFilePath = "/upload/" + today + "/" + saveName;
//
//            FileVO fileVO = FileVO.builder()
//                    .fileName(fileName)
//                    .filePath(dbFilePath)
//                    .fileUuid(uuid)
//                    .userKey(vo.getUserKey())
//                    .build();
//
//            filesMapper.registFile(fileVO); // DB insert
//        }
//
//        // 3. 세션 갱신 - DB에서 최신 정보 다시 조회해서 저장
//        UserVO updatedUser = userService.findInfo(vo); // 또는 findUserByKey(vo.getUserKey())
//        session.setAttribute("userInfo", updatedUser);
//
//
//        return "redirect:/child";

        // 2. 프로필 이미지 업로드 (AWS S3로 업로드)
        if (!profile.isEmpty()) {
            try {
                // AWS S3로 이미지 업로드
                fileService.uploadProfileImageToS3(vo, profile);
            } catch (IOException e) {
                e.printStackTrace();
                ra.addFlashAttribute("msg", "프로필 이미지 업로드에 실패했습니다.");
                return "redirect:/user/userInfoModi";
            }
        }

        // 3. 세션 갱신 - DB에서 최신 정보 다시 조회해서 저장
        UserVO updatedUser = userService.findInfo(vo); // 또는 findUserByKey(vo.getUserKey())
        session.setAttribute("userInfo", updatedUser);

        return "redirect:/child";
    }


}
