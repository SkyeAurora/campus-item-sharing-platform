package org.huaperion.itemservice.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/19
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class ProductDTO {
    private String title;

    private BigDecimal price;

    private String mainImage;

    private String description;

    private List<String> tags;

    private Long userId;

    private Integer categoryId;

    private String pickupLocation;
}
