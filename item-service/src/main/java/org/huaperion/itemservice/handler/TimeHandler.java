package org.huaperion.itemservice.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: Huaperion
 * @Date: 2025/3/11
 * @Blog: blog.huaperion.cn
 * @Description: 时间自动填充Handler
 */
@Slf4j
@Component
public class TimeHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充...");
        this.strictInsertFill(metaObject, "publishTime", String.class, LocalDateTime.now().toString());
        this.strictInsertFill(metaObject, "createTime", String.class, LocalDateTime.now().toString());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
