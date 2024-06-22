package com.example.springboot.mapper.Impl;

import com.example.springboot.mapper.EnvMapper;
import com.example.springboot.utils.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EnvMapperImpl implements EnvMapper {
    @Autowired
    private DBUtil dbUtil;

    /**
     * 获取指定环境变量的值
     *
     * @param env 要获取的环境变量名称
     * @return 对应环境变量的值
     */
    @Override
    public String findVarByEnv(String env) {
        try {

            String sql = "select value from envvalue where env='" + env + "'";
            dbUtil.getConnection();
            ResultSet rs = dbUtil.executeQuery(sql);
            String value = "";
            while (rs.next()) {
                value = rs.getString("value");
            }
            dbUtil.close();
            return value;
        } catch (SQLException e) {
            dbUtil.close();
            throw new RuntimeException(e);
        }

    }

    /**
     * 更新环境变量
     *
     * @param env   要更新的环境变量名
     * @param value 要更新的值
     */
    @Override
    public void updateByEnv(String env, String value) {
        dbUtil.getConnection();
        String sql = "update envvalue set value='" + value + "' where env='" + env + "'";
        System.out.println(sql);
        dbUtil.executeUpdate(sql);
        dbUtil.close();
    }
}
