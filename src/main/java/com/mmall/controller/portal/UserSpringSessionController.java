//package com.mmall.controller.portal;
//
//import com.mmall.common.Const;
//import com.mmall.common.ResponseCode;
//import com.mmall.common.ServerResponse;
//import com.mmall.pojo.User;
//import com.mmall.service.IUserService;
//import com.mmall.util.CookieUtil;
//import com.mmall.util.JsonUtil;
//import com.mmall.util.RedisShardedPoolUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// * Created by Administrator on 2018/3/7 0007.
// */
//@Controller
//@RequestMapping("/user/springsession/")
//@Slf4j
//public class UserSpringSessionController {
//
//    @Autowired
//    private IUserService iUserService;
//
//    /**
//     * 用户登录校验
//     * @param username
//     * @param password
//     * @param session
//     * @return
//     */
//    @RequestMapping(value = "login.do", method = RequestMethod.POST)
//    @ResponseBody
//    public ServerResponse<User> login(String username, String password, HttpSession session) {
//        // 测试全局异常
//        // int i = 0; int j = 1/i;
//
//        ServerResponse<User> response = iUserService.login(username, password);
//        if (response.isSccess()) {
//            session.setAttribute(Const.CURRENT_USER, response.getData());
//
//            // 将sessionId放入到cookie中
//            // CookieUtil.writeLoginToken(res, session.getId());
//            // 放入sessionId到redis中
//            // RedisShardedPoolUtil.setKeyEx(session.getId(), JsonUtil.Obj2String(response.getData()), Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
//        }
//        return response;
//    }
//
//    /**
//     * 退出登录
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
//    @ResponseBody
//    public ServerResponse<String> logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
////        String loginToken = CookieUtil.readLoginToken(request);
////        // 删除cookie中的token
////        CookieUtil.delLoginToken(request, response);
////        // 删除redis中loginToken的用户信息
////        RedisShardedPoolUtil.delKey(loginToken);
//
//        session.removeAttribute(Const.CURRENT_USER);
//        return ServerResponse.createBySuccess();
//    }
//
//
//    /**
//     * 获取用户信息
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
//    @ResponseBody
//    public ServerResponse<User> getUserInfo(HttpServletRequest request, HttpSession session) {
////        String loginToken = CookieUtil.readLoginToken(request);
////        if (StringUtils.isEmpty(loginToken)) {
////            return ServerResponse.createByErrorMsg("用户未登录，无法获取当前用户的信息");
////        }
////        String userJsonStr = RedisShardedPoolUtil.getKey(loginToken);
////        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if (user != null) {
//            return ServerResponse.createBySuccess(user);
//        }
//        return ServerResponse.createByErrorMsg("用户未登录，无法获取当前用户的信息");
//    }
//}
