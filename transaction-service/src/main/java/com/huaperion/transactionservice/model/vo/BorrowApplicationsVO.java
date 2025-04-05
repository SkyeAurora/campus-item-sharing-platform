package com.huaperion.transactionservice.model.vo;

import com.huaperion.transactionservice.model.entity.BorrowApplication;
import lombok.Data;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/27
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class BorrowApplicationsVO {
    private Integer total;

    private List<BorrowApplication> borrowApplications;
}
