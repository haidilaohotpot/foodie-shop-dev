package com.wonder4work.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 轮播图 
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Data
@Accessors(chain = true)
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 图片 图片地址
     */
    @TableField("image_url")
    private String imageUrl;
    /**
     * 背景色
     */
    @TableField("background_color")
    private String backgroundColor;
    /**
     * 商品id 商品id
     */
    @TableField("item_id")
    private String itemId;
    /**
     * 商品分类id 商品分类id
     */
    @TableField("cat_id")
    private String catId;
    /**
     * 轮播图类型 轮播图类型，用于判断，可以根据商品id或者分类进行页面跳转，1：商品 2：分类
     */
    private Integer type;
    /**
     * 轮播图展示顺序
     */
    private Integer sort;
    /**
     * 是否展示
     */
    @TableField("is_show")
    private Integer isShow;
    /**
     * 创建时间 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间 更新
     */
    @TableField("update_time")
    private Date updateTime;


}
