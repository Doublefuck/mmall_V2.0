package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.OrderItem;
import com.mmall.vo.OrderVo;
import net.sf.jsqlparser.schema.Server;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/11 0011.
 */
public interface IOrderService {

    /**
     * 创建订单
     * @param userId
     * @param ShippingId
     * @return
     */
    ServerResponse<OrderVo> createOrder(Integer userId, Integer ShippingId);

    /**
     * 取消订单
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse<String> cancleOrder(Integer userId, Long orderNo);

    ServerResponse getOrderCartProduct(Integer userId);

    /**
     * 支付接口
     * 将订单生成的二维码图片上传到ftp服务器
     * @param userId
     * @param path
     * @param orderNo
     * @return
     */
    ServerResponse pay(Integer userId, String path, Long orderNo);

    /**
     * 获取支付宝的数据，验证数据的正确性
     * @param params
     * @return
     */
    ServerResponse aliCallback(Map<String, String> params);

    /**
     * 查询订单状态
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);

    /**-----------------------------------前台-------------------------**/

    /**
     * 获取某一订单详情
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);

    /**
     * 获取所有订单列表数据
     * @param userId
     * @return
     */
    ServerResponse<PageInfo> getOrderList(Integer userId, Integer pageNo, Integer pageSize);

    /**------------------------------------后台-------------------------------**/

    /**
     * 后台管理员获取订单列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> manageList(int pageNo, int pageSize);

    /**
     * 管理员查询某一订单详情
     * @param orderNo
     * @return
     */
    ServerResponse<OrderVo> manageDetail(Long orderNo);

    /**
     * 动态搜索订单（一期以orderNo精确匹配）
     * @param orderNo
     * @return
     */
    ServerResponse<PageInfo> manageSearch(Long orderNo, Integer pageNo, Integer pageSize);

    /**
     * 后台发货管理
     * @param orderNo
     * @return
     */
    ServerResponse<String> manageSendGoods(Long orderNo);
}
