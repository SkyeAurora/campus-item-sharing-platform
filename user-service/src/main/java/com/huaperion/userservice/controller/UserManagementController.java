package com.huaperion.userservice.controller;

import com.huaperion.userservice.model.UsersVO;
import com.huaperion.userservice.service.impl.UserManagementServiceImpl;
import com.huaperion.userservice.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Huaperion
 * @Date: 2025/3/23
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping(value = "user-management")
public class UserManagementController {

    @Autowired
    private UserManagementServiceImpl userManagementService;

    @GetMapping("/users")
    public Result<UsersVO> getUsers(@RequestParam Integer page, @RequestParam Integer pageSize, String studentId) {
        log.info("查询用户列表");
        return userManagementService.getUsers(page, pageSize, studentId);
    }

    @PatchMapping("/user/{id}")
    public Result changeUserStatus(@PathVariable Integer id, @RequestParam Integer status) {
        log.info("修改用户状态");
        return userManagementService.changeUserStatus(id, status);
    }
}
