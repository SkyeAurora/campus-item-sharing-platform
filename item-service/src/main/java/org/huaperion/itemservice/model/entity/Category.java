package org.huaperion.itemservice.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
@TableName("categories")
public class Category {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    // @JsonRawValue
    private String icon;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    private Boolean status;
}
