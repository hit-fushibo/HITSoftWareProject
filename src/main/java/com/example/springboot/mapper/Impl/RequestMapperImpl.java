package com.example.springboot.mapper.Impl;

import com.example.springboot.mapper.RequestMapper;
import com.example.springboot.utils.DBUtil;
import com.example.springboot.utils.ThreadLocalUtil;
import com.example.springboot.utils.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
public class RequestMapperImpl implements RequestMapper {
    @Autowired
    private DBUtil dbUtil;

    @Override
    public int addMyStudent(String addUid, String level, String startTime, String endTime) {
        dbUtil.getConnection();
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否已经是自己的学生
        String sql="select * from tree where teacher_uid='"+uid+"' and student_uid='"+addUid+"'";
        ResultSet rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 2;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //检查是否为自己的老师
        sql="select * from tree where teacher_uid='"+addUid+"' and student_uid='"+uid+"'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 3;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //检查是否有同样作用的请求，自己已经申请过或者别人给自己申请过
        //检查自己的请求中是否有同样的请求
        sql="select * from request where uid='"+addUid+"' and from_uid='"+uid+"' and to_uid='"+uid+"' and rtype='011'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //检查他人对自己的修改请求中是否有同样的请求
        sql="select * from request where uid='"+uid+"' and to_uid='"+addUid+"' and rtype='111'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //请求没有问题，添加请求
        String rid= TimeStamp.getTimeStamp();
        String rType="011";
        sql="insert into request (rid,uid,from_uid,to_uid,rtype,level,start_time,end_time) " +
                "values ('"+rid+"','"+addUid+"','"+uid+"','"+uid+"','"+rType+"','"+level+"','"+startTime+"','"+endTime+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    @Override
    public int addMyTeacher(String addUid, String level, String startTime, String endTime) {
        dbUtil.getConnection();
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否已经是自己的老师
        String sql="select * from tree where teacher_uid='"+addUid+"' and student_uid='"+uid+"'";
        ResultSet rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();

                return 2;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //检查是否为自己的学生
        sql="select * from tree where teacher_uid='"+uid+"' and student_uid='"+addUid+"'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 3;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //检查是否有同样作用的请求，自己已经申请过或者别人给自己申请过
        //检查自己的请求中是否有同样的请求
        sql="select * from request where uid='"+addUid+"' and from_uid='"+uid+"' and to_uid='"+uid+"' and rtype='012'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //检查他人对自己的修改请求中是否有同样的请求
        sql="select * from request where uid='"+uid+"' and to_uid='"+addUid+"' and rtype='112'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String rid= TimeStamp.getTimeStamp();
        String rType="012";
        sql="insert into request (rid,uid,from_uid,to_uid,rtype,level,start_time,end_time) " +
                "values ('"+rid+"','"+addUid+"','"+uid+"','"+uid+"','"+rType+"','"+level+"','"+startTime+"','"+endTime+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    @Override
    public int addOthersStudent(String who, String addUid, String level, String startTime, String endTime) {
        dbUtil.getConnection();
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否已经是其的学生
        String sql="select * from tree where teacher_uid='"+who+"' and student_uid='"+addUid+"'";
        ResultSet rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 2;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //检查是否为其的老师
        sql="select * from tree where teacher_uid='"+addUid+"' and student_uid='"+who+"'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 3;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //检查是否有同样作用的请求，其已经申请过或者别人给其申请过
        //检查其的请求中是否有同样的请求
        sql="select * from request where uid='"+addUid+"' and from_uid='"+who+"' and to_uid='"+who+"' and rtype='011'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //检查他人对其的修改请求中是否有同样的请求
        sql="select * from request where uid='"+who+"' and to_uid='"+addUid+"' and rtype='111'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //请求没有问题，添加请求
        String rid= TimeStamp.getTimeStamp();
        String rType="111";
        sql="insert into request (rid,uid,from_uid,to_uid,rtype,level,start_time,end_time) " +
                "values ('"+rid+"','"+who+"','"+uid+"','"+addUid+"','"+rType+"','"+level+"','"+startTime+"','"+endTime+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    @Override
    public int addOthersTeacher(String who, String addUid, String level, String startTime, String endTime) {
        dbUtil.getConnection();
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否已经是其的老师
        String sql="select * from tree where teacher_uid='"+addUid+"' and student_uid='"+who+"'";
        ResultSet rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 2;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //检查是否为其的学生
        sql="select * from tree where teacher_uid='"+who+"' and student_uid='"+addUid+"'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 3;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //检查是否有同样作用的请求，其已经申请过或者别人给其申请过
        //检查其的请求中是否有同样的请求
        sql="select * from request where uid='"+addUid+"' and from_uid='"+who+"' and to_uid='"+who+"' and rtype='012'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();

                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //检查他人对其的修改请求中是否有同样的请求
        sql="select * from request where uid='"+who+"' and to_uid='"+addUid+"' and rtype='112'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //请求没有问题，添加请求
        String rid= TimeStamp.getTimeStamp();
        String rType="112";
        sql="insert into request (rid,uid,from_uid,to_uid,rtype,level,start_time,end_time) " +
                "values ('"+rid+"','"+who+"','"+uid+"','"+addUid+"','"+rType+"','"+level+"','"+startTime+"','"+endTime+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    @Override
    public int delMyStudent(String delUid) {
        dbUtil.getConnection();
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否有同样作用的请求，自己已经申请过或者别人给自己申请过
        //检查自己的请求中是否有同样的请求
        String  sql="select * from request where uid='"+delUid+"' and from_uid='"+uid+"' and to_uid='"+uid+"' and rtype='001'";
        ResultSet rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();

                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //检查他人对其的修改请求中是否有同样的请求
        sql="select * from request where uid='"+uid+"' and to_uid='"+delUid+"' and rtype='101'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //请求没有问题，添加请求

        String rid= TimeStamp.getTimeStamp();
        String rType="001";
        sql="insert into request (rid,uid,from_uid,to_uid,rtype) " +
                "values ('"+rid+"','"+delUid+"','"+uid+"','"+uid+"','"+rType+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    @Override
    public int delMyTeacher(String delUid) {
        dbUtil.getConnection();
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否有同样作用的请求，自己已经申请过或者别人给自己申请过
        //检查自己的请求中是否有同样的请求
        String  sql="select * from request where uid='"+delUid+"' and from_uid='"+uid+"' and to_uid='"+uid+"' and rtype='002'";
        ResultSet rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();

                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //检查他人对其的修改请求中是否有同样的请求
        sql="select * from request where uid='"+uid+"' and to_uid='"+delUid+"' and rtype='102'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //请求没有问题，添加请求
        String rid= TimeStamp.getTimeStamp();
        String rType="002";
        sql="insert into request (rid,uid,from_uid,to_uid,rtype) " +
                "values ('"+rid+"','"+delUid+"','"+uid+"','"+uid+"','"+rType+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }
}
