package com.wonder4work.pojo.vo;


import lombok.Data;

import java.util.List;

/**
 * @since 1.0.0 2020/4/1
 */
@Data
public class CategoryVO {

    private Integer id;
    private String name;
    private int type;
    private Integer fatherId;

    // 三级分类vo list
    private List<SubCategoryVO> subCatList;


}
