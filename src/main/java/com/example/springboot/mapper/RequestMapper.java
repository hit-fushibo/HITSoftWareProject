package com.example.springboot.mapper;

public interface RequestMapper {

    int addMyStudent(String addUid, String level, String startTime, String endTime);

    int addMyTeacher(String addUid, String level, String startTime, String endTime);

    int addOthersStudent(String who, String addUid, String level, String startTime, String endTime);

    int addOthersTeacher(String who, String addUid, String level, String startTime, String endTime);

    int delMyStudent(String delUid,String level);

    int delMyTeacher(String delUid,String level);

    int delOthersStudent(String who, String delUid,String level);

    int delOthersTeacher(String who, String delUid,String level);
}
