package com.huaperion.userservice.service;

import com.huaperion.userservice.model.UsersVO;
import org.huaperion.common.result.Result;

/**
 * @Author: Huaperion
 * @Date: 2025/3/23
 * @Blog: blog.huaperion.cn
 * @Description:
 */
public interface IUserManagementService {
    Result<UsersVO> getUsers(Integer page, Integer pageSize, String studentId);

    Result changeUserStatus(Integer userId, Integer status);
}
