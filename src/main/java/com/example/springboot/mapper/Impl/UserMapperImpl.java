package com.example.springboot.mapper.Impl;

import com.example.springboot.mapper.UserMapper;
import com.example.springboot.pojo.Users;
import com.example.springboot.utils.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Objects;
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private DBUtil dbUtil;
    @Override
    public Users findByPhone(String userPhone) {
        try {

            String sql="select * from users where phone='"+userPhone+"'";
            return getUsers(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addWithPhonePwdAndUid(String userPhone, String password, String uid) {
        String sql="insert into users(phone, pwd, uid) values('"+userPhone+"', '"+password+"','"+uid+"')";
        System.out.println(sql);
        dbUtil.getConnection();
        dbUtil.executeUpdate(sql);
        dbUtil.close();
    }

    @Override
    public Users findByUid(String id) {
        try {
            String sql="select * from users where uid='"+id+"'";
            return getUsers(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Users user) {
        dbUtil.getConnection();
        String uid=(String) user.getUid();
//        String pwd=(String) user.getPwd();
        String phone=(String) user.getPhone();
        String email=(String) user.getEmail();
        String name=(String) user.getName();
        String nickname=(String) user.getNickname();
        String sql ="select * from users where phone='"+phone+"' and uid!='"+uid+"'";
        ResultSet rs=dbUtil.executeQuery(sql);
        //出错标识 0-正常，1-重复手机号，2-重复昵称
        while (true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return 1;
        }
        sql="select * from users where nickname='"+nickname+"' and uid!='"+uid+"'";
        rs=dbUtil.executeQuery(sql);
        while (true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return 2;
        }

        sql = "UPDATE users SET "
                + "phone = '" + phone + "', "
                + "email = '" + email + "', "
                + "name = '" + name + "', "
                + "nickname = '" + nickname + "' "
                + "WHERE uid = '" + uid + "'";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    private Users getUsers(String sql) throws SQLException {
        System.out.println(sql);
        String uid;
        String pwd;
        String phone;
        String email;
        String name;
        String nickname;
        String usrPic;
        dbUtil.getConnection();
        try (ResultSet rs = dbUtil.executeQuery(sql)) {
            uid = "";
            pwd = "";
            phone = "";
            email = "";
            name = "";
            nickname = "";
            usrPic = "";

            while (rs.next()) {
                uid = rs.getString("uid");
                pwd = rs.getString("pwd");
                phone = rs.getString("phone");
                email = rs.getString("email");
                name = rs.getString("name");
                nickname = rs.getString("nickname");
                usrPic = rs.getString("usr_pic");
            }
        }
        if(Objects.equals(uid, "")){
            return null;
        }
        dbUtil.close();
        return new Users(uid,pwd,phone,email,name,nickname,usrPic);
    }
}
