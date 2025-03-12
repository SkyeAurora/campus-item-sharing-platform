package com.huaperion.userservice.service;

import com.huaperion.userservice.model.User;
import com.huaperion.userservice.model.UserLoginDTO;
import com.huaperion.userservice.model.UserLoginVO;
import com.huaperion.userservice.model.UserRegisterDTO;
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
}
