package org.huaperion.itemservice.service;

import org.huaperion.common.result.Result;
import org.huaperion.itemservice.model.vo.CategoriesVO;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
public interface ICategoryService {
    Result<CategoriesVO> getCategoriesList();
}
