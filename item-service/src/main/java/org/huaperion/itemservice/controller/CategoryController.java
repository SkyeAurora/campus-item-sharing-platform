package org.huaperion.itemservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.result.Result;
import org.huaperion.itemservice.model.CategoriesVO;
import org.huaperion.itemservice.model.ProductsPageVO;
import org.huaperion.itemservice.service.impl.CategoryServiceImpl;
import org.huaperion.itemservice.service.impl.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("categories")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     *
     * @return
     */
    @GetMapping("/getCategories")
    public Result<CategoriesVO> getCategoriesList() {
        log.info("获取分类列表");
        return categoryService.getCategoriesList();
    }
}
