package com.microfeeling.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * USER
 * @author 
 */
@Data
public class UserEntity extends AuditEntity {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 简介id
     */
    private Integer profileId;

    /**
     * 状态
     */
    private String status;

    /**
     * 是否删除（0：不是，1：是）
     */
    private Boolean isDeleted;


    private String oldPassword;
    private ProfileEntity profile;
}