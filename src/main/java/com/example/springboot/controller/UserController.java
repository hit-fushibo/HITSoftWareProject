package com.example.springboot.controller;

import com.example.springboot.pojo.Result;
import com.example.springboot.pojo.Users;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.JwtUtil;
import com.example.springboot.utils.MD5Util;
import com.example.springboot.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private usrPicDir picDir;

    /**
     * 注册接口，在其中检查是否已经被注册
     *
     * @param userPhone 用户注册手机号
     * @param password  用户注册的密码
     * @return Result的一个实例，被自动解析成json
     */
    @PostMapping("/register")
    public Result register(String userPhone, String password) {
        //查询手机号是否被注册
        Users u = userService.findByPhone(userPhone);
        if (u == null) {
            //没有占用
            // 注册
            userService.register(userPhone, password);
            return Result.success();
        } else {
            //占用
            return Result.error("手机号已被注册");
        }
    }

    /**
     * 用户登录接口
     *
     * @param id       用户登录凭证，可以为UID或手机号
     * @param password 登录密码
     * @param pori     登录凭证类型，0-手机号、1-UID
     * @return 成功，带有JWT令牌的响应数据实例。失败，带有失败提示信息的响应数据实例。
     * 会被自动解析为JSON
     */
    @PostMapping("/login")
    public Result<String> login(String id, String password, int pori) {
        Users u;
        //查询用户
        if (pori == 0) {
            u = userService.findByPhone(id);
        } else if (pori == 1) {
            u = userService.findByUid(id);
        } else {
            return Result.error("请求参数错误，pori只能为0或1");
        }
        //判断用户是否存在
        if (u == null) {
            return Result.error("用户名错误");
        }
        //判断密码是否正确
        if (MD5Util.encrypt(password).equals(u.getPwd())) {
            //生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("uid", u.getUid());
            claims.put("phone", u.getPhone());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    /**
     * 获取用户信息的接口
     *
     * @return 携带登录用户的信息的响应数据。自动解析为JSON。
     */
    @GetMapping("/userinfo")
    public Result<Users> userInfo() {
        //根据用户名查询用户

        // 优化：在拦截器中解析了token，在这里使用解析结果，
        // 拦截器解析时使用ThreadLocal存储线程局部变量
        // 后续在Control、Service、Mapper层使用时使用ThreadLocal获取局部变量

        Map<String, Object> map = ThreadLocalUtil.get();
        String uid = (String) map.get("uid");
        Users user = userService.findByUid(uid);
        return Result.success(user);
    }

    /**
     * 查询用户的接口
     *
     * @param id   查询的输入
     * @param type 查询的类型。0-根据姓名查询、1-根据uid查询、2-根据手机号查询
     * @return 携带查询结果的响应数据
     */
    @PostMapping("/searchUsers")
    public Result searchUsers(String id, String type) {
        ArrayList<Users> u = new ArrayList<>();
        if (Objects.equals(type, "0")) {
            u = userService.findByName(id);
        } else if (Objects.equals(type, "1")) {
            Users user = userService.findByUid(id);
            u.add(user);
        } else if (Objects.equals(type, "2")) {
            Users user = userService.findByPhone(id);
            u.add(user);
        } else {
            return Result.error("type字段不符合要求");
        }
        return Result.success(u);
    }

    /**
     * 更新用户信息的接口
     *
     * @param user 要更新的用户信息，由Spring自动从JSON数据中解析成一个Users实例
     * @return 成功，返回成功响应。错误，返回带有错误提示信息的响应。
     */
    @PutMapping("/update")
    public Result update(@RequestBody Users user) {
        //判断user是否存在
        if (userService.findByUid(user.getUid()) == null) {
            return Result.error("用户不存在");
        }
        //进行更新操作
        int flag = userService.update(user);
        //判断是否出错
        if (flag == 0) {
            return Result.success();
        } else if (flag == 1) {
            return Result.error("手机号已被占用");
        } else if (flag == 2) {
            return Result.error("昵称已被占用");
        } else {
            return Result.error("未知错误：更新用户信息-1");
        }
    }

    /**
     * 更新用户头像地址的url
     *
     * @param avatarUrl 新的用户头像地址
     * @return 成功的响应信息
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    /**
     * 更新用户密码接口
     *
     * @param params 旧密码和新密码的一个字典，由Spring自动解析
     * @return 成功-成功响应。失败-带有错误提示信息的响应
     */
    @PatchMapping("updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        int flag = userService.updatePwd(params.get("old_pwd"), params.get("new_pwd"));
        if (flag == 0) {
            return Result.success();
        } else {
            return Result.error("密码错误");
        }
    }

    /**
     * 更新用户头像至本地存储
     *
     * @param file 解析得到的头像文件
     * @return 携带用户头像访问地址的响应数据
     * @throws IOException 可能会抛出IOException
     */
    @PostMapping("uploadAvatar")
    public Result<String> uploadAvatar(MultipartFile file) throws IOException {
        Map<String, Object> map = ThreadLocalUtil.get();
        String uid = (String) map.get("uid");


        String directoryPath = picDir.picLocPath;
        System.out.println("0013245646" + directoryPath);
        String fileNamePrefix = uid + "00";
        File directory = new File(directoryPath);

        // 检查usr_pic目录下是否有以uid为前缀的png文件
        File[] files = directory.listFiles((dir, name) -> name.startsWith(fileNamePrefix) && name.endsWith(".png"));

        if (files != null && files.length > 0) {
            // 存在符合条件的文件
            File originalFile = files[0];
            String originalFileName = originalFile.getName();
            String lastTwoDigits = originalFileName.substring(originalFileName.length() - 6, originalFileName.length() - 4);
            int newSuffix = (Integer.parseInt(lastTwoDigits) + 1) % 100;
            String newFileName = uid + String.format("%02d", newSuffix) + ".png";

            File newFile = new File(directoryPath + newFileName);
            originalFile.delete();

            file.transferTo(newFile);
            String url = picDir.picReqPath + newFileName;
            // userService.updateAvatar(url);

            return Result.success(url);
        } else {
            // 不存在符合条件的文件
            String newFileName = fileNamePrefix + ".png";
            File newFile = new File(directoryPath + newFileName);

            file.transferTo(newFile);
            String url = picDir.picReqPath + newFileName;
            // userService.updateAvatar(url);

            return Result.success(url);
        }
    }
}

@Component
class usrPicDir {
    @Value("${local-img.r-path}")
    public String picReqPath; // 请求地址
    @Value("${local-img.save-path}")
    public String picLocPath; // 本地存放资源目录的绝对路径
}
