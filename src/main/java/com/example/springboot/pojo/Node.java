package com.example.springboot.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
public class Node {
    private String uid;
    private String type;
    private ArrayList<Map<String,String>> relationships;
    private String myPage;
    private String name;

    public Node(String uid, String type, String myPage, String name) {
        this.uid = uid;
        this.type = type;
        this.myPage = myPage;
        this.name = name;
        relationships=new ArrayList<>();
    }

    public void addRelation(String level,String startTime,String endTime){
        Map<String,String>relation=new HashMap<>();
        relation.put("level",level);
        relation.put("startTime",startTime.substring(0,4)+"-"+startTime.substring(4));
        relation.put("endTime",endTime.substring(0,4)+"-"+endTime.substring(4));
        relationships.add(relation);

    }

}
