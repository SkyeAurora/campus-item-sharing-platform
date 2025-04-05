package org.huaperion.itemservice.model.vo;

import lombok.Data;
import org.huaperion.common.entity.User2Item;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/18
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class ProductInfoVO {
    private Long id;

    private String title;

    private BigDecimal price;

    private String mainImage;

    private List<String> tags;

    private String description;

    private String publishTime;

    private Integer status;

    private Integer categoryId;

    private Integer viewCount;

    private String pickupLocation;

    private User2Item seller;
}
