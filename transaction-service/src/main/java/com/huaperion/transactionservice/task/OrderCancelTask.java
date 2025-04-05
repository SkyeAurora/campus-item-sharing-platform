package com.huaperion.transactionservice.task;

import com.huaperion.transactionservice.mapper.OrderMapper;
import com.huaperion.transactionservice.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: Huaperion
 * @Date: 2025/4/2
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Component
@Slf4j
public class OrderCancelTask {
    @Autowired
    private OrderServiceImpl orderService;

    @Scheduled(fixedRate = 60000)
    public void checkAndCancelExpiredOrders() {
        log.info("定时任务触发：每分钟自动取消超时订单");
        orderService.cancelExpiredOrders();
    }
}
