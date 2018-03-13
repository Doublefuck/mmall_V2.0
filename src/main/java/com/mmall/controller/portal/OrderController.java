package com.mmall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/11 0011.
 */
@Slf4j
@Controller
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    /**
     * 创建订单
     * 返回OrderVo对象
     * @param request
     * @param shippingId
     * @return
     */
    @RequestMapping("create.do")
    @ResponseBody
    public ServerResponse create(HttpServletRequest request, Integer shippingId) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return iOrderService.createOrder(user.getId(), shippingId);
    }

    /**
     * 取消订单
     * @param request
     * @param orderNo
     * @return
     */
    @RequestMapping("cancle.do")
    @ResponseBody
    public ServerResponse cancle(HttpServletRequest request, Long orderNo) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return iOrderService.cancleOrder(user.getId(), orderNo);
    }

    @RequestMapping("get_order_cart_product.do")
    @ResponseBody
    public ServerResponse getOrderCartProduct(HttpServletRequest request, Integer orderNo) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return iOrderService.getOrderCartProduct(user.getId());
    }

    /**
     * 支付订单
     * 调用支付宝接口，实现二维码扫码支付
     * 生成的二维码图片上传到ftp服务器
     * @param orderNo
     * @param request
     * @return
     */
    @RequestMapping("pay.do")
    @ResponseBody
    public ServerResponse pay(Long orderNo, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        // 获取上传文件的路径
        String path = request.getSession().getServletContext().getRealPath("upload");
        return iOrderService.pay(user.getId(), path, orderNo);
    }

    /**
     * 获取支付宝的数据，验证数据的正确性
     * @param request
     * @return
     */
    @RequestMapping("alipay_callback.do")
    @ResponseBody
    public Object alipayCallback(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();

        // 返回的是<key, values[]>的形式
        Map requestParams = request.getParameterMap();
        Iterator iter = requestParams.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            String[] valueArr = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0 ; i < valueArr.length ; i++) {
                valueStr = (i == valueArr.length - 1) ? valueStr + valueArr[i] : valueStr + valueArr[i] + ",";
            }
            params.put(name, valueStr);
        }
        // sign验签,参数就是params
        // 对应的key是支付宝回调中指定的字段
        log.info("支付宝回调，sign:{}，trade_status:{}，参数:{}", params.get("sign"), params.get("trade_status"), params.toString());

        // 验证回调的正确性，避免重复通知
        // 需要移除掉sign和sign_type，sign在源码中已经被移除
        params.remove("sign_type");
        try {
            Boolean alipayRSACheckV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            if (!alipayRSACheckV2) {
                return ServerResponse.createByErrorMsg("非法请求，验证不通过");
            }
        } catch (AlipayApiException e) {
            log.error("支付宝验证回调异常", e);
            e.printStackTrace();
        }
        // TODO 验证各种数据

        ServerResponse serverResponse = iOrderService.aliCallback(params);
        if (serverResponse.isSccess()) {
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    /**
     * 查询订单支付状态
     * 如果处于支付及其之后的状态，则返回true，否则返回false
     * @param orderNo
     * @param request
     * @return
     */
    @RequestMapping("query_order_pay_status.do")
    @ResponseBody
    public ServerResponse queryOrderPayStatus(Long orderNo, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        ServerResponse serverResponse = iOrderService.queryOrderPayStatus(user.getId(), orderNo);
        if (serverResponse.isSccess()) {
            return ServerResponse.createBySuccess(true);
        }  else {
            return ServerResponse.createBySuccess(false);
        }
    }



    /**------------------------------前台用户--------------------------------**/

    /**
     * 查看订单详情
     * @param orderNo
     * @param request
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(Long orderNo, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return iOrderService.getOrderDetail(user.getId(), orderNo);
    }

    /**
     * 查看订单列表
     * @param request
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return iOrderService.getOrderList(user.getId(), pageNo, pageSize);
    }
}
