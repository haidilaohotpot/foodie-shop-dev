package com.wonder4work.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @since 1.0.0 2020/4/11
 */
@Data
@Accessors(chain = true)
public class ItemCommentsVO {

    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createTime;
    private String userFace;
    private String nickname;



}



