package com.wonder4work.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品图片 
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Data
@Accessors(chain = true)
@TableName("items_img")
public class ItemsImg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片主键
     */
    private String id;
    /**
     * 商品外键id 商品外键id
     */
    @TableField("item_id")
    private String itemId;
    /**
     * 图片地址 图片地址
     */
    private String url;
    /**
     * 顺序 图片顺序，从小到大
     */
    private Integer sort;
    /**
     * 是否主图 是否主图，1：是，0：否
     */
    @TableField("is_main")
    private Integer isMain;
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
