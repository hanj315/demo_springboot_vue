package com.microfeeling.demo.mapper;

import com.microfeeling.demo.entity.ProfileEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileMapper {

    ProfileEntity selectByPrimaryKey(Integer id);

    int insert(ProfileEntity record);

    int updateByPrimaryKey(ProfileEntity record);
}