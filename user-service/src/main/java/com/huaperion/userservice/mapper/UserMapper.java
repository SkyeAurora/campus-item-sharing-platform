package com.huaperion.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huaperion.userservice.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Huaperion
 * @Date: 2025/3/7
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
