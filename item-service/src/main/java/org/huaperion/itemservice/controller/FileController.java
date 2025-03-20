package org.huaperion.itemservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.exception.ErrorCode;
import org.huaperion.common.result.Result;
import org.huaperion.itemservice.config.AliOssUtil;
import org.huaperion.itemservice.model.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Author: Huaperion
 * @Date: 2025/3/19
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<FileVO> upload(MultipartFile file) {
        log.info("文件上传：{}", file);

        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名的后缀   dfdfdf.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;
            //文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            log.info("文件上传成功{}", filePath);
            return Result.success(new FileVO(filePath),"上传成功");
        } catch (IOException e) {
            return Result.error(ErrorCode.FILE_UPLOAD_FAIL.getCode(), ErrorCode.FILE_UPLOAD_FAIL.getMessage());
        }
    }
}
