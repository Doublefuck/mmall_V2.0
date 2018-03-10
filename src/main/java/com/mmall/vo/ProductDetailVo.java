package com.mmall.vo;

import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.util.DatetimeUtil;
import com.mmall.util.PropertiesUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 商品详情
 * Created by Administrator on 2018/3/10 0010.
 */
@Setter
@Getter
@NoArgsConstructor
public class ProductDetailVo {

    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImage;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String createTime;
    private String updataTime;

    private String imageHost; //图片url地址的前缀
    private Integer parentCategoryId; //父分类

    /**
     * 根据Product对象组装ProductDetailVo对象
     * @param product
     * @return
     */
    public static ProductDetailVo assembleProductDetailVo(Product product, Category category) {
        ProductDetailVo productDetailVo = new ProductDetailVo();

        productDetailVo.setId(product.getId());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setName(product.getName());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImage(product.getMainImage());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setStatus(product.getStatus());
        // imageHost
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        // parentCategoryId
        if (category == null) {
            // 默认根节点0
            productDetailVo.setParentCategoryId(0);
        } else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        // createTime
        productDetailVo.setCreateTime(DatetimeUtil.date2Str(product.getCreateTime()));
        // updateTime
        productDetailVo.setUpdataTime(DatetimeUtil.date2Str(product.getUpdateTime()));

        return productDetailVo;
    }
}
