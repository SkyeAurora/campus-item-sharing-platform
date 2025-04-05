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
public class MyOrderVO {
    private String id;
    private Long buyerId;
    private Long itemId;
    private Integer status; // 1-待付款,2-待收货,3-已完成,4-已取消
    private BigDecimal totalAmount;
    private String createTime;
}
