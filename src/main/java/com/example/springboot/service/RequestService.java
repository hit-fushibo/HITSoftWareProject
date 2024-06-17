package com.example.springboot.service;

public interface RequestService {

    int addMyStudent(String addUid, String level, String startTime, String endTime);

    int addMyTeacher(String addUid, String level, String startTime, String endTime);

    int addOthersStudent(String who, String addUid, String level, String startTime, String endTime);

    int addOthersTeacher(String who, String addUid, String level, String startTime, String endTime);

    int delMyStudent(String delUid);

    int delMyTeacher(String delUid);
}
