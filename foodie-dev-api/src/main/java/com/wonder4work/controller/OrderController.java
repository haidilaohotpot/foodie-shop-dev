package com.wonder4work.controller;

import com.wonder4work.enums.OrderStatusEnum;
import com.wonder4work.enums.PayMethod;
import com.wonder4work.enums.YesOrNo;
import com.wonder4work.pojo.UserAddress;
import com.wonder4work.pojo.bo.AddressBO;
import com.wonder4work.pojo.bo.SubmitOrderBO;
import com.wonder4work.pojo.vo.MerchantOrdersVO;
import com.wonder4work.pojo.vo.OrderVO;
import com.wonder4work.service.OrdersService;
import com.wonder4work.service.UserAddressService;
import com.wonder4work.utils.CookieUtils;
import com.wonder4work.utils.JSONResult;
import com.wonder4work.utils.JsonUtils;
import com.wonder4work.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @since 1.0.0 2020/4/1
 */
@Slf4j
@Api(value = "订单相关接口",tags = "订单相关接口")
@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController{

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "创建订单", notes = "创建订单",httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(
            @ApiParam(value = "submitOrderBO",name = "用于创建订单数据的对象",required = true)
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request, HttpServletResponse response) {

        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type &&
                submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type){

            return JSONResult.errorMsg("支付方式不支持");
        }

        log.info(">>>>>>>："+JsonUtils.objectToJson(submitOrderBO));

        // 1  创建订单
        OrderVO orderVO = ordersService.createOrder(submitOrderBO);

        String orderId = orderVO.getOrderId();
        // 2  创建订单以后，移除购物车中已结算（已提交）的商品
        // TODO: 2020/4/16 整合redis后需要更新其中的数据 同步前端
//        CookieUtils.setCookie(request,response,FOODIE_SHOPCART,"",true);
        // 3 向支付中心发起请求用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId","imooc");
        headers.add("password","immoc");

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);

        ResponseEntity<JSONResult> responseEntity = restTemplate.postForEntity(paymentUrl, entity, JSONResult.class);

        JSONResult paymentResult = responseEntity.getBody();

       /* if (paymentResult.getStatus() != 200) {
            return JSONResult.errorMsg("支付中心订单创建失败，请联系管理员");
        }
*/
        return JSONResult.ok(orderId);
    }

    @ApiOperation(value = "改变订单状态为已付款", notes = "改变订单状态为已付款",httpMethod = "POST")
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(
            @ApiParam(value = "merchantOrderId",name = "merchantOrderId",required = true)
            String merchantOrderId) {
        log.info(merchantOrderId);
        ordersService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);

        return HttpStatus.OK.value();
    }


    @PostMapping("/getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(String orderId) {
        return JSONResult.ok(OrderStatusEnum.WAIT_DELIVER.type);
    }


}
