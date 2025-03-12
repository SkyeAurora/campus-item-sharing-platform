package com.huaperion.userservice.model;

import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/7
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class UserLoginDTO {
    public String studentId;

    public String password;
}
