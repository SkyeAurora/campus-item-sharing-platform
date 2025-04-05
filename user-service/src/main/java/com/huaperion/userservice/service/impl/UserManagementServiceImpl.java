package com.huaperion.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huaperion.userservice.mapper.UserMapper;
import com.huaperion.userservice.model.User;
import com.huaperion.userservice.model.UsersVO;
import com.huaperion.userservice.service.IUserManagementService;
import org.huaperion.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/23
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Service
public class UserManagementServiceImpl implements IUserManagementService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public Result<UsersVO> getUsers(Integer page, Integer pageSize, String studentId) {
        // 参数校验
        if (page == null || page < 1) page = 1;
        if (pageSize == null || pageSize < 1) pageSize = 10;

        // 创建分页参数对象
        Page<User> rowPage = new Page<>(page, pageSize);

        // 动态构建查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(studentId != null, User::getStudentId, studentId);

        // 执行分页查询
        Page<User> productPage = userMapper.selectPage(rowPage, queryWrapper);

        // 封装返回结果
        List<User> users = productPage.getRecords();
        UsersVO usersVO = new UsersVO();
        usersVO.setUsers(users);
        usersVO.setTotal(users.size());

        return Result.success(usersVO, "成功获取用户列表");
    }

    @Override
    public Result changeUserStatus(Integer userId, Integer status) {
        // 使用 LambdaUpdateWrapper 构建原子更新条件
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper
                .set(User::getStatus, status) // 直接通过 SQL 原子递增
                .eq(User::getId, userId); // 根据 ID 定位记录
        // 执行更新操作（参数为 null 表示不更新实体对象，仅通过 Wrapper 操作）
        userMapper.update(null, updateWrapper);

        // 判断更新结果
        return Result.success("用户状态更新成功");
    }
}
