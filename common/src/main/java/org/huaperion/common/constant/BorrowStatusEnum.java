package org.huaperion.common.constant;

import lombok.Getter;

/**
 * @Author: Huaperion
 * @Date: 2025/4/3
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Getter
public enum BorrowStatusEnum {
    DELETED(0, "已删除"),
    PENDING_REVIEW(1, "待审核"),
    ON_BORROWING(2, "在借"),
    PENDING_PICKUP(3, "待取"),
    REMOVED(4, "待上架"),
    BORROW_OUT(5, "已借出");


    private final int code;
    private final String desc;

    BorrowStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
