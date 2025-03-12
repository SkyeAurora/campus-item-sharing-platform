package com.huaperion.userservice.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/12
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class UserRegisterDTO {
    private String username;

    private String password;

    private String email;

    private String phone;

    private String studentId;

    private Boolean sex;
}
