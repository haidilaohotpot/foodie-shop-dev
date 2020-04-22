package com.wonder4work.controller;

import org.springframework.stereotype.Controller;

/**
 * @since 1.0.0 2020/4/11
 */
@Controller
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    // 微信支付成功 - 通知支付中心 - 通知天天吃货平台  回调通知的url
    public static final String payReturnUrl = "http://localhost:8080/orders/notifyMerchantOrderPaid";

    // 支付中心的调用地址
    public static final String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

}
