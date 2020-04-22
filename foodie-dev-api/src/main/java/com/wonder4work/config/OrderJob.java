package com.wonder4work.config;

import com.wonder4work.service.OrdersService;
import com.wonder4work.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @since 1.0.0 2020/4/22
 */
@Slf4j
@Component
@EnableScheduling
public class OrderJob {

    @Autowired
    private OrdersService ordersService;


    /**
     * 使用定时任务关闭超期未支付的订单会有很多问题
     * 1.会有时间差
     * 2. 数据量太大 全表搜索 很慢
     * 3. 不支持集群  使用集群后就有多个任务了 可以用一台机器设置定时任务
     *
     * 解决方案：使用消息队列 RabbitMQ等 延时任务，队列
     *
     */
    // TODO: 2020/4/22  使用消息队列 RabbitMQ等 延时任务，队列
//    @Scheduled(cron = "0/3 * * * * ?") 3s
    @Scheduled(cron = "0 0 0/1 * * ?")  // 1h
    public void autoCloseOrder(){
        ordersService.closeOrder();
        log.info("执行定时任务，当前时间"+DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }


}
