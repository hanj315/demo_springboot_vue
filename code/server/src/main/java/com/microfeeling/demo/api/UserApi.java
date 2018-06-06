package com.microfeeling.demo.api;

import com.microfeeling.demo.consts.EntityConsts;
import com.microfeeling.demo.dto.ResPageDTO;
import com.microfeeling.demo.entity.ProfileEntity;
import com.microfeeling.demo.entity.UserEntity;
import com.microfeeling.demo.exception.ErrorException;
import com.microfeeling.demo.service.UserService;
import com.microfeeling.demo.util.ErrorMsgUtil;
import com.microfeeling.demo.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Add by jianhan on 2018/6/5
 */
@RestController
@RequestMapping("/api/user")
public class UserApi extends BaseApi {

    private Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private ErrorMsgUtil errorMsgUtil;

    @Autowired
    private UserService userService;

    //////////////////////////////////////// 管理员用户的操作 start ////////////////////////////////////////
    @ApiOperation("分页查询用户")
    @GetMapping
    public ResPageDTO search(@RequestParam(value = "keyword", required = false) String keyword) {
        logger.info("try to search user by keyword: " + keyword);
        return userService.search(getPage(), keyword);
    }

    @ApiOperation("根据用户id获取用户")
    @GetMapping("/{id}")
    public UserEntity selectByPrimaryKey(@PathVariable("id") Integer id) {
        logger.info("try to get user by id: " + id);
        return getUser(id);
    }

    @ApiOperation("新建用户")
    @PostMapping
    public int insert(@RequestBody UserEntity userEntity) {
        logger.info("try to create user by userEntity: " + userEntity);
        UserEntity currentUser = getCurrentUser();
        Integer currentUserId = currentUser.getId();

        insert(userEntity, currentUserId);
        return 1;
    }

    @ApiOperation(value = "根据用户id更新用户")
    @PutMapping
    public int update(@RequestBody UserEntity userEntity) {
        logger.info("try to update user by userEntity : " + userEntity);
        UserEntity currentUser = getCurrentUser();
        Integer currentUserId = currentUser.getId();

        return updateUser(userEntity, currentUserId);
    }

    @ApiOperation(value = "修改用户密码")
    @PutMapping("/password")
    public int updatePassword(@RequestBody UserEntity userEntity) {
        logger.info("try to update user password by userEntity : " + userEntity);
        UserEntity currentUser = getCurrentUser();
        Integer currentUserId = currentUser.getId();

        updatePassword(userEntity, currentUserId);
        return 1;
    }

    @ApiOperation("根据用户id删除用户")
    @DeleteMapping
    public int delete(@RequestParam("id") Integer id) {
        logger.info("try to delete user by id: " + id);
        UserEntity currentUser = getCurrentUser();
        Integer currentUserId = currentUser.getId();
        Date currentDate = new Date();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setUpdatedAt(currentDate);
        userEntity.setUpdatedBy(currentUserId);
        userService.deleteByPrimaryKey(userEntity);
        return 1;
    }
    //////////////////////////////////////// 管理员用户的操作 end ////////////////////////////////////////

    //////////////////////////////////////// 当前用户的操作 start ////////////////////////////////////////
    @ApiOperation("获取当前用户")
    @GetMapping("/current")
    public UserEntity getCurrent() {
        logger.info("try to get current user");
        UserEntity currentUser = getCurrentUser();
        Integer currentUserId = currentUser.getId();
        return getUser(currentUserId);
    }

    @ApiOperation("注册用户")
    @PostMapping("/register")
    public int register(@RequestBody UserEntity userEntity) {
        logger.info("try to register user by userEntity: " + userEntity);
        Integer currentUserId = 0;

        insert(userEntity, currentUserId);
        return 1;
    }

    @ApiOperation(value = "更新当前用户")
    @PutMapping("/current")
    public int updateCurrent(@RequestBody UserEntity userEntity) {
        logger.info("try to update current user by userEntity : " + userEntity);
        UserEntity currentUser = getCurrentUser();
        Integer currentUserId = currentUser.getId();
        userEntity.setId(currentUserId);

        return updateUser(userEntity, currentUserId);
    }

    @ApiOperation(value = "修改用户密码")
    @PutMapping("/current/password")
    public int updatePasswordCurrent(@RequestBody UserEntity userEntity) {
        logger.info("try to update current user password by userEntity : " + userEntity);
        UserEntity currentUser = getCurrentUser();
        Integer currentUserId = currentUser.getId();

        userEntity.setId(currentUserId);
        updatePassword(userEntity, currentUserId);
        return 1;
    }
    //////////////////////////////////////// 当前用户的操作 end ////////////////////////////////////////

    /**
     * 获取用户
     * @param id
     * @return
     */
    private UserEntity getUser(Integer id) {
        UserEntity userEntity = userService.selectByPrimaryKey(id);
        if (userEntity != null) {
            userEntity.setPassword(null);
        }
        return userEntity;
    }

    /**
     * 新建用户
     * @param userEntity
     * @param currentUserId
     */
    private void insert(UserEntity userEntity, Integer currentUserId) {
        Date currentDate = new Date();

        ProfileEntity profileEntity = userEntity.getProfile();
        if (profileEntity == null) {
            profileEntity = new ProfileEntity();
        }
        // 性别
        if (StringUtil.isEmpty(profileEntity.getGender())) {
            profileEntity.setGender(EntityConsts.PROFILE_GENDER_UNKNOWN);
        }
        profileEntity.setCreatedAt(currentDate);
        profileEntity.setCreatedBy(currentUserId);
        profileEntity.setUpdatedAt(null);
        profileEntity.setUpdatedBy(null);

        userEntity.setProfile(profileEntity);
        userEntity.setStatus(EntityConsts.USER_STATUS_DEFAULT);
        userEntity.setIsDeleted(EntityConsts.IS_DELETED_DEFAULT);
        userEntity.setCreatedAt(currentDate);
        userEntity.setCreatedBy(currentUserId);
        userEntity.setUpdatedAt(null);
        userEntity.setUpdatedBy(null);
        try {
            userService.insert(userEntity);
        } catch (DuplicateKeyException ex) {
            String msg = ex.getCause().getMessage();
            if (msg != null && msg.contains("UNIQUE_USERNAME")) {
                throw new ErrorException("200001", errorMsgUtil.errorMsg(getLanguage(),"200001"));
            }
            // default
            throw new ErrorException("200001", errorMsgUtil.errorMsg(getLanguage(),"200001"));
        }
    }

    /**
     * 更新用户
     * @param userEntity
     * @param currentUserId
     * @return
     */
    private int updateUser(UserEntity userEntity, Integer currentUserId) {
        Date currentDate = new Date();

        ProfileEntity profileEntity = userEntity.getProfile();
        if (profileEntity != null) {
            profileEntity.setUpdatedAt(currentDate);
            profileEntity.setUpdatedBy(currentUserId);
        }
        userEntity.setUpdatedAt(currentDate);
        userEntity.setUpdatedBy(currentUserId);
        return userService.updateByPrimaryKey(userEntity);
    }

    /**
     * 更新密码
     * @param userEntity
     * @param currentUserId
     */
    private void updatePassword(UserEntity userEntity, Integer currentUserId) {
        Date currentDate = new Date();

        userEntity.setUpdatedAt(currentDate);
        userEntity.setUpdatedBy(currentUserId);
        int res = userService.updatePasswordByPrimaryKey(userEntity);
        switch (res) {
            case 2: throw new ErrorException("200002", errorMsgUtil.errorMsg(getLanguage(),"200002"));
            case 3: throw new ErrorException("200003", errorMsgUtil.errorMsg(getLanguage(),"200003"));
            case 4: throw new ErrorException("100002", errorMsgUtil.errorMsg(getLanguage(),"100002"));
        }
    }

}
