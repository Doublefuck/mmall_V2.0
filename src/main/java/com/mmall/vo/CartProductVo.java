package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 结合了产品和购物车两个对象
 * 在本例子中，Cart对象对应的是userId+productId的唯一组合
 * Created by Administrator on 2018/3/11 0011.
 */
@Setter
@Getter
public class CartProductVo {

    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice; // 单一商品单价
    private Integer productStatus;
    private BigDecimal productTotalPrice; // 单一商品总价
    private Integer productStock;
    private Integer productChecked; // 此商品是否被勾选
    private String limitQuantity; // 限制商品数量的返回结果
}
