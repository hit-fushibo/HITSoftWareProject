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

    /**
     * 获取指定用户师承树的接口
     *
     * @param uid 用户uid
     * @return 带有师承树信息的响应数据
     */
    @PostMapping("/getTree")
    public Result<ArrayList<Node>> getTree(String uid) {
        ArrayList<Node> nodes = treeService.getTree(uid);
        return Result.success(nodes);
    }
}
