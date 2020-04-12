package com.wonder4work.pojo.vo;

/**
 * @since 1.0.0 2020/4/11
 */

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 用于展示商品搜索结果
 */
@Data
@Accessors(chain = true)
public class SearchItemsVO {
    private String itemId;
    private String itemName;
    private int sellCounts;
    private String imgUrl;
    private int price;  // 金额都以分为单位 防止出错

}
