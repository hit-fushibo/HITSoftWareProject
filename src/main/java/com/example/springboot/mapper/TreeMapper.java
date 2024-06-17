package com.example.springboot.mapper;

import com.example.springboot.pojo.Node;

import java.util.ArrayList;

public interface TreeMapper {

    public boolean IsTeacherOfWhomInLevel(String tUid,String sUid,String level);

    ArrayList<Node> getTree(String uid);
    
    void del(String tid,String sid,String level);

    void add(String tid, String sid, String level, String startTime, String endTime);

    void modify(String tid, String sid, String level, String startTime, String endTime);
}
