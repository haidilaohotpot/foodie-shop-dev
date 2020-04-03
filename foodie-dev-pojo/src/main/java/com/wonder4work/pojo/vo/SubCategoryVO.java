package com.wonder4work.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @since 1.0.0 2020/4/1
 */
@Data
public class SubCategoryVO {

    private Integer subId;
    private String subName;
    private int subType;
    private Integer subFatherId;

}
