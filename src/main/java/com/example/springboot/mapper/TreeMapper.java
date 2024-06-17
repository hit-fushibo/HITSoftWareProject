package com.example.springboot.mapper;

import com.example.springboot.pojo.Node;

import java.util.ArrayList;

public interface TreeMapper {

    public boolean IsTeacherOfWhomInLevel(String tUid,String sUid,String level);

    ArrayList<Node> getTree(String uid);
}
