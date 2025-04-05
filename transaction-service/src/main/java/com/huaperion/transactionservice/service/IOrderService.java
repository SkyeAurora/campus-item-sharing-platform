package com.huaperion.transactionservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huaperion.transactionservice.model.dto.CancelOrderDTO;
import com.huaperion.transactionservice.model.dto.ConfirmOrderDTO;
import com.huaperion.transactionservice.model.dto.CreateOrderDTO;
import com.huaperion.transactionservice.model.entity.Order;
import com.huaperion.transactionservice.model.vo.CreateOrderVO;
import com.huaperion.transactionservice.model.vo.MyOrderVO;
import org.huaperion.common.result.Result;

/**
 * @Author: Huaperion
 * @Date: 2025/4/1
 * @Blog: blog.huaperion.cn
 * @Description:
 */
public interface IOrderService {
    Result<CreateOrderVO> createOrder(CreateOrderDTO createOrderDTO);

    Result<IPage<MyOrderVO>> getMyOrders(Page<MyOrderVO> page, Integer buyerId);

    Order getOrderById(String orderId);

    Result cancelOrder(CancelOrderDTO cancelOrderDTO);

    void cancelExpiredOrders();

    void payOrder(String orderId, String gmtPayment, String tradeNo, String payMethod);

    Result confirmOrder(ConfirmOrderDTO confirmOrderDTO);
}
