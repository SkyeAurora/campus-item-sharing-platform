package com.huaperion.transactionservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.exception.BusinessException;
import org.huaperion.common.result.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @Author: Huaperion
 * @Date: 2025/3/11
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理业务异常
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.warn("业务异常: code={}, msg={}", e.getCode(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    // 处理参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return Result.error(400, errorMsg);
    }

    // 兜底异常处理（生产环境隐藏堆栈）
    //    @ExceptionHandler(Exception.class)
    //    public Result<?> handleGlobalException(Exception e) {
    //        log.error("系统异常: ", e);
    //        return Result.error(500, "系统繁忙，请稍后重试")
    //                .setErr(isProd() ? null : e.getMessage()); // 根据环境配置显示错误详情
    //    }
    //
    //    private boolean isProd() {
    //        return "prod".equals(env.getActiveProfiles()[0]);
    //    }
}
