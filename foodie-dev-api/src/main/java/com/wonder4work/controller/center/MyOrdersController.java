package com.wonder4work.controller.center;

import com.wonder4work.controller.BaseController;
import com.wonder4work.pojo.Orders;
import com.wonder4work.pojo.vo.OrderStatusCountsVO;
import com.wonder4work.service.center.MyOrdersService;
import com.wonder4work.utils.JSONResult;
import com.wonder4work.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

/**
 * @since 1.0.0 2020/5/2
 */
@RestController
@RequestMapping("/myorders")
@Api(value = "用户中心我的订单",tags = {"用户中心我的订单相关接口"})
public class MyOrdersController extends BaseController {

    private static final Integer COMMON_PAGE_SIZE = 10;
    @Autowired
    private MyOrdersService myOrdersService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public JSONResult query(@ApiParam(name = "userId",value = "用户id",required = true)
                               @RequestParam String userId,
                               @ApiParam(name = "orderStatus",value = "订单状态",required = false)
                               @RequestParam Integer orderStatus,
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
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = myOrdersService.queryMyOrders(userId,orderStatus,page,pageSize);

        return JSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "模拟商家发货",notes = "模拟商家发货",httpMethod = "GET")
    @GetMapping("/deliver")
    public JSONResult deliver(@ApiParam(name = "orderId",value = "订单id",required = true)
                              @RequestParam String orderId) throws Exception{

        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("订单id不能为空");
        }

        myOrdersService.updateDeliverOrderStatus(orderId);

        return JSONResult.ok();
    }

    @ApiOperation(value = "确认收货",notes = "确认收货",httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public JSONResult confirmReceive(@ApiParam(name = "orderId",value = "订单id",required = true)
                              @RequestParam String orderId,@ApiParam(name = "userId",value = "用户id",required = true)
                              @RequestParam String userId) throws Exception{

        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("订单id不能为空");
        }

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }

        JSONResult result = checkUserOrder(orderId, userId);
        if (!result.isOK()) {
            return result;
        }

        boolean b = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!b) {
            return JSONResult.errorMsg("订单确认收货失败");
        }

        return JSONResult.ok();
    }


    @ApiOperation(value = "删除订单",notes = "删除订单",httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(@ApiParam(name = "orderId",value = "订单id",required = true)
                                     @RequestParam String orderId,@ApiParam(name = "userId",value = "用户id",required = true)
                                     @RequestParam String userId) throws Exception{

        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("订单id不能为空");
        }

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }

        JSONResult result = checkUserOrder(orderId, userId);
        if (!result.isOK()) {
            return result;
        }

        boolean b = myOrdersService.deleteOrder(userId, orderId);

        if (!b) {
            return JSONResult.errorMsg("删除失败");

        }
        return JSONResult.ok();
    }

    @ApiOperation(value = "查询订单各种状态的数量", notes = "查询订单各种状态的数量", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public JSONResult statusCounts(@ApiParam(name = "userId",value = "用户id",required = true)
                            @RequestParam String userId) {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg(null);
        }

        OrderStatusCountsVO myOrderStatusCounts = myOrdersService.getMyOrderStatusCounts(userId);

        return JSONResult.ok(myOrderStatusCounts);
    }

    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public JSONResult trend(@ApiParam(name = "userId",value = "用户id",required = true)
                            @RequestParam String userId,
                            @ApiParam(name = "page",value = "查询的页数",required = false)
                            @RequestParam Integer page,
                            @ApiParam(name = "pageSize",value = "每页显示多少条",required = false)
                                @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg(null);
        }
        if (page==null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult list = myOrdersService.getMyOrderTrend(userId, page, pageSize);

        return JSONResult.ok(list);
    }

}
