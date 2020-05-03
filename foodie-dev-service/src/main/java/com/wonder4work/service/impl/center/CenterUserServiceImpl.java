package com.wonder4work.service.impl.center;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonder4work.mapper.UsersMapper;
import com.wonder4work.pojo.Users;
import com.wonder4work.pojo.bo.center.CenterUserBO;
import com.wonder4work.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @since 1.0.0 2020/4/25
 */
@Service
public class CenterUserServiceImpl extends ServiceImpl<UsersMapper, Users> implements CenterUserService {


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Users user = this.getById(userId);

        user.setPassword(null);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {

        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUserBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        this.updateById(updateUser);

        return queryUserInfo(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserFace(String userId, String faceUrl) {
        Users updateUser = new Users();
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        updateUser.setFace(faceUrl);
        this.updateById(updateUser);

        return queryUserInfo(userId);
    }
}
