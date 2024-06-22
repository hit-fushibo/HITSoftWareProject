package com.example.springboot.controller;


import com.example.springboot.pojo.Requests;
import com.example.springboot.pojo.Result;
import com.example.springboot.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/requests")
public class RequestController {
    @Autowired
    private RequestService requestService;

    /**
     * 添加自己的学生接口
     *
     * @param addUid    要添加学生的Uid
     * @param level     要添加学生的师生关系层次
     * @param startTime 师生关系开始时间
     * @param endTime   师生关系结束时间
     * @return 成功，成功响应。失败，带有错误提示信息的失败响应。
     */
    @PostMapping("/addMyStudent")
    public Result addMyStudent(String addUid, String level, String startTime, String endTime) {
        int flag = requestService.addMyStudent(addUid, level, startTime, endTime);
        if (flag == 0) {
            return Result.success();
        } else if (flag == 1) {
            return Result.error("已经有相同操作");
        } else if (flag == 2) {
            return Result.error("已经是您的学生");
        } else {
            return Result.error("不能添加老师为学生");
        }
    }

    /**
     * 添加自己的老师接口
     *
     * @param addUid    要添加老师的Uid
     * @param level     要添加的师生关系层次
     * @param startTime 师生关系开始时间
     * @param endTime   师生关系结束时间
     * @return 成功，成功响应。失败，带有错误提示信息的失败响应。
     */
    @PostMapping("/addMyTeacher")
    public Result addMyTeacher(String addUid, String level, String startTime, String endTime) {

        int flag = requestService.addMyTeacher(addUid, level, startTime, endTime);
        if (flag == 0) {
            return Result.success();
        } else if (flag == 1) {
            return Result.error("已经有相同操作");
        } else if (flag == 2) {
            return Result.error("已经是您的老师");
        } else {
            return Result.error("不能添加学生为老师");
        }
    }

    /**
     * 添加其他人学生的接口
     *
     * @param who       要添加的用户uid
     * @param addUid    添加学生的uid
     * @param level     层次
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 0-成功，1-存在同类申请、2-已经是其的该层次学生、3-是其的老师
     */
    @PostMapping("/addOthersStudent")
    public Result addOthersStudent(String who, String addUid, String level, String startTime, String endTime) {
        int flag = requestService.addOthersStudent(who, addUid, level, startTime, endTime);
        if (flag == 0) {
            return Result.success();
        } else if (flag == 1) {
            return Result.error("已经有相同操作");
        } else if (flag == 2) {
            return Result.error("已经是其的学生");
        } else {
            return Result.error("不能添加其老师为其学生");
        }
    }

    /**
     * 添加其他人老师的接口
     *
     * @param who       要添加的用户uid
     * @param addUid    添加老师的uid
     * @param level     层次
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 0-成功，1-存在同类申请、2-已经是其的该层次学生、3-是其的老师
     */
    @PostMapping("/addOthersTeacher")
    public Result addOthersTeacher(String who, String addUid, String level, String startTime, String endTime) {
        int flag = requestService.addOthersTeacher(who, addUid, level, startTime, endTime);
        if (flag == 0) {
            return Result.success();
        } else if (flag == 1) {
            return Result.error("已经有相同操作");
        } else if (flag == 2) {
            return Result.error("已经是其的老师");
        } else {
            return Result.error("不能添加其学生为其老师");
        }
    }

    /**
     * 删除自己学生的接口
     *
     * @param delUid 删除学生uid
     * @param level  删除层次
     * @return 0-成功，1-存在同类申请
     */
    @PostMapping("/delMyStudent")
    public Result delMyStudent(String delUid, String level) {
        int flag = requestService.delMyStudent(delUid, level);
        if (flag == 0) {
            return Result.success();
        } else {
            return Result.error("已经有相同操作");
        }

    }

    /**
     * 删除自己老师的接口
     *
     * @param delUid 删除老师uid
     * @param level  删除层次
     * @return 0-成功，1-存在同类申请
     */
    @PostMapping("/delMyTeacher")
    public Result delMyTeacher(String delUid, String level) {
        int flag = requestService.delMyTeacher(delUid, level);
        if (flag == 0) {
            return Result.success();
        } else {
            return Result.error("已经有相同操作");
        }
    }

    /**
     * 删除他人学生的接口
     *
     * @param who    要删除的用户
     * @param delUid 删除学生uid
     * @param level  删除层次
     * @return 0-成功，1-存在同类申请
     */
    @PostMapping("/delOthersStudent")
    public Result delOthersStudent(String who, String delUid, String level) {
        int flag = requestService.delOthersStudent(who, delUid, level);
        if (flag == 0) {
            return Result.success();
        } else {
            return Result.error("已经有相同操作");
        }

    }

    /**
     * 删除他人老师的接口
     *
     * @param who    要删除的用户
     * @param delUid 删除老师uid
     * @param level  删除层次
     * @return 0-成功，1-存在同类申请
     */
    @PostMapping("/delOthersTeacher")
    public Result delOthersTeacher(String who, String delUid, String level) {
        int flag = requestService.delOthersTeacher(who, delUid, level);
        if (flag == 0) {
            return Result.success();
        } else {
            return Result.error("已经有相同操作");
        }
    }

    /**
     * 修改自己树结点的接口
     *
     * @param modifyUid 修改的结点uid
     * @param level     修改层次
     * @param startTime 修改后的开始时间
     * @param endTime   修改后的结束时间
     * @return 0-成功，1-存在同类申请
     */
    @PostMapping("/modifyMyTree")
    public Result modifyMyTree(String modifyUid, String level, String startTime, String endTime) {
        int flag = requestService.modifyMyTree(modifyUid, level, startTime, endTime);
        if (flag == 0) {
            return Result.success();
        } else {
            return Result.error("已经有相同操作");
        }
    }

    /**
     * 修改他人树结点的接口
     *
     * @param who       修改的用户uid
     * @param modifyUid 修改的结点uid
     * @param level     修改层次
     * @param startTime 修改后的开始时间
     * @param endTime   修改后的结束时间
     * @return 0-成功，1-存在同类申请
     */
    @PostMapping("/modifyOthersTree")
    public Result modifyOthersTree(String who, String modifyUid, String level, String startTime, String endTime) {
        int flag = requestService.modifyOthersTree(who, modifyUid, level, startTime, endTime);
        if (flag == 0) {
            return Result.success();
        } else {
            return Result.error("已经有相同操作");
        }
    }

    /**
     * 同意申请的接口
     *
     * @param rid 同意申请的rid
     * @return 成功响应
     */
    @PostMapping("/acceptRequest")
    public Result acceptRequest(String rid) {
        requestService.acceptRequest(rid);

        return Result.success();
    }

    /**
     * 拒绝申请的接口
     *
     * @param rid 拒绝申请的rid
     * @return 成功响应
     */
    @PostMapping("/refuseRequest")
    public Result refuseRequest(String rid) {
        requestService.refuseRequest(rid);

        return Result.success();
    }

    /**
     * 获取用户所有申请接口
     *
     * @return 带有申请类实例构成的ArrayList构成的响应数据
     */
    @GetMapping("/getAllRequests")
    public Result<ArrayList<Requests>> getAllRequests() {
        ArrayList<Requests> r = requestService.getAllRequests();
        return Result.success(r);
    }


}
