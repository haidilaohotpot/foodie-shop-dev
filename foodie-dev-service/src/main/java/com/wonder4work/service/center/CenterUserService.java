package com.wonder4work.service.center;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wonder4work.pojo.Users;
import com.wonder4work.pojo.bo.UserBO;
import com.wonder4work.pojo.bo.center.CenterUserBO;

/**
 * <p>
 * 用户表  服务类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
public interface CenterUserService extends IService<Users> {


    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     * @param userId
     * @param centerUserBO
     * @return
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 用户头像更新
     * @param userId
     * @param faceUrl
     * @return
     */
    Users updateUserFace(String userId,String faceUrl);

}
