package com.wonder4work.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wonder4work.pojo.UserAddress;
import com.wonder4work.pojo.bo.AddressBO;

import java.util.List;

/**
 * <p>
 * 用户地址表  服务类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
public interface UserAddressService extends IService<UserAddress> {


    /**
     * 根据用户id查询所有的收货地址
     * @param userId
     * @return
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 新增用户地址
     * @param addressBO
     */
    void addNewUserAddress(AddressBO addressBO);

    /**
     * 修改用户地址
     * @param addressBO
     */
    void updateUserAddress(AddressBO addressBO);

    void deleteUserAddress(String userId, String addressId);

    /**
     * 设为默认地址
     * @param userId
     * @param addressId
     */
    void updateUserAddressToBeDefault(String userId, String addressId);

}
