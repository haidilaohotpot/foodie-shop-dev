package com.wonder4work.pojo.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @since 1.0.0 2020/4/11
 */
@Data
@Accessors(chain = true)
public class ShopcatBO {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private Integer buyCounts;
    private String priceDiscount;
    private String priceNormal;


}
