package org.huaperion.itemservice.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class ProductsPageVO {
    private Long total;

    private Long page;

    private Integer size;

    private List<ProductVO> items;
}
