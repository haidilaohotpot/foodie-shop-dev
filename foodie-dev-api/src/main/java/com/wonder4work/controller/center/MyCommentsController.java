package com.wonder4work.controller.center;

import com.wonder4work.controller.BaseController;
import com.wonder4work.enums.YesOrNo;
import com.wonder4work.pojo.OrderItems;
import com.wonder4work.pojo.Orders;
import com.wonder4work.pojo.bo.center.OrderItemsCommentBO;
import com.wonder4work.service.center.MyCommentsService;
import com.wonder4work.utils.JSONResult;
import com.wonder4work.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @since 1.0.0 2020/5/3
 */
@Slf4j
@RestController
@RequestMapping("/mycomments")
@Api(value = "用户评价相关接口",tags = {"用户评价相关接口"})
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;


    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/pending")
    public JSONResult pending(@ApiParam(name = "userId",value = "用户id",required = true)
                               @RequestParam String userId,
                               @ApiParam(name = "orderId",value = "订单id",required = true)
                               @RequestParam String orderId){


        JSONResult checkUserOrder = checkUserOrder(orderId, userId);

        if (!checkUserOrder.isOK()){
            return checkUserOrder;
        }

        Orders orders = (Orders) checkUserOrder.getData();
        if (orders.getIsComment() == YesOrNo.YES.type) {
            return JSONResult.errorMsg("该笔订单已经评价");
        }

        List<OrderItems> orderItemsList = myCommentsService.queryPendingComment(orderId);


        return JSONResult.ok(orderItemsList);
    }

    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("/saveList")
    public JSONResult saveList(@ApiParam(name = "userId",value = "用户id",required = true)
                              @RequestParam String userId,
                               @ApiParam(name = "orderId",value = "订单id",required = true)
                              @RequestParam String orderId,
                               @RequestBody List<OrderItemsCommentBO> commentList){

        log.info(String.valueOf(commentList));


        JSONResult checkUserOrder = checkUserOrder(orderId, userId);

        if (!checkUserOrder.isOK()){
            return checkUserOrder;
        }

        if (commentList == null || commentList.isEmpty() ){
            return JSONResult.errorMsg("评论内容不能为空");
        }

        myCommentsService.saveComments(orderId, userId, commentList);
        return JSONResult.ok();
    }


    @ApiOperation(value = "查询我的评论列表", notes = "查询我的评论列表", httpMethod = "POST")
    @PostMapping("/query")
    public JSONResult query(@ApiParam(name = "userId",value = "用户id",required = true)
                            @RequestParam String userId,
                            @ApiParam(name = "page",value = "查询的页数",required = false)
                            @RequestParam Integer page,
                            @ApiParam(name = "pageSize",value = "每页显示多少条",required = false)
                            @RequestParam Integer pageSize ) {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg(null);
        }

        if (page==null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = myCommentsService.queryMyComments(userId,page,pageSize);

        return JSONResult.ok(pagedGridResult);
    }

}
