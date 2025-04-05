package com.huaperion.messageservice.model;

import lombok.Data;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/28
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class MessagesVO {
    private Integer total;

    private List<Message> messages;
}
