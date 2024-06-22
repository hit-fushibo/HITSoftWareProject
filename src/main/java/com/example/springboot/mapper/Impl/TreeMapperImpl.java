package com.example.springboot.mapper.Impl;

import com.example.springboot.mapper.TreeMapper;
import com.example.springboot.pojo.Node;
import com.example.springboot.utils.TreeDBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

@Component
public class TreeMapperImpl implements TreeMapper {

    @Autowired
    private TreeDBUtil treeDbUtil;

    /**
     * 判断是否存在指定师生的某个层次的师生关系
     *
     * @param tUid  老师
     * @param sUid  学生
     * @param level 层次
     * @return true-存在，false-不存在
     */
    @Override
    public boolean IsTeacherOfWhomInLevel(String tUid, String sUid, String level) {
        treeDbUtil.getConnection();
        String sql = "select * from tree where teacher_uid='" + tUid + "' and student_uid='" + sUid + "' and level='" + level + "'";
        ResultSet rs = treeDbUtil.executeQuery(sql);
        try {
            if (rs.next()) {
                treeDbUtil.close();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        treeDbUtil.close();
        return false;
    }

    /**
     * 查询是否存在指定师生的师生关系
     *
     * @param tUid 老师
     * @param sUid 学生
     * @return true-存在，false-不存在
     */
    @Override
    public boolean IsTeacherOfWhomInAnyLevel(String tUid, String sUid) {
        treeDbUtil.getConnection();
        String sql = "select * from tree where teacher_uid='" + tUid + "' and student_uid='" + sUid + "'";
        try (ResultSet rs = treeDbUtil.executeQuery(sql)) {
            try {
                if (rs.next()) {
                    treeDbUtil.close();
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        treeDbUtil.close();
        return false;
    }

    /**
     * 获取指定用户的师承树
     *
     * @param uid 用户uid
     * @return 树节点类Node的实例构成的ArrayList
     */
    @Override
    public ArrayList<Node> getTree(String uid) {
        ArrayList<Node> nodes = new ArrayList<>();
        treeDbUtil.getConnection();
        //获取老师
        String sql = "select * from tree where student_uid='" + uid + "'";
        try (ResultSet rs = treeDbUtil.executeQuery(sql)) {
            String tUid;
            String type;
            while (rs.next()) {
                tUid = rs.getString("teacher_uid");
                type = "1";
                addRelation(nodes, rs, tUid, type);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //获取学生
        sql = "select * from tree where teacher_uid='" + uid + "'";
        try (ResultSet rs = treeDbUtil.executeQuery(sql)) {
            String sUid;
            String type;
            while (rs.next()) {
                sUid = rs.getString("student_uid");
                type = "2";
                addRelation(nodes, rs, sUid, type);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        nodes.add(new Node(uid, "0", "", ""));
        treeDbUtil.close();
        return nodes;
    }

    /**
     * 删除指定师生的指定层次师生关系
     *
     * @param tid   老师
     * @param sid   学生
     * @param level 层次
     */
    @Override
    public void del(String tid, String sid, String level) {
        treeDbUtil.getConnection();
        String sql = "delete from tree where teacher_uid='" + tid + "' and student_uid='" + sid + "' and level='" + level + "'";
        treeDbUtil.executeUpdate(sql);
        treeDbUtil.close();
    }

    /**
     * 添加新的师生关系
     *
     * @param tid       老师
     * @param sid       学生
     * @param level     层次
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    @Override
    public void add(String tid, String sid, String level, String startTime, String endTime) {
        treeDbUtil.getConnection();
        String sql = "insert into tree (teacher_uid,student_uid,level,start_time,end_time)" +
                "values ('" + tid + "','" + sid + "','" + level + "','" + startTime + "','" + endTime + "')";
        treeDbUtil.executeUpdate(sql);
        treeDbUtil.close();
    }

    /**
     * 修改师生关系
     *
     * @param tid       老师
     * @param sid       学生
     * @param level     层次
     * @param startTime 新的开始时间
     * @param endTime   新的结束时间
     */
    @Override
    public void modify(String tid, String sid, String level, String startTime, String endTime) {
        treeDbUtil.getConnection();
        String sql = "update tree " +
                "set start_time='" + startTime + "' end_time='" + endTime + "' " +
                "where teacher_uid='" + tid + "' and student_uid='" + sid + "' and level='" + level + "'";
        treeDbUtil.executeUpdate(sql);
        treeDbUtil.close();
    }

    /**
     * 添加新的师生关系，如果存在结点，则添加到该节点否则新建结点并添加
     *
     * @param nodes 结点列表
     * @param rs    查询结果
     * @param sUid  学生uid
     * @param type  结点类型，0-自己，1-学生，2-老师
     * @throws SQLException 可能会抛出SQLException，因为调用ResultSet的方法了
     */
    private void addRelation(ArrayList<Node> nodes, ResultSet rs, String sUid, String type) throws SQLException {
        String level = rs.getString("level");
        String startTime = rs.getString("start_time");
        String endTime = rs.getString("end_time");
        boolean flag = true;
        for (Node node : nodes) {
            if (Objects.equals(node.getUid(), sUid)) {
                node.addRelation(level, startTime, endTime);
                flag = false;
                break;
            }
        }
        if (flag) {
            Node node = new Node(sUid, type, "", "");
            node.addRelation(level, startTime, endTime);
            nodes.add(node);

        }
    }
}
