package org.huaperion.itemservice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.huaperion.itemservice.model.entity.Category;

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
