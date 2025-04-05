package com.huaperion.transactionservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.huaperion.transactionservice.mapper.BorrowApplicationMapper;
import com.huaperion.transactionservice.model.entity.BorrowApplication;
import com.huaperion.transactionservice.model.dto.BorrowApplicationUpdateDTO;
import com.huaperion.transactionservice.model.vo.BorrowApplicationsVO;
import com.huaperion.transactionservice.model.vo.MyApplicationsVO;
import com.huaperion.transactionservice.service.IBorrowService;
import org.huaperion.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/27
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Service
public class BorrowServiceImpl implements IBorrowService {
    @Autowired
    private BorrowApplicationMapper borrowApplicationMapper;

    @Override
    public Result addApplication(BorrowApplication borrowApplication) {
        borrowApplicationMapper.insert(borrowApplication);
        return Result.success("申请成功，待发布人处理");
    }

    @Override
    public Result<MyApplicationsVO> getMyApplications(Long userId) {
        MyApplicationsVO myApplicationsVO = new MyApplicationsVO();

        LambdaQueryWrapper<BorrowApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BorrowApplication::getApplicantId, userId).orderByDesc(BorrowApplication::getUpdateTime);

        List<BorrowApplication> borrowApplications = borrowApplicationMapper.selectList(queryWrapper);

        myApplicationsVO.setBorrowApplications(borrowApplications);
        myApplicationsVO.setTotal(borrowApplications.size());

        return Result.success(myApplicationsVO, "获取我的申请记录成功");
    }

    @Override
    public Result<BorrowApplicationsVO> getBorrowApplications(Long borrowId) {
        BorrowApplicationsVO borrowApplicationsVO = new BorrowApplicationsVO();

        LambdaQueryWrapper<BorrowApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BorrowApplication::getBorrowId, borrowId).ne(BorrowApplication::getStatus, 4).orderByAsc(BorrowApplication::getCreateTime);
        List<BorrowApplication> borrowApplications = borrowApplicationMapper.selectList(queryWrapper);

        borrowApplicationsVO.setBorrowApplications(borrowApplications);
        borrowApplicationsVO.setTotal(borrowApplications.size());

        return Result.success(borrowApplicationsVO, "获取物品申请记录成功");
    }

    @Override
    public Result applicationStatus(Long id, Integer status) {
        // 使用 LambdaUpdateWrapper 构建原子更新条件
        LambdaUpdateWrapper<BorrowApplication> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper
                .set(BorrowApplication::getStatus, status) // 直接通过 SQL 原子递增
                .eq(BorrowApplication::getId, id); // 根据 ID 定位记录
        // 执行更新操作（参数为 null 表示不更新实体对象，仅通过 Wrapper 操作）
        borrowApplicationMapper.update(null, updateWrapper);

        // 判断更新结果
        return Result.success("借用记录状态更新成功");
    }

    @Override
    public Result updateApplication(Long id, BorrowApplicationUpdateDTO borrowApplicationUpdateDTO) {
        // 更新商品
        LambdaUpdateWrapper<BorrowApplication> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper
                .eq(BorrowApplication::getId, id)
                .set(BorrowApplication::getStartDate, borrowApplicationUpdateDTO.getStartDate())
                .set(BorrowApplication::getEndDate, borrowApplicationUpdateDTO.getEndDate())
                .set(BorrowApplication::getRemark, borrowApplicationUpdateDTO.getRemark());
        borrowApplicationMapper.update(null, updateWrapper);
        return Result.success("更新成功");
    }

    @Override
    public Result deleteApplication(Long id) {
        borrowApplicationMapper.deleteById(id);

        return Result.success("删除申请记录成功");
    }
}
