package com.microfeeling.demo.exception;

/**
 * Add by jianhan on 2018/6/5
 */
public class ErrorException extends RuntimeException {

    public ErrorException(String errorCode, String errorMsg) {
        super(errorCode + ":" + errorMsg);
    }
}
