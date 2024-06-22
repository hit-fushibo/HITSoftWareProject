package com.example.springboot.service.impl;

import com.example.springboot.mapper.TreeMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.pojo.Node;
import com.example.springboot.pojo.Users;
import com.example.springboot.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TreeServiceImpl implements TreeService {

    @Autowired
    private TreeMapper treeMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ArrayList<Node> getTree(String uid) {
        ArrayList<Node> nodes = treeMapper.getTree(uid);
        //解析nodes中的name和myPage
        for (Node node : nodes) {
            Users u = userMapper.findByUid(node.getUid());
            node.setName(u.getName());
            node.setMyPage(u.getMyPage());
        }
        return nodes;
    }
}
