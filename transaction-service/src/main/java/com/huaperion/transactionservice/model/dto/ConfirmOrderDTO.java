package com.huaperion.transactionservice.model.dto;

import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/4/3
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class ConfirmOrderDTO {
    private String orderId;

    private Integer rating;

    private String comment;
}
