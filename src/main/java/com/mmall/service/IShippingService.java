package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

/**
 * Created by Administrator on 2018/3/11 0011.
 */
public interface IShippingService {

    /**
     * 新增收货地址
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse add(Integer userId, Shipping shipping);

    /**
     * 删除收货地址
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse<String> del(Integer userId, Integer shippingId);

    /**
     * 更新收货地址
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse<String> update(Integer userId, Shipping shipping);

    /**
     * 查询当前用户的某个收货地址详情
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    /**
     * 获取当前用户的所有地址并分页显示
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    ServerResponse<PageInfo> list(int pageNum, int pageSize, Integer userId);
}
