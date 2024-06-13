package com.example.springboot.controller;

import com.example.springboot.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class JwtParseExpController {
    @GetMapping("/jwtParse")
    public Result<String > test(){
//        //验证token
//        try {
//            Map<String,Object> claims=JwtUtil.parseToken(token);
//            return Result.success("通过验证");
//        }
//        catch (Exception e){
//            response.setStatus(401);
//            return Result.error("未登录");
//        }
        return Result.success("通过验证");

    }
}
