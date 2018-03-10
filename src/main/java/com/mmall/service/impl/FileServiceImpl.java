package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2018/3/10 0010.
 */
@Slf4j
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    /**
     * 上传文件到ftp服务器
     * 返回上传后文件的文件名
     * @param multipartFile
     * @param path
     * @return
     */
    @Override
    public String upload(MultipartFile multipartFile, String path) {
        // 获取上传文件的文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件的扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 组装唯一性的文件名
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;

        log.info("开始上传文件，上传文件的文件名：{}，上传的路径：{}，新文件名：{}", fileName, path, uploadFileName);
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true); //文件夹可写
            fileDir.mkdirs(); // 创建文件夹
        }
        // 文件路径+文件名
        File targetFile = new File(path, uploadFileName);
        try {
            // 文件上传到指定路径
            multipartFile.transferTo(targetFile);

            // 将文件上传到FTP服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));

            // 上传结束后删除upload文件夹下的文件，保留upload文件夹
            targetFile.delete();

        } catch (IOException e) {
            log.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }
}
