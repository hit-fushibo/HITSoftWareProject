package com.example.springboot.mapper.Impl;

import com.example.springboot.mapper.EnvMapper;
import com.example.springboot.utils.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
@Component
public class EnvMapperImpl implements EnvMapper {
    @Autowired
    private DBUtil dbUtil;
    @Override
    public String findVarByEnv(String env) {
        try {

            String sql="select value from envvalue where env='"+env+"'";
            System.out.println(sql);
            dbUtil.getConnection();
            ResultSet rs=dbUtil.executeQuery(sql);
            String value="";
            while (rs.next()) {
                 value= rs.getString("value");
            }
            dbUtil.close();
            return value;
        } catch (SQLException e) {
            dbUtil.close();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateByEnv(String env, String value) {
        dbUtil.getConnection();
        String sql="update envvalue set value='"+value+"' where env='"+env+"'";
        System.out.println(sql);
        dbUtil.executeUpdate(sql);
        dbUtil.close();
    }
}
