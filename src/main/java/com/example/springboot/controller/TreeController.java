package com.example.springboot.controller;

import com.example.springboot.pojo.Node;
import com.example.springboot.pojo.Result;
import com.example.springboot.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/tree")
public class TreeController {


    @Autowired
    private TreeService treeService;

    @PostMapping("/getTree")
    public Result<ArrayList<Node>> getTree(String uid){
        ArrayList<Node> nodes=treeService.getTree(uid);

        //-----for test-----
//        ArrayList<Node> nodes=new ArrayList<>();
//        Node node=new Node("123456789","1","","zhangsan");
//        node.addRelation("0","202005","202106");
//        node.addRelation("1","202301","202305");
//        nodes.add(node);
//
//        node=new Node("987654321","2","","lisi");
//        node.addRelation("0","202005","202106");
//        node.addRelation("1","202301","202305");
//        nodes.add(node);
//
//        node=new Node("456789123","0","","lisi");
//        nodes.add(node);
        //-----for test-----
        return Result.success(nodes);
    }
}
