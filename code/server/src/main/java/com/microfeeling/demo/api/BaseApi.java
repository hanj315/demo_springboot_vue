package com.microfeeling.demo.api;

import com.microfeeling.demo.entity.UserEntity;
import com.microfeeling.demo.util.StringUtil;
import com.microfeeling.demo.dto.ReqPageDTO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Add by jianhan on 2018/6/5
 */
public class BaseApi {

    @Resource
    private HttpServletRequest request;

    //获取token
    public String getToken() {
        String token = StringUtil.trim(request.getHeader("token"));
        return StringUtil.isEmpty(token) ? StringUtil.trim(request.getParameter("token")) : token;
    }

    //获取语言
    public String getLanguage() {
        String language = StringUtil.trim(request.getHeader("language"));
        return StringUtil.isEmpty(language) ? StringUtil.trim(request.getParameter("language")) : language;
    }

    //获取分页对象
    public ReqPageDTO getPage() {

        ReqPageDTO reqPageDTO = new ReqPageDTO();

        String pageNum_str = StringUtil.trim(request.getParameter("pageNumber"));
        int pageNum = StringUtil.isEmpty(pageNum_str) ? 0 : Integer.parseInt(pageNum_str);

        String pageSize_str = StringUtil.trim(request.getParameter("pageSize"));
        int pageSize = StringUtil.isEmpty(pageSize_str) ? 0 : Integer.parseInt(pageSize_str);

        String sort = StringUtil.trim(request.getParameter("sort"));

        String desc = StringUtil.trim(request.getParameter("desc"));

        reqPageDTO.setPageNumber(pageNum);
        reqPageDTO.setPageSize(pageSize);
        reqPageDTO.setSort(sort);
        reqPageDTO.setDesc(desc);

        return reqPageDTO;
    }

    public UserEntity getCurrentUser(){
        String token = this.getToken();
        // TODO 获取当前用户
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        return	userEntity;
    }
}
