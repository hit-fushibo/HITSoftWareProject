package com.example.springboot.mapper.Impl;

import com.example.springboot.mapper.TreeMapper;
import com.example.springboot.pojo.Node;
import com.example.springboot.utils.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public ArrayList<Node> getTree(String uid) {
        ArrayList<Node> nodes=new ArrayList<>();
        dbUtil.getConnection();
        //获取老师
        String sql="select * from tree where student_uid='"+uid+"'";
        try (ResultSet rs = dbUtil.executeQuery(sql)) {
            String tUid;
            String type;
            while (rs.next()) {
                tUid=rs.getString("teacher_uid");
                type="1";
                addRelation(nodes, rs, tUid, type);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //获取学生
        sql="select * from tree where teacher_uid='"+uid+"'";
        try (ResultSet rs = dbUtil.executeQuery(sql)) {
            String sUid;
            String type;
            while (rs.next()) {
                sUid=rs.getString("student_uid");
                type="2";
                addRelation(nodes, rs, sUid, type);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        nodes.add(new Node(uid,"0","",""));

        return nodes;
    }

    @Override
    public void del(String tid, String sid, String level) {
        dbUtil.getConnection();
        String sql="delete from tree where teacher_uid='"+tid+"' and student_uid='"+sid+"' and level='"+level+"'";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
    }

    @Override
    public void add(String tid, String sid, String level, String startTime, String endTime) {
        dbUtil.getConnection();
        String sql="insert into tree (teacher_uid,student_uid,level,start_time,end_time)" +
                "values ('"+tid+"','"+sid+"','"+level+"','"+startTime+"','"+endTime+"')";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
    }

    @Override
    public void modify(String tid, String sid, String level, String startTime, String endTime) {
        dbUtil.getConnection();
        String sql="update tree " +
                "set start_time='"+startTime+"' end_time='"+endTime+"' " +
                "where teacher_uid='"+tid+"' and student_uid='"+sid+"' and level='"+level+"'";
        dbUtil.executeUpdate(sql);
        dbUtil.close();
    }

    private void addRelation(ArrayList<Node> nodes, ResultSet rs, String sUid, String type) throws SQLException {
        String level=rs.getString("level");
        String startTime=rs.getString("start_time");
        String endTime=rs.getString("end_time");
        boolean flag=true;
        for(Node node:nodes){
            if(Objects.equals(node.getUid(), sUid)){
                node.addRelation(level,startTime,endTime);
                flag=false;
                break;
            }
        }
        if(flag){
            Node node=new Node(sUid,type,"","");
            node.addRelation(level,startTime,endTime);
            nodes.add(node);

        }
    }
}
