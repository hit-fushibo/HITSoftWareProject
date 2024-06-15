package com.example.springboot.service.impl;

import com.example.springboot.mapper.EnvMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.pojo.Users;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EnvMapper envMapper;
    @Override
    public Users findByPhone(String userPhone) {
        Users u=userMapper.findByPhone(userPhone);
        return u;
    }

    @Override
    public void register(String userPhone, String password) {
        //加密密码
        String md5String=MD5Util.encrypt(password);
        //获取uid
        String cnt=envMapper.findVarByEnv("id_cnt");
        int new_cnt=Integer.parseInt(cnt)+1;
        envMapper.updateByEnv("id_cnt",String.valueOf(new_cnt));
        String uid= String.valueOf(100000000+new_cnt);
        //调用Mapper添加用户
        userMapper.addWithPhonePwdAndUid(userPhone,md5String,uid);

    }

    @Override
    public Users findByUid(String id) {
        Users u=userMapper.findByUid(id);
        return u;
    }

    @Override
    public int update(Users user) {
        return userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        userMapper.updateAvatar(avatarUrl);
    }

    @Override
    public int updatePwd(String oldPwd, String newPwd) {
        System.out.println(oldPwd);
        System.out.println(newPwd);
        String md5OldPwd=MD5Util.encrypt(oldPwd);
        String md5NewPwd=MD5Util.encrypt(newPwd);

        int flag=userMapper.updatePwd(md5OldPwd,md5NewPwd);
        return flag;
    }
}
