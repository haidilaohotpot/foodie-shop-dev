package com.wonder4work.pojo.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户新增或修改地址的BO
 * @since 1.0.0 2020/4/12
 */
@Data
@Accessors(chain = true)
public class AddressBO {

    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;


}
