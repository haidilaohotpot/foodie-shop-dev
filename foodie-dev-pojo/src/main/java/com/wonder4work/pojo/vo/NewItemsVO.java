package com.wonder4work.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.EAN;

import java.util.List;

/**
 * 最新商品VO
 *
 * @since 1.0.0 2020/4/1
 */
@Data
public class NewItemsVO {

    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemVO> simpleItemList;

}
