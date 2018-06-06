package com.microfeeling.demo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Add by jianhan on 2018/6/5
 */
@Data
public class ReqPageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int pageNumber;
    private int pageSize;

    private String sort = "";
    private String desc;
}
