package com.project.driverapp.util.interceptor;

import com.project.driverapp.command.DriverVO;
import com.project.driverapp.driver.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component // webConfig에서 bean으로 등록안해도됨 (자동 빈 등록)
public class LoginInterceptor implements HandlerInterceptor {
    // 로딩후 무조건 로그인화면
    @Autowired
    private DriverService driverService; // 💡 서비스 주입 필요

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("driverInfo") != null) {
            return true; // 이미 로그인 상태
        }

        // 쿠키에서 로그인 토큰 꺼내기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("loginToken".equals(cookie.getName())) {
                    String userId = cookie.getValue();

                    DriverVO vo = DriverVO.builder().userId(userId).build();
                    // 🔍 DB 조회를 통해 사용자 정보 가져오기
                    DriverVO driver = driverService.findInfo(vo);
                    if (driver != null) {
                        request.getSession().setAttribute("driverInfo", driver); // 세션에 유저 저장

                        // 앱 접속시 쿠키 재생성
                        Cookie renewedCookie = new Cookie("loginToken", userId);
                        renewedCookie.setHttpOnly(true);
                        renewedCookie.setSecure(true);
                        renewedCookie.setMaxAge(60 * 60 * 24 * 7); // 다시 7일로 설정
                        renewedCookie.setPath("/"); // 모든 경로의 요청에서 쿠키 전송
                        response.addCookie(renewedCookie); // 쿠키 갱신
                        return true;
                    }
                }
            }
        }

        // 로그인 페이지로 리디렉션
        response.sendRedirect("/user/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("로그인 컨트롤러 실행 후 인터셉터 동작");
    }
}
