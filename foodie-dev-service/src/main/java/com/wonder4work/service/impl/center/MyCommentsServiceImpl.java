package com.wonder4work.service.impl.center;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wonder4work.enums.YesOrNo;
import com.wonder4work.mapper.ItemsCommentsCustomMapper;
import com.wonder4work.mapper.OrderStatusMapper;
import com.wonder4work.mapper.OrdersMapper;
import com.wonder4work.pojo.OrderItems;
import com.wonder4work.pojo.OrderStatus;
import com.wonder4work.pojo.Orders;
import com.wonder4work.pojo.bo.center.OrderItemsCommentBO;
import com.wonder4work.pojo.vo.MyCommentVO;
import com.wonder4work.service.OrderItemsService;
import com.wonder4work.service.OrderStatusService;
import com.wonder4work.service.OrdersService;
import com.wonder4work.service.center.MyCommentsService;
import com.wonder4work.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0.0 2020/5/3
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {

    @Autowired
    private ItemsCommentsCustomMapper itemsCommentsCustomMapper;

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {

        QueryWrapper<OrderItems> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        return orderItemsService.list(queryWrapper);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {

        // 1 保存评价 items_comments
        for (OrderItemsCommentBO commentBO : commentList) {

            commentBO.setCommentId(sid.nextShort());

        }

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsCustomMapper.saveComments(map);

        // 2 修改订单表 orders

        Orders orders = new Orders();

        orders.setId(orderId);
        orders.setIsComment(YesOrNo.YES.type);
        ordersService.updateById(orders);

        // 3 订单状态表 留言时间order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setCommentTime(new Date());
        UpdateWrapper<OrderStatus> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_id", orderId);
        orderStatusService.update(orderStatus,updateWrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsCustomMapper.queryMyComments(map);
        PagedGridResult pagedGridResult = setterPagedGrid(page, list);
        return pagedGridResult;
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
