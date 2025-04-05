package org.huaperion.common.constant;

import lombok.Getter;

/**
 * @Author: Huaperion
 * @Date: 2025/4/2
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Getter
public enum OrderStatusEnum {
    PENDING_PAYMENT(1, "待付款"),
    TO_BE_SHIPPED(2, "待取货"),
    COMPLETED(3, "已完成"),
    CANCELED(4, "已取消");

    private final int code;
    private final String desc;

    OrderStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
