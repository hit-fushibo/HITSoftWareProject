package com.example.springboot.service;


import com.example.springboot.pojo.Users;

public interface UserService {
    //根据手机号查询用户
    Users findByPhone(String userPhone);

    //注册
    void register(String userPhone, String password);

    Users findByUid(String id);

    int update(Users user);
}
