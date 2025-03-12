package com.huaperion.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/7
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
@AllArgsConstructor
public class UserLoginVO {
    private String token;

    private String studentId;

    private String username;

    private String avatar;

    private Integer creditScore;
}
