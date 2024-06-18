package com.example.springboot.config;

import com.example.springboot.interceptors.LoginInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${local-img.request-path}")
    private String picReqPath; // 请求地址
    @Value("${local-img.save-path}")
    private String picLocPath; // 本地存放资源目录的绝对路径

    @Autowired
    private LoginInterceptors loginInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录和注册不拦截
        registry.addInterceptor(loginInterceptors).excludePathPatterns("/user/login","/user/register","/static/**","/error",picReqPath);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(picReqPath).addResourceLocations(picLocPath)
                .setCacheControl(CacheControl.noCache());
    }
}
