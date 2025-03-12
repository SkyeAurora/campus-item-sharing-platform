package com.huaperion.userservice.model;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/7
 * @Blog: blog.huaperion.cn
 * @Description: 用户实体对应数据库User表
 */
@TableName("users")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String studentId;

    private String avatar;

    private Integer creditScore;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    private String lastLoginTime;

    private Boolean status;

    private Boolean sex;

    private String address;
}
