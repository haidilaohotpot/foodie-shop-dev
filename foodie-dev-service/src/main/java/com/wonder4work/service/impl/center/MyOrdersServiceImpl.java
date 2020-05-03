package com.wonder4work.service.impl.center;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wonder4work.enums.OrderStatusEnum;
import com.wonder4work.enums.YesOrNo;
import com.wonder4work.mapper.OrdersMapperCustom;
import com.wonder4work.pojo.OrderStatus;
import com.wonder4work.pojo.Orders;
import com.wonder4work.pojo.vo.MyOrdersVO;
import com.wonder4work.pojo.vo.OrderStatusCountsVO;
import com.wonder4work.service.OrderStatusService;
import com.wonder4work.service.OrdersService;
import com.wonder4work.service.center.MyOrdersService;
import com.wonder4work.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0.0 2020/5/2
 */
@Service
public class MyOrdersServiceImpl implements MyOrdersService {


    @Autowired
    private OrdersService ordersService;

    @Autowired
    public OrdersMapperCustom ordersMapperCustom;

    @Autowired
    private OrderStatusService orderStatusService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();



        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }

        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> myOrdersVOList = ordersMapperCustom.queryMyOrders(map);

        return setterPagedGrid(page, myOrdersVOList);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        UpdateWrapper<OrderStatus> updateWrapper = new UpdateWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", orderId);
        map.put("order_status",OrderStatusEnum.WAIT_DELIVER.type);
        updateWrapper.allEq(map);
        orderStatusService.update(updateOrder,updateWrapper);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryMyOrder(String orderId, String userId) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("id", orderId);
        map.put("is_delete", YesOrNo.NO.type);
        queryWrapper.allEq(map);
        Orders orders = ordersService.getOne(queryWrapper);
        return orders;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        updateOrder.setSuccessTime(new Date());

        UpdateWrapper<OrderStatus> updateWrapper = new UpdateWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", orderId);
        map.put("order_status", OrderStatusEnum.WAIT_RECEIVE.type);
        updateWrapper.allEq(map);
        boolean update = orderStatusService.update(updateOrder, updateWrapper);

        return update;
    }



    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteOrder(String userId, String orderId) {
        Orders updateOrder = new Orders();

        updateOrder.setIsDelete(YesOrNo.YES.type);
        updateOrder.setUpdatedTime(new Date());

        UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", orderId);
        map.put("user_id",userId);
        updateWrapper.allEq(map);
        boolean update = ordersService.update(updateOrder, updateWrapper);

        return update;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatusCountsVO getMyOrderStatusCounts(String userId) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);

        int waitPayCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        int waitDeliverCounts = ordersMapperCustom.getMyOrderStatusCounts(map);
        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);


        int waitReceiveCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.type);

        int waitCommentCountS = ordersMapperCustom.getMyOrderStatusCounts(map);

        OrderStatusCountsVO orderStatusCountsVO = new OrderStatusCountsVO();

        orderStatusCountsVO.setWaitCommentCounts(waitCommentCountS);
        orderStatusCountsVO.setWaitDeliverCounts(waitDeliverCounts);
        orderStatusCountsVO.setWaitPayCounts(waitPayCounts);
        orderStatusCountsVO.setWaitReceiveCounts(waitReceiveCounts);


        return orderStatusCountsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult getMyOrderTrend(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<OrderStatus> list = ordersMapperCustom.getMyOrderTrend(map);
        return setterPagedGrid(page,list);
    }

    private PagedGridResult setterPagedGrid(Integer page, List<?> list) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
