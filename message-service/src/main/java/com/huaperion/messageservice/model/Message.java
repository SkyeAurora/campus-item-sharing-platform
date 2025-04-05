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
@TableName("messages")
public class Message {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long senderId;

    private Long receiverId;

    private String content;

    private Boolean isRead;

    private String relativeItem;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.UPDATE)
    private String updateTime;
}
