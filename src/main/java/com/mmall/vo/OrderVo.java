package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/3/13 0013.
 */
@Setter
@Getter
public class OrderVo {

    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private String paymentTypeDesc;

    private String paymentTime;

    private Integer postage;

    private Integer status;

    private String statusDesc;

    private String sendTime;

    private String endTime;

    private String closeTime;

    private String createTime;

    // 订单明细
    private List<OrderItemVo> orderItemVoList;

    private String imageHost;

    private Integer shippingId;

    private String receiverName;

    // 收货地址信息
    private ShippingVo shippingVo;


}
