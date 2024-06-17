package com.example.springboot.service.impl;

import com.example.springboot.mapper.RequestMapper;
import com.example.springboot.mapper.TreeMapper;
import com.example.springboot.service.RequestService;
import com.example.springboot.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RequsetServiceImpl implements RequestService {

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private TreeMapper treeMapper;

    @Override
    public int addMyStudent(String addUid, String level, String startTime, String endTime) {
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否已经是自己的学生
        if(treeMapper.IsTeacherOfWhomInLevel(uid,addUid,level)){
            return 2;
        }
        //检查是否为自己的老师
        if(treeMapper.IsTeacherOfWhomInLevel(addUid,uid,level)){
            return 3;
        }
        return requestMapper.addMyStudent(addUid,level,startTime,endTime);
    }

    @Override
    public int addMyTeacher(String addUid, String level, String startTime, String endTime) {
        Map<String,Object> map= ThreadLocalUtil.get();
        String uid=(String) map.get("uid");
        //检查是否已经是自己的老师
        if(treeMapper.IsTeacherOfWhomInLevel(addUid,uid,level)){
            return 2;
        }
        //检查是否为自己的学生
        if(treeMapper.IsTeacherOfWhomInLevel(uid,addUid,level)){
            return 3;
        }
        return requestMapper.addMyTeacher(addUid,level,startTime,endTime);
    }

    @Override
    public int addOthersStudent(String who, String addUid, String level, String startTime, String endTime) {
        //检查是否已经是其的学生
        if(treeMapper.IsTeacherOfWhomInLevel(who,addUid,level)){
            return 2;
        }
        //检查是否为其的老师
        if(treeMapper.IsTeacherOfWhomInLevel(addUid,who,level)){
            return 3;
        }
        return requestMapper.addOthersStudent(who,addUid,level,startTime,endTime);
    }

    @Override
    public int addOthersTeacher(String who, String addUid, String level, String startTime, String endTime) {
        //检查是否已经是其的老师
        if(treeMapper.IsTeacherOfWhomInLevel(addUid,who,level)){
            return 2;
        }
        //检查是否为其的学生
        if(treeMapper.IsTeacherOfWhomInLevel(who,addUid,level)){
            return 3;
        }
        return requestMapper.addOthersTeacher(who,addUid,level,startTime,endTime);
    }

    @Override
    public int delMyStudent(String delUid,String level) {
        return requestMapper.delMyStudent(delUid,level);
    }

    @Override
    public int delMyTeacher(String delUid,String level) {
        return requestMapper.delMyTeacher(delUid,level);

    }

    @Override
    public int delOthersStudent(String who, String delUid,String level) {
        return requestMapper.delOthersStudent(who,delUid,level);
    }

    @Override
    public int delOthersTeacher(String who, String delUid,String level) {
        return requestMapper.delOthersTeacher(who,delUid,level);
    }

}
