package com.huaperion.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Huaperion
 * @Date: 2025/3/4
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @GetMapping(value = "/test")
    public String test() {
        return "This is a User-Service";
    }
}
