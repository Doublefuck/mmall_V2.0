package com.mmall.controller.common.interceptor;

import com.mmall.common.Const;
import com.mmall.common.RedisShardedPool;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * 拦截器
 * Created by Administrator on 2018/3/18 0018.
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    /**
     * 请求进入controller之前首先进入proHandle
     * 此方法中实现了对参数的解析，对用户是否登录的判断一级对用户登录信息的判断处理
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");

        // 请求中Controller中的方法名和类名
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();

        // 解析参数，主要为了调试
        StringBuffer requestParamBuffer = new StringBuffer();
        Map paramMap = request.getParameterMap();
        Iterator iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String mapKey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY; // 空字符串
            Object obj = entry.getValue(); // 会返回数组
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapKey).append("=").append(mapValue);
        }

        // 验证用户登录信息
        User user = null;
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isNotEmpty(loginToken)) {
            String userJsonStr = RedisShardedPoolUtil.getKey(loginToken);
            user = JsonUtil.string2Obj(userJsonStr, User.class);
        }
        // 用户为空或者不是管理员
        if (user == null || (user.getRole() != Const.Role.ROLE_ADMIN)) {
            response.reset(); // 重置response
            response.setCharacterEncoding("utf-8"); // 设置重置后的response的编码
            response.setContentType("application/json;charset=utf-8"); // 设置响应类型
            PrintWriter out = response.getWriter(); // 重新获取out对象

            if (user == null) {
                out.print(JsonUtil.Obj2String(ServerResponse.createByErrorMsg("拦截器拦截，用户未登录")));
            } else {
                out.print(JsonUtil.Obj2String(ServerResponse.createByErrorMsg("拦截器拦截，用户不是管理员，无权限操作")));
            }
            out.flush(); // 将out流数据清空
            out.close(); // 关闭out流
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        log.info("afterCompletion");
    }
}
