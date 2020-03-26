package com.wonder4work.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 订单商品关联表 
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Data
@Accessors(chain = true)
@TableName("order_items")
public class OrderItems implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;
    /**
     * 归属订单id
     */
    @TableField("order_id")
    private String orderId;
    /**
     * 商品id
     */
    @TableField("item_id")
    private String itemId;
    /**
     * 商品图片
     */
    @TableField("item_img")
    private String itemImg;
    /**
     * 商品名称
     */
    @TableField("item_name")
    private String itemName;
    /**
     * 规格id
     */
    @TableField("item_spec_id")
    private String itemSpecId;
    /**
     * 规格名称
     */
    @TableField("item_spec_name")
    private String itemSpecName;
    /**
     * 成交价格
     */
    private Integer price;
    /**
     * 购买数量
     */
    @TableField("buy_counts")
    private Integer buyCounts;


}
