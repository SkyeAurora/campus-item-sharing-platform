package org.huaperion.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Huaperion
 * @Date: 2025/3/11
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    ACCOUNT_NOT_FOUND(1001, "账户不存在"),
    PASSWORD_ERROR(1002, "密码错误"),
    ACCOUNT_EXISTS(1003, "账户已存在"),
    USER_NOT_FOUND(1004, "用户不存在"),
    USER_NOT_LOGIN(1005, "用户未登录或登录过期"),
    AUTH_TOKEN_EMPTY(1006, "请求头未包含Auth-Token"),
    FILE_UPLOAD_FAIL(1007,"文件上传失败");

    private final int code;
    private final String message;
}
