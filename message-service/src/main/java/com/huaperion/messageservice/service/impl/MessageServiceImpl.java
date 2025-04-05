package com.huaperion.messageservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huaperion.messageservice.mapper.MessageMapper;
import com.huaperion.messageservice.model.ChatUserVO;
import com.huaperion.messageservice.model.Message;
import com.huaperion.messageservice.model.MessageDTO;
import com.huaperion.messageservice.model.MessagesVO;
import com.huaperion.messageservice.service.IMessageService;
import org.huaperion.common.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/28
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Result<MessagesVO> getUnreadMessageList(Long id) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        // 查询未读消息
        queryWrapper.eq(Message::getReceiverId, id).eq(Message::getIsRead, 0);

        List<Message> messages = messageMapper.selectList(queryWrapper);

        MessagesVO messagesVO = new MessagesVO();
        messagesVO.setMessages(messages);
        messagesVO.setTotal(messages.size());
        return Result.success(messagesVO);
    }

    @Override
    public Result readMessage(Long senderId, Long receiverId) {
        LambdaUpdateWrapper<Message> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper
                .set(Message::getIsRead, 1) // 直接通过 SQL 原子递增
                .eq(Message::getSenderId, senderId)
                .eq(Message::getReceiverId, receiverId);// 根据 ID 定位记录

        // 执行更新操作（参数为 null 表示不更新实体对象，仅通过 Wrapper 操作）
        messageMapper.update(null, updateWrapper);

        // 判断更新结果
        return Result.success("消息更新为已读");
    }

    @Override
    public Result addMessage(MessageDTO messageDTO) {
        Message message = new Message();

        BeanUtils.copyProperties(messageDTO, message);
        messageMapper.insert(message);

        return Result.success("成功发送消息");
    }

    @Override
    public Result<Long> getUnreadMessageCount(Long id) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiverId, id).eq(Message::getIsRead, 0);
        return Result.success(messageMapper.selectCount(queryWrapper));
    }

    @Override
    public Result<List<ChatUserVO>> getChatUsers(Long currentUserId) {
        return Result.success(messageMapper.listChatUsers(currentUserId), "成功获取聊天用户列表");
    }

    @Override
    public Result<IPage<Message>> getHistory(Long userId, Long contactId, int page, int pageSize) {
        Page<Message> pageParam = new Page<>(page, pageSize);
        return Result.success(messageMapper.getChatHistory(pageParam, userId, contactId), "成功获取消息历史记录");
    }
}
