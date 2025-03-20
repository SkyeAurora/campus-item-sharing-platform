package org.huaperion.common.entity;

import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class User2Item {
    private Long id;

    private String username;

    private String avatar;

    private Integer creditScore;
}
