package com.wonder4work.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wonder4work.pojo.Orders;
import com.wonder4work.pojo.bo.SubmitOrderBO;
import com.wonder4work.pojo.vo.OrderVO;

/**
 * <p>
 * 订单表; 服务类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 创建一个新订单
     * @param submitOrderBO
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    void updateOrderStatus(String orderId,Integer orderStatus);

    /**
     * 定时关闭超时未支付的订单
     */
    void closeOrder();

}
