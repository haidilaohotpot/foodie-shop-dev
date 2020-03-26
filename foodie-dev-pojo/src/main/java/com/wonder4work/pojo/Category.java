package com.wonder4work.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品分类 
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Data
@Accessors(chain = true)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类类型
     */
    private Integer type;
    /**
     * 父id
     */
    @TableField("father_id")
    private Integer fatherId;
    /**
     * 图标
     */
    private String logo;
    /**
     * 口号
     */
    private String slogan;
    /**
     * 分类图
     */
    @TableField("cat_image")
    private String catImage;
    /**
     * 背景颜色
     */
    @TableField("bg_color")
    private String bgColor;


}
