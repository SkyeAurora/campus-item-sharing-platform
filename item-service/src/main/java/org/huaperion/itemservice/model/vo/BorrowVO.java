package org.huaperion.itemservice.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.huaperion.common.entity.User2Item;
import org.huaperion.itemservice.model.entity.Borrow;

/**
 * @Author: Huaperion
 * @Date: 2025/3/25
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class BorrowVO {
    private Long id;

    private String title;

    private String description;

    private Long userId;

    private String mainImage;

    private Integer categoryId;

    private Integer creditRequired;

    private Integer maxBorrowDays;

    private String pickupLocation;

    private Integer status;

    private Integer borrowCount;

    private String publishTime;

    private String updateTime;

    private User2Item seller;
}
