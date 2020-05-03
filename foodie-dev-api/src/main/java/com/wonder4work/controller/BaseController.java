package com.wonder4work.controller;

import com.wonder4work.pojo.Orders;
import com.wonder4work.service.center.MyOrdersService;
import com.wonder4work.utils.JSONResult;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;

/**
 * @since 1.0.0 2020/4/11
 */
@Controller
public class BaseController {

    @Autowired
    private MyOrdersService myOrdersService;

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    // 微信支付成功 - 通知支付中心 - 通知天天吃货平台  回调通知的url
    public static final String payReturnUrl = "http://localhost:8080/orders/notifyMerchantOrderPaid";

    // 支付中心的调用地址
    public static final String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    // 用户上传头像的位置
    public static final String IMAGE_USER_FACE_LOCATION = File.separator +"workspaces"+File.separator+"images"+File.separator+"foodie"+File.separator+"faces";


    /**
     * 查看用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
    public JSONResult checkUserOrder(String orderId, String userId){

        Orders orders = myOrdersService.queryMyOrder(orderId, userId);
        if (orders == null) {
            return JSONResult.errorMsg("订单不存在！");
        }

        return JSONResult.ok(orders);
    }
}
