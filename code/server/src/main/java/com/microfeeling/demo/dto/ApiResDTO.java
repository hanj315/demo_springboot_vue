package com.microfeeling.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String success;
    private String errorCode;
    private String errorMessage;
    private Object data;
}
