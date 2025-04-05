package com.huaperion.transactionservice.model.dto;

import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/4/1
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class CancelOrderDTO {
    private String orderId;

    private String cancelReason;
}
