package org.huaperion.itemservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.huaperion.common.constant.ProductStatusEnum;
import org.huaperion.common.result.Result;
import org.huaperion.itemservice.feign.UserServiceClient;
import org.huaperion.itemservice.mapper.ProductTagsMapper;
import org.huaperion.itemservice.mapper.ProductsMapper;
import org.huaperion.itemservice.model.dto.ProductDTO;
import org.huaperion.itemservice.model.entity.Product;
import org.huaperion.itemservice.model.entity.ProductTags;
import org.huaperion.itemservice.model.vo.MyProductsVO;
import org.huaperion.itemservice.model.vo.ProductInfoVO;
import org.huaperion.itemservice.model.vo.ProductVO;
import org.huaperion.itemservice.model.vo.ProductsPageVO;
import org.huaperion.itemservice.service.IProductsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Service
public class ProductsServiceImpl implements IProductsService {

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private ProductTagsMapper productTagsMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    public Result<ProductsPageVO> getProductsList(Integer page, Integer pageSize, Integer categoryId, Integer productStatus, Long userId) {
        // 参数校验
        if (page == null || page < 1) page = 1;
        if (pageSize == null || pageSize < 1) pageSize = 10;

        // 创建分页参数对象
        Page<Product> rowPage = new Page<>(page, pageSize);

        // 动态构建查询条件
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(categoryId != null && categoryId != 0, Product::getCategoryId, categoryId).eq(Product::getStatus, productStatus).ne(Product::getUserId, userId);

        // 执行分页查询
        Page<Product> productPage = productsMapper.selectPage(rowPage, queryWrapper);

        // 封装返回结果
        List<Product> records = productPage.getRecords();
        List<ProductVO> items = new ArrayList<>();
        for (Product record : records) {
            // 从 product -> productVO
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(record, productVO);
            productVO.setTags(productTagsMapper.selectList(new LambdaQueryWrapper<ProductTags>().eq(ProductTags::getProductId, record.getId())).stream().map(ProductTags::getTagName)             // 提取tagName字段// 过滤null值（可选）
                    .collect(Collectors.toList()));
            productVO.setUser2Item(userServiceClient.getUser2Item(record.getUserId()));
            items.add(productVO);
        }

        ProductsPageVO pageVO = new ProductsPageVO();
        pageVO.setItems(items);    // 当前页数据列表
        pageVO.setTotal(productPage.getTotal());     // 总记录数
        pageVO.setSize(items.size());     // 总页数
        pageVO.setPage(productPage.getCurrent()); // 当前页码

        return Result.success(pageVO, "获取成功");
    }

    @Override
    public Result<ProductInfoVO> getProductById(Integer id) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getId, id);
        Product product = productsMapper.selectOne(queryWrapper);
        ProductInfoVO productInfoVO = new ProductInfoVO();
        BeanUtils.copyProperties(product, productInfoVO);

        productInfoVO.setTags(productTagsMapper.selectList(new LambdaQueryWrapper<ProductTags>().eq(ProductTags::getProductId, product.getId())).stream().map(ProductTags::getTagName)             // 提取tagName字段// 过滤null值（可选）
                .collect(Collectors.toList()));
        productInfoVO.setSeller(userServiceClient.getUser2Item(product.getUserId()));
        return Result.success(productInfoVO, "获取成功");
    }

    @Override
    public Result<ProductInfoVO> addProduct(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        productsMapper.insert(product);
        for (String tag : productDTO.getTags()) {
            ProductTags productTags = new ProductTags(product.getId(), tag);
            productTagsMapper.insert(productTags);
        }
        return Result.success("发布成功");
    }

    @Override
    public Result viewProduct(Integer id) {
        // 使用 LambdaUpdateWrapper 构建原子更新条件
        LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper
                .setSql("view_count = view_count + 1") // 直接通过 SQL 原子递增
                .eq(Product::getId, id); // 根据 ID 定位记录
        // 执行更新操作（参数为 null 表示不更新实体对象，仅通过 Wrapper 操作）
        int affectedRows = productsMapper.update(null, updateWrapper);

        // 判断更新结果
        return Result.success("浏览次数更新成功");
    }

    @Override
    public Result<MyProductsVO> getMyProducts(Long userId) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getUserId, userId);
        List<Product> products = productsMapper.selectList(queryWrapper);
        MyProductsVO myProductsVO = new MyProductsVO();
        myProductsVO.setTotal(products.size());
        myProductsVO.setItems(products);

        return Result.success(myProductsVO, "获取成功");
    }

    @Override
    public Result updateProduct(Long id, ProductDTO productDTO) {
        // 更新商品
        LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper
                .eq(Product::getId, id)
                .set(Product::getTitle, productDTO.getTitle())
                .set(Product::getPrice, productDTO.getPrice())
                .set(Product::getMainImage, productDTO.getMainImage())
                .set(Product::getDescription, productDTO.getDescription())
                .set(Product::getCategoryId, productDTO.getCategoryId())
                .set(Product::getPickupLocation, productDTO.getPickupLocation());
        productsMapper.update(null, updateWrapper);
        // 更新标签
        productTagsMapper.delete(new LambdaQueryWrapper<ProductTags>().eq(ProductTags::getProductId, id));
        for (String tag : productDTO.getTags()) {
            ProductTags productTags = new ProductTags(id, tag);
            productTagsMapper.insert(productTags);
        }
        return Result.success("更新成功");
    }

    @Override
    public Result deleteProduct(Long id) {
        LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(Product::getId, id).set(Product::getStatus, ProductStatusEnum.DELETED.getCode());
        productsMapper.update(null, updateWrapper);
        // productTagsMapper.delete(new LambdaQueryWrapper<ProductTags>().eq(ProductTags::getProductId, id));
        return Result.success("删除物品成功");
    }

    @Override
    public Result updateProductStatus(Long id, Integer status) {
        // 使用 LambdaUpdateWrapper 构建原子更新条件
        LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper
                .set(Product::getStatus, status) // 直接通过 SQL 原子递增
                .eq(Product::getId, id); // 根据 ID 定位记录
        // 商品下架修改浏览次数为 0
        if (status == ProductStatusEnum.REMOVED.getCode()) {
            updateWrapper.set(Product::getViewCount, 0);
        }
        // 执行更新操作（参数为 null 表示不更新实体对象，仅通过 Wrapper 操作）
        productsMapper.update(null, updateWrapper);

        // 判断更新结果
        return Result.success("物品状态更新成功");
    }
}
