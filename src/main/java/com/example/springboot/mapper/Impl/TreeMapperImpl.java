package com.example.springboot.mapper.Impl;

import com.example.springboot.mapper.TreeMapper;
import com.example.springboot.utils.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TreeMapperImpl implements TreeMapper {

    @Autowired
    private DBUtil dbUtil;
    @Override
    public boolean IsTeacherOfWhomInLevel(String tUid, String sUid,String level) {
        dbUtil.getConnection();
        String sql="select * from tree where teacher_uid='"+tUid+"' and student_uid='"+sUid+"' and level='"+level+"'";
        ResultSet rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
