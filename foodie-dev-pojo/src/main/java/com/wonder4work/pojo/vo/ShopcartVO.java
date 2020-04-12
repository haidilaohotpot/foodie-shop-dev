package com.wonder4work.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @since 1.0.0 2020/4/11
 */
@Data
@Accessors(chain = true)
public class ShopcartVO {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String priceDiscount;
    private String priceNormal;


}
