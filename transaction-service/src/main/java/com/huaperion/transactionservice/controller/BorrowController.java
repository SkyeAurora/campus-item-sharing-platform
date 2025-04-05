package com.huaperion.transactionservice.controller;

import com.huaperion.transactionservice.model.entity.BorrowApplication;
import com.huaperion.transactionservice.model.dto.BorrowApplicationUpdateDTO;
import com.huaperion.transactionservice.model.vo.BorrowApplicationsVO;
import com.huaperion.transactionservice.model.vo.MyApplicationsVO;
import com.huaperion.transactionservice.service.impl.BorrowServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Huaperion
 * @Date: 2025/3/27
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("borrow")
public class BorrowController {

    @Autowired
    private BorrowServiceImpl borrowService;

    @PostMapping("/apply")
    public Result apply(@RequestBody BorrowApplication borrowApplication) {
        log.info("申请物品:{}", borrowApplication.toString());
        return borrowService.addApplication(borrowApplication);
    }

    @GetMapping("/myApplications")
    public Result<MyApplicationsVO> getMyApplications(@RequestParam("id") Long userId) {
        log.info("获取我的借用申请记录");
        return borrowService.getMyApplications(userId);
    }

    @GetMapping("/borrowApplications")
    public Result<BorrowApplicationsVO> getBorrowApplications(@RequestParam("id") Long borrowId) {
        log.info("获取物品的申请记录");
        return borrowService.getBorrowApplications(borrowId);
    }

    @PatchMapping("/applicationStatus/{id}")
    public Result updateApplicationStatus(@PathVariable("id") Long id, @RequestParam Integer status) {
        log.info("改变申请记录:{}状态为:{}", id, status);
        return borrowService.applicationStatus(id, status);
    }

    @PutMapping("/updateApplication/{id}")
    public Result updateApplication(@PathVariable("id") Long id, @RequestBody BorrowApplicationUpdateDTO borrowApplicationUpdateDTO) {
        log.info("修改申请记录,{}", id);
        return borrowService.updateApplication(id, borrowApplicationUpdateDTO);
    }

    @DeleteMapping("/deleteApplication/{id}")
    public Result deleteApplication(@PathVariable("id") Long id) {
        log.info("删除申请记录,{}", id);
        return borrowService.deleteApplication(id);
    }
}
