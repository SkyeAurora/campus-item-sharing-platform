package com.huaperion.managementservice.model;

import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/23
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class AdminLoginDTO {
    private String accountId;

    private String password;
}
