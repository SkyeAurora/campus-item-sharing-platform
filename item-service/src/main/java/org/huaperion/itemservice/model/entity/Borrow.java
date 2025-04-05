package org.huaperion.itemservice.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/25
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
@TableName("borrows")
public class Borrow {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String description;

    private Long userId;

    private String mainImage;

    private Integer categoryId;

    private Integer creditRequired;

    private Integer maxBorrowDays;

    private String pickupLocation;

    private Integer status;

    private Integer borrowCount;

    @TableField(fill = FieldFill.INSERT)
    private String publishTime;

    @TableField(fill = FieldFill.UPDATE)
    private String updateTime;
}
