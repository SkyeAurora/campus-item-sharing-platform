package org.huaperion.itemservice.model;

import lombok.Data;

/**
 * @Author: Huaperion
 * @Date: 2025/3/19
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class FileVO {
    String url;

    public FileVO(String url) {
        this.url = url;
    }
}
