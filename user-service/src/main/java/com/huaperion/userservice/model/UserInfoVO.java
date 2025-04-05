package com.huaperion.userservice.model;

import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/27
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class UserInfoVO {
    private Long id;

    private String username;

    private String email;

    private String phone;

    private String studentId;

    private String avatar;

    private Integer creditScore;

    private Boolean status;

    private Boolean sex;

    private String address;
}
