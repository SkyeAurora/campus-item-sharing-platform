package com.huaperion.transactionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huaperion.transactionservice.model.entity.Order;
import com.huaperion.transactionservice.model.vo.MyOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/4/1
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT id, buyer_id, item_id, status, total_amount, create_time " +
            "FROM orders WHERE buyer_id = #{buyerId} " +
            "ORDER BY create_time DESC")
    IPage<MyOrderVO> selectMyOrders(Page<MyOrderVO> page, @Param("buyerId") Integer buyerId);
}
