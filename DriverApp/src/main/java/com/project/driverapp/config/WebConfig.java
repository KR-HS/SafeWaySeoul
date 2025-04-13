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


//        registry.addInterceptor(LoginInterceptor)
//                .addPathPatterns("/**"); // 모든 경로에 적용

//        registry.addInterceptor(LoginInterceptor)
//                .addPathPatterns("/**") // 모든 경로에 적용
//                .excludePathPatterns("/intro", "/css/**", "/js/**", "/img/**"); // 리다이렉트 대상은 제외!
    }
}
