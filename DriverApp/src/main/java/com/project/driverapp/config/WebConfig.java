package com.project.driverapp.config;

import com.project.driverapp.util.interceptor.FirstVisitInterceptor;
import com.project.driverapp.util.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private FirstVisitInterceptor firstVisitInterceptor;


    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(firstVisitInterceptor)
                .addPathPatterns("/**"); // 모든 경로에 적용



        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") // 모든 경로에 적용
                .excludePathPatterns( "/",               // 첫 방문 페이지
                    "/user/*", // 유저로그인관련
                    "/css/**", "/js/**", "/img/**", "/favicon.ico"); // 정적 리소스 제외); // 리다이렉트 대상은 제외!

    }
}
