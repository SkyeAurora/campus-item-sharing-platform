package com.huaperion.messageservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huaperion.messageservice.model.ChatUserVO;
import com.huaperion.messageservice.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/28
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    List<ChatUserVO> listChatUsers(@Param("currentUserId") Long currentUserId);

    @Select("SELECT * FROM messages " +
            "WHERE (sender_id = #{userId} AND receiver_id = #{contactId}) " +
            "   OR (sender_id = #{contactId} AND receiver_id = #{userId}) " +
            "ORDER BY create_time DESC")
    IPage<Message> getChatHistory(Page<Message> page, @Param("userId") Long userId, @Param("contactId") Long contactId);
}
