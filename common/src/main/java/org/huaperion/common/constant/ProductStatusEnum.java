package org.huaperion.common.constant;

import lombok.Getter;

/**
 * @Author: Huaperion
 * @Date: 2025/4/3
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Getter
public enum ProductStatusEnum {
    DELETED(0, "已删除"),
    PENDING_REVIEW(1, "待审核"),
    ON_SALE(2, "在售"),
    SALE_OUT(3, "已售出"),
    REMOVED(4, "已下架");

    private final int code;
    private final String desc;

    ProductStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
