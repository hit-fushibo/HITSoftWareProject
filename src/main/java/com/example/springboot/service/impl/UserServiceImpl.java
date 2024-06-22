package com.example.springboot.service.impl;

import com.example.springboot.mapper.EnvMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.pojo.Users;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EnvMapper envMapper;

    /**
     * 根据手机号查询用户
     *
     * @param userPhone 要查询的用户手机号
     * @return 如果用户存在，则返回用户的一个Users实例，否则返回null
     */
    @Override
    public Users findByPhone(String userPhone) {
        Users u = userMapper.findByPhone(userPhone);
        return u;
    }

    /**
     * 根据手机号和密码注册用户
     *
     * @param userPhone 要注册用户的手机号
     * @param password  要注册用户设置的密码
     */
    @Override
    public void register(String userPhone, String password) {
        //加密密码
        String md5String = MD5Util.encrypt(password);
        //获取uid
        String cnt = envMapper.findVarByEnv("id_cnt");
        int new_cnt = Integer.parseInt(cnt) + 1;
        envMapper.updateByEnv("id_cnt", String.valueOf(new_cnt));
        String uid = String.valueOf(100000000 + new_cnt);
        //调用Mapper添加用户
        userMapper.addWithPhonePwdAndUid(userPhone, md5String, uid);
    }

    /**
     * 根据Uid查询用户
     *
     * @param id 查询用户的uid
     * @return 如果存在，返回对应的用户实例。否则返回null
     */
    @Override
    public Users findByUid(String id) {
        Users u = userMapper.findByUid(id);
        return u;
    }

    /**
     * 更新用户信息
     *
     * @param user 要更新的用户信息
     * @return 0-成功，1-重复手机号，2-重复昵称
     */
    @Override
    public int update(Users user) {
        return userMapper.update(user);
    }

    /**
     * 更新用户头像地址
     *
     * @param avatarUrl 要更新的用户头像地址
     */
    @Override
    public void updateAvatar(String avatarUrl) {
        userMapper.updateAvatar(avatarUrl);
    }

    /**
     * 更更新用户密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return 0-成功，1-旧密码错误
     */
    @Override
    public int updatePwd(String oldPwd, String newPwd) {
        String md5OldPwd = MD5Util.encrypt(oldPwd);
        String md5NewPwd = MD5Util.encrypt(newPwd);
        return userMapper.updatePwd(md5OldPwd, md5NewPwd);
    }

    /**
     * 根据姓名查询用户
     *
     * @param id 查询的用户姓名
     * @return 查询到的用户对应的Users实例组成的ArrayList
     */
    @Override
    public ArrayList<Users> findByName(String id) {
        return userMapper.findByName(id);
    }
}
