package com.wonder4work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonder4work.enums.Sex;
import com.wonder4work.mapper.UsersMapper;
import com.wonder4work.pojo.Users;
import com.wonder4work.pojo.bo.UserBO;
import com.wonder4work.service.UsersService;
import com.wonder4work.utils.DateUtil;
import com.wonder4work.utils.MD5Utils;
import io.swagger.annotations.ApiParam;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 用户表  服务实现类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    private Sid sid;

    private static final String USER_FACE = "http://www.wonder4work.com/image/logo.png";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<Users>();
        queryWrapper.eq("username", username);
        Users users = this.getOne(queryWrapper);
        return users != null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {

        String userId = sid.nextShort();
        Users user = new Users();
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 默认用户昵称同用户名
        user.setNickname(userBO.getUsername())
                .setFace(USER_FACE)
                .setBirthday(DateUtil.stringToDate("1900-01-01"))
                .setSex(Sex.secret.type)
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setId(userId);
        // 保存
        save(user);
        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<Users>();
        Users condition = new Users();
        condition.setUsername(username);
        condition.setPassword(password);
        queryWrapper.setEntity(condition);
        Users users = this.getOne(queryWrapper);
        return users;
    }
}
