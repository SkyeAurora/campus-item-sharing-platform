package org.huaperion.itemservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.result.Result;
import org.huaperion.itemservice.model.dto.ProductDTO;
import org.huaperion.itemservice.model.vo.MyProductsVO;
import org.huaperion.itemservice.model.vo.ProductInfoVO;
import org.huaperion.itemservice.model.vo.ProductsPageVO;
import org.huaperion.itemservice.service.impl.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Huaperion
 * @Date: 2025/3/13
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping(value = "/products")
public class ProductsController {
    @Autowired
    private ProductsServiceImpl productsService;

    /**
     * 分页获取商品列表
     *
     * @param page
     * @param pageSize
     * @param categoryId
     * @param userId
     * @return
     */
    @GetMapping("")
    public Result<ProductsPageVO> getProductsList(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam Integer categoryId, @RequestParam Long userId) {
        log.info("获取出售商品列表");
        return productsService.getProductsList(page, pageSize, categoryId, 2, userId);
    }

    /**
     * 根据id获取商品详情
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result<ProductInfoVO> getProductsById(@PathVariable Integer id) {
        log.info("查询商品详情,{}", id);
        return productsService.getProductById(id);
    }

    /**
     * 发布商品
     *
     * @param productDTO
     * @return
     */
    @PostMapping("")
    public Result addProduct(@RequestBody ProductDTO productDTO) {
        log.info("发布商品，{}", productDTO.getTitle());
        return productsService.addProduct(productDTO);
    }

    /**
     * 增加商品浏览次数
     *
     * @param id
     * @return
     */
    @PostMapping("/{id}/view")
    public Result viewProduct(@PathVariable Integer id) {
        log.info("商品{}浏览一次", id);
        return productsService.viewProduct(id);
    }

    /**
     * 查看用户发布的所有商品
     *
     * @param userId
     * @return
     */
    @GetMapping("/my")
    public Result<MyProductsVO> getMyProducts(@RequestParam Long userId) {
        log.info("查询用户发布的商品:{}", userId);
        return productsService.getMyProducts(userId);
    }

    /**
     * 更新商品数据
     *
     * @param id
     * @param productDTO
     * @return
     */
    @PutMapping("/{id}")
    public Result updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        log.info("更新物品数据{}", id);
        return productsService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public Result deleteProduct(@PathVariable Long id) {
        log.info("删除物品{}", id);
        return productsService.deleteProduct(id);
    }

    @PatchMapping("/{id}")
    public Result updateProductStatus(@PathVariable Long id, @RequestParam Integer status) {
        log.info("修改物品:{}状态:{}", id, status);
        return productsService.updateProductStatus(id, status);
    }

    @PutMapping("/status/{id}")
    public Result changeProductStatus(@PathVariable Long id, @RequestParam Integer status) {
        log.info("修改物品状态");
        return productsService.updateProductStatus(id, status);
    }
}
