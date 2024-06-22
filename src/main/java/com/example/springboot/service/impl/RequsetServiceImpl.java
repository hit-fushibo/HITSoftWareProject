package com.example.springboot.service.impl;

import com.example.springboot.mapper.RequestMapper;
import com.example.springboot.mapper.TreeMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.pojo.Requests;
import com.example.springboot.pojo.Users;
import com.example.springboot.service.RequestService;
import com.example.springboot.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Service
public class RequsetServiceImpl implements RequestService {

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private TreeMapper treeMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加自己的学生
     *
     * @param addUid    要添加学生的uid
     * @param level     师生关系层次
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 0-成功，1-存在同类申请、2-已经是自己的该层次学生、3-是自己的老师
     */
    @Override
    public int addMyStudent(String addUid, String level, String startTime, String endTime) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String uid = (String) map.get("uid");
        //检查是否已经是自己的学生
        if (treeMapper.IsTeacherOfWhomInLevel(uid, addUid, level)) {
            return 2;
        }
        //检查是否为自己的老师
        if (treeMapper.IsTeacherOfWhomInAnyLevel(addUid, uid)) {
            return 3;
        }
        return requestMapper.addMyStudent(addUid, level, startTime, endTime);
    }

    /**
     * 添加自己的老师
     *
     * @param addUid    添加老师的uid
     * @param level     添加师生关系的层次
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 0-成功，1-存在同类申请、2-已经是自己的该层次老师、3-是自己的学生
     */
    @Override
    public int addMyTeacher(String addUid, String level, String startTime, String endTime) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String uid = (String) map.get("uid");
        //检查是否已经是自己的老师
        if (treeMapper.IsTeacherOfWhomInLevel(addUid, uid, level)) {
            return 2;
        }
        //检查是否为自己的学生
        if (treeMapper.IsTeacherOfWhomInAnyLevel(uid, addUid)) {
            return 3;
        }
        return requestMapper.addMyTeacher(addUid, level, startTime, endTime);
    }

    /**
     * 添加其他人的学生
     *
     * @param who       要添加的用户uid
     * @param addUid    添加学生的uid
     * @param level     层次
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 0-成功，1-存在同类申请、2-已经是其的该层次学生、3-是其的老师
     */
    @Override
    public int addOthersStudent(String who, String addUid, String level, String startTime, String endTime) {
        //检查是否已经是其的学生
        if (treeMapper.IsTeacherOfWhomInLevel(who, addUid, level)) {
            return 2;
        }
        //检查是否为其的老师
        if (treeMapper.IsTeacherOfWhomInAnyLevel(addUid, who)) {
            return 3;
        }
        return requestMapper.addOthersStudent(who, addUid, level, startTime, endTime);
    }

    /**
     * 添加其他人的老师
     *
     * @param who       要添加的用户uid
     * @param addUid    添加老师的uid
     * @param level     层次
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 0-成功，1-存在同类申请、2-已经是其的该层次老师、3-是其的学生
     */
    @Override
    public int addOthersTeacher(String who, String addUid, String level, String startTime, String endTime) {
        //检查是否已经是其的老师
        if (treeMapper.IsTeacherOfWhomInLevel(addUid, who, level)) {
            return 2;
        }
        //检查是否为其的学生
        if (treeMapper.IsTeacherOfWhomInAnyLevel(who, addUid)) {
            return 3;
        }
        return requestMapper.addOthersTeacher(who, addUid, level, startTime, endTime);
    }

    /**
     * 删除自己的学生
     *
     * @param delUid 删除学生uid
     * @param level  删除层次
     * @return 0-成功，1-存在同类申请
     */
    @Override
    public int delMyStudent(String delUid, String level) {
        return requestMapper.delMyStudent(delUid, level);
    }

    /**
     * 删除自己的老师
     *
     * @param delUid 删除老师uid
     * @param level  删除层次
     * @return 0-成功，1-存在同类申请
     */
    @Override
    public int delMyTeacher(String delUid, String level) {
        return requestMapper.delMyTeacher(delUid, level);

    }

    /**
     * 删除他人的学生
     *
     * @param who    要删除的用户
     * @param delUid 删除学生uid
     * @param level  删除层次
     * @return 0-成功，1-存在同类申请
     */
    @Override
    public int delOthersStudent(String who, String delUid, String level) {
        return requestMapper.delOthersStudent(who, delUid, level);
    }

    /**
     * 删除他人的老师
     *
     * @param who    要删除的用户
     * @param delUid 删除老师uid
     * @param level  删除层次
     * @return 0-成功，1-存在同类申请
     */
    @Override
    public int delOthersTeacher(String who, String delUid, String level) {
        return requestMapper.delOthersTeacher(who, delUid, level);
    }

    /**
     * 修改自己树的结点
     *
     * @param modifyUid 修改的结点uid
     * @param level     修改层次
     * @param startTime 修改后的开始时间
     * @param endTime   修改后的结束时间
     * @return 0-成功，1-存在同类申请
     */
    @Override
    public int modifyMyTree(String modifyUid, String level, String startTime, String endTime) {
        return requestMapper.modifyMyTree(modifyUid, level, startTime, endTime);
    }

    /**
     * 修改他人树的结点
     *
     * @param who       修改的用户uid
     * @param modifyUid 修改的结点uid
     * @param level     修改层次
     * @param startTime 修改后的开始时间
     * @param endTime   修改后的结束时间
     * @return 0-成功，1-存在同类申请
     */
    @Override
    public int modifyOthersTree(String who, String modifyUid, String level, String startTime, String endTime) {
        return requestMapper.modifyOthersTree(who, modifyUid, level, startTime, endTime);
    }

    /**
     * 获取用户所有申请
     *
     * @return 申请类实例构成的ArrayList
     */
    @Override
    public ArrayList<Requests> getAllRequests() {
        ArrayList<Requests> requests = requestMapper.getAllRequests();
        //解析 fromName toName description
        for (Requests r : requests) {
            String uid = r.getFromUid();
            Users u = userMapper.findByUid(uid);
            r.setFromName(u.getName());
            uid = r.getToUid();
            u = userMapper.findByUid(uid);
            r.setToName(u.getName());
            r.genDescription();
        }
        return requests;
    }

    /**
     * 拒绝申请
     *
     * @param rid 拒绝申请的rid
     */
    @Override
    public void refuseRequest(String rid) {
        requestMapper.refuseRequest(rid);
    }

    /**
     * 同意申请
     *
     * @param rid 同意申请的rid
     */
    @Override
    public void acceptRequest(String rid) {
        Requests request = requestMapper.acceptRequest(rid);
        String uid = request.getUid();
        String fromUid = request.getFromUid();
        String toUid = request.getToUid();
        String meRoOthers = request.getMeRoOthers();
        String type = request.getType();
        String sOrt = request.getSOrt();
        String tid;
        String sid;
        String level = request.getLevel();
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();
        //根据request中的内容修改tree表
        if (Objects.equals(meRoOthers, "0")) {
            if (Objects.equals(type, "0")) {
                if (Objects.equals(sOrt, "0")) {
                    //删除自己的学生
                    tid = request.getFromUid();
                    sid = request.getUid();

                } else {
                    //删除自己的老师
                    sid = request.getFromUid();
                    tid = request.getUid();
                }
                treeMapper.del(tid, sid, level);

            } else if (Objects.equals(type, "1")) {
                if (Objects.equals(sOrt, "0")) {
                    //增加自己老师
                    tid = request.getFromUid();
                    sid = request.getUid();
                } else {
                    //增加自己的老师
                    sid = request.getFromUid();
                    tid = request.getUid();
                }
                treeMapper.add(tid, sid, level, startTime, endTime);
            } else {
                //修改自己树结点
                tid = request.getFromUid();
                sid = request.getUid();
                if (treeMapper.IsTeacherOfWhomInLevel(tid, sid, level)) {
                    treeMapper.modify(tid, sid, level, startTime, endTime);
                } else {
                    treeMapper.modify(sid, tid, level, startTime, endTime);
                }
            }
            requestMapper.refuseRequest(rid);
        } else {
            requestMapper.changeToMyRequest(rid, uid, fromUid, toUid, "0" + type + sOrt);
        }
    }

}
