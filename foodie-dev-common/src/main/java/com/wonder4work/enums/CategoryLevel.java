package com.wonder4work.enums;

/**
 * 商品分类 级别
 * @since 1.0.0 2020/4/1
 */
public enum CategoryLevel {


    ROOT(1, "一级分类"),
    SECOND(2, "二级分类"),
    THIRD(3, "三级分类");

    public final Integer type;
    public final String value;

    CategoryLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

}
