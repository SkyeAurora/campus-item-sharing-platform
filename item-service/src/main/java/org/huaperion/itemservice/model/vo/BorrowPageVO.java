package org.huaperion.itemservice.model.vo;

import lombok.Data;
import org.huaperion.itemservice.model.entity.Borrow;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/25
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Data
public class BorrowPageVO {
    private Long total;

    private List<BorrowVO> items;
}
