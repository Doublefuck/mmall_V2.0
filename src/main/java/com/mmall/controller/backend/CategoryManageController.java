package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 商品分类
 * Created by Administrator on 2018/3/9 0009.
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加商品类
     * @param request
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest request, String categoryName,
                                      @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMsg("用户未登录，无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.getKey(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        // 校验是否是管理员
//        if (iUserService.checkAdminRole(user).isSccess()) {
//            // 管理员
//            // 增加处理分类的逻辑
//            return iCategoryService.addCategory(categoryName, parentId);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }

        // 全部通过拦截器验证是否登录以及是否是管理员权限
        return iCategoryService.addCategory(categoryName, parentId);
    }

    /**
     * 更新categoryName
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest request, Integer categoryId, String categoryName) {
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMsg("用户未登录，无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.getKey(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        // 校验是否是管理员
//        if (iUserService.checkAdminRole(user).isSccess()) {
//            // 管理员
//            // 更新categoryName
//            return iCategoryService.updateCategryName(categoryId, categoryName);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        // 全部通过拦截器验证是否登录以及是否是管理员权限
        // 更新categoryName
        return iCategoryService.updateCategryName(categoryId, categoryName);
    }

    /**
     * 查询当前节点categoryId的子节点的商品类别，不递归
     * @param categoryId
     * @return
     */
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest request,
                                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMsg("用户未登录，无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.getKey(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        // 校验是否是管理员
//        if (iUserService.checkAdminRole(user).isSccess()) {
//            // 查询当前节点categoryId的子节点的商品类别，不递归
//            return iCategoryService.getChildrenParalelCategory(categoryId);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        // 全部通过拦截器验证是否登录以及是否是管理员权限
        // 查询当前节点categoryId的子节点的商品类别，不递归
        return iCategoryService.getChildrenParalelCategory(categoryId);
    }

    /**
     * 递归查询当前节点商品类及其子节点商品类
     * 查询子以当前节点的parentId作为参数
     * @param request
     * @param categoryId
     * @return
     */
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeeptChildrenCategory(HttpServletRequest request,
                                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMsg("用户未登录，无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.getKey(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        // 校验是否是管理员
//        if (iUserService.checkAdminRole(user).isSccess()) {
//            // 查询当前节点categoryId的子节点的商品类别，递归
//            return iCategoryService.selectCategoryAndChildrenById(categoryId);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        // 全部通过拦截器验证是否登录以及是否是管理员权限
        // 查询当前节点categoryId的子节点的商品类别，递归
        return iCategoryService.selectCategoryAndChildrenById(categoryId);

    }
}
