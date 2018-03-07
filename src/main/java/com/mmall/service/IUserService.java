package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/3/7 0007.
 */
@Service
public interface IUserService {

    /**
     * 登录逻辑接口
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username, String password);

}
