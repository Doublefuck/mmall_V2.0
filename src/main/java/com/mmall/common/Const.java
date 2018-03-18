package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 常量类
 * Created by Administrator on 2018/3/7 0007.
 */
public class Const {

    public static final String CURRENT_USER = "current_user";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

    public static final String TOKEN_PREFIX = "token_";

    /**
     * redis存储数据期限
     */
    public interface RedisCacheExtime {
        int REDIS_SESSION_EXTIME = 60 * 30; // 有效期为30分钟
    }

    public interface Cart {
        int CHECKED = 1; // 商品被选中
        int UN_CHECKED = 0; //商品没有被选中

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    /**
     * 商品排序规则，按照价格升序或者降序
     */
    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }

    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1; // 管理员
    }

    /**
     * 商品状态枚举
     */
    public enum ProductStatusEnum {

        ON_SALE(1, "在线");

        private int code;
        private String value;


        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 订单状态枚举
     */
    public enum OrderStatusEnum {

        CANCLED(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已支付"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭");

        private int code;
        private String value;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    /**
     * 支付宝回调状态
     */
    public interface AlipayCallback {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY"; // 等待买家付款
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS"; // 交易成功

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    /**
     * 支付方式
     */
    public enum PayPlatFormEnum {

        ALIPAY(1, "支付宝");

        private int code;
        private String value;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        PayPlatFormEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }
    }

    public enum PaymentTypeEnum {

        ONLINE_PAY(1, "在线支付");

        private int code;
        private String value;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        PaymentTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static PaymentTypeEnum codeOf(int code) {
            for (PaymentTypeEnum paymentTypeEnum : values()) {
                if (paymentTypeEnum.getCode() == code) {
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }
}
