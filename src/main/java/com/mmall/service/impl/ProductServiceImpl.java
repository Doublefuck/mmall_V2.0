package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/10 0010.
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 更新或者保存商品
     * @param product
     * @return
     */
    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product != null) {
            // 取商品的第一个子图作为商品的主图
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }
            int rowCount = 0;
            if (product.getId() != null) {
                // 更新操作
                rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccessMsg("更新商品成功");
                }
                return ServerResponse.createBySuccessMsg("更新商品失败");
            } else {
                // 新增操作
                rowCount = productMapper.insert(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccessMsg("新增商品成功");
                }
                return ServerResponse.createBySuccessMsg("新增商品失败");
            }
        } else {
            return ServerResponse.createByErrorMsg("新增或更新商品的参数不正确");
        }
    }

    /**
     * 修改商品的销售状态
     * @param productId
     * @param status
     * @return
     */
    @Override
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            // 参数错误
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int resultCount = productMapper.updateByPrimaryKeySelective(product);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMsg("修改商品销售状态成功");
        }
        return ServerResponse.createByErrorMsg("修改商品销售状态失败");
    }

    /**
     * 获取商品详情
     * @param productId
     * @return
     */
    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorMsg("参数从错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMsg("产品已下架或者删除");
        }
        // 组装ProductDetailVo对象
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        ProductDetailVo productDetailVo = ProductDetailVo.assembleProductDetailVo(product, category);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * 获取商品列表并分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        //  start pageHelper
        PageHelper.startPage(pageNum, pageSize);
        // 查询逻辑
        List<ProductListVo> productListVoList = Lists.newArrayList();
        List<Product> productList = productMapper.selectList();
        for (Product product : productList) {
            ProductListVo productListVo = ProductListVo.assembleProductList(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 动态搜索商品并分页
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        //  start pageHelper
        PageHelper.startPage(pageNum, pageSize);
        // 查询逻辑
        if (StringUtils.isNotBlank(productName)) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo productListVo = ProductListVo.assembleProductList(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 前台用户获取商品详情
     * @param productId
     * @return
     */
    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorMsg("参数从错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.createByErrorMsg("产品已下架或者删除");
        }
        // 组装ProductDetailVo对象
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        ProductDetailVo productDetailVo = ProductDetailVo.assembleProductDetailVo(product, category);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * 前台用户动态获取商品列表并分页
     * 递归获取当前层级商品及其子级商品列表
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId,
                                                                int pageNum, int pageSize, String orderBy) {

        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        List<Integer> categoryIdList = new ArrayList<Integer>();

        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && keyword == null) {
                // 无查询数据
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVo> productListVoList = new ArrayList<>();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            // 递归查询
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }

        // 组装查询条件
        if (StringUtils.isNotBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        // 排序 排序关键字以"_"分割
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArr = orderBy.split("_");
                // orderBy("xxx xxx")
                PageHelper.orderBy(orderByArr[0] + " " + orderByArr[1]);
            }
        }
        // 获取商品列表，组装成商品vo对象
        List<Product> productList = productMapper.selectByNameAndCategoryIds(
                StringUtils.isBlank(keyword) ? null : keyword,
                categoryIdList.size() == 0 ? null : categoryIdList);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo productListVo = ProductListVo.assembleProductList(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);

        return ServerResponse.createBySuccess(pageInfo);
    }
}
