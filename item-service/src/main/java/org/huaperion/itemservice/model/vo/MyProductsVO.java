package org.huaperion.itemservice.model.vo;

import lombok.Data;
import org.huaperion.itemservice.model.entity.Product;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/20
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class MyProductsVO {
    private Integer total;

    private List<Product> items;
}
