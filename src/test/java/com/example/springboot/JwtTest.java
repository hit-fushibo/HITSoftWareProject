package com.example.springboot;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("name","张三");
        String token=JWT.create().withClaim("user",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))//添加过期时间
                .sign(Algorithm.HMAC256("mymiyao"));//添加密钥
        System.out.println(token);
    }
    @Test
    public void testParse(){
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7Im5hbWUiOiLlvKDkuIkiLCJpZCI6MX0sImV4cCI6MTcxODI0NDQ2Nn0" +
                ".xcitWAlv0MLRTHqmgWtC6yI97GFoqLfMK9iiSRGBEdw";
        JWTVerifier jwtVerifier= JWT.require(Algorithm.HMAC256("mymiyao")).build();
        DecodedJWT decodedJWT= jwtVerifier.verify(token);//验证并生成解析后的JWT对象
        Map<String, Claim> claims=decodedJWT.getClaims();
        System.out.println(claims.get("user"));
        //头部和载荷修改会解析失败
        // 密钥修改也会解析失败
        // token过期也会失败

    }
}
