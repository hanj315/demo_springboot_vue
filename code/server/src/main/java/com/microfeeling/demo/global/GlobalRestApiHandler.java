package com.microfeeling.demo.global;

import com.microfeeling.demo.util.ResUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Add by jianhan on 2018/6/5
 */
@ControllerAdvice(basePackages = "com.microfeeling.demo.api")
public class GlobalRestApiHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
//		//获取当前处理请求的controller的方法
//        String methodName=returnType.getMethod().getName();
//        // 不拦截/不需要处理返回值 的方法
//        String method= "getFileContent"; //如登录
//        //不拦截
//        return !method.equals(methodName);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        return ResUtil.successMsg(body);
    }
}
