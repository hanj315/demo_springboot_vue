package com.microfeeling.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * PROFILE
 * @author 
 */
@Data
public class ProfileEntity extends AuditEntity {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 姓名
     */
    private String fullname;

    /**
     * 头像
     */
    private String avatarPath;

    /**
     * 性别
     */
    private String gender;

    /**
     * 民族
     */
    private String nationality;

    /**
     * 政治面貌
     */
    private String politicalStatus;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 户籍地址
     */
    private String residenceAddress;

    /**
     * 常住地址
     */
    private String permanentAddress;

    /**
     * 毕业院校
     */
    private String university;

    /**
     * 固定电话
     */
    private String phone;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * QQ
     */
    private String qq;

    /**
     * 微信
     */
    private String weixin;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 传真
     */
    private String fax;

    /**
     * 备注
     */
    private String remarks;
}