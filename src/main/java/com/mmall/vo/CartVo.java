package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 封装所有商品
 * Created by Administrator on 2018/3/11 0011.
 */
@Setter
@Getter
public class CartVo {

    private List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice; // 所有商品总价
    private boolean allChecked; // 是否已经都勾选
    private String imageHost;

}
