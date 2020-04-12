package com.wonder4work.pojo.vo;

import lombok.Data;

/**
 * @since 1.0.0 2020/4/4
 */
@Data
public class CommentLevelCountsVO {

    private Integer totalCounts;
    private Integer goodCounts;
    private Integer normalCounts;
    private Integer badCounts;

}
