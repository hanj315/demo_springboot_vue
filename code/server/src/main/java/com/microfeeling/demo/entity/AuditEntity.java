package com.microfeeling.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public abstract class AuditEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建者
     */
    private Integer createdBy;

    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     * 修改者
     */
    private Integer updatedBy;
}
