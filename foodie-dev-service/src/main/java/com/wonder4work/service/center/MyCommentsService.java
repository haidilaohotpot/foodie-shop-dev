package com.wonder4work.service.center;

import com.wonder4work.pojo.OrderItems;
import com.wonder4work.pojo.bo.center.OrderItemsCommentBO;
import com.wonder4work.pojo.vo.MyCommentVO;
import com.wonder4work.utils.PagedGridResult;

import java.util.List;

/**
 * @since 1.0.0 2020/5/3
 */
public interface MyCommentsService {

    /**
     * 根据订单id查询关联的商品
     * @param orderId
     * @return
     */
    List<OrderItems> queryPendingComment(String orderId);


    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);

    /**
     * 查询我的订单
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
