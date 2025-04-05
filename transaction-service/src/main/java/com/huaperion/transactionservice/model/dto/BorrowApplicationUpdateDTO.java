package com.huaperion.transactionservice.model.dto;

import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/27
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class BorrowApplicationUpdateDTO {
    private String startDate;

    private String endDate;

    private String remark;
}
