package org.huaperion.itemservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
@TableName("product_tags")
public class ProductTags {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long productId;

    private String tagName;

    public ProductTags(Long productId, String tagName) {
        this.productId = productId;
        this.tagName = tagName;
    }
}
