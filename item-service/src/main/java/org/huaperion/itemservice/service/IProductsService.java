package org.huaperion.itemservice.service;

import org.huaperion.common.result.Result;
import org.huaperion.itemservice.model.vo.MyProductsVO;
import org.huaperion.itemservice.model.dto.ProductDTO;
import org.huaperion.itemservice.model.vo.ProductInfoVO;
import org.huaperion.itemservice.model.vo.ProductsPageVO;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
public interface IProductsService {
    /**
     * 分页查询status的商品列表
     * @param page
     * @param pageSize
     * @param categoryId
     * @param productStatus
     * @return
     */
    Result<ProductsPageVO> getProductsList(Integer page, Integer pageSize, Integer categoryId,Integer productStatus,Long userId);

    Result<ProductInfoVO> getProductById(Integer id);

    Result<ProductInfoVO> addProduct(ProductDTO productDTO);

    Result viewProduct(Integer id);

    Result<MyProductsVO> getMyProducts(Long userId);

    Result updateProduct(Long id, ProductDTO productDTO);

    Result deleteProduct(Long id);

    Result updateProductStatus(Long id, Integer status);
}
