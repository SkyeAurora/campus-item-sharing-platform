package org.huaperion.common.exception;

import lombok.Getter;

/**
 * @Author: Huaperion
 * @Date: 2025/3/11
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Getter
public class BusinessException extends RuntimeException {
    private final int code;  // 错误码，对应ErrorCode枚举
    private final String message;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}