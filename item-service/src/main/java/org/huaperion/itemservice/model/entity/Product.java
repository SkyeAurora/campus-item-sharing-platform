package org.huaperion.itemservice.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
@TableName("products")
public class Product {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private BigDecimal price;

    private String mainImage;

    private String description;

    private Long userId;

    private Integer categoryId;

    private Integer status;

    private Integer viewCount;

    private String pickupLocation;

    @TableField(fill = FieldFill.INSERT)
    private String publishTime;

    @TableField(fill = FieldFill.UPDATE)
    private String updateTime;
}
