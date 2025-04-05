package com.huaperion.userservice.controller;

import com.huaperion.userservice.model.*;
import com.huaperion.userservice.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.entity.User2Item;
import org.huaperion.common.result.Result;
import org.huaperion.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Huaperion
 * @Date: 2025/3/4
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping(value = "/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录,学号:{} ", userLoginDTO.getStudentId());
        return Result.success(userService.login(userLoginDTO));
    }

    /**
     * 用户注册
     *
     * @param userRegisterDTO 用户注册信息，包含用户名、密码、邮箱、电话和学号
     * @return 返回注册结果，如果注册成功则返回成功信息，否则返回错误信息
     */
    @PostMapping(value = "/register")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("用户注册,{}", userRegisterDTO.getStudentId());
        return userService.register(userRegisterDTO);
    }

    /**
     * 查询指定学号的用户信息。
     *
     * @param studentId 学生的唯一标识符
     * @return 包含用户信息的结果对象，如果查询成功则返回用户信息，否则返回错误信息
     */
    @GetMapping(value = "/info")
    public Result<User> getUserInfo(@RequestParam String studentId) {
        log.info("查询当前用户信息:{}", studentId);
        return userService.getUserInfo(studentId);
    }

    /**
     * 查询学生信息
     */
    @GetMapping("/getInfoById")
    public Result<UserInfoVO> getUserInfoById(@RequestParam Long id) {
        log.info("查询用户信息:{}", id);
        return userService.getUserInfoById(id);
    }

    /**
     * user-service to item-service
     * @param id
     * @return
     */
    @GetMapping(value = "/info2Item/{id}")
    public User2Item getUser2Item(@PathVariable("id") Long id) {
        log.info("根据id查询用户信息");
        return userService.getUser2Item(id);
    }

    /**
     * 用户修改密码
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/changePassword")
    public Result<UserLoginVO> changePassword(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户修改密码,学号: {} ", userLoginDTO.getStudentId());

        return Result.success();
    }
}
