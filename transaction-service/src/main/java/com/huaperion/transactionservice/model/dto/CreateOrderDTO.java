package com.huaperion.transactionservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Huaperion
 * @Date: 2025/4/1
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class CreateOrderDTO {
    private Long buyerId;
    private Long sellerId;
    private Long itemId;
    private String itemTitle;
    private BigDecimal amount;
    private String pickupLocation;
}
