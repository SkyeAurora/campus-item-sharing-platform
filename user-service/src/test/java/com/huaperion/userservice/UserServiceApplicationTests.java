package com.huaperion.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class UserServiceApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void RedisTest() {
        redisTemplate.opsForValue().set("name", "huaperion");
        System.out.println("Redis Get Test:" + redisTemplate.opsForValue().get("name"));
    }

}