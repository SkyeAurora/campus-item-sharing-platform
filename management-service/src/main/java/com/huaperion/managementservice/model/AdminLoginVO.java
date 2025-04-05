package com.huaperion.managementservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Huaperion
 * @Date: 2025/3/23
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginVO {
    private String token;

    private String accountId;

    private String adminName;

    private Integer role;
}
