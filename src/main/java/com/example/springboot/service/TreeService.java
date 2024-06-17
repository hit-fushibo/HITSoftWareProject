package com.example.springboot.service;

import com.example.springboot.pojo.Node;

import java.util.ArrayList;

public interface TreeService {
    ArrayList<Node> getTree(String uid);
}
