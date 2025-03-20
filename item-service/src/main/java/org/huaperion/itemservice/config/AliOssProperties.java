package org.huaperion.itemservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Huaperion
 * @Date: 2025/3/19
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "hua.alioss")
@Data
public class AliOssProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
