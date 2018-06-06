package com.microfeeling.demo.mapper;

import com.microfeeling.demo.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    List<UserEntity> search(@Param("keyword") String keyword);

    UserEntity selectByPrimaryKey(Integer id);

    UserEntity selectByUsername(String username);

    int insert(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    int updatePasswordByPrimaryKey(UserEntity record);

    int updateStatusByPrimaryKey(UserEntity record);

    int deleteByPrimaryKey(UserEntity record);
}