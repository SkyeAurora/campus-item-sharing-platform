package com.huaperion.transactionservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Huaperion
 * @Date: 2025/4/2
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "alipay")
@Data
public class AlipayKeyConfig {
    // 支付宝AppId
    private String appId;

    // 应用私钥
    private String appPrivateKey;

    // 支付宝公钥
    private String alipayPublicKey;

    // 支付宝本地通知接口
    private String notifyUrl;

    // 跳转地址
    private String returnUrl;
}
