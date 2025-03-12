package com.huaperion.adminservice.controller;

import org.springframework.web.bind.annotation.*;
/**
 * @Author: Huaperion
 * @Date: 2025/3/4
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@RestController
@RequestMapping(value = "admin")
public class admin {

    @GetMapping(value = "/test")
    public String test() {
        return "this is admin-service";
    }
}
