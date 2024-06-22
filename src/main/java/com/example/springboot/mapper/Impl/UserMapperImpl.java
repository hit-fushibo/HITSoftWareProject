package com.example.springboot.mapper.Impl;

import com.example.springboot.mapper.UserMapper;
import com.example.springboot.pojo.Users;
import com.example.springboot.utils.DBUtil;
import com.example.springboot.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private DBUtil dbUtil;

    /**
     * 根据手机号查询用户
     *
     * @param userPhone 要查询的手机号
     * @return 如果存在用户则返回对应的用户实例，否则返回null
     */
    @Override
    public Users findByPhone(String userPhone) {
        try {

            String sql = "select * from users where phone='" + userPhone + "'";
            return getUsers(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据手机号、密码和Uid添加新用户
     *
     * @param userPhone 添加用户的手机号
     * @param password  添加用户的密码，MD5处理过后的
     * @param uid       添加用户的uid
     */
    @Override
    public void addWithPhonePwdAndUid(String userPhone, String password, String uid) {
        String sql = "insert into users(phone, pwd, uid) values('" + userPhone + "', '" + password + "','" + uid + "')";
        System.out.println(sql);
        dbUtil.getConnection();
        dbUtil.executeUpdate(sql);
        dbUtil.close();
    }

    /**
     * 根据uid查询用户
     *
     * @param id 查询的uid
     * @return 如果存在，返回对应的用户实例。否则返回null
     */
    @Override
    public Users findByUid(String id) {
        try {
            String sql = "select * from users where uid='" + id + "'";
            return getUsers(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新用户信息
     *
     * @param user 要更新的用户信息
     * @return 0-成功，1-重复手机号，2-重复昵称
     */
    @Override
    public int update(Users user) {
        dbUtil.getConnection();
        String uid = user.getUid();
        String phone = user.getPhone();
        String email = user.getEmail();
        String name = user.getName();
        String nickname = user.getNickname();
        String myPage = user.getMyPage();
        String sql = "select * from users where phone='" + phone + "' and uid!='" + uid + "'";
        ResultSet rs = dbUtil.executeQuery(sql);
        //出错标识 0-正常，1-重复手机号，2-重复昵称
        try {
            if (rs.next()) {
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sql = "select * from users where nickname='" + nickname + "' and uid!='" + uid + "'";
        rs = dbUtil.executeQuery(sql);
        try {
            if (rs.next()) {
                dbUtil.close();
                return 2;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sql = "UPDATE users SET "
                + "phone = '" + phone + "', "
                + "email = '" + email + "', "
                + "name = '" + name + "', "
                + "nickname = '" + nickname + "',"
                + "mypage = '" + myPage + "' "
                + "WHERE uid = '" + uid + "'";
        System.out.println(sql);
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    /**
     * 更新用户头像地址
     *
     * @param avatarUrl 要更新的用户头像地址
     */
    @Override
    public void updateAvatar(String avatarUrl) {
        dbUtil.getConnection();
        Map<String, Object> map = ThreadLocalUtil.get();
        String uid = (String) map.get("uid");
        String sql = "update users set usr_pic='" + avatarUrl + "' where uid='" + uid + "'";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
    }

    /**
     * 更新用户密码
     *
     * @param oldPwd 旧密码MD5
     * @param newPwd 新密码MD5
     * @return 0-成功，1-旧密码错误
     */
    @Override
    public int updatePwd(String oldPwd, String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String uid = (String) map.get("uid");
        String sql = "select * from users where uid='" + uid + "' and pwd='" + oldPwd + "'";
        dbUtil.getConnection();
        ResultSet rs = dbUtil.executeQuery(sql);
        try {
            if (rs.next()) {
                sql = "update users set pwd='" + newPwd + "'";
                dbUtil.executeUpdate(sql);
                dbUtil.close();
                return 0;
            } else {
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据姓名查询用户
     *
     * @param id 查询的用户姓名
     * @return 查询到的用户对应的Users实例组成的ArrayList
     */
    @Override
    public ArrayList<Users> findByName(String id) {
        ArrayList<Users> u = new ArrayList<>();
        dbUtil.getConnection();
        String sql = "select * from users where name='" + id + "'";
        ResultSet rs = dbUtil.executeQuery(sql);
        String uid;
        String pwd;
        String phone;
        String email;
        String name;
        String nickname;
        String usrPic;
        String myPage;
        while (true) {
            try {
                if (!rs.next()) break;
                uid = rs.getString("uid");
                pwd = rs.getString("pwd");
                phone = rs.getString("phone");
                email = rs.getString("email");
                name = rs.getString("name");
                nickname = rs.getString("nickname");
                usrPic = rs.getString("usr_pic");
                myPage = rs.getString("mypage");
                Users user = new Users(uid, pwd, phone, email, name, nickname, usrPic, myPage);
                u.add(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        dbUtil.close();
        return u;
    }

    /**
     * 根据sql语句查询用户
     *
     * @param sql 查询的sql语句
     * @return 如果存在用户则返回该用户对应的用户实例，否则返回null
     * @throws SQLException 可能会抛出SQLException
     */
    private Users getUsers(String sql) throws SQLException {
        System.out.println(sql);
        String uid;
        String pwd;
        String phone;
        String email;
        String name;
        String nickname;
        String usrPic;
        String myPage;
        dbUtil.getConnection();
        try (ResultSet rs = dbUtil.executeQuery(sql)) {
            uid = "";
            pwd = "";
            phone = "";
            email = "";
            name = "";
            nickname = "";
            usrPic = "";
            myPage = "";

            while (rs.next()) {
                uid = rs.getString("uid");
                pwd = rs.getString("pwd");
                phone = rs.getString("phone");
                email = rs.getString("email");
                name = rs.getString("name");
                nickname = rs.getString("nickname");
                usrPic = rs.getString("usr_pic");
                myPage = rs.getString("mypage");
            }
        }
        if (Objects.equals(uid, "")) {
            return null;
        }
        dbUtil.close();
        return new Users(uid, pwd, phone, email, name, nickname, usrPic, myPage);
    }
}
