package com.huaperion.transactionservice.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Huaperion
 * @Date: 2025/4/1
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class CreateOrderVO {
    private String id;

    private Long buyerId;
    private Long sellerId;
    private Long itemId;

    private Integer status; // 1-待付款,2-待收货,3-已完成,4-已取消

    private String pickupLocation;

    private BigDecimal amount;
    private BigDecimal serviceFee;
    private BigDecimal discount;
    private BigDecimal totalAmount;

    private String createTime;
    private String updateTime;
}
