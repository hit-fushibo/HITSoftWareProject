package com.example.springboot.mapper;

import com.example.springboot.pojo.Users;

//@Mapper
public interface UserMapper {

    //根据手机号查询用户
//    @Select("select * from users where phone=#{userPhone}")
    Users findByPhone(String userPhone);

    //使用手机号和加密后的密码添加用户
//    @Insert("insert into users(phone, pwd, uid) values(#{userPhone}, #{password}, #{uid})")
    void addWithPhonePwdAndUid(String userPhone, String password, String uid);

    Users findByUid(String id);

    int update(Users user);
}
