package com.example.springboot.service;

import com.example.springboot.pojo.Requests;

import java.util.ArrayList;

public interface RequestService {

    int addMyStudent(String addUid, String level, String startTime, String endTime);

    int addMyTeacher(String addUid, String level, String startTime, String endTime);

    int addOthersStudent(String who, String addUid, String level, String startTime, String endTime);

    int addOthersTeacher(String who, String addUid, String level, String startTime, String endTime);

    int delMyStudent(String delUid,String level);

    int delMyTeacher(String delUid,String level);

    int delOthersStudent(String who, String delUid,String level);

    int delOthersTeacher(String who, String delUid,String level);

    int modifyMyTree(String modifyUid, String level, String startTime, String endTime);

    int modifyOthersTree(String who, String modifyUid, String level, String startTime, String endTime);

    ArrayList<Requests> getAllRequests();

    void refuseRequest(String rid);

    void acceptRequest(String rid);
}
