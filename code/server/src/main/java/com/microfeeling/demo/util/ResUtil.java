package com.microfeeling.demo.util;

import com.microfeeling.demo.dto.ApiResDTO;

/**
 * Add by jianhan on 2018/6/5
 */
public class ResUtil {

    public static ApiResDTO successMsg(Object data){
        ApiResDTO minfo = new ApiResDTO();
        minfo.setSuccess("1");
        minfo.setErrorCode("");
        minfo.setErrorMessage("");
        minfo.setData(data);
        return minfo;
    }

    public static ApiResDTO errorMsg(String errorCode, String errorMsg){
        ApiResDTO minfo = new ApiResDTO();
        minfo.setSuccess("0");
        minfo.setErrorCode(errorCode);
        minfo.setErrorMessage(errorMsg);
        minfo.setData(null);
        return minfo;
    }
}
