package org.huaperion.itemservice.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/25
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class BorrowDTO {
    private String title;

    private String description;

    private Long userId;

    private String mainImage;

    private Integer categoryId;

    private Integer creditRequired;

    private Integer maxBorrowDays;

    private String pickupLocation;
}
