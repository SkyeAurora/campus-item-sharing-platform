package com.huaperion.messageservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huaperion.messageservice.model.ChatUserVO;
import com.huaperion.messageservice.model.Message;
import com.huaperion.messageservice.model.MessageDTO;
import com.huaperion.messageservice.model.MessagesVO;
import org.huaperion.common.result.Result;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/28
 * @Blog: blog.huaperion.cn
 * @Description:
 */
public interface IMessageService {
    Result<MessagesVO> getUnreadMessageList(Long id);

    Result readMessage(Long senderId, Long receiverId);

    Result addMessage(MessageDTO messageDTO);

    Result<Long> getUnreadMessageCount(Long id);

    Result<List<ChatUserVO>> getChatUsers(Long currentUserId);

    Result<IPage<Message>> getHistory(Long userId, Long contactId, int page, int pageSize);
}

