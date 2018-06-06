package com.microfeeling.demo.global;

import com.microfeeling.demo.dto.ApiResDTO;
import com.microfeeling.demo.exception.ErrorException;
import com.microfeeling.demo.util.ResUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Add by jianhan on 2018/6/5
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ErrorException.class)
    @ResponseBody
    public ApiResDTO jsonErrorHandler(HttpServletRequest req, ErrorException e) {
        String errorMsg = e.getMessage();
        String errorCode = "";
        if (errorMsg.contains(":")) {
            errorCode = errorMsg.split(":")[0];
            errorMsg = errorMsg.split(":")[1];
        }
        return ResUtil.errorMsg(errorCode, errorMsg);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ApiResDTO methodArgumentTypeMismatch(HttpServletRequest req, MethodArgumentTypeMismatchException e){
        logger.info(e.getMessage());
        return ResUtil.errorMsg("100001", "method argument type mismatch exception:" + e.getMessage());
    }


    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public ApiResDTO missingServletRequestParameter(HttpServletRequest req, MissingServletRequestParameterException e){
        logger.info(e.getMessage());
        return  ResUtil.errorMsg("100001", "missing servlet request Parameter exception:" + e.getMessage());
    }


    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    public ApiResDTO sql(HttpServletRequest req, SQLException e){
        logger.info(e.getMessage());
        return  ResUtil.errorMsg("100001", "sql exception: " + e.getMessage());
    }


//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public ApiResDTO exception(HttpServletRequest req, Exception e){
//  	  logger.info(e.getMessage());
//        return  ResUtil.errorMsg("100001", e.getMessage());
//    }
}
