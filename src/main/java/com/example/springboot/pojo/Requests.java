package com.example.springboot.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Requests {
    private String rid;
    private  String fromUid;
    private String fromName;
    private String toUid;
    private String toName;
    @JsonIgnore
    private String  meOrOthers;//0-对自己树的申请，1-对他人树的申请
    @JsonIgnore
    private String type;//0-删除，1-增加，2-修改
    @JsonIgnore
    private String sOrt;//0-学生，1-老师
    @JsonIgnore
    private String newLevel;
    @JsonIgnore
    private String startTime;
    @JsonIgnore
    private String endTime;
    private String description;
}
