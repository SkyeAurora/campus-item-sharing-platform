package org.huaperion.common.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Huaperion
 * @Date: 2025/3/6
 * @Blog: blog.huaperion.cn
 * @Description: 后端统一返回结果
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; // 状态码
    private String msg; // 错误信息
    private T data; // 数据

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1000;
        return result;
    }

    public static <T> Result<T> success(String msg) {
        Result<T> result = new Result<T>();
        result.code = 1000;
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1000;
        return result;
    }

    public static <T> Result<T> success(T object, String msg) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1000;
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> error(Integer code, String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = code;
        return result;
    }
}
