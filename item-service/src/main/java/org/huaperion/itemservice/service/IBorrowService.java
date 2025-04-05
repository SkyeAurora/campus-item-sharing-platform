package org.huaperion.itemservice.service;

import org.huaperion.common.result.Result;
import org.huaperion.itemservice.model.dto.BorrowDTO;
import org.huaperion.itemservice.model.entity.Borrow;
import org.huaperion.itemservice.model.vo.BorrowPageVO;
import org.huaperion.itemservice.model.vo.BorrowVO;
import org.huaperion.itemservice.model.vo.MyBorrowsVO;

import java.util.List;

/**
 * @Author: Huaperion
 * @Date: 2025/3/25
 * @Blog: blog.huaperion.cn
 * @Description:
 */
public interface IBorrowService {

    Result publishBorrowing(BorrowDTO borrowDTO);

    Result<BorrowPageVO> getBorrowingList(Integer page, Integer pageSize, Integer categoryId, Long userId);

    Result<BorrowVO> getBorrowingInfo(Long id);

    Result<MyBorrowsVO> getMyBorrowing(Long userId);

    Result updateBorrowStatus(Long id, Integer status);

    Result deleteBorrow(Long id);

    Result updateBorrow(Long id, BorrowDTO borrowDTO);
}
