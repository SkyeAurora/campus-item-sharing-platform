package com.huaperion.transactionservice.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/27
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
@TableName("borrow_application")
public class BorrowApplication {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long borrowId;

    private Long ownerId;

    private Long applicantId;

    private String startDate;

    private String endDate;

    private String remark;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.UPDATE)
    private String updateTime;
}
