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

        //检查是否有同样作用的请求，自己已经申请过或者别人给自己申请过
        if(isRepeatOfMyRequest(addUid,"11",level)==1) {
            return 1;
        }

        String rid= TimeStamp.getTimeStamp();
        String rType="011";
        String sql="insert into request (rid,uid,from_uid,to_uid,rtype,level,start_time,end_time) " +
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

        //检查是否有同样作用的请求，自己已经申请过或者别人给自己申请过
        if(isRepeatOfMyRequest(addUid,"12",level)==1){
            return 1;
        }


        String rid= TimeStamp.getTimeStamp();
        String rType="012";
        String sql="insert into request (rid,uid,from_uid,to_uid,rtype,level,start_time,end_time) " +
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

        //检查是否为其的老师


        //检查是否有同样作用的请求，其已经申请过或者别人给其申请过
        //检查其的请求中是否有同样的请求
        if(isRepeatOfOthersRequest(who,addUid,"11",level)==1){
            return 1;
        }

        //请求没有问题，添加请求
        String rid= TimeStamp.getTimeStamp();
        String rType="111";
        String  sql="insert into request (rid,uid,from_uid,to_uid,rtype,level,start_time,end_time) " +
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
        //检查是否为其的学生


        //检查是否有同样作用的请求，其已经申请过或者别人给其申请过
        //检查其的请求中是否有同样的请求
        if(isRepeatOfOthersRequest(who,addUid,"12",level)==1){
            return 1;
        }

        //请求没有问题，添加请求
        String rid= TimeStamp.getTimeStamp();
        String rType="112";
        String sql="insert into request (rid,uid,from_uid,to_uid,rtype,level,start_time,end_time) " +
                "values ('"+rid+"','"+who+"','"+uid+"','"+addUid+"','"+rType+"','"+level+"','"+startTime+"','"+endTime+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    @Override
    public int delMyStudent(String delUid,String level) {
        dbUtil.getConnection();
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否有同样作用的请求，自己已经申请过或者别人给自己申请过
        //检查自己的请求中是否有同样的请求
        if(isRepeatOfMyRequest(delUid,"01",level)==1){
            return 1;
        }
        //请求没有问题，添加请求

        String rid= TimeStamp.getTimeStamp();
        String rType="001";
        String sql="insert into request (rid,uid,from_uid,to_uid,rtype) " +
                "values ('"+rid+"','"+delUid+"','"+uid+"','"+uid+"','"+rType+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    @Override
    public int delMyTeacher(String delUid,String level) {
        dbUtil.getConnection();
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否有同样作用的请求，自己已经申请过或者别人给自己申请过
        if(isRepeatOfMyRequest(delUid,"02",level)==1){
            return 1;
        }

        //请求没有问题，添加请求
        String rid= TimeStamp.getTimeStamp();
        String rType="002";
        String  sql="insert into request (rid,uid,from_uid,to_uid,rtype) " +
                "values ('"+rid+"','"+delUid+"','"+uid+"','"+uid+"','"+rType+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    @Override
    public int delOthersStudent(String who, String delUid,String level) {
        dbUtil.getConnection();
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否有同样作用的请求，其已经申请过或者别人给其申请过
        if(isRepeatOfOthersRequest(who,delUid,"01",level)==1){
            return 1;
        }
        //请求没有问题，添加请求

        String rid= TimeStamp.getTimeStamp();
        String rType="101";
        String  sql="insert into request (rid,uid,from_uid,to_uid,rtype) " +
                "values ('"+rid+"','"+who+"','"+uid+"','"+delUid+"','"+rType+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    @Override
    public int delOthersTeacher(String who, String delUid,String level) {
        dbUtil.getConnection();
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否有同样作用的请求，其已经申请过或者别人给其申请过
        if(isRepeatOfOthersRequest(who,delUid,"02",level)==1){
            return 1;
        }
        //请求没有问题，添加请求

        String rid= TimeStamp.getTimeStamp();
        String rType="102";
        String sql="insert into request (rid,uid,from_uid,to_uid,rtype) " +
                "values ('"+rid+"','"+who+"','"+uid+"','"+delUid+"','"+rType+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    public int isRepeatOfMyRequest(String toUid,String type,String level){

        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        return repeatTest(uid, toUid, type,level);
    }
    public int isRepeatOfOthersRequest(String who,String  toUid,String type,String level){

        return repeatTest(who, toUid, type,level);
    }

    private int repeatTest(String who, String toUid, String type,String level) {
        dbUtil.getConnection();

        String sql="select * from request where uid='"+toUid+"' and from_uid='"+who+"' and to_uid='"+who+"' and level='"+level+"' and rtype='0"+type+"'";
        ResultSet rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sql="select * from request where uid='"+who+"' and to_uid='"+toUid+"' and level='"+level+"' and rtype='1"+type+"'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                dbUtil.close();
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
