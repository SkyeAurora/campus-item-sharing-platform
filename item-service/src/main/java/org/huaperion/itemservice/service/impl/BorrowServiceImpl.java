package org.huaperion.itemservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.huaperion.common.constant.BorrowStatusEnum;
import org.huaperion.common.result.Result;
import org.huaperion.itemservice.feign.UserServiceClient;
import org.huaperion.itemservice.mapper.BorrowMapper;
import org.huaperion.itemservice.model.dto.BorrowDTO;
import org.huaperion.itemservice.model.entity.Borrow;
import org.huaperion.itemservice.model.vo.BorrowPageVO;
import org.huaperion.itemservice.model.vo.BorrowVO;
import org.huaperion.itemservice.model.vo.MyBorrowsVO;
import org.huaperion.itemservice.service.IBorrowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/25
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Service
public class BorrowServiceImpl implements IBorrowService {

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public Result publishBorrowing(BorrowDTO borrowDTO) {
        Borrow borrow = new Borrow();
        BeanUtils.copyProperties(borrowDTO, borrow);
        borrowMapper.insert(borrow);
        return Result.success("发布借用物品成功，等待管理员审核");
    }

    @Override
    public Result<BorrowPageVO> getBorrowingList(Integer page, Integer pageSize, Integer categoryId, Long userId) {
        // 参数校验
        if (page == null || page < 1) page = 1;
        if (pageSize == null || pageSize < 1) pageSize = 10;

        // 创建分页参数对象
        Page<Borrow> rowPage = new Page<>(page, pageSize);

        // 动态构建查询条件
        LambdaQueryWrapper<Borrow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(categoryId != null && categoryId != 0, Borrow::getCategoryId, categoryId).eq(Borrow::getStatus, BorrowStatusEnum.ON_BORROWING.getCode()).ne(Borrow::getUserId, userId);

        // 执行分页查询
        Page<Borrow> borrowPage = borrowMapper.selectPage(rowPage, queryWrapper);

        List<Borrow> records = borrowPage.getRecords();
        List<BorrowVO> itemVOs = new ArrayList<>();
        for (Borrow borrow : records) {
            BorrowVO borrowVO = new BorrowVO();
            BeanUtils.copyProperties(borrow, borrowVO);
            borrowVO.setSeller(userServiceClient.getUser2Item(borrow.getUserId()));
            itemVOs.add(borrowVO);
        }

        BorrowPageVO borrowPageVO = new BorrowPageVO();
        borrowPageVO.setItems(itemVOs);
        borrowPageVO.setTotal(borrowPage.getTotal());
        return Result.success(borrowPageVO, "获取借用商品列表成功");
    }

    @Override
    public Result<BorrowVO> getBorrowingInfo(Long id) {
        LambdaQueryWrapper<Borrow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Borrow::getId, id);

        Borrow borrow = borrowMapper.selectOne(queryWrapper);
        BorrowVO borrowVO = new BorrowVO();
        BeanUtils.copyProperties(borrow, borrowVO);
        borrowVO.setSeller(userServiceClient.getUser2Item(borrow.getUserId()));
        return Result.success(borrowVO, "获取可借用物品详情成功");
    }

    @Override
    public Result<MyBorrowsVO> getMyBorrowing(Long userId) {
        LambdaQueryWrapper<Borrow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Borrow::getUserId, userId);
        List<Borrow> borrows = borrowMapper.selectList(queryWrapper);
        MyBorrowsVO myBorrowsVO = new MyBorrowsVO();
        myBorrowsVO.setBorrows(borrows);
        myBorrowsVO.setTotal(borrows.size());
        return Result.success(myBorrowsVO, "获取我发布的借用物品列表");
    }

    @Override
    public Result updateBorrowStatus(Long id, Integer status) {
        // 使用 LambdaUpdateWrapper 构建原子更新条件
        LambdaUpdateWrapper<Borrow> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper
                .set(Borrow::getStatus, status) // 直接通过 SQL 原子递增
                .eq(Borrow::getId, id); // 根据 ID 定位记录
        // 执行更新操作（参数为 null 表示不更新实体对象，仅通过 Wrapper 操作）
        borrowMapper.update(null, updateWrapper);

        // 判断更新结果
        return Result.success("物品状态更新成功");
    }

    @Override
    public Result deleteBorrow(Long id) {
        LambdaUpdateWrapper<Borrow> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(Borrow::getId, id).set(Borrow::getStatus, BorrowStatusEnum.DELETED.getCode());
        borrowMapper.update(null, updateWrapper);
        return Result.success("成功删除我发布的物品");
    }

    @Override
    public Result updateBorrow(Long id, BorrowDTO borrowDTO) {
        // 更新商品
        LambdaUpdateWrapper<Borrow> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper
                .eq(Borrow::getId, id)
                .set(Borrow::getTitle, borrowDTO.getTitle())
                .set(Borrow::getCreditRequired, borrowDTO.getCreditRequired())
                .set(Borrow::getMaxBorrowDays, borrowDTO.getMaxBorrowDays())
                .set(Borrow::getMainImage, borrowDTO.getMainImage())
                .set(Borrow::getDescription, borrowDTO.getDescription())
                .set(Borrow::getCategoryId, borrowDTO.getCategoryId())
                .set(Borrow::getPickupLocation, borrowDTO.getPickupLocation());
        borrowMapper.update(null, updateWrapper);
        return Result.success("更新成功");
    }
}
