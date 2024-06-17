package com.example.springboot.service.impl;

import com.example.springboot.mapper.RequestMapper;
import com.example.springboot.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequsetServiceImpl implements RequestService {

    @Autowired
    private RequestMapper requestMapper;

    @Override
    public int addMyStudent(String addUid, String level, String startTime, String endTime) {
        return requestMapper.addMyStudent(addUid,level,startTime,endTime);
    }

    @Override
    public int addMyTeacher(String addUid, String level, String startTime, String endTime) {
        return requestMapper.addMyTeacher(addUid,level,startTime,endTime);
    }

    @Override
    public int addOthersStudent(String who, String addUid, String level, String startTime, String endTime) {
        return requestMapper.addOthersStudent(who,addUid,level,startTime,endTime);
    }

    @Override
    public int addOthersTeacher(String who, String addUid, String level, String startTime, String endTime) {
        return requestMapper.addOthersTeacher(who,addUid,level,startTime,endTime);
    }

    @Override
    public int delMyStudent(String delUid) {
        return requestMapper.delMyStudent(delUid);
    }

    @Override
    public int delMyTeacher(String delUid) {
        return requestMapper.delMyTeacher(delUid);

    }
}
