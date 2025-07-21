package com.yupi.usercenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 账号
     */
    @TableField(value = "userAccount")
    private String userAccount;

    /**
     * 头像
     */
    @TableField(value = "avatarUrl")
    private String avatarUrl;

    /**
     * 性别
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 密码
     */
    @TableField(value = "userPassword")
    private String userPassword;

    /**
     * 盐值
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 状态0-正常，1-封号
     */
    @TableField(value = "userStatus")
    private Integer userStatus;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    private Date updateTime;

    /**
     * 删除
     */
    @TableLogic
    @TableField(value = "isDelete")
    private Integer isDelete;


    /**
     * 角色 0-普通用户，1-管理员
     */
    @TableField(value = "role")
    private int role;
}