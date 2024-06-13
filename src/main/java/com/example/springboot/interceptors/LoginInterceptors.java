package com.example.springboot.interceptors;

import com.example.springboot.utils.JwtUtil;
import com.example.springboot.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptors implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //放行预检请求
        if ("OPTIONS".equals(request.getMethod()) && request.getHeader("Access-Control-Request-Headers") != null) {
            // Skip Authorization header validation for preflight requests
            return true;
        }
        //令牌验证
        String token= request.getHeader("Authorization");
        System.out.printf("jwt令牌:"+token+"\n");
        //验证token
        try {
            Map<String,Object> claims= JwtUtil.parseToken(token);
            //把数据存储在ThreadLocal中
            ThreadLocalUtil.set(claims);
            return true;
        }
        catch (Exception e){
            response.setStatus(401);
            //不放行
            System.out.println("拦截访问");
            return false;

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
