package com.huaperion.messageservice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huaperion.messageservice.model.ChatUserVO;
import com.huaperion.messageservice.model.Message;
import com.huaperion.messageservice.model.MessageDTO;
import com.huaperion.messageservice.model.MessagesVO;
import com.huaperion.messageservice.service.IMessageService;
import com.huaperion.messageservice.service.impl.MessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/28
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageServiceImpl messageService;

    @GetMapping("/unread")
    public Result<MessagesVO> getUnreadMessageList(@RequestParam Long id) {
        log.info("获取当前用户未读消息列表,{}", id);
        return messageService.getUnreadMessageList(id);
    }

    @PatchMapping("/read")
    public Result readMessage(@RequestParam Long senderId, @RequestParam Long receiverId) {
        log.info("将消息设为已读");
        return messageService.readMessage(senderId, receiverId);
    }

    @PostMapping("/send")
    public Result sendMessage(@RequestBody MessageDTO messageDTO) {
        log.info("{} --> {} 发送消息", messageDTO.getSenderId(), messageDTO.getReceiverId());
        return messageService.addMessage(messageDTO);
    }

    @GetMapping("/count")
    public Result<Long> getUnreadMessageCount(@RequestParam Long id) {
        log.info("获取用户未读消息数量");
        return messageService.getUnreadMessageCount(id);
    }

    @GetMapping("/chatUsers")
    public Result<List<ChatUserVO>> getChatUsers(@RequestParam Long userId) {
        log.info("获取用户{}的聊天用户列表", userId);
        return messageService.getChatUsers(userId);
    }

    @GetMapping("/history")
    public Result<IPage<Message>> getHistory(@RequestParam Long userId,
                                             @RequestParam Long contactId,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int pageSize) {
        log.info("分页查询消息历史记录 => userId:{},contactId:{}", userId, contactId);
        return messageService.getHistory(userId, contactId, page, pageSize);
    }
}
