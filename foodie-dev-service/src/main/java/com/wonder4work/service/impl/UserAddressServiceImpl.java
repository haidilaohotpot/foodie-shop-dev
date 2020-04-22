package com.wonder4work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonder4work.enums.YesOrNo;
import com.wonder4work.mapper.UserAddressMapper;
import com.wonder4work.pojo.UserAddress;
import com.wonder4work.pojo.bo.AddressBO;
import com.wonder4work.service.UserAddressService;
import lombok.experimental.Accessors;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 用户地址表  服务实现类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Autowired
    private Sid sid;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return this.list(queryWrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {

        // 1 判断用户是否存在地址，如果没有，则新增为默认地址
        Integer isDefault = 0;
        List<UserAddress> addressList = this.queryAll(addressBO.getUserId());
        if (addressList == null || addressList.isEmpty() || addressList.size() == 0) {
            isDefault = 1;
        }
        // 2 保存地址到数据库
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(sid.nextShort());
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        this.save(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {

        String addressId = addressBO.getAddressId();
        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, pendingAddress);
        pendingAddress.setId(addressId)
                .setUpdatedTime(new Date());
        this.updateById(pendingAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", addressId);
        map.put("user_id", userId);
        this.removeByMap(map);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        // 查找默认地址，设置为不默认
        Collection<UserAddress> list = this.listByMap(map);

        for (UserAddress userAddress : list) {
            userAddress.setIsDefault(YesOrNo.NO.type);
            this.updateById(userAddress);
        }

        // 设置默认地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setIsDefault(YesOrNo.YES.type)
                .setId(addressId)
                .setUserId(userId);
        this.updateById(defaultAddress);

    }
}
