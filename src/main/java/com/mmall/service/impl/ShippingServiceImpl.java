package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/11 0011.
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;


    /**
     * 新增收货地址
     * 返回新的收货地址的id
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0) {
            Map result = Maps.newHashMap();
            result.put("shippingId", shipping.getId());
            return ServerResponse.createBySuccessMsg("新建地址成功", result);
        }
        return ServerResponse.createByErrorMsg("新建地址失败");
    }

    /**
     * 删除收货地址
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<String> del(Integer userId, Integer shippingId) {
        int resultCount = shippingMapper.deleteByShippingAndUserId(userId, shippingId);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMsg("删除地址成功");
        }
        return ServerResponse.createByErrorMsg("删除地址失败");
    }

    /**
     * 更新收货地址
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse<String> update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateShipping(shipping);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMsg("更新地址成功");
        }
        return ServerResponse.createByErrorMsg("更新地址失败");
    }

    /**
     * 查询当前用户的某个收货地址详情
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId, shippingId);
        if (shipping == null) {
            return ServerResponse.createByErrorMsg("无法查询单该地址");
        }
        return ServerResponse.createBySuccessMsg("获取地址成功", shipping);
    }

    /**
     * 获取当前用户的所有地址并分页显示
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<PageInfo> list(int pageNum, int pageSize, Integer userId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);

        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
