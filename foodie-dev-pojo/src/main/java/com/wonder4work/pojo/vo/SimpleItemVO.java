package com.wonder4work.pojo.vo;

import lombok.Data;

/**
 * 六个最新商品的简单数据类型
 * @since 1.0.0 2020/4/1
 */
@Data
public class SimpleItemVO {

    private String itemId;
    private String itemName;
    private String itemUrl;
    private String createTime;


}
