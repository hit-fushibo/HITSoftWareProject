package com.example.springboot.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Objects;

@Data
public class Requests {
    private String rid;
    private  String fromUid;
    private String fromName;
    private String toUid;
    private String toName;
    private String level;
    @JsonIgnore
    private String  meRoOthers;//0-对自己树的申请，1-对他人树的申请
    @JsonIgnore
    private String type;//0-删除，1-增加，2-修改
    @JsonIgnore
    private String sOrt;//0-学生，1-老师
    @JsonIgnore
    private String startTime;
    @JsonIgnore
    private String endTime;
    private String description;

    public Requests(String rid, String fromUid, String fromName, String toUid, String toName, String level, String meRoOthers, String type, String sOrt, String startTime, String endTime, String description) {
        this.rid = rid;
        this.fromUid = fromUid;
        this.fromName = fromName;
        this.toUid = toUid;
        this.toName = toName;
        this.level = level;
        this.meRoOthers = meRoOthers;
        this.type = type;
        this.sOrt = sOrt;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public void genDescription(){
        String Level;
        if(Objects.equals(level, "0")){
            Level="本科";
        }else if(Objects.equals(level, "1")){
            Level="硕士";
        }
        else {
            Level="博士";
        }

        if(Objects.equals(meRoOthers, "0")){
            if(Objects.equals(type, "0")){
                if(Objects.equals(sOrt, "0")){
                    //删除自己的学生
                    description="删除您为"+fromName+"("+fromUid+")的"+Level+"学生";
                }
                else {
                    //删除自己的老师
                    description="删除您为"+fromName+"("+fromUid+")的"+Level+"老师";
                }
            } else if (Objects.equals(type, "1")) {
                if(Objects.equals(sOrt, "0")){
                    description="添加您为"+fromName+"("+fromUid+")的"+Level+"学生";
                }
                else {
                    //增加自己的老师
                    description="添加您为"+fromName+"("+fromUid+")的"+Level+"老师";
                }
            }else {
                description="修改您与"+fromName+"("+fromUid+")的"+Level+"师生关系起止时间为"+startTime.substring(0,4)+"-"+startTime.substring(4)+"至"+endTime.substring(0,4)+"-"+endTime.substring(4);
            }
        }
        else {
            if(Objects.equals(type, "0")){
                if(Objects.equals(sOrt, "0")){
                    //删除自己的学生
                    description="删除"+toName+"("+toUid+")为您的"+Level+"学生";
                }
                else {
                    //删除自己的老师
                    description="删除"+toName+"("+toUid+")为您的"+Level+"老师";
                }
            } else if (Objects.equals(type, "1")) {
                if(Objects.equals(sOrt, "0")){
                    description="添加"+toName+"("+toUid+")为您的"+Level+"学生";
                }
                else {
                    //增加自己的老师
                    description="添加"+toName+"("+toUid+")为您的"+Level+"老师";
                }
            }else {
                description="修改您与"+toName+"("+toName+")的"+Level+"师生关系起止时间为"+startTime.substring(0,4)+"-"+startTime.substring(4)+"至"+endTime.substring(0,4)+"-"+endTime.substring(4);
            }

        }
    }
}
