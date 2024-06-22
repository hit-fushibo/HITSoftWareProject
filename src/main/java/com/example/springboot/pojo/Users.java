package com.example.springboot.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Users {
    private String uid;

    @JsonIgnore
    private String pwd;
    private String phone;
    private String email;
    private String name;
    private String nickname;
    private String usrPic;
    private String myPage;


    public Users() {
    }

    public Users(String uid, String pwd, String phone, String email, String name, String nickname, String usrPic, String myPage) {
        this.uid = uid;
        this.pwd = pwd;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.usrPic = usrPic;
        this.myPage = myPage;
    }

    public Users(String uid, String phone, String email, String name, String nickname) {
        this.uid = uid;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsrPic() {
        return usrPic;
    }

    public void setUsrPic(String usrPic) {
        this.usrPic = usrPic;
    }
}
