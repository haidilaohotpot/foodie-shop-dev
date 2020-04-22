package com.wonder4work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonder4work.enums.OrderStatusEnum;
import com.wonder4work.enums.YesOrNo;
import com.wonder4work.mapper.OrdersMapper;
import com.wonder4work.pojo.*;
import com.wonder4work.pojo.bo.SubmitOrderBO;
import com.wonder4work.pojo.vo.MerchantOrdersVO;
import com.wonder4work.pojo.vo.OrderVO;
import com.wonder4work.service.*;
import com.wonder4work.utils.DateUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单表; 服务实现类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {


    @Autowired
    private Sid sid;
    @Autowired
    private UserAddressService addressService;

    @Autowired
    private ItemsSpecService itemsSpecService;

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private ItemsImgService itemsImgService;

    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderStatusService orderStatusService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {

        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮
        Integer postAmount = 0;

         // 1 新订单数据保存
        Orders orders = new Orders();
        String orderId = sid.nextShort();
        orders.setId(orderId);

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(userAddress);

        UserAddress dbUserAddress = addressService.getOne(queryWrapper);


        orders.setUserId(userId)
                .setReceiverName(dbUserAddress.getReceiver())
                .setReceiverMobile(dbUserAddress.getMobile())
                .setReceiverAddress(dbUserAddress.getProvince() + " "
                        + dbUserAddress.getCity() + " " + dbUserAddress.getDistrict()
                        + " " + dbUserAddress.getDetail())
//                .setTotalAmount()
//                .setRealPayAmount()
                .setPostAmount(postAmount)
                .setLeftMsg(leftMsg)
                .setPayMethod(payMethod)
                .setIsComment(YesOrNo.NO.type)
                .setIsDelete(YesOrNo.NO.type)
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date());

        // 2 循环 根据itemSpecIds 保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");

        Integer totalAmount = 0;
        Integer realPayAmount = 0;

        for (String itemSpectId : itemSpecIdArr) {

            // 2.1 根据规格id 查询规格的具体信息 主要获取价格
            ItemsSpec itemsSpec = itemsSpecService.getById(itemSpectId);
            // TODO: 2020/4/15  整合redis后 商品购买的数量从其中获取
            int buyCounts =1;
            totalAmount += itemsSpec.getPriceNormal() * buyCounts; // 数量要从后端获取
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
            // 2.2 根据商品id获取商品信息以及商品图片
            String itemId = itemsSpec.getItemId();
            Items item = itemsService.queryItemById(itemId);
            QueryWrapper<ItemsImg> wrapper = new QueryWrapper<>();

            ItemsImg itemsImg = new ItemsImg();
            itemsImg.setItemId(itemId);
            itemsImg.setIsMain(YesOrNo.YES.type);
            wrapper.setEntity(itemsImg);
            ItemsImg img = itemsImgService.getOne(wrapper);
            String imgUrl = "";
            if (img != null&&img.getUrl()!=null) {
                imgUrl = img.getUrl();
            }

            // 保存数据到数据库
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(sid.nextShort());
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpectId);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            orderItemsService.save(subOrderItem);

            // 扣除库存
            itemsService.decreaseItemSpecStock(itemSpectId, buyCounts);
        }

        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);

        ordersService.save(orders);

        // 3 保存订单状态


        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());

        orderStatusService.save(waitPayOrderStatus);


        // 4 构件商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);

        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);
        OrderVO orderVO = new OrderVO();
        orderVO.setMerchantOrdersVO(merchantOrdersVO);
        orderVO.setOrderId(orderId);
        return orderVO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId)
                .setOrderStatus(orderStatus)
                .setPayTime(new Date());
        orderStatusService.updateById(paidStatus);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {

        // 查询所有未付款订单，判断时间是否超时（1天）超时则关闭交易

        OrderStatus queryOrder = new OrderStatus();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);

        QueryWrapper<OrderStatus> queryMapper = new QueryWrapper<>();
        queryMapper.setEntity(queryOrder);
        List<OrderStatus> list = orderStatusService.list(queryMapper);
        for (OrderStatus orderStatus : list) {

            // 获得订单创建时间
            Date createTime = orderStatus.getCreatedTime();

            // 和当前时间进行对比
            int daysBetween = DateUtil.daysBetween(createTime, new Date());

            if (daysBetween >= 1){
                // 超过一天关闭订单
                doCloseOrder(orderStatus.getOrderId());
            }
        }


    }

    @Transactional(propagation = Propagation.REQUIRED)
    void doCloseOrder(String orderId){
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.CLOSE.type);
        orderStatus.setCloseTime(new Date());
        orderStatusService.updateById(orderStatus);
    }
}
