package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by Administrator on 2018/3/9 0009.
 */
public interface ICategoryService {

    /**
     * 添加商品类
     * @param categoryName
     * @param parentId
     * @return
     */
    ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     * 更新categoryName
     * @param categoryId
     * @param categoryName
     * @return
     */
    ServerResponse updateCategryName(Integer categoryId, String categoryName);

    /**
     * 查询当前节点categoryId的子节点的商品类别，不递归
     * @param categoryId
     * @return
     */
    ServerResponse<List<Category>> getChildrenParalelCategory(Integer categoryId);

    /**
     * 递归查询当前节点商品类及其子节点商品类
     * @param categoryId
     * @return
     */
    ServerResponse selectCategoryAndChildrenById(Integer categoryId);
}
