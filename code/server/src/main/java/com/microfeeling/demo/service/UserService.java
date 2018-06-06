package com.microfeeling.demo.service;

import com.microfeeling.demo.dto.ReqPageDTO;
import com.microfeeling.demo.dto.ResPageDTO;
import com.microfeeling.demo.entity.UserEntity;

/**
 * Add by jianhan on 2018/6/5
 */
public interface UserService {

    ResPageDTO search(ReqPageDTO reqPageDTO, String keyword);

    UserEntity selectByPrimaryKey(Integer id);

    void insert(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    int updatePasswordByPrimaryKey(UserEntity record);

    void deleteByPrimaryKey(UserEntity record);
}
