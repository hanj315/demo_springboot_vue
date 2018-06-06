package com.microfeeling.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Add by jianhan on 2018/6/6
 */
@Component
public class ErrorMsgUtil {
    @Autowired
    protected MessageSource messageSource;

    public  String errorMsg(String system_language,String key){
        Locale locale = new Locale("zh","CN");
        if(system_language.contains("_")){
            locale = new Locale(system_language.split("_")[0],system_language.split("_")[1]);
        }
        String value = messageSource.getMessage(key, null, locale);
        return value;
    }
}
