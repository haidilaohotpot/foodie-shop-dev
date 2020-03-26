package com.wonder4work.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Data
@Accessors(chain = true)
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品主键id
     */
    private String id;
    /**
     * 商品名称 商品名称
     */
    @TableField("item_name")
    private String itemName;
    /**
     * 分类外键id 分类id
     */
    @TableField("cat_id")
    private Integer catId;
    /**
     * 一级分类外键id
     */
    @TableField("root_cat_id")
    private Integer rootCatId;
    /**
     * 累计销售 累计销售
     */
    @TableField("sell_counts")
    private Integer sellCounts;
    /**
     * 上下架状态 上下架状态,1:上架 2:下架
     */
    @TableField("on_off_status")
    private Integer onOffStatus;
    /**
     * 商品内容 商品内容
     */
    private String content;
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
