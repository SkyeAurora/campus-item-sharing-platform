package com.huaperion.transactionservice.feign;

import org.huaperion.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Huaperion
 * @Date: 2025/4/1
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@FeignClient(value = "item-service", path = "/products")
public interface ItemServiceClient {
    @PutMapping("/status/{id}")
    public Result changeProductStatus(@PathVariable("id") Long id, @RequestParam("status") Integer status);
}
