package com.wonder4work.service.center;

import com.wonder4work.pojo.OrderStatus;
import com.wonder4work.pojo.Orders;
import com.wonder4work.pojo.vo.OrderStatusCountsVO;
import com.wonder4work.utils.PagedGridResult;

import java.util.List;

/**
 * @since 1.0.0 2020/5/2
 */
public interface MyOrdersService {


    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyOrders(String userId,Integer orderStatus,
                                         Integer page,Integer pageSize);

    /**
     * 发货
     * @param orderId
     */
    void updateDeliverOrderStatus(String orderId);


    /**
     * 查询我的订单
     * @param orderId
     * @param userId
     * @return
     */
    Orders queryMyOrder(String orderId, String userId);


    /**
     * 更新订单状态
     * @param orderId
     * @return
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 逻辑删除
     * @param userId
     * @param orderId
     * @return
     */
    boolean deleteOrder(String userId,String orderId);


    /**
     * 查询用户订单数
     * @param userId
     * @return
     */
    OrderStatusCountsVO getMyOrderStatusCounts(String userId);


    /**
     * 查询订单动向
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult getMyOrderTrend(String userId,Integer page,Integer pageSize);
}
