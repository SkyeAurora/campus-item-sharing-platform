package org.huaperion.itemservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.huaperion.itemservice.model.entity.Product;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Mapper
public interface ProductsMapper extends BaseMapper<Product> {

}
