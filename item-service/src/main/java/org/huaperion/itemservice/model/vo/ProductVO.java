package org.huaperion.itemservice.model.vo;

import lombok.Data;
import org.huaperion.common.entity.User2Item;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class ProductVO {
    private Long id;

    private String title;

    private BigDecimal price;

    private String mainImage;

    private String description;

    private List<String> tags;

    private String publishTime;

    private Integer status;

    private Integer viewCount;

    private User2Item user2Item;
}
