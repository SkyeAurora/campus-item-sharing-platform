package com.huaperion.transactionservice.service;

import com.huaperion.transactionservice.model.entity.BorrowApplication;
import com.huaperion.transactionservice.model.dto.BorrowApplicationUpdateDTO;
import com.huaperion.transactionservice.model.vo.BorrowApplicationsVO;
import com.huaperion.transactionservice.model.vo.MyApplicationsVO;
import org.huaperion.common.result.Result;

/**
 * @Author: Huaperion
 * @Date: 2025/3/27
 * @Blog: blog.huaperion.cn
 * @Description:
 */
public interface IBorrowService {
    Result addApplication(BorrowApplication borrowApplication);

    Result<MyApplicationsVO> getMyApplications(Long userId);

    Result<BorrowApplicationsVO> getBorrowApplications(Long borrowId);

    Result applicationStatus(Long id, Integer status);

    Result updateApplication(Long id, BorrowApplicationUpdateDTO borrowApplicationUpdateDTO);

    Result deleteApplication(Long id);
}
