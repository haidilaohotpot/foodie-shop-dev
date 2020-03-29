package com.wonder4work.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wonder4work.pojo.Users;
import com.wonder4work.pojo.bo.UserBO;

/**
 * <p>
 * 用户表  服务类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
public interface UsersService extends IService<Users> {

    /**
     * 判断用户名是否已经存在
     * @param username
     * @return
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     * @param userBO
     * @return
     */
    Users createUser(UserBO userBO);


    /**
     * 检索用户名和密码是否匹配，用于登录
     * @param username
     * @param password
     * @return
     */
    Users queryUserForLogin(String username, String password);


}
