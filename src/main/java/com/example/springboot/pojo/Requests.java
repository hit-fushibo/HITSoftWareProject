package com.example.springboot.pojo;


import lombok.Data;

@Data
public class Requests {
    private String rid;
    private String uid;
    private  String fromUid;
    private String toUid;
    private String  rType;
    private String newLevel;
    private String startTime;
    private String endTime;
}
