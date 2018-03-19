package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 全部通过拦截器验证是否登录以及是否是管理员权限
 * Created by Administrator on 2018/3/10 0010.
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    /**
     * 新增或者更新商品
     * @param request
     * @param product
     * @return
     */
    @RequestMapping("/save.do")
    @ResponseBody
    public ServerResponse productSave(HttpServletRequest request, Product product) {
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
//            return iProductService.saveOrUpdateProduct(product);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        return iProductService.saveOrUpdateProduct(product);
    }

    /**
     * 修改商品的销售状态
     * @param request
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping("/set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest request, Integer productId, Integer status) {
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
//            return iProductService.setSaleStatus(productId, status);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }

        return iProductService.setSaleStatus(productId, status);
    }

    /**
     * 获取商品详情
     * @param request
     * @param productId
     * @return
     */
    @RequestMapping("/detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> getDetail(HttpServletRequest request, Integer productId) {
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
//            return iProductService.manageProductDetail(productId);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        return iProductService.manageProductDetail(productId);
    }

    /**
     * 获取商品列表并分页
     * @param request
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpServletRequest request,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
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
//            return iProductService.getProductList(pageNum, pageSize);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        return iProductService.getProductList(pageNum, pageSize);
    }

    /**
     * 动态搜索商品并分页
     * @param request
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpServletRequest request, String productName, Integer productId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
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
//            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }

        return iProductService.searchProduct(productName, productId, pageNum, pageSize);
    }

    /**
     * 上传文件到ftp服务器
     * 返回新的上传文件名和地址
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping("/upload.do")
    @ResponseBody
    public ServerResponse<Map> upload(@RequestParam(value = "upload_file", required = false) MultipartFile multipartFile, HttpServletRequest request){
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
//            // 自定义路径
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            // 上传后返回新的文件名
//            String targetFileName = iFileService.upload(multipartFile, path);
//            // 组装文件url
//            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
//
//            Map fileMap = Maps.newHashMap();
//            fileMap.put("uri", targetFileName);
//            fileMap.put("url", url);
//            return ServerResponse.createBySuccess(fileMap);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }

        // 自定义路径
        String path = request.getSession().getServletContext().getRealPath("upload");
        // 上传后返回新的文件名
        String targetFileName = iFileService.upload(multipartFile, path);
        // 组装文件url
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }


}
