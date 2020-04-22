package com.wonder4work.pojo.bo;

import lombok.Data;

/**
 * @since 1.0.0 2020/4/15
 */
@Data
public class SubmitOrderBO {

    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;

}
