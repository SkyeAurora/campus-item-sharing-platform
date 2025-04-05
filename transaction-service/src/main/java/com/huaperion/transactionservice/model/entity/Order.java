package com.huaperion.transactionservice.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: Huaperion
 * @Date: 2025/4/1
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
@TableName("orders")
public class Order {
    @TableId(type = IdType.INPUT) // 因为 ID 是 varchar(32)，可能需要手动生成
    private String id;

    private Long buyerId;
    private Long sellerId;
    private Long itemId;
    private String itemTitle;

    private Integer status; // 1-待付款,2-待收货,3-已完成,4-已取消

    private String pickupLocation;

    private BigDecimal amount;
    private BigDecimal serviceFee;
    private BigDecimal discount;
    private BigDecimal totalAmount;

    private String paymentMethod; // 支付方式（如支付宝）
    private String payNo;
    private String paymentTime;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    private String cancelReason;
    private String completionTime;

    private Integer rating;
    private String comment;
}
