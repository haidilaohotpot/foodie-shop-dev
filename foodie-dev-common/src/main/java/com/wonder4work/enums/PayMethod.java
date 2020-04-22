package com.wonder4work.enums;


/**
 * 支付方式枚举
 *
 * @since 1.0.0 2020/4/15
 */

public enum PayMethod {

    WEIXIN(1, "微信"),ALIPAY(2, "支付宝");

    public final Integer type;
    public final String value;

    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }


}
