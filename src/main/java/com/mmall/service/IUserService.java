package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/3/7 0007.
 */
public interface IUserService {

    /**
     * 登录逻辑接口
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 用户注册接口
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 注册时实时验证邮箱和用户名是否已存在
     * @param str 传入的用户名或者邮箱
     * @param type 对应用户名或邮箱类型值
     * @return
     */
    ServerResponse<String> checkValid(String str, String type);

    /**
     * 忘记密码情况下，根据用户名获取密码提示问题
     * @param username
     * @return
     */
    ServerResponse<String> selectQuestion(String username);

    /**
     * 验证密码提示问题的答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse<String> checkAnswer(String username, String question, String answer);

    /**
     * 忘记密码中的重置密码
     * 使用本地缓存的token值进行校验
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    /**
     * 防止横向越权，旧密码必须指向当前用户，否则无论传入什么旧密码，都有可能查询到用户
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    /**
     * 更新个人信息，需要验证新的email是否已存在且不是当前用户的email
     * @param user
     * @return
     */
    ServerResponse<User> updateInfo(User user);

    /**
     * 登录状态下获取用户详细信息
     * @param userId
     * @return
     */
    ServerResponse<User> getInfo(Integer userId);

    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    ServerResponse checkAdminRole(User user);
}
