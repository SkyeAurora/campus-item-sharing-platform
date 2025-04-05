package org.huaperion.itemservice.service.impl;

import org.huaperion.common.result.Result;
import org.huaperion.itemservice.mapper.CategoryMapper;
import org.huaperion.itemservice.model.vo.CategoriesVO;
import org.huaperion.itemservice.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Result<CategoriesVO> getCategoriesList() {
        return Result.success(new CategoriesVO(categoryMapper.selectList(null), categoryMapper.selectList(null).size()), "成功获取所有分类信息");
    }
}
