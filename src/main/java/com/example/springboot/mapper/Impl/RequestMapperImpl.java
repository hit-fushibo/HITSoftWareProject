package com.example.springboot.mapper.Impl;

import com.example.springboot.mapper.RequestMapper;
import com.example.springboot.pojo.Requests;
import com.example.springboot.utils.DBUtil;
import com.example.springboot.utils.ThreadLocalUtil;
import com.example.springboot.utils.TimeStamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            dbUtil.close();
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
            dbUtil.close();
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
            dbUtil.close();
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
            dbUtil.close();
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
            dbUtil.close();
            return 1;
        }
        //请求没有问题，添加请求

        String rid= TimeStamp.getTimeStamp();
        String rType="001";
        String sql="insert into request (rid,uid,from_uid,to_uid,rtype,level) " +
                "values ('"+rid+"','"+delUid+"','"+uid+"','"+uid+"','"+rType+"','"+level+"')";
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
            dbUtil.close();
            return 1;
        }

        //请求没有问题，添加请求
        String rid= TimeStamp.getTimeStamp();
        String rType="002";
        String  sql="insert into request (rid,uid,from_uid,to_uid,rtype,level) " +
                "values ('"+rid+"','"+delUid+"','"+uid+"','"+uid+"','"+rType+"','"+level+"')";
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
            dbUtil.close();
            return 1;
        }
        //请求没有问题，添加请求

        String rid= TimeStamp.getTimeStamp();
        String rType="101";
        String  sql="insert into request (rid,uid,from_uid,to_uid,rtype,level) " +
                "values ('"+rid+"','"+who+"','"+uid+"','"+delUid+"','"+rType+"','"+level+"')";
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
            dbUtil.close();
            return 1;
        }
        //请求没有问题，添加请求

        String rid= TimeStamp.getTimeStamp();
        String rType="102";
        String sql="insert into request (rid,uid,from_uid,to_uid,rtype,level) " +
                "values ('"+rid+"','"+who+"','"+uid+"','"+delUid+"','"+rType+"','"+level+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    @Override
    public int modifyMyTree(String modifyUid, String level, String startTime, String endTime) {
        dbUtil.getConnection();
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否有同样作用的请求，自己已经申请过或者别人给自己申请过
        if(isRepeatOfOthersRequest(uid,modifyUid,"20",level)==1){
            dbUtil.close();
            return 1;
        }
        //请求没有问题，添加请求

        String rid= TimeStamp.getTimeStamp();
        String rType="020";
        String sql="insert into request (rid,uid,from_uid,to_uid,rtype,level,start_time,end_time) " +
                "values ('"+rid+"','"+modifyUid+"','"+uid+"','"+uid+"','"+rType+"','"+level+"','"+startTime+"','"+endTime+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    @Override
    public int modifyOthersTree(String who, String modifyUid, String level, String startTime, String endTime) {
        dbUtil.getConnection();
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否有同样作用的请求，其已经申请过或者别人给其申请过
        if(isRepeatOfOthersRequest(who,modifyUid,"20",level)==1){
            dbUtil.close();
            return 1;
        }
        //请求没有问题，添加请求

        String rid= TimeStamp.getTimeStamp();
        String rType="120";
        String sql="insert into request (rid,uid,from_uid,to_uid,rtype,level,start_time,end_time) " +
                "values ('"+rid+"','"+who+"','"+uid+"','"+modifyUid+"','"+rType+"','"+level+"','"+startTime+"','"+endTime+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
        return 0;
    }

    @Override
    public ArrayList<Requests> getAllRequests() {
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        ArrayList<Requests> requests=new ArrayList<>();
        dbUtil.getConnection();
        String sql="select * from request where uid='"+uid+"'";
        try (ResultSet rs = dbUtil.executeQuery(sql)) {
            String rid;
            String fromUid;
            String fromName;
            String toUid;
            String toName;
            String level;
            String meRoOthers;//0-对自己树的申请，1-对他人树的申请
            String type;//0-删除，1-增加，2-修改
            String sOrt;//0-学生，1-老师
            String startTime;
            String endTime;
            String description;
            while (rs.next()) {
                rid=rs.getString("rid");
                fromUid=rs.getString("from_uid");
                fromName="";
                toUid=rs.getString("to_uid");
                toName="";
                level=rs.getString("level");
                String rtype=rs.getString("rtype");
                meRoOthers=String.valueOf(rtype.charAt(0));
                type=String.valueOf(rtype.charAt(1));
                sOrt=String.valueOf(rtype.charAt(2));
                startTime=rs.getString("start_time");
                endTime=rs.getString("end_time");

                requests.add(new Requests(rid,fromUid,fromName,toUid,toName,level,meRoOthers,type,sOrt,startTime,endTime,""));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dbUtil.close();
        return requests;
    }

    @Override
    public void refuseRequest(String rid) {
        dbUtil.getConnection();
        String sql="delete from request where rid='"+rid+"'";
        dbUtil.executeUpdate(sql);
        dbUtil.close();

    }

    @Override
    public Requests acceptRequest(String rid) {
        dbUtil.getConnection();
        String sql="select * from request where rid='"+rid+"'";
        try (ResultSet rs = dbUtil.executeQuery(sql)) {
            String fromUid;
            String fromName;
            String toUid;
            String toName;
            String level;
            String meRoOthers;//0-对自己树的申请，1-对他人树的申请
            String type;//0-删除，1-增加，2-修改
            String sOrt;//0-学生，1-老师
            String startTime;
            String endTime;
            String description;
            if (rs.next()) {
                rid=rs.getString("rid");
                fromUid=rs.getString("from_uid");
                fromName="";
                toUid=rs.getString("to_uid");
                toName="";
                level=rs.getString("level");
                String rtype=rs.getString("rtype");
                meRoOthers=String.valueOf(rtype.charAt(0));
                type=String.valueOf(rtype.charAt(1));
                sOrt=String.valueOf(rtype.charAt(2));
                startTime=rs.getString("start_time");
                endTime=rs.getString("end_time");
                String uid=rs.getString("uid");
                dbUtil.close();
                return new Requests(rid,fromUid,fromName,toUid,toName,level,meRoOthers,type,sOrt,startTime,endTime,"",uid);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dbUtil.close();
        return null;
    }

    @Override
    public void changeToMyRequest(String rid, String uid, String fromUid, String toUid, String s) {
        String newTime=TimeStamp.getTimeStamp();
        dbUtil.getConnection();
        String sql="update request " +
                "set rid='"+newTime+"',uid='"+toUid+"',from_uid='"+uid+"',to_uid='"+uid+"',rtype='"+s+"' " +
                "where rid='"+rid+"'";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
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
        //由外层调用，不控制数据库的连接和关闭
//        dbUtil.getConnection();

        String sql="select * from request where uid='"+toUid+"' and from_uid='"+who+"' and to_uid='"+who+"' and level='"+level+"' and rtype='0"+type+"'";
        ResultSet rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                //由外层调用，不控制数据库的连接和关闭
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sql="select * from request where uid='"+who+"' and to_uid='"+toUid+"' and level='"+level+"' and rtype='1"+type+"'";
        rs=dbUtil.executeQuery(sql);
        try {
            if(rs.next()){
                //由外层调用，不控制数据库的连接和关闭
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
