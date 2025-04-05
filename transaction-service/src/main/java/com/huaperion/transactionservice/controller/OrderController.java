package com.huaperion.transactionservice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huaperion.transactionservice.model.dto.CancelOrderDTO;
import com.huaperion.transactionservice.model.dto.ConfirmOrderDTO;
import com.huaperion.transactionservice.model.dto.CreateOrderDTO;
import com.huaperion.transactionservice.model.entity.Order;
import com.huaperion.transactionservice.model.vo.CreateOrderVO;
import com.huaperion.transactionservice.model.vo.MyOrderVO;
import com.huaperion.transactionservice.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Huaperion
 * @Date: 2025/4/1
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/create")
    public Result<CreateOrderVO> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        log.info("创建订单，{}", createOrderDTO);
        return orderService.createOrder(createOrderDTO);
    }

    @GetMapping("/myOrders")
    public Result<IPage<MyOrderVO>> getMyOrders(@RequestParam Integer buyerId,
                                                @RequestParam(defaultValue = "1") Integer pageNo,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("获取我的订单列表，{}", buyerId);
        Page<MyOrderVO> page = new Page<>(pageNo, pageSize);
        return orderService.getMyOrders(page, buyerId);
    }

    @GetMapping("/orderDetail")
    public Result<Order> getOrderDetail(@RequestParam String orderId) {
        log.info("获取订单明细{}", orderId);
        return Result.success(orderService.getOrderById(orderId), "成功获取订单明细");
    }

    @PostMapping("/cancel")
    public Result cancelOrder(@RequestBody CancelOrderDTO cancelOrderDTO) {
        log.info("取消订单{}", cancelOrderDTO);
        return orderService.cancelOrder(cancelOrderDTO);
    }

    @PostMapping("/confirm")
    public Result confirmOrder(@RequestBody ConfirmOrderDTO confirmOrderDTO) {
        log.info("确认订单，{}", confirmOrderDTO.getOrderId());
        orderService.confirmOrder(confirmOrderDTO);
        return Result.success("成功确认订单并向卖方转账");
    }
}
