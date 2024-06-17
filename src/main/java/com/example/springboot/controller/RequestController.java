package com.example.springboot.controller;


import com.example.springboot.mapper.TreeMapper;
import com.example.springboot.mapper.UserMapper;
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

    @PostMapping("/addMyStudent")
    public Result addMyStudent(String addUid,String level,String startTime,String endTime){

        int flag=requestService.addMyStudent(addUid,level,startTime,endTime);
        if(flag==0){
            return Result.success();
        } else if (flag==1) {
            return Result.error("已经有相同操作");
        } else if (flag==2) {
            return Result.error("已经是您的学生");
        }
        else {
            return Result.error("不能添加老师为学生");
        }

    }

    @PostMapping("/addMyTeacher")
    public Result addMyTeacher(String addUid,String level,String startTime,String endTime){

        int flag=requestService.addMyTeacher(addUid,level,startTime,endTime);
        if(flag==0){
            return Result.success();
        } else if (flag==1) {
            return Result.error("已经有相同操作");
        } else if (flag==2) {
            return Result.error("已经是您的老师");
        }
        else {
            return Result.error("不能添加学生为老师");
        }
    }

    @PostMapping("/addOthersStudent")
    public Result addOthersStudent(String who,String addUid,String level,String startTime,String endTime){
        int flag=requestService.addOthersStudent(who,addUid,level,startTime,endTime);
        if(flag==0){
            return Result.success();
        } else if (flag==1) {
            return Result.error("已经有相同操作");
        } else if (flag==2) {
            return Result.error("已经是其的学生");
        }
        else {
            return Result.error("不能添加其老师为其学生");
        }
    }

    @PostMapping("/addOthersTeacher")
    public Result addOthersTeacher(String who,String addUid,String level,String startTime,String endTime){
        int flag=requestService.addOthersTeacher(who,addUid,level,startTime,endTime);
        if(flag==0){
            return Result.success();
        } else if (flag==1) {
            return Result.error("已经有相同操作");
        } else if (flag==2) {
            return Result.error("已经是其的老师");
        }
        else {
            return Result.error("不能添加其学生为其老师");
        }
    }

    @PostMapping("/delMyStudent")
    public Result delMyStudent(String delUid,String level){
        int flag= requestService.delMyStudent(delUid,level);
        if(flag==0){
            return Result.success();
        }
        else {
            return Result.error("已经有相同操作");
        }

    }

    @PostMapping("/delMyTeacher")
    public Result delMyTeacher(String delUid,String level){
        int flag = requestService.delMyTeacher(delUid,level);
        if(flag==0){
            return Result.success();
        }
        else {
            return Result.error("已经有相同操作");
        }
    }

    @PostMapping("/delOthersStudent")
    public Result delOthersStudent(String who,String delUid,String level){
        int flag= requestService.delOthersStudent(who,delUid,level);
        if(flag==0){
            return Result.success();
        }
        else {
            return Result.error("已经有相同操作");
        }

    }

    @PostMapping("/delOthersTeacher")
    public Result delOthersTeacher(String who,String delUid,String level){
        int flag = requestService.delOthersTeacher(who,delUid,level);
        if(flag==0){
            return Result.success();
        }
        else {
            return Result.error("已经有相同操作");
        }
    }

    @PostMapping("/modifyMyTree")
    public Result modifyMyTree(String modifyUid,String level,String startTime,String endTime){
        int flag= requestService.modifyMyTree(modifyUid,level,startTime,endTime);
        if(flag==0){
            return Result.success();
        }
        else {
            return Result.error("已经有相同操作");
        }
    }

    @PostMapping("/modifyOthersTree")
    public Result modifyOthersTree(String who,String modifyUid,String level,String startTime,String endTime){
        int flag=requestService.modifyOthersTree(who,modifyUid,level,startTime,endTime);
        if(flag==0){
            return Result.success();
        }
        else {
            return Result.error("已经有相同操作");
        }
    }

    @PostMapping("/acceptRequest")
    public Result acceptRequest(String rid){
        requestService.acceptRequest(rid);

        return Result.success();
    }

    @PostMapping("/refuseRequest")
    public Result refuseRequest(String rid){
        requestService.refuseRequest(rid);

        return Result.success();
    }

    @GetMapping("/getAllRequests")
    public Result<ArrayList<Requests>> getAllRequests(){
        ArrayList<Requests> r=requestService.getAllRequests();
        return Result.success(r);
    }


}
