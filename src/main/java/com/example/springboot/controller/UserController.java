package com.example.springboot.controller;

import com.example.springboot.pojo.Result;
import com.example.springboot.pojo.Users;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.JwtUtil;
import com.example.springboot.utils.MD5Util;
import com.example.springboot.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(String userPhone,String password){
        //查询手机号是否被注册
        Users u=userService.findByPhone(userPhone);
        if(u==null){
            //没有占用
            // 注册
            userService.register(userPhone,password);
            return Result.success();
        }
        else {
            //占用
            return Result.error("手机号已被注册");
        }


    }
    @PostMapping("/login")
    public Result<String> login(String id,String password,int pori){
        System.out.println(id+password+pori);
        Users u;
        //查询用户
        if(pori==0)
        {
            u=userService.findByPhone(id);
        } else if (pori==1) {
            u=userService.findByUid(id);
        }
        else {
            return Result.error("请求参数错误，pori只能为0或1");
        }

        //判断用户是否存在
        if(u==null){
            return Result.error("用户名错误");
        }
        //判断密码是否正确
        if(MD5Util.encrypt(password).equals(u.getPwd())){
            //生成JWT令牌
            Map<String,Object> claims=new HashMap<>();
            claims.put("uid",u.getUid());
            claims.put("phone",u.getPhone());
            String token=JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");

    }

    @GetMapping("/userinfo")
    public Result<Users> userInfo(@RequestHeader(name="Authorization")String token){
        //根据用户名查询用户

        // 优化：在拦截器中解析了token，在这里使用解析结果，
        // 拦截器解析时使用ThreadLocal存储线程局部变量
        // 后续在Control、Service、Mapper层使用时使用ThreadLocal获取局部变量

        Map<String,Object> map = ThreadLocalUtil.get();
        String uid=(String)map.get("uid");
        System.out.println("用户获取用户信息");
        Users user=userService.findByUid(uid);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Users user){
        //判断user是否存在
        if(userService.findByUid(user.getUid())==null){
            return Result.error("用户不存在");
        }

        //进行更新操作
        int flag=userService.update(user);

        //判断是否出错
        if(flag==0){
            return Result.success();
        } else if (flag==1) {
            return Result.error("手机号已被占用");
        }
        else if (flag==2) {
            return Result.error("昵称已被占用");
        }
        else {
            return Result.error("未知错误：更新用户信息-1");
        }

    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }


    @PatchMapping("updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        int flag=userService.updatePwd(params.get("old_pwd"),params.get("new_pwd"));
        if(flag==0){
            return Result.success();
        }
        else {
            return Result.error("密码错误");
        }

    }


    @PostMapping("uploadAvatar")
    public Result<String> uploadAvatar(MultipartFile file) throws IOException {
        //文件的内容存储到本地磁盘
        Map<String,Object> map=ThreadLocalUtil.get();
        String uid= (String) map.get("uid");
        file.transferTo(new File("D:\\code\\SoftWareProject\\src\\main\\resources\\static\\usr_pic\\"+uid+".png"));
        String url="\\static\\usr_pic\\"+uid+".png";
        return Result.success(url);
    }
}
