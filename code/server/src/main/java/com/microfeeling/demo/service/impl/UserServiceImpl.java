package com.microfeeling.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microfeeling.demo.dto.ReqPageDTO;
import com.microfeeling.demo.dto.ResPageDTO;
import com.microfeeling.demo.entity.ProfileEntity;
import com.microfeeling.demo.entity.UserEntity;
import com.microfeeling.demo.mapper.ProfileMapper;
import com.microfeeling.demo.mapper.UserMapper;
import com.microfeeling.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Add by jianhan on 2018/6/5
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @Override
    public ResPageDTO search(ReqPageDTO reqPageDTO, String keyword) {

        ResPageDTO pd = new ResPageDTO();

        int pageNumber = reqPageDTO.getPageNumber();
        int pageSize = reqPageDTO.getPageSize();
        String sort = reqPageDTO.getSort();
        String desc = reqPageDTO.getDesc();
        if(pageNumber > 0 &&  pageSize > 0){
            PageHelper.startPage(pageNumber, pageSize);
        }
        if(!sort.isEmpty() && !desc.isEmpty()){
            PageHelper.orderBy(sort + " " + desc);
        }

        List<UserEntity> list_auth = userMapper.search(keyword);

        pd.setPageNumber(pageNumber);
        pd.setPageSize(pageSize);
        pd.setTotal((int) new PageInfo<UserEntity>(list_auth).getTotal());
        pd.setData(new PageInfo<UserEntity>(list_auth).getList());

        return pd;
    }

    @Override
    public UserEntity selectByPrimaryKey(Integer id) {
        UserEntity userEntity = userMapper.selectByPrimaryKey(id);
        if (userEntity != null) {
            userEntity.setProfile(profileMapper.selectByPrimaryKey(userEntity.getProfileId()));
        }
        return userEntity;
    }

    @Override
    @Transactional(value="masterTransactionManager")
    public void insert(UserEntity record) {
        ProfileEntity profileEntity = record.getProfile();
        profileMapper.insert(profileEntity);
        record.setPassword(passwordEncoder.encode(record.getPassword()));
        record.setProfileId(profileEntity.getId());
        userMapper.insert(record);
    }

    /**
     * 更新用户 1：成功 2：用户不存在 3：输入有误
     * @param record
     */
    @Override
    @Transactional(value="masterTransactionManager")
    public int updateByPrimaryKey(UserEntity record) {
        if (record != null && record.getId() != null) {
            UserEntity oldUserEntity = userMapper.selectByPrimaryKey(record.getId());
            if (oldUserEntity != null) {
                userMapper.updateByPrimaryKey(record);
                ProfileEntity profileEntity = record.getProfile();
                if (profileEntity != null) {
                    profileEntity.setId(oldUserEntity.getProfileId());
                    profileMapper.updateByPrimaryKey(profileEntity);
                }
            } else {
                return 2;
            }
        } else {
            return 3;
        }
        return 1;
    }

    /**
     * 修改密码 1：成功 2：旧密码错误 3：用户不存在 4：输入有误
     * @param record
     */
    @Override
    public int updatePasswordByPrimaryKey(UserEntity record) {
        if (record == null ||
                record.getId() <= 0 ||
                record.getOldPassword() == null ||
                "".equals(record.getOldPassword()) ||
                record.getPassword() == null ||
                "".equals(record.getPassword()) ||
                record.getPassword().length() < 6) {
            return 4;
        }
        UserEntity oldUserEntity = userMapper.selectByPrimaryKey(record.getId());
        if (oldUserEntity == null) {
            return 3;
        } else {
            if (passwordEncoder.matches(record.getOldPassword(), oldUserEntity.getPassword())) {
                record.setPassword(passwordEncoder.encode(record.getPassword()));
                userMapper.updatePasswordByPrimaryKey(record);
                return 1;
            } else {
                return 2;
            }
        }
    }

    @Override
    public void deleteByPrimaryKey(UserEntity record) {
        userMapper.deleteByPrimaryKey(record);
    }
}
