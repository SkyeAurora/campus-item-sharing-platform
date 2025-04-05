package com.huaperion.messageservice.model;

import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/30
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class ChatUserVO {
    private String id;

    private String username;

    private String studentId;

    private String avatar;

    private String lastMessage;

    private String lastMessageTime;

    private Integer unreadCount;
}
