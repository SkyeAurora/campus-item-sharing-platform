package com.huaperion.messageservice.model;

import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/28
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class MessageDTO {
    private Long senderId;

    private Long receiverId;

    private String content;

    private String relativeItem;
}
