//package com.mmall.service.impl;
//
//import com.alipay.api.response.AlipayTradePrecreateResponse;
//import com.alipay.demo.trade.model.ExtendParams;
//import com.alipay.demo.trade.model.GoodsDetail;
//import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
//import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
//import com.google.common.collect.Maps;
//import com.mmall.common.ServerResponse;
//import com.mmall.dao.OrderMapper;
//import com.mmall.pojo.Order;
//import com.mmall.service.IOrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Administrator on 2018/3/11 0011.
// */
//@Service("iOrderService")
//public class OrderServiceImpl implements IOrderService {
//
//    @Autowired
//    private OrderMapper orderMapper;
//
//
//    /**
//     * 支付接口
//     * 将订单生成的二维码图片上传到ftp服务器
//     * @param userId
//     * @param path
//     * @param orderNo
//     * @return
//     */
//    @Override
//    public ServerResponse pay(Integer userId, String path, Long orderNo) {
//        Map<String, String> resultMap = Maps.newHashMap();
//
//        // 根据userId和orderNo查询指定订单
//        Order order = orderMapper.selectByUserIdOrderNo(userId, orderNo);
//        if (order == null) {
//            return ServerResponse.createByErrorMsg("用户没有该订单");
//        }
//        resultMap.put("orderNo", order.getOrderNo().toString());
//
//        // 开始组装支付宝订单
//
//        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
//        String outTradeNo = order.getOrderNo().toString();
//
//        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
//        String subject = new StringBuilder().append("hayypmmall扫码支付，订单号：").append(outTradeNo).toString();
//
//        // (必填) 订单总金额，单位为元，不能超过1亿元
//        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
//        String totalAmount = order.getPayment().toString();
//
//        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
//        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
//        String undiscountableAmount = "0";
//
//        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
//        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
//        String sellerId = "";
//
//        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
//        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").
//                append(totalAmount).append("元").toString();
//
//        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
//        String operatorId = "test_operator_id";
//
//        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
//        String storeId = "test_store_id";
//
//        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
//        ExtendParams extendParams = new ExtendParams();
//        extendParams.setSysServiceProviderId("2088100200300400500");
//
//        // 支付超时，定义为120分钟
//        String timeoutExpress = "120m";
//
//
//        // 商品明细列表，需填写购买商品详细信息，
//        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
//        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
//        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1000, 1);
//        // 创建好一个商品后添加至商品明细列表
//        goodsDetailList.add(goods1);
//        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
//        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
//        goodsDetailList.add(goods2);
//
//        // 创建扫码支付请求builder，设置请求参数
//        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
//                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
//                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
//                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
//                .setTimeoutExpress(timeoutExpress)
//                //                .setNotifyUrl("http://www.test-notify-url.com")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
//                .setGoodsDetailList(goodsDetailList);
//
//        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
//        switch (result.getTradeStatus()) {
//            case SUCCESS:
//                log.info("支付宝预下单成功: )");
//
//                AlipayTradePrecreateResponse response = result.getResponse();
//                dumpResponse(response);
//
//                // 需要修改为运行机器上的路径
//                String filePath = String.format("/Users/sudo/Desktop/qr-%s.png",
//                        response.getOutTradeNo());
//                log.info("filePath:" + filePath);
//                //                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
//                break;
//
//            case FAILED:
//                log.error("支付宝预下单失败!!!");
//                break;
//
//            case UNKNOWN:
//                log.error("系统异常，预下单状态未知!!!");
//                break;
//
//            default:
//                log.error("不支持的交易状态，交易返回异常!!!");
//                break;
//        }
//
//
//        return null;
//    }
//}
