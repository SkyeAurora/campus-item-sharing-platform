package org.huaperion.itemservice.feign;

import org.huaperion.common.entity.User2Item;
import org.huaperion.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@FeignClient(name = "user-service",path = "/user")
public interface UserServiceClient {
    @GetMapping(value = "/info2Item/{id}")
    User2Item getUser2Item(@PathVariable("id") Long id);
}
