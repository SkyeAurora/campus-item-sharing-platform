package com.huaperion.messageservice.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/28
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
@TableName("notifications")
public class Notification {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String content;

    private Long receiverId;

    private Boolean isRead;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.UPDATE)
    private String updateTime;
}
