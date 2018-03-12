//package com.mmall.controller.portal;
//
//import com.mmall.common.Const;
//import com.mmall.common.ResponseCode;
//import com.mmall.common.ServerResponse;
//import com.mmall.pojo.User;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
///**
// * Created by Administrator on 2018/3/11 0011.
// */
//@Controller
//@RequestMapping("/order/")
//public class OrderController {
//
//    @RequestMapping()
//    @ResponseBody
//    public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request) {
//        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
//        }
//
//        // 获取上传文件的路径
//        String path = request.getSession().getServletContext().getRealPath("upload");
//
//    }
//
//}
