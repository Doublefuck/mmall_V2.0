package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * Created by Administrator on 2018/3/11 0011.
 */
public interface IOrderService {

    /**
     * 支付接口
     * 将订单生成的二维码图片上传到ftp服务器
     * @param userId
     * @param path
     * @param orderNo
     * @return
     */
    ServerResponse pay(Integer userId, String path, Long orderNo);

}
