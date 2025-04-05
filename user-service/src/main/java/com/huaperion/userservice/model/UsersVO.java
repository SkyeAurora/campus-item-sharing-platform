package com.huaperion.userservice.model;

import lombok.Data;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/23
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class UsersVO {
    private Integer total;

    private List<User> users;
}
