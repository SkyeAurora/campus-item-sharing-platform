package org.huaperion.itemservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesVO {
    private List<Category> categories;

    private Integer count;
}
