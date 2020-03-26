package com.wonder4work.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品规格 每一件商品都有不同的规格，不同的规格又有不同的价格和优惠力度，规格表为此设计
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Data
@Accessors(chain = true)
@TableName("items_spec")
public class ItemsSpec implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品规格id
     */
    private String id;
    /**
     * 商品外键id
     */
    @TableField("item_id")
    private String itemId;
    /**
     * 规格名称
     */
    private String name;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 折扣力度
     */
    private BigDecimal discounts;
    /**
     * 优惠价
     */
    @TableField("price_discount")
    private Integer priceDiscount;
    /**
     * 原价
     */
    @TableField("price_normal")
    private Integer priceNormal;
    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 更新时间
     */
    @TableField("updated_time")
    private Date updatedTime;


}
