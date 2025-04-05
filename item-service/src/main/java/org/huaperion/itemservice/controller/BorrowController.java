package org.huaperion.itemservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.result.Result;
import org.huaperion.itemservice.model.dto.BorrowDTO;
import org.huaperion.itemservice.model.entity.Borrow;
import org.huaperion.itemservice.model.vo.BorrowPageVO;
import org.huaperion.itemservice.model.vo.BorrowVO;
import org.huaperion.itemservice.model.vo.MyBorrowsVO;
import org.huaperion.itemservice.service.impl.BorrowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/25
 * @Blog: blog.huaperion.cn
 * @Description: 借用物品controller层
 */
@Slf4j
@RestController
@RequestMapping(value = "/borrows")
public class BorrowController {

    @Autowired
    private BorrowServiceImpl borrowService;

    /**
     * 发布借用物品
     *
     * @param borrowDTO
     * @return
     */
    @PostMapping("/publish")
    public Result publishBorrowing(@RequestBody BorrowDTO borrowDTO) {
        log.info("用户：{} 发布借用物品:{},", borrowDTO.getUserId(), borrowDTO.getTitle());
        return borrowService.publishBorrowing(borrowDTO);
    }

    /**
     * 获取借用物品列表
     *
     * @param page
     * @param pageSize
     * @param categoryId
     * @param userId
     * @return
     */
    @GetMapping("/getList")
    public Result<BorrowPageVO> getBorrowingList(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam Integer categoryId, @RequestParam Long userId) {
        log.info("获取借用物品列表");
        return borrowService.getBorrowingList(page, pageSize, categoryId, userId);
    }

    /**
     * 获取借用物品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/getInfo")
    public Result<BorrowVO> getBorrowingInfo(@RequestParam Long id) {
        log.info("获取借用物品详情:{}", id);
        return borrowService.getBorrowingInfo(id);
    }

    /**
     * @param userId
     * @return
     */
    @GetMapping("/my")
    public Result<MyBorrowsVO> getBorrowingMy(@RequestParam Long userId) {
        log.info("获取我发布的借用物品,{}", userId);
        return borrowService.getMyBorrowing(userId);
    }

    @PatchMapping("/{id}")
    public Result updateBorrowStatus(@PathVariable Long id, @RequestParam Integer status) {
        log.info("修改借用物品:{}状态:{}", id, status);
        return borrowService.updateBorrowStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public Result deleteBorrow(@PathVariable Long id) {
        log.info("删除我发布的物品");
        return borrowService.deleteBorrow(id);
    }

    @PutMapping("/{id}")
    public Result updateBorrow(@PathVariable Long id, @RequestBody BorrowDTO borrowDTO) {
        log.info("更新商品信息,{}", id);
        return borrowService.updateBorrow(id, borrowDTO);
    }
}
