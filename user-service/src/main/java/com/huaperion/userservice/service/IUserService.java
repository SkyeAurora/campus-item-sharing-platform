package com.huaperion.userservice.service;

import com.huaperion.userservice.model.*;
import org.huaperion.common.entity.User2Item;
import org.huaperion.common.result.Result;

/**
 * @Author: Huaperion
 * @Date: 2025/3/7
 * @Blog: blog.huaperion.cn
 * @Description:
 */
public interface IUserService {
    UserLoginVO login(UserLoginDTO userLoginDTO);

    Result register(UserRegisterDTO userRegisterDTO);

    Result<User> getUserInfo(String studentId);

    User2Item getUser2Item(Long id);

    Result<UserInfoVO> getUserInfoById(Long id);
}
