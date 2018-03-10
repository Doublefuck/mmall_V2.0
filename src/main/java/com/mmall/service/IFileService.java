package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 处理文件
 * Created by Administrator on 2018/3/10 0010.
 */
public interface IFileService {

    String upload(MultipartFile multipartFile, String path);

}
